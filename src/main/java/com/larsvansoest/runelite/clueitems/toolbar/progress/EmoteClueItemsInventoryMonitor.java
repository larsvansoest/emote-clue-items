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

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueAssociations;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.runelite.api.Item;
import net.runelite.client.plugins.cluescrolls.clues.item.AllRequirementsCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;

public class EmoteClueItemsInventoryMonitor
{
	private final Map<EmoteClueItem, RequirementStatus> requirementStatusMap;

	public EmoteClueItemsInventoryMonitor()
	{
		this.requirementStatusMap = Arrays.stream(EmoteClueItem.values()).collect(Collectors.toMap(Function.identity(), $ -> RequirementStatus.Unknown));
	}

	public Map<EmoteClueItem, RequirementStatus> getRequirementStatusMap()
	{
		return this.requirementStatusMap;
	}

	public void processItems(Item[] items)
	{
		this.requirementStatusMap.forEach(((emoteClueItem, $) -> this.requirementStatusMap.put(emoteClueItem, RequirementStatus.InComplete)));
		List<EmoteClueItem> emoteClueItems = Arrays.stream(items)
			.map(Item::getId)
			.map(EmoteClueAssociations.ItemIdToEmoteClueItemSlot::get)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
		emoteClueItems.forEach(emoteClueItem -> this.requirementStatusMap.put(emoteClueItem, RequirementStatus.Complete));
		this.processRequirements(emoteClueItems);
	}

	private void processRequirements(List<EmoteClueItem> emoteClueItems)
	{
		while (!emoteClueItems.isEmpty())
		{
			Stream<EmoteClueItem> completedParents = emoteClueItems.stream().map(EmoteClueItem::getParents).flatMap(List::stream)
				.filter(parent -> {
						RequirementStatus parentStatus = this.getParentStatus(parent);
						this.requirementStatusMap.put(parent, parentStatus);
						return parentStatus == RequirementStatus.Complete;
					}
				);
			emoteClueItems = completedParents.collect(Collectors.toList());
		}
	}

	private RequirementStatus getParentStatus(EmoteClueItem parent)
	{
		ItemRequirement parentRequirement = parent.getItemRequirement();
		List<EmoteClueItem> children = parent.getChildren();
		return (parentRequirement instanceof AllRequirementsCollection) ? this.getParentAllStatus(children) : RequirementStatus.Complete;
	}

	private RequirementStatus getParentAllStatus(List<EmoteClueItem> children)
	{
		boolean anyMatch = false;
		boolean allMatch = true;
		for (EmoteClueItem child : children)
		{
			if (this.requirementStatusMap.get(child) == RequirementStatus.Complete)
			{
				anyMatch = true;
			}
			else
			{
				allMatch = false;
			}
		}
		return allMatch ? RequirementStatus.Complete : anyMatch ? RequirementStatus.InProgress : RequirementStatus.InComplete;
	}
}
