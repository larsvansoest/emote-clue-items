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

package com.larsvansoest.runelite.clueitems.toolbar.component.requirement;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.header.RequirementPanelHeaderTextLabel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueSubPanel;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.runelite.client.plugins.cluescrolls.clues.item.SlotLimitationRequirement;

public class RequirementPanelProvider
{
	private final Map<Object, List<? extends UpdatablePanel>> requirementPanelsMap;
	private final RequirementContainer requirementContainer;

	public RequirementPanelProvider(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette)
	{
		/* Create EmoteClueItem requirement panel network. */
		this.requirementContainer = new RequirementContainer();

		// Create parent EmoteClueItem panels.
		Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemPanelMap = EmoteClue.CLUES.stream()
			.map(EmoteClue::getItemRequirements).flatMap(Stream::of)
			.filter(itemRequirement -> !(itemRequirement instanceof SlotLimitationRequirement))
			.map(itemRequirement -> (EmoteClueItem) itemRequirement)
			.distinct()
			.collect(Collectors.toMap(
				Function.identity(),
				key -> new EmoteClueItemPanel(this.requirementContainer, emoteClueItemsPanelPalette, key.getCollectiveName(null))
			));

		// Create EmoteClueItem-EmoteClue (*-1) sub-panels.
		Map<EmoteClue, EmoteClueSubPanel> emoteClueSubPanelMap = EmoteClue.CLUES.stream()
			.collect(Collectors.toMap(
				Function.identity(),
				EmoteClueSubPanel::new
			));

		// Add EmoteClues to EmoteClueItem requirements.
		emoteClueItemPanelMap.forEach((emoteClueItem, emoteClueItemPanel) -> {
			EmoteClue[] emoteClues = EmoteClueAssociations.EmoteClueItemToEmoteClues.get(emoteClueItem);

			// Add EmoteClueItem icons.
			emoteClueItemPanel.addIcon(new RequirementPanelHeaderTextLabel(new Dimension(7, 15), String.valueOf(emoteClues.length)));
			Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().sorted().map(EmoteClueImages::getRibbon).map(ImageIcon::new).map(JLabel::new).forEach(emoteClueItemPanel::addIcon);

			Arrays.stream(emoteClues).map(emoteClueSubPanelMap::get).forEach(emoteClueItemPanel::addChild);
		});

		this.requirementPanelsMap = new HashMap<>();
		this.addToRequirementPanelsMap(emoteClueItemPanelMap);
		this.addToRequirementPanelsMap(emoteClueSubPanelMap);

		this.requirementContainer.load(emoteClueItemPanelMap.values());
	}

	public RequirementContainer getRequirementContainer() {
		return this.requirementContainer;
	}

	private <T extends UpdatablePanel> void addToRequirementPanelsMap(Map<?, T> requirementPanelMap)
	{
		this.requirementPanelsMap.putAll(requirementPanelMap.entrySet().stream().collect(Collectors.toMap(
			Map.Entry::getKey,
			entry -> new ArrayList<T>()
			{{
				this.add(entry.getValue());
			}},
			(l1, l2) -> {
				l1.addAll(l2);
				return l1;
			}
		)));
	}

	public final void updateRequirementPanels(Object key, RequirementStatus status)
	{
		this.requirementPanelsMap.get(key).forEach(requirementPanel -> requirementPanel.setStatus(status));
	}
}
