package com.larsvansoest.runelite.clueitems.progress;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsConfig;
import com.larsvansoest.runelite.clueitems.data.EmoteClue;
import com.larsvansoest.runelite.clueitems.data.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import com.larsvansoest.runelite.clueitems.ui.components.UpdatablePanel;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.vars.AccountType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.cluescrolls.clues.item.AllRequirementsCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.AnyRequirementCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Keeps track of item requirement progression. Contains inventory change and STASHUnit fill status functionality.
 *
 * @author Lars van Soest
 * @since 2.0.0
 */
public class ProgressManager
{
	private final Client client;
	private final ClientThread clientThread;
	private final EmoteClueItemsConfig config;
	private final InventoryMonitor inventoryMonitor;
	private final StashMonitor stashMonitor;
	private final HashMap<EmoteClueItem, UpdatablePanel.Status> inventoryStatusMap;
	private final Map<EmoteClueItem, Map<StashUnit, Boolean>> stashFilledStatusMap;
	private final BiConsumer<EmoteClueItem, Integer> onEmoteClueItemQuantityChanged;
	private final BiConsumer<EmoteClueItem, UpdatablePanel.Status> onEmoteClueItemInventoryStatusChanged;
	private final BiConsumer<EmoteClueItem, UpdatablePanel.Status> onEmoteClueItemStatusChanged;

	public ProgressManager(
			final Client client, final ClientThread clientThread, final ConfigManager configManager, final EmoteClueItemsConfig config, final ItemManager itemManager,
			final BiConsumer<EmoteClueItem, Integer> onEmoteClueItemQuantityChanged, final BiConsumer<EmoteClueItem, UpdatablePanel.Status> onEmoteClueItemInventoryStatusChanged,
			final BiConsumer<EmoteClueItem, UpdatablePanel.Status> onEmoteClueItemStatusChanged)
	{
		this.client = client;
		this.clientThread = clientThread;
		this.config = config;
		this.inventoryMonitor = new InventoryMonitor(config, itemManager);
		this.stashMonitor = new StashMonitor("[EmoteClueItems]", "STASHUnit fill statuses", configManager);
		this.inventoryStatusMap = new HashMap<>(EmoteClueItem.values().length);
		this.stashFilledStatusMap = new HashMap<>(EmoteClueAssociations.EmoteClueItemToEmoteClues.keySet().size());
		this.onEmoteClueItemQuantityChanged = onEmoteClueItemQuantityChanged;
		this.onEmoteClueItemInventoryStatusChanged = onEmoteClueItemInventoryStatusChanged;
		this.onEmoteClueItemStatusChanged = onEmoteClueItemStatusChanged;

		EmoteClueAssociations.EmoteClueItemToEmoteClues.forEach(((emoteClueItem, emoteClues) ->
		{
			final Map<StashUnit, Boolean> emoteClueStashFillStatusMap = new HashMap<>(emoteClues.length);
			for (final EmoteClue emoteClue : emoteClues)
			{
				emoteClueStashFillStatusMap.put(emoteClue.getStashUnit(), false);
			}
			this.stashFilledStatusMap.put(emoteClueItem, emoteClueStashFillStatusMap);
		}));

		this.reset();
	}

	/**
	 * Clears all cached progression data.
	 */
	public void reset()
	{
		this.inventoryMonitor.reset();
		for (final EmoteClueItem emoteClueItem : EmoteClueItem.values())
		{
			this.inventoryStatusMap.put(emoteClueItem, UpdatablePanel.Status.InComplete);
		}
		for (final EmoteClueItem emoteClueItem : EmoteClueAssociations.EmoteClueItemToEmoteClues.keySet())
		{
			final Map<StashUnit, Boolean> emoteClueStashFillStatusMap = this.stashFilledStatusMap.get(emoteClueItem);
			emoteClueStashFillStatusMap.keySet().forEach(key -> emoteClueStashFillStatusMap.put(key, false));
		}
	}

	/**
	 * Writes item container changes to progression data.
	 *
	 * @param containerId The modified item container
	 * @param items       The items in the container
	 */
	public void processInventoryChanges(final int containerId, final Item[] items)
	{
		this.handleItemChanges(this.inventoryMonitor.fetchEmoteClueItemChanges(containerId, items));
	}

