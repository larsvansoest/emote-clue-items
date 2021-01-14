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
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.toolbar.component.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.foldable.FoldablePanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementContainer;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemSlotPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemSubPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteCluePanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RequirementPanelProvider
{
	private final RequirementContainer requirementContainer;
	private final Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemParentPanelMap2;

	public RequirementPanelProvider(EmoteClueItemsPanelPalette palette)
	{
		/* Create EmoteClueItem requirement panel network. */
		this.requirementContainer = new RequirementContainer();

		// Create parent EmoteClueItem panels.
		Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemPanelMap = EmoteClueAssociations.EmoteClueItemToEmoteClues.keySet().stream()
			.collect(Collectors.toMap(
				Function.identity(),
				key -> new EmoteClueItemPanel(this.requirementContainer, palette, key.getCollectiveName())
			));

		// Create an item panel for all required items.
		Map<EmoteClueItem, EmoteClueItemSlotPanel> slotPanelMap = EmoteClueAssociations.ItemIdToEmoteClueItemSlot.values().stream()
			.collect(Collectors.toMap(
				Function.identity(),
				emoteClueItem -> new EmoteClueItemSlotPanel(emoteClueItem.getCollectiveName())
			));

		// Create nested parent requirement panels
		Map<EmoteClueItem, EmoteClueItemSubPanel> subPanelMap = new HashMap<>();
		emoteClueItemPanelMap.forEach((parent, parentPanel) -> this.setNestedPanels(subPanelMap, slotPanelMap, palette, parent, parentPanel));

		// Create EmoteClueItem-EmoteClue (*-1) sub-panels.
		Map<EmoteClue, EmoteCluePanel> emoteCluePanelMap = EmoteClue.CLUES.stream()
			.collect(Collectors.toMap(
				Function.identity(),
				emoteClue -> new EmoteCluePanel(palette, emoteClue)
			));

		// Add EmoteClue properties to EmoteClueItem requirements.
		emoteClueItemPanelMap.forEach((emoteClueItem, emoteClueItemPanel) -> {
			EmoteClue[] emoteClues = EmoteClueAssociations.EmoteClueItemToEmoteClues.get(emoteClueItem);
			List<EmoteClueDifficulty> difficulties = Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().collect(Collectors.toList());
			emoteClueItemPanel.setFilterable("difficulty", difficulties);
			emoteClueItemPanel.setFilterable("quantity", emoteClues.length);
			emoteClueItemPanel.setQuantity(emoteClues.length);
			difficulties.stream().map(EmoteClueImages::getRibbon).map(ImageIcon::new).map(JLabel::new).forEach(emoteClueItemPanel::addIcon);
			Arrays.stream(emoteClues).map(emoteCluePanelMap::get).forEach(emoteClueItemPanel::addChild);
		});

		this.requirementContainer.load(emoteClueItemPanelMap.values());
		this.emoteClueItemParentPanelMap2 = emoteClueItemPanelMap;
	}

	private void setNestedPanels(Map<EmoteClueItem, EmoteClueItemSubPanel> subPanelMap, Map<EmoteClueItem, EmoteClueItemSlotPanel> slotPanelMap, EmoteClueItemsPanelPalette palette, EmoteClueItem child, FoldablePanel updatablePanel) {
		EmoteClueItemSlotPanel childSlotPanel = slotPanelMap.get(child);
		if(childSlotPanel != null) {
			updatablePanel.addChild(childSlotPanel);
			return;
		}

		EmoteClueItemSubPanel mappedChildPanel = subPanelMap.get(child);
		EmoteClueItemSubPanel childPanel;
		if(mappedChildPanel == null) {
			childPanel = new EmoteClueItemSubPanel(palette, child.getCollectiveName());
			subPanelMap.put(child, childPanel);
		}
		else {
			childPanel = mappedChildPanel;
		}
		updatablePanel.addChild(childPanel);

		List<EmoteClueItem> successors = child.getChildren();
		if (successors != null) {
			for(EmoteClueItem successor : successors) {
				this.setNestedPanels(subPanelMap, slotPanelMap, palette, successor, childPanel);
			}
		}
	}

	public RequirementContainer getRequirementContainer() {
		return this.requirementContainer;
	}

	public EmoteClueItemPanel getEmoteClueItemPanel(EmoteClueItem emoteClueItem) {
		return this.emoteClueItemParentPanelMap2.get(emoteClueItem);
	}
}
