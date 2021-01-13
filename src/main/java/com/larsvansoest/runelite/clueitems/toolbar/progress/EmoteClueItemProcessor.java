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

import java.util.Map;
import java.util.function.BiConsumer;
import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;

public class EmoteClueItemProcessor
{
	private final RequirementPanelProvider requirementPanelProvider;
	private final Map<Integer, EmoteClueItemData> emoteClueItemDataMap;

	public EmoteClueItemProcessor(RequirementPanelProvider requirementPanelProvider)
	{
		this.emoteClueItemDataMap = null;

		//EmoteClueAssociations.ItemIdToEmoteClueItems.entrySet().stream().collect(Collectors.toMap(
		//	Map.Entry::getKey,
		//	entry -> new EmoteClueItemData(entry.getValue())
		//));
		this.requirementPanelProvider = requirementPanelProvider;
	}

	public void onItemContainerChanged(ItemContainerChanged event)
	{
		Item[] items = event.getItemContainer().getItems();
		switch (event.getContainerId())
		{
			case 95:
				this.progressItems(items, EmoteClueItemData::setBankQuantity);
				break;
			case 93:
				this.progressItems(items, EmoteClueItemData::setInventoryQuantity);
				break;
			default:
				break;
		}
	}

	private void progressItems(Item[] items, BiConsumer<EmoteClueItemData, Integer> setQuantityFunction)
	{
		for(Item item : items) {
			EmoteClueItemData emoteClueItemData = this.emoteClueItemDataMap.get(item.getId());
			if (emoteClueItemData != null)
			{
				setQuantityFunction.accept(emoteClueItemData, item.getQuantity());
				Integer bankQuantity = emoteClueItemData.getBankQuantity();
				Integer inventoryQuantity = emoteClueItemData.getInventoryQuantity();

			}
		}
	}
}