	/**
	 * Toggles including items from the bank in the collection log.
	 * <p>
	 * Must be called on the clientThread.
	 *
	 * @param track True if the bank should be tracked, false otherwise.
	 * @see net.runelite.client.callback.ClientThread
	 */
	public void toggleBankTracking(final boolean track)
	{
		this.handleItemChanges(this.inventoryMonitor.toggleBankTracking(track));
		if (track)
		{
			refreshContainer(InventoryID.BANK);
		}
	}

	/**
	 * Toggles including items from the inventory in the collection log.
	 * <p>
	 * Must be called on the clientThread.
	 *
	 * @param track True if the inventory should be tracked, false otherwise.
	 * @see net.runelite.client.callback.ClientThread
	 */
	public void toggleInventoryTracking(final boolean track)
	{
		this.handleItemChanges(this.inventoryMonitor.toggleInventoryTracking(track));
		if (track)
		{
			refreshContainer(InventoryID.INVENTORY);
		}
	}

	/**
	 * Toggles including equipped items in the collection log.
	 * <p>
	 * Must be called on the clientThread.
	 *
	 * @param track True if equipment should be tracked, false otherwise.
	 * @see net.runelite.client.callback.ClientThread
	 */
	public void toggleEquipmentTracking(final boolean track)
	{
		this.handleItemChanges(this.inventoryMonitor.toggleEquipmentTracking(track));
		if (track)
		{
			refreshContainer(InventoryID.EQUIPMENT);
		}
	}

	/**
	 * Toggles including items from the group storage in the collection log.
	 * <p>
	 * Must be called on the clientThread.
	 *
	 * @param track True if the group storage should be tracked, false otherwise.
	 * @see net.runelite.client.callback.ClientThread
	 */
	public void toggleGroupStorageTracking(boolean track)
	{
		this.handleItemChanges(this.inventoryMonitor.toggleGroupStorageTracking(track));
		if (track)
		{
			refreshContainer(InventoryID.GROUP_STORAGE);
		}
	}

	private void refreshContainer(InventoryID inventoryID)
	{
		ItemContainer container = client.getItemContainer(inventoryID);
		if (container != null)
		{
			this.processInventoryChanges(container.getId(), container.getItems());
		}
	}

	/**
	 * Returns a list of unopened interfaces.
	 * <p>
	 * Only shows interfaces that are tracked and have not yet been seen once after reset.
	 *
	 * @return The list of unopened interfaces.
	 */
	public List<String> getUnopenedInterfaces()
	{
		final List<String> unopenedContainers = new ArrayList<>(4);
		if (this.config.trackBank() && !this.inventoryMonitor.getHasSeenBank())
		{
			unopenedContainers.add("bank");
		}
		if (this.config.trackInventory() && !this.inventoryMonitor.getHasSeenInventory())
		{
			unopenedContainers.add("inventory");
		}
		if (this.config.trackEquipment() && !this.inventoryMonitor.getHasSeenEquipment())
		{
			unopenedContainers.add("equipment");
		}
		final AccountType accountType = this.client.getAccountType();
		if ((accountType == AccountType.GROUP_IRONMAN || accountType == AccountType.HARDCORE_GROUP_IRONMAN) && this.config.trackGroupStorage() && !this.inventoryMonitor.getHasSeenGroupStorage())
		{
			unopenedContainers.add("group storage");
		}
		return unopenedContainers;
	}

	/**
	 * Call this function after user login.
	 * <p>
	 * Using {@link com.larsvansoest.runelite.clueitems.progress.StashMonitor}, verifies if user-data is consistent with data stored in Runelite's {@link net.runelite.client.config.ConfigManager}.
	 * <p>
	 * Resets config data if data is corrupted, possible by game update.
	 */
	public void validateConfig()
	{
		this.stashMonitor.validate();
		for (final StashUnit stashUnit : StashUnit.values())
		{
			this.setStashUnitFilled(stashUnit, this.stashMonitor.getStashFilled(stashUnit));
		}
	}

