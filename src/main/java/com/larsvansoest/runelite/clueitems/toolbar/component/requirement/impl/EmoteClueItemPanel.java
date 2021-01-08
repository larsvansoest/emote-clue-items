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

package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.header.RequirementPanelHeaderText;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.data.vendor.EmoteClue;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.runelite.api.Item;
import org.apache.commons.lang3.ArrayUtils;

public class EmoteClueItemPanel extends RequirementPanel
{
	private final EmoteClueDifficulty[] difficulties;
	private final EmoteClueItem emoteClueItem;

	public EmoteClueItemPanel(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette, EmoteClueItem emoteClueItem, EmoteClue... emoteClues)
	{
		this(emoteClueItemsPanelPalette, emoteClueItem, emoteClues, Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().sorted().toArray(EmoteClueDifficulty[]::new));
	}

	private EmoteClueItemPanel(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette, EmoteClueItem emoteClueItem, EmoteClue[] emoteClues, EmoteClueDifficulty[] difficulties)
	{
		super(emoteClueItemsPanelPalette, false, emoteClueItem.getCollectiveName(null), ArrayUtils.add(
			Arrays.stream(difficulties).map(difficulty -> {
				JLabel ribbon = new JLabel();
				ribbon.setIcon(new ImageIcon(EmoteClueImages.getRibbon(difficulty)));
				return ribbon;
			}).toArray(JLabel[]::new),
			new RequirementPanelHeaderText(new Dimension(7, 15), String.valueOf(emoteClues.length))));
		super.setStatus(RequirementStatus.InComplete);
		this.difficulties = difficulties;
		this.emoteClueItem = emoteClueItem;
	}

	public EmoteClueDifficulty[] getDifficulties()
	{
		return this.difficulties;
	}

	@Override
	public void updateBankItems(Item[] bankItems)
	{

	}

	@Override
	public void updateInventoryItems(Item[] inventoryItems)
	{

	}
}
