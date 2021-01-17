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

package com.larsvansoest.runelite.clueitems.toolbar.progress;

import com.larsvansoest.runelite.clueitems.data.util.EmoteClueAssociations;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;

class EmoteClueItemsMonitor
{
	private final EmoteClueItemsTracker inventoryTracker;
	private final EmoteClueItemsTracker bankTracker;
	private final HashMap<Integer, Integer> collectionLog;

	public EmoteClueItemsMonitor()
	{
		this.inventoryTracker = new EmoteClueItemsTracker(26);
		this.bankTracker = new EmoteClueItemsTracker(816);

		Set<Integer> itemIds = EmoteClueAssociations.ItemIdToEmoteClueItemSlot.keySet();
		this.collectionLog = new HashMap<>(itemIds.size());
		for (Integer itemId : itemIds)
		{
			this.collectionLog.put(itemId, 0);
		}
	}

	public List<Item> fetchEmoteClueItemChanges(ItemContainerChanged event)
	{
		int containerId = event.getContainerId();

		List<Item> deltas;
		if (containerId == 93)
		{
			deltas = this.inventoryTracker.writeDeltas(event.getItemContainer().getItems());
		}
		else if (containerId == 95)
		{
			deltas = this.bankTracker.writeDeltas(event.getItemContainer().getItems());
		}
		else
		{
			return null;
		}

		List<Item> emoteClueDeltas = new LinkedList<>();
		for (Item delta : deltas)
		{
			int id = delta.getId();
			Integer quantity = this.collectionLog.get(id);
			if (quantity != null)
			{
				emoteClueDeltas.add(new Item(id, quantity + delta.getQuantity()));
			}
		}
		return emoteClueDeltas;
	}
}
