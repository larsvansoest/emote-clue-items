/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Lars van Soest
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.larsvansoest.runelite.clueitems.progress;

import com.larsvansoest.runelite.clueitems.clues.ClueItem;
import com.larsvansoest.runelite.clueitems.clues.Associations;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPanel;
import com.larsvansoest.runelite.clueitems.ui.content.requirement.RequirementPanelProvider;
import com.larsvansoest.runelite.clueitems.ui.content.requirement.Status;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.runelite.api.*;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.plugins.cluescrolls.clues.item.AllRequirementsCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;

/**
 * Monitors player owned items and subsequent changes to {@link ItemContainer} objects for Inventory, Bank and Equipment.
 * <p>
 * Uses {@link RequirementPanelProvider} to represent requirement statuses on {@link EmoteClueItemsPanel} accordingly.
 *
 * @author Lars van Soest
 * @see ClueItem
 * @since 2.0.0
 */
public class ProgressManager
{
	private final Map<ClueItem, Status> statusMap;
	private final ItemMonitor itemsMonitor;
	private final RequirementPanelProvider panelProvider;
	private final Client client;
	private final ClientThread clientThread;
	private boolean initialState;

	public ProgressManager(RequirementPanelProvider panelProvider, Client client, ClientThread clientThread)
	{
		this.statusMap = new HashMap<>(ClueItem.values().length);
		this.itemsMonitor = new ItemMonitor();
		this.panelProvider = panelProvider;
		this.client = client;
		this.clientThread = clientThread;
		this.reset();
	}

	public void reset()
	{
		for (ClueItem clueItem : ClueItem.values())
		{
			this.statusMap.put(clueItem, Status.InComplete);
			this.panelProvider.setEmoteClueItemStatus(clueItem, Status.InComplete);
			this.panelProvider.setItemSlotStatus(clueItem, 0);
			this.itemsMonitor.reset();
			this.initialState = true;
		}
	}

	public void handleEmoteClueItemChanges(ItemContainerChanged event)
	{
		int containerId = event.getContainerId();

		if (this.initialState && containerId == 95)
		{
			this.clientThread.invoke(() -> {
				ItemContainer bankContainer = this.client.getItemContainer(InventoryID.BANK);
				if (bankContainer != null)
				{
					this.handleChanges(this.itemsMonitor.fetchEmoteClueItemChanges(95, bankContainer.getItems()));
					this.initialState = false;
				}
			});
		}
		else
		{
			this.handleChanges(this.itemsMonitor.fetchEmoteClueItemChanges(containerId, event.getItemContainer().getItems()));
		}
	}

	private void handleChanges(List<Item> emoteClueItemChanges)
	{
		if (emoteClueItemChanges != null)
		{
			LinkedList<Map.Entry<ClueItem, Status>> parents = new LinkedList<>();

			// Set single item (sub-)requirement status
			for (Item item : emoteClueItemChanges)
			{
				int quantity = item.getQuantity();
				ClueItem clueItem = Associations.ItemIdToEmoteClueItemSlot.get(item.getId());

				Status status = quantity > 0 ? Status.Complete : Status.InComplete;
				this.statusMap.put(clueItem, status);
				parents.add(new AbstractMap.SimpleEntry<>(clueItem, status));

				this.panelProvider.setItemSlotStatus(clueItem, quantity);
			}

			LinkedList<Map.Entry<ClueItem, Status>> parentCache = new LinkedList<>();
			// Update requirement ancestors accordingly
			while (parents.size() > 0)
			{
				while (parents.size() > 0)
				{
					parentCache.add(parents.poll());
				}
				while (parentCache.size() > 0)
				{
					Map.Entry<ClueItem, Status> childEntry = parentCache.poll();
					ClueItem child = childEntry.getKey();
					Status status = childEntry.getValue();
					this.panelProvider.setEmoteClueItemStatus(child, status);
					for (ClueItem parent : child.getParents())
					{
						Status parentStatus = this.getParentStatus(parent);
						parents.add(new AbstractMap.SimpleEntry<>(parent, parentStatus));
						this.statusMap.put(parent, parentStatus);
					}
				}
			}
		}
	}

	private Status getParentStatus(ClueItem parent)
	{
		ItemRequirement parentRequirement = parent.getItemRequirement();
		List<ClueItem> children = parent.getChildren();
		return (parentRequirement instanceof AllRequirementsCollection) ? this.getParentAllStatus(children) : this.getParentAnyStatus(children);
	}

	private Status getParentAnyStatus(List<ClueItem> children)
	{
		for (ClueItem child : children)
		{
			if (this.statusMap.get(child) == Status.Complete)
			{
				return Status.Complete;
			}
		}
		return Status.InComplete;
	}

	private Status getParentAllStatus(List<ClueItem> children)
	{
		boolean anyMatch = false;
		boolean allMatch = true;
		for (ClueItem child : children)
		{
			if (this.statusMap.get(child) == Status.Complete)
			{
				anyMatch = true;
			}
			else
			{
				allMatch = false;
			}
		}
		return allMatch ? Status.Complete : anyMatch ? Status.InProgress : Status.InComplete;
	}
}
