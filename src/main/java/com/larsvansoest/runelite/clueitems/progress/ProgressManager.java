package com.larsvansoest.runelite.clueitems.progress;

import com.google.common.reflect.AbstractInvocationHandler;
import com.larsvansoest.runelite.clueitems.data.*;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPanel;
import com.larsvansoest.runelite.clueitems.ui.components.UpdatablePanel;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.cluescrolls.clues.item.AllRequirementsCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.AnyRequirementCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;
import net.runelite.client.plugins.cluescrolls.clues.item.SingleItemRequirement;

import javax.swing.*;
import java.util.*;

public class ProgressManager
{
	private final InventoryMonitor inventoryMonitor;
	private final StashMonitor stashMonitor;
	private final HashMap<EmoteClueItem, UpdatablePanel.Status> inventoryStatusMap;
	private final HashMap<EmoteClueItem, Boolean> stashFilledStatusMap;
	private final EmoteClueItemsPanel panel;
	private final Client client;
	private final ClientThread clientThread;
	private boolean initialState;

	public ProgressManager(ConfigManager configManager, EmoteClueItemsPanel panel, Client client, ClientThread clientThread) {
		this.panel = panel;
		this.client = client;
		this.clientThread = clientThread;
		this.inventoryMonitor = new InventoryMonitor();
		this.stashMonitor = new StashMonitor("[EmoteClueItems]", "STASHUnit fill statuses", configManager);
		this.inventoryStatusMap = new HashMap<>(EmoteClueItem.values().length);
		this.stashFilledStatusMap = new HashMap<>(EmoteClueItem.values().length);
	}

	public void reset()
	{
		this.initialState = true;
		this.inventoryMonitor.reset();
		for (final EmoteClueItem emoteClueItem : EmoteClueItem.values())
		{
			this.inventoryStatusMap.put(emoteClueItem, UpdatablePanel.Status.InComplete);
			this.stashFilledStatusMap.put(emoteClueItem, false);
			this.panel.setEmoteClueItemStatus(emoteClueItem, UpdatablePanel.Status.InComplete);
			this.panel.setItemSlotStatus(emoteClueItem, 0);
		}
		for (final StashUnit stashUnit : StashUnit.values())
		{
			this.panel.turnOnSTASHFilledButton(stashUnit);
			this.panel.turnOffSTASHFilledButton(stashUnit, new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.WAITING), "Please open your bank to log STASH progress.");
		}
	}

	public void processInventoryChanges(final ItemContainerChanged event)
	{
		final int containerId = event.getContainerId();

		if (this.initialState && containerId == 95)
		{
			this.clientThread.invoke(() ->
			{
				final ItemContainer bankContainer = this.client.getItemContainer(InventoryID.BANK);
				if (bankContainer != null)
				{
					this.handleItemChanges(this.inventoryMonitor.fetchEmoteClueItemChanges(95, bankContainer.getItems()));
					this.initialState = false;
				}
			});
		}
		else
		{
			this.handleItemChanges(this.inventoryMonitor.fetchEmoteClueItemChanges(containerId, event.getItemContainer().getItems()));
		}
	}

	public void validateConfig() {
		this.stashMonitor.validate();
		for(StashUnit stashUnit : StashUnit.values()) {
			this.setStashUnitFilled(stashUnit, stashMonitor.getStashFilled(stashUnit));
		}
	}

	private void handleItemChanges(final List<Item> emoteClueItemChanges)
	{
		if (emoteClueItemChanges != null)
		{
			final ArrayList<EmoteClueItem> emoteClueItems = new ArrayList<>();
			// Set single item (sub-)requirement status
			for (final Item item : emoteClueItemChanges)
			{
				final int quantity = item.getQuantity();
				final EmoteClueItem emoteClueItem = EmoteClueAssociations.ItemIdToEmoteClueItem.get(item.getId());

				final UpdatablePanel.Status status = quantity > 0 ? UpdatablePanel.Status.Complete : UpdatablePanel.Status.InComplete;
				this.panel.setItemSlotStatus(emoteClueItem, quantity);
				this.setEmoteClueItemInventoryStatus(emoteClueItem, status);
			}
		}
	}

	public boolean getStashUnitFilled(StashUnit stashUnit) {
		return this.stashMonitor.getStashFilled(stashUnit);
	}

	public void setStashUnitFilled(StashUnit stashUnit, boolean filled) {
		this.stashMonitor.setStashFilled(stashUnit, filled);
		for(EmoteClue emoteClue : EmoteClueAssociations.STASHUnitToEmoteClues.get(stashUnit)) {
			for(EmoteClueItem emoteClueItem : EmoteClueAssociations.EmoteClueToEmoteClueItems.get(emoteClue)) {
				this.setEmoteClueItemStashFilledStatus(emoteClueItem, filled);
			}
		}
	}

	private void setEmoteClueItemStashFilledStatus(EmoteClueItem emoteClueItem, Boolean filled) {
		this.stashFilledStatusMap.put(emoteClueItem, filled);
		this.setEmoteClueItemStatus(emoteClueItem, this.updateEmoteClueItemStatus(emoteClueItem));
	}

	private void setEmoteClueItemInventoryStatus(EmoteClueItem emoteClueItem, UpdatablePanel.Status status) {
		this.inventoryStatusMap.put(emoteClueItem, status);
		this.setEmoteClueItemStatus(emoteClueItem, this.updateEmoteClueItemStatus(emoteClueItem));
	}

	private UpdatablePanel.Status updateEmoteClueItemStatus(final EmoteClueItem emoteClueItem)
	{
		UpdatablePanel.Status inventoryStatus = this.inventoryStatusMap.get(emoteClueItem);
		this.panel.setCollectionLogStatus(emoteClueItem, inventoryStatus);

		if(this.stashFilledStatusMap.get(emoteClueItem)) {
			return UpdatablePanel.Status.Complete;
		}

		return inventoryStatus;
	}

	private void setEmoteClueItemStatus(EmoteClueItem emoteClueItem, UpdatablePanel.Status status) {
		this.panel.setEmoteClueItemStatus(emoteClueItem, status);
		for(EmoteClueItem parent : emoteClueItem.getParents()) {
			this.setEmoteClueItemStatus(parent, this.getParentStatus(parent));
		}
	}

	private UpdatablePanel.Status getParentStatus(final EmoteClueItem parent)
	{
		final ItemRequirement parentRequirement = parent.getItemRequirement();
		final List<EmoteClueItem> children = parent.getChildren();
		return (parentRequirement instanceof AllRequirementsCollection) ? this.getParentAllStatus(children) : this.getParentAnyStatus(children);
	}

	private UpdatablePanel.Status getParentAnyStatus(final List<EmoteClueItem> children)
	{
		for (final EmoteClueItem child : children)
		{
			if (this.updateEmoteClueItemStatus(child) == UpdatablePanel.Status.Complete)
			{
				return UpdatablePanel.Status.Complete;
			}
		}
		return UpdatablePanel.Status.InComplete;
	}

	private UpdatablePanel.Status getParentAllStatus(final List<EmoteClueItem> children)
	{
		boolean anyMatch = false;
		boolean allMatch = true;
		for (final EmoteClueItem child : children)
		{
			if (this.updateEmoteClueItemStatus(child) == UpdatablePanel.Status.Complete)
			{
				anyMatch = true;
			}
			else
			{
				allMatch = false;
			}
		}
		return allMatch ? UpdatablePanel.Status.Complete : anyMatch ? UpdatablePanel.Status.InProgress : UpdatablePanel.Status.InComplete;
	}
}