	private void handleItemChanges(final List<Item> emoteClueItemChanges)
	{
		if (emoteClueItemChanges != null)
		{
			// Set single item (sub-)requirement status
			for (final Item item : emoteClueItemChanges)
			{
				final int quantity = item.getQuantity();
				final EmoteClueItem emoteClueItem = EmoteClueAssociations.ItemIdToEmoteClueItem.get(item.getId());

				this.onEmoteClueItemQuantityChanged.accept(emoteClueItem, quantity);

				final UpdatablePanel.Status status = quantity > 0 ? UpdatablePanel.Status.Complete : UpdatablePanel.Status.InComplete;
				this.inventoryStatusMap.put(emoteClueItem, status);

				this.setEmoteClueItemStatus(emoteClueItem, this.getEmoteClueItemStatus(emoteClueItem));
			}
		}
	}

	/**
	 * Returns whether given {@link com.larsvansoest.runelite.clueitems.data.StashUnit} is set as filled in progression data.
	 */
	public boolean getStashUnitFilled(final StashUnit stashUnit)
	{
		return this.stashMonitor.getStashFilled(stashUnit);
	}

	/**
	 * Specify given {@link com.larsvansoest.runelite.clueitems.data.StashUnit} as filled in progression data.
	 */
	public void setStashUnitFilled(final StashUnit stashUnit, final boolean filled)
	{
		this.stashMonitor.setStashFilled(stashUnit, filled);
		for (final EmoteClue emoteClue : EmoteClueAssociations.STASHUnitToEmoteClues.get(stashUnit))
		{
			for (final EmoteClueItem emoteClueItem : EmoteClueAssociations.EmoteClueToEmoteClueItems.get(emoteClue))
			{
				this.stashFilledStatusMap.get(emoteClueItem).put(stashUnit, filled);
				this.setEmoteClueItemStatus(emoteClueItem, this.getEmoteClueItemStatus(emoteClueItem));
			}
		}
	}

	private UpdatablePanel.Status getEmoteClueItemStatus(final EmoteClueItem emoteClueItem)
	{
		// Check inventory data.
		UpdatablePanel.Status intermediateStatus = this.inventoryStatusMap.get(emoteClueItem);
		this.onEmoteClueItemInventoryStatusChanged.accept(emoteClueItem, intermediateStatus);
		if (intermediateStatus == UpdatablePanel.Status.Complete)
		{
			return UpdatablePanel.Status.Complete;
		}

		// Check STASHUnit fill status.
		final Map<StashUnit, Boolean> emoteClueStashFilledMap = this.stashFilledStatusMap.get(emoteClueItem);
		if (Objects.nonNull(emoteClueStashFilledMap))
		{
			if (this.stashFilledStatusMap.get(emoteClueItem).values().stream().allMatch(Boolean::booleanValue))
			{
				return UpdatablePanel.Status.Complete;
			}
			if (this.stashFilledStatusMap.get(emoteClueItem).values().stream().anyMatch(Boolean::booleanValue))
			{
				intermediateStatus = UpdatablePanel.Status.InProgress;
			}
		}

		// Check item requirement relations.
		final ItemRequirement itemRequirement = emoteClueItem.getItemRequirement();
		if (itemRequirement instanceof AnyRequirementCollection)
		{
			for (final EmoteClueItem child : emoteClueItem.getChildren())
			{
				if (this.getEmoteClueItemStatus(child) == UpdatablePanel.Status.Complete)
				{
					return UpdatablePanel.Status.Complete;
				}
			}
		}
		if (itemRequirement instanceof AllRequirementsCollection)
		{
			boolean anyMatch = false;
			boolean allMatch = true;
			for (final EmoteClueItem child : emoteClueItem.getChildren())
			{
				if (this.getEmoteClueItemStatus(child) == UpdatablePanel.Status.Complete)
				{
					anyMatch = true;
				}
				else
				{
					allMatch = false;
				}
			}
			if (allMatch)
			{
				return UpdatablePanel.Status.Complete;
			}
			if (anyMatch)
			{
				intermediateStatus = UpdatablePanel.Status.InProgress;
			}
		}
		return intermediateStatus;
	}

	private void setEmoteClueItemStatus(final EmoteClueItem emoteClueItem, final UpdatablePanel.Status status)
	{
		this.onEmoteClueItemStatusChanged.accept(emoteClueItem, status);
		for (final EmoteClueItem parent : emoteClueItem.getParents())
		{
			this.setEmoteClueItemStatus(parent, this.getEmoteClueItemStatus(parent));
		}
	}
}