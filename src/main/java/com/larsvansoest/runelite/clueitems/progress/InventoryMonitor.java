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

import com.larsvansoest.runelite.clueitems.EmoteClueItemsConfig;
import com.larsvansoest.runelite.clueitems.data.EmoteClueAssociations;
import net.runelite.api.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InventoryMonitor
{
	private final HashMap<Integer, Integer> collectionLog;

	private final ItemTracker bankTracker;
	private final ItemTracker inventoryTracker;
	private final ItemTracker equipmentTracker;
	private final ItemTracker groupStorageTracker;

	private Boolean isTrackingBank;
	private Boolean isTrackingInventory;
	private Boolean isTrackingEquipment;
	private Boolean isTrackingGroupStorage;

	public InventoryMonitor(EmoteClueItemsConfig config)
	{
		this.collectionLog = new HashMap<>(EmoteClueAssociations.ItemIdToEmoteClueItem.keySet().size());

		this.bankTracker = new ItemTracker();
		this.inventoryTracker = new ItemTracker();
		this.equipmentTracker = new ItemTracker();
		this.groupStorageTracker = new ItemTracker();

		this.isTrackingBank = config.trackBank();
		this.isTrackingInventory = config.trackInventory();
		this.isTrackingEquipment = config.trackEquipment();
		this.isTrackingGroupStorage = config.trackGroupStorage();

		this.reset();
	}

	public void reset()
	{
		for (final Integer itemId : EmoteClueAssociations.ItemIdToEmoteClueItem.keySet())
		{
			this.collectionLog.put(itemId, 0);
		}
		this.inventoryTracker.reset();
		this.bankTracker.reset();
		this.equipmentTracker.reset();
		this.groupStorageTracker.reset();
	}

	public List<Item> toggleInventoryTracking(boolean track)
	{
		this.isTrackingInventory = track;
		return this.toggleItemTracker(this.inventoryTracker, track);
	}

	public List<Item> toggleBankTracking(boolean track)
	{
		this.isTrackingBank = track;
		return this.toggleItemTracker(this.bankTracker, track);
	}

	public List<Item> toggleEquipmentTracking(boolean track)
	{
		this.isTrackingEquipment = track;
		return this.toggleItemTracker(this.equipmentTracker, track);
	}

	public List<Item> toggleGroupStorageTracking(boolean track)
	{
		this.isTrackingGroupStorage = track;
		return this.toggleItemTracker(this.groupStorageTracker, track);
	}

	private List<Item> getEmoteClueItemDeltas(Map<Integer, Integer> deltas)
	{

		final List<Item> emoteClueDeltas = new ArrayList<>();
		deltas.forEach((id, delta) ->
		{
			final Integer logQuantity = this.collectionLog.get(id);
			if (logQuantity != null)
			{
				final int quantity = logQuantity + delta;
				this.collectionLog.put(id, quantity);
				emoteClueDeltas.add(new Item(id, quantity));
			}
		});
		return emoteClueDeltas;
	}

	private List<Item> toggleItemTracker(ItemTracker itemTracker, boolean track)
	{
		if (track)
		{
			return null;
		}
		Map<Integer, Integer> items = itemTracker.getItems();
		items.replaceAll((id, quantity) -> -quantity);
		itemTracker.reset();
		return this.getEmoteClueItemDeltas(items);
	}

	public List<Item> fetchEmoteClueItemChanges(final int containerId, final Item[] items)
	{
		switch (containerId)
		{
			case 93:
			case 660: // Group storage inventory
				if (this.isTrackingInventory)
				{
					return this.getEmoteClueItemDeltas(this.inventoryTracker.writeDeltas(items));
				}
				return null;
			case 94:
				if (this.isTrackingEquipment)
				{
					return this.getEmoteClueItemDeltas(this.equipmentTracker.writeDeltas(items));
				}
				return null;
			case 95:
				if (this.isTrackingBank)
				{
					return this.getEmoteClueItemDeltas(this.bankTracker.writeDeltas(items));
				}
				return null;
			case 659:
				if (this.isTrackingGroupStorage)
				{
					return this.getEmoteClueItemDeltas(this.groupStorageTracker.writeDeltas(items));
				}
				return null;
			default:
				return null;
		}
	}
}
