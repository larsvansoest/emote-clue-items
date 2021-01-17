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

package com.larsvansoest.runelite.clueitems.toolbar;

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.toolbar.component.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementContainer;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.UpdatablePanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.item.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.item.inventory.EmoteClueItemSlotPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.item.clue.EmoteCluePanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.item.inventory.EmoteClueItemSubPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.item.inventory.StashUnitLabel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import net.runelite.client.plugins.cluescrolls.clues.item.AllRequirementsCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.AnyRequirementCollection;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;

public class RequirementPanelProvider
{
	private final RequirementContainer requirementContainer;
	private final Map<EmoteClueItem, LinkedList<UpdatablePanel>> updatablePanelMap;

	public RequirementPanelProvider(EmoteClueItemsPanelPalette palette, ItemManager itemManager)
	{
		/* Create EmoteClueItem requirement panel network. */
		this.requirementContainer = new RequirementContainer();
		this.updatablePanelMap = new HashMap<>();

		// Create parent EmoteClueItem panels.
		Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemPanelMap = EmoteClueAssociations.EmoteClueItemToEmoteClues.keySet().stream()
			.collect(Collectors.toMap(
				Function.identity(),
				key -> new EmoteClueItemPanel(this.requirementContainer, palette, key.getCollectiveName())
			));

		// Create an item panel for all required items.
		Map<EmoteClueItem, EmoteClueItemSlotPanel> slotPanelMap = EmoteClueAssociations.ItemIdToEmoteClueItemSlot.entrySet().stream()
			.collect(Collectors.toMap(
				Map.Entry::getValue,
				entry -> new EmoteClueItemSlotPanel(itemManager.getImage(entry.getKey()), entry.getValue().getCollectiveName())
			));

		// Create EmoteClueItem-EmoteClue (*-1) sub-panels.
		Map<EmoteClue, EmoteCluePanel> emoteCluePanelMap = EmoteClue.CLUES.stream()
			.collect(Collectors.toMap(
				Function.identity(),
				emoteClue -> new EmoteCluePanel(palette, emoteClue)
			));

		Map<EmoteClueItem, EmoteClueItemSubPanel> subPanelMap = new HashMap<>();

		// Add EmoteClue properties to EmoteClueItem requirements.
		emoteClueItemPanelMap.forEach((emoteClueItem, emoteClueItemPanel) -> {
			EmoteClue[] emoteClues = EmoteClueAssociations.EmoteClueItemToEmoteClues.get(emoteClueItem);

			// Add item panels & info
			// TODO move stash unit icons to emote clue panels, and set status to their location names.
			EmoteClueItemSubPanel subPanel = new EmoteClueItemSubPanel(palette, "Item Log");
			this.addSubItems(slotPanelMap, subPanel, emoteClueItem);

			for(EmoteClue emoteClue : emoteClues) {
				STASHUnit stashUnit = emoteClue.getStashUnit();
				if (stashUnit != null) {
					String locationName = emoteClue.getLocationName();
					StashUnitLabel stashUnitLabel = new StashUnitLabel(locationName, locationName.charAt(0));
					subPanel.addStashUnitIcon(stashUnitLabel);
				}
			}

			subPanel.setRequiredAmount(this.getRequiredAmount(emoteClueItem));
			subPanel.addAmountLabel();
			emoteClueItemPanel.addChild(subPanel);
			subPanelMap.put(emoteClueItem, subPanel);

			// Add emote clue panels & info
			List<EmoteClueDifficulty> difficulties = Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().collect(Collectors.toList());
			emoteClueItemPanel.setFilterable("difficulty", difficulties);
			emoteClueItemPanel.setFilterable("quantity", emoteClues.length);
			emoteClueItemPanel.setQuantity(String.valueOf(emoteClues.length));
			difficulties.stream().map(EmoteClueImages::getRibbon).map(ImageIcon::new).map(JLabel::new).forEach(emoteClueItemPanel::addRightIcon);
			Arrays.stream(emoteClues).map(emoteCluePanelMap::get).forEach(emoteClueItemPanel::addChild);
		});

		this.addToRequirementPanelsMap(emoteClueItemPanelMap);
		this.addToRequirementPanelsMap(slotPanelMap);
		this.addToRequirementPanelsMap(subPanelMap);

		this.requirementContainer.load(emoteClueItemPanelMap.values());
	}

	private void addSubItems(Map<EmoteClueItem, EmoteClueItemSlotPanel> slotPanelMap, EmoteClueItemSubPanel subPanel, EmoteClueItem child) {

		EmoteClueItemSlotPanel childSlotPanel = slotPanelMap.get(child);
		if (childSlotPanel != null)
		{
			subPanel.addChild(childSlotPanel);
			return;
		}

		List<EmoteClueItem> successors = child.getChildren();
		if (successors != null)
		{
			for (EmoteClueItem successor : successors)
			{
				this.addSubItems(slotPanelMap, subPanel, successor);
			}
		}
	}

	private int getRequiredAmount(EmoteClueItem child) {
		ItemRequirement itemRequirement = child.getItemRequirement();

		if(itemRequirement instanceof AnyRequirementCollection) {
			return child.getChildren().stream().map(this::getRequiredAmount).reduce((n, m) -> n < m ? n : m).orElse(0);
		}

		if(itemRequirement instanceof AllRequirementsCollection) {
			return child.getChildren().stream().map(this::getRequiredAmount).reduce(Integer::sum).orElse(0);
		}

		return 1;
	}

	private void addToRequirementPanelsMap(Map<EmoteClueItem, ? extends UpdatablePanel> requirementPanelMap)
	{
		requirementPanelMap.forEach((emoteClueItem, updatablePanel) ->
			this.updatablePanelMap.merge(emoteClueItem, new LinkedList<UpdatablePanel>()
				{{
					this.add(updatablePanel);
				}},
				(l1, l2) -> {
					l1.addAll(l2);
					return l1;
				}
			));
	}

	public void setItemSlotStatus(EmoteClueItem emoteClueItem, int quantity) {

	}

	public void setStatus(EmoteClueItem emoteClueItem, RequirementStatus status) {

	}
/*
	public void setStatus(EmoteClueItem emoteClueItem, RequirementStatus status)
	{
		LinkedList<UpdatablePanel> panels = this.updatablePanelMap.get(emoteClueItem);
		if (panels != null)
		{
			for (UpdatablePanel panel : panels)
			{
				panel.setStatus(status);
			}
		}
	} */

	public RequirementContainer getRequirementContainer()
	{
		return this.requirementContainer;
	}
}
