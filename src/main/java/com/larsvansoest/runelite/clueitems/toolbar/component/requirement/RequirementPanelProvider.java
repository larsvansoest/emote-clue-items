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
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.util.EmoteClues;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class RequirementPanelProvider
{
	private final Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemPanelMap;
	//private final Map<STASHUnit, StashUnitPanel> stashUnitPanelMap;
	//private final Map<EmoteClue, EmoteCluePanel> emoteCluePanelMap;

	public RequirementPanelProvider(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette) {
		this.emoteClueItemPanelMap = EmoteClues.Associations.EMOTECLUEITEM.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new EmoteClueItemPanel(emoteClueItemsPanelPalette, entry.getKey(), entry.getValue())));
		//this.stashUnitPanelMap = EmoteClues.Associations.STASHUNIT.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new StashUnitPanel(entry.getKey(), entry.getValue())));
		//this.emoteCluePanelMap = EmoteClue.CLUES.stream().collect(Collectors.toMap(Function.identity(), EmoteCluePanel::new));
	}

	public final EmoteClueItemPanel getEmoteClueItemPanel(EmoteClueItem emoteClueItem) {
		return this.emoteClueItemPanelMap.get(emoteClueItem);
	}

	public final Collection<EmoteClueItemPanel> getEmoteClueItemPanels() {
		return this.emoteClueItemPanelMap.values();
	}
}
