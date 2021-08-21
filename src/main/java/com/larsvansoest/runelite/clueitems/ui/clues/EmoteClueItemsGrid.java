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

package com.larsvansoest.runelite.clueitems.ui.clues;

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.ui.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Main {@link net.runelite.client.ui.PluginPanel} of the {@link com.larsvansoest.runelite.clueitems.EmoteClueItemsPlugin}, which displays {@link com.larsvansoest.runelite.clueitems.data.EmoteClueItem} requirement status progression.
 * <p>
 * Includes search bar, {@link com.larsvansoest.runelite.clueitems.ui.components.CycleButton} buttons to filter and sort by properties, such as {@link com.larsvansoest.runelite.clueitems.ui.components.Status}, requirement name, and more.
 *
 * @author Lars van Soest
 * @since 2.0.0
 */
public class EmoteClueItemsGrid extends DataGrid<ClueItemPanel>
{
	public EmoteClueItemsGrid(final EmoteClueItemsPalette emoteClueItemsPalette, final RequirementPanelProvider requirementPanelProvider)
	{
		super(emoteClueItemsPalette);

		this.createRequirementStatusFilterButton();
		this.createDifficultyFilterButton();
		this.createSortFilterButton();

		final FooterPanel footerPanel = new FooterPanel(emoteClueItemsPalette, "Emote Clue Items", "v2.0.2", "https://github.com/larsvansoest/emote-clue-items");

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.searchBar, c);

		c.weightx = 0;
		c.gridx++;
		super.add(this.sortCycleButton, c);
		c.gridx++;
		super.add(this.difficultyCycleButton, c);
		c.gridx++;
		super.add(this.requirementStatusCycleButton, c);

		c.weightx = 1;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy++;
		super.add(this.separator, c);

		c.gridy++;
		super.add(this.disclaimerPanel, c);

		c.gridy++;
		super.add(this.dataGrid, c);

		c.gridy++;
		c.insets.top = 10;
		c.insets.left = 20;
		c.insets.right = 20;
		super.add(footerPanel, c);
	}

	private void createRequirementStatusFilterButton()
	{
		final String toolTipTextFormat = "Toggle show %s statuses.";
		super.addFilter("status", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.ALL), this.getToolTipText(toolTipTextFormat, "all"), $ -> true);
		super.addFilter(
				"status",
				new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.INCOMPLETE),
				this.getToolTipText(toolTipTextFormat, "incomplete"),
				itemPanel -> itemPanel.getStatus == Status.InComplete
		);
		requirementStatusCycleButton.addOption(Status.Complete, new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.COMPLETE), this.getToolTipText(toolTipTextFormat, "complete"));
		return requirementStatusCycleButton;
	}

	private CycleButton<EmoteClueDifficulty> createDifficultyFilterButton(final EmoteClueItemsPalette emoteClueItemsPalette)
	{
		final CycleButton<EmoteClueDifficulty> difficultyCycleButton = new CycleButton<>(null,
				new ImageIcon(EmoteClueImages.Ribbon.ALL),
				this.getToolTipText("Toggle show %s difficulties.", "all"),
				new Dimension(25, 30),
				emoteClueItemsPalette.getDefaultColor(),
				emoteClueItemsPalette.getHoverColor(),
				7,
				this::onDifficultyFilterChanged
		);
		final String toolTipTextFormat = "Toggle show %s difficulty.";
		difficultyCycleButton.addOption(EmoteClueDifficulty.Beginner, new ImageIcon(EmoteClueImages.Ribbon.BEGINNER), this.getToolTipText(toolTipTextFormat, "beginner"));
		difficultyCycleButton.addOption(EmoteClueDifficulty.Easy, new ImageIcon(EmoteClueImages.Ribbon.EASY), this.getToolTipText(toolTipTextFormat, "easy"));
		difficultyCycleButton.addOption(EmoteClueDifficulty.Medium, new ImageIcon(EmoteClueImages.Ribbon.MEDIUM), this.getToolTipText(toolTipTextFormat, "medium"));
		difficultyCycleButton.addOption(EmoteClueDifficulty.Hard, new ImageIcon(EmoteClueImages.Ribbon.HARD), this.getToolTipText(toolTipTextFormat, "hard"));
		difficultyCycleButton.addOption(EmoteClueDifficulty.Elite, new ImageIcon(EmoteClueImages.Ribbon.ELITE), this.getToolTipText(toolTipTextFormat, "elite"));
		difficultyCycleButton.addOption(EmoteClueDifficulty.Master, new ImageIcon(EmoteClueImages.Ribbon.MASTER), this.getToolTipText(toolTipTextFormat, "master"));
		return difficultyCycleButton;
	}

	private CycleButton<Map.Entry<SortType, Boolean>> createSortFilterButton(final EmoteClueItemsPalette emoteClueItemsPalette)
	{
		final CycleButton<Map.Entry<SortType, Boolean>> sortCycleButton = new CycleButton<>(new AbstractMap.SimpleImmutableEntry<>(SortType.Quantity, true),
				new ImageIcon(EmoteClueImages.Toolbar.SortType.QUANTITY_DESCENDING),
				this.getToolTipText("Toggle order by %s (descending).", "quantity"),
				new Dimension(25, 30),
				emoteClueItemsPalette.getDefaultColor(),
				emoteClueItemsPalette.getHoverColor(),
				7,
				this::onSortFilterChanged
		);
		sortCycleButton.addOption(new AbstractMap.SimpleImmutableEntry<>(SortType.Quantity, false),
				new ImageIcon(EmoteClueImages.Toolbar.SortType.QUANTITY_ASCENDING),
				this.getToolTipText("Toggle order by %s (ascending).", "quantity")
		);
		sortCycleButton.addOption(new AbstractMap.SimpleImmutableEntry<>(SortType.Name, true),
				new ImageIcon(EmoteClueImages.Toolbar.SortType.NAME_DESCENDING),
				this.getToolTipText("Toggle order by %s (descending).", "name")
		);
		sortCycleButton.addOption(new AbstractMap.SimpleImmutableEntry<>(SortType.Name, false),
				new ImageIcon(EmoteClueImages.Toolbar.SortType.NAME_ASCENDING),
				this.getToolTipText("Toggle order by %s (ascending).", "name")
		);
		return sortCycleButton;
	}

	private String getToolTipText(final String format, final String keyword)
	{
		return String.format("<html>%s</html>", String.format(format, String.format("<b>%s</b>", keyword)));
	}

	public void search()
	{
		this.dataGrid.runFilters();
	}
}