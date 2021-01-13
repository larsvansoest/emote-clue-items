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

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.toolbar.component.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementContainer;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueSubItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueSubPanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.runelite.api.Item;
import org.apache.commons.lang3.ArrayUtils;

public class RequirementPanelProvider
{
	private final Map<EmoteClueItem, EmoteClueItemPanel[]> emoteClueItemPanelsMap;
	private final RequirementContainer requirementContainer;

	public RequirementPanelProvider(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette)
	{
		/* Create EmoteClueItem requirement panel network. */
		this.requirementContainer = new RequirementContainer();

		// Create parent EmoteClueItem panels.
		Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemParentPanelMap = EmoteClueAssociations.EmoteClueItemParentToEmoteClues.keySet().stream()
			.collect(Collectors.toMap(
				Function.identity(),
				key -> new EmoteClueItemPanel(this.requirementContainer, emoteClueItemsPanelPalette, key.getCollectiveName(null))
			));

		// Map related emoteClueItem requirements to panels of parents.
		this.emoteClueItemPanelsMap = emoteClueItemParentPanelMap.entrySet().stream()
			.flatMap(entry -> Arrays.stream(EmoteClueAssociations.EmoteClueItemParentToSuccessors.get(entry.getKey()))
				.map(successor -> new AbstractMap.SimpleImmutableEntry<EmoteClueItem, EmoteClueItemPanel>(successor, entry.getValue())))
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> new EmoteClueItemPanel[] { entry.getValue() },
				ArrayUtils::addAll
			));

		// Create EmoteClueItem-EmoteClue (*-1) sub-panels.
		Map<EmoteClue, EmoteClueSubPanel> emoteClueSubPanelMap = EmoteClue.CLUES.stream()
			.collect(Collectors.toMap(
				Function.identity(),
				emoteClue -> new EmoteClueSubPanel(emoteClueItemsPanelPalette, emoteClue)
			));

		// Create ItemId for all emoteclueitem parents
		Map<Integer, EmoteClueSubItemPanel> emoteClueSubItemPanelMap = EmoteClueAssociations.ItemIdToEmoteClueItemParents.keySet().stream()
			.collect(Collectors.toMap(
				Function.identity(),
				EmoteClueSubItemPanel::new
			));

		// Add EmoteClues to EmoteClueItem requirements.
		emoteClueItemParentPanelMap.forEach((emoteClueItem, emoteClueItemPanel) -> {
			EmoteClue[] emoteClues = EmoteClueAssociations.EmoteClueItemParentToEmoteClues.get(emoteClueItem);

			// Add EmoteClueItem properties
			List<EmoteClueDifficulty> difficulties = Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().collect(Collectors.toList());
			emoteClueItemPanel.setFilterable("difficulty", difficulties);
			emoteClueItemPanel.setFilterable("quantity", emoteClues.length);

			// Add EmoteClueItem icons.
			emoteClueItemPanel.setQuantity(emoteClues.length);
			difficulties.stream().map(EmoteClueImages::getRibbon).map(ImageIcon::new).map(JLabel::new).forEach(emoteClueItemPanel::addIcon);

			Arrays.stream(EmoteClueAssociations.EmoteClueItemParentToItemIds.get(emoteClueItem)).map(emoteClueSubItemPanelMap::get).forEach(emoteClueItemPanel::addChild);
			Arrays.stream(emoteClues).map(emoteClueSubPanelMap::get).forEach(emoteClueItemPanel::addChild);
		});

		this.requirementContainer.load(emoteClueItemParentPanelMap.values());
	}

	public RequirementContainer getRequirementContainer() {
		return this.requirementContainer;
	}

	public final void processPlayerBank(Item[] bankItems) {
	}

	public final void processPlayerInventory(Item[] inventoryItems) {

	}

	public final void updateRequirementPanels(Object key, RequirementStatus status)
	{
	}
}
