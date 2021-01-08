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
import com.larsvansoest.runelite.clueitems.data.EmoteClueImage;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.toolbar.component.footer.FooterPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.input.FilterButton;
import com.larsvansoest.runelite.clueitems.toolbar.component.input.SearchBarFactory;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementContainer;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementPanelProvider;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.impl.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

public class EmoteClueItemsPanel extends PluginPanel
{
	private final RequirementSearchData requirementSearchData;
	private final EmoteClueItemsPanelPalette emoteClueItemsPanelPalette;

	private final IconTextField searchBar;
	private final JSeparator separator;
	private final RequirementContainer requirementContainer;

	private final FilterButton<RequirementStatus> requirementStatusFilterButton;
	private final FilterButton<EmoteClueDifficulty> difficultyFilterButton;

	private final RequirementPanelProvider requirementPanelProvider;

	public EmoteClueItemsPanel(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette, RequirementPanelProvider requirementPanelProvider)
	{
		super();
		super.setLayout(new GridBagLayout());
		super.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.requirementPanelProvider = requirementPanelProvider;

		this.emoteClueItemsPanelPalette = emoteClueItemsPanelPalette;
		this.searchBar = new SearchBarFactory(this::onSearchBarTextChanged).defaultColor(emoteClueItemsPanelPalette.getDefaultColor()).hoverColor(emoteClueItemsPanelPalette.getHoverColor()).build();

		this.requirementContainer = new RequirementContainer(requirementPanelProvider.getEmoteClueItemPanels());

		this.separator = new JSeparator();
		this.setSeparatorColor(null);

		this.requirementStatusFilterButton = this.createRequirementStatusFilterButton(emoteClueItemsPanelPalette);
		this.difficultyFilterButton = this.createDifficultyFilterButton(emoteClueItemsPanelPalette);

		this.requirementSearchData = new RequirementSearchData(this.searchBar.getText(), this.difficultyFilterButton.getSelectedValue(), this.requirementStatusFilterButton.getSelectedValue());

		FooterPanel footerPanel = new FooterPanel(emoteClueItemsPanelPalette, "Emote Clue Items", "v2.0.0", "https://github.com/larsvansoest/emote-clue-items");

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.searchBar, c);

		c.weightx = 0;
		c.gridx++;
		super.add(this.difficultyFilterButton, c);
		c.gridx++;
		super.add(this.requirementStatusFilterButton, c);

		c.weightx = 1;
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 3;
		super.add(this.separator, c);

		c.gridy++;
		super.add(this.requirementContainer, c);

		c.gridy++;
		c.insets.top = 10;
		c.insets.left = 20;
		c.insets.right = 20;
		super.add(footerPanel, c);
	}

	private void onSearchBarTextChanged()
	{
		this.requirementSearchData.setRequirementName(this.searchBar.getText());
		this.search();
	}

	private void onRequirementStatusFilterChanged()
	{
		this.requirementSearchData.setRequirementStatus(this.requirementStatusFilterButton.getSelectedValue());
		this.search();
	}

	private void onDifficultyFilterChanged()
	{
		EmoteClueDifficulty emoteClueDifficulty = this.difficultyFilterButton.getSelectedValue();
		this.requirementSearchData.setRequirementDifficulty(emoteClueDifficulty);
		this.setSeparatorColor(emoteClueDifficulty);
		this.search();
	}

	private FilterButton<RequirementStatus> createRequirementStatusFilterButton(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette)
	{
		FilterButton<RequirementStatus> requirementStatusFilterButton = new FilterButton<>(null, new ImageIcon(EmoteClueImage.Toolbar.CheckSquare.ALL), this.getToolTipText("Toggle show %s statuses.", "all"), new Dimension(25, 30), emoteClueItemsPanelPalette.getDefaultColor(), emoteClueItemsPanelPalette.getHoverColor(), 10, this::onRequirementStatusFilterChanged);
		String toolTipTextFormat = "Toggle show %s status.";
		requirementStatusFilterButton.addOption(RequirementStatus.InComplete, new ImageIcon(EmoteClueImage.Toolbar.CheckSquare.INCOMPLETE), this.getToolTipText(toolTipTextFormat, "incomplete"));
		requirementStatusFilterButton.addOption(RequirementStatus.InProgress, new ImageIcon(EmoteClueImage.Toolbar.CheckSquare.IN_PROGRESS), this.getToolTipText(toolTipTextFormat, "in progress"));
		requirementStatusFilterButton.addOption(RequirementStatus.Complete, new ImageIcon(EmoteClueImage.Toolbar.CheckSquare.COMPLETE), this.getToolTipText(toolTipTextFormat, "complete"));
		return requirementStatusFilterButton;
	}

	private FilterButton<EmoteClueDifficulty> createDifficultyFilterButton(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette)
	{
		FilterButton<EmoteClueDifficulty> difficultyFilterButton = new FilterButton<>(null, new ImageIcon(EmoteClueImage.Ribbon.ALL), this.getToolTipText("Toggle show %s difficulties.", "all"), new Dimension(25, 30), emoteClueItemsPanelPalette.getDefaultColor(), emoteClueItemsPanelPalette.getHoverColor(), 7, this::onDifficultyFilterChanged);
		String toolTipTextFormat = "Toggle show %s difficulty.";
		difficultyFilterButton.addOption(EmoteClueDifficulty.Beginner, new ImageIcon(EmoteClueImage.Ribbon.BEGINNER), this.getToolTipText(toolTipTextFormat, "beginner"));
		difficultyFilterButton.addOption(EmoteClueDifficulty.Easy, new ImageIcon(EmoteClueImage.Ribbon.EASY), this.getToolTipText(toolTipTextFormat, "easy"));
		difficultyFilterButton.addOption(EmoteClueDifficulty.Medium, new ImageIcon(EmoteClueImage.Ribbon.MEDIUM), this.getToolTipText(toolTipTextFormat, "medium"));
		difficultyFilterButton.addOption(EmoteClueDifficulty.Hard, new ImageIcon(EmoteClueImage.Ribbon.HARD), this.getToolTipText(toolTipTextFormat, "hard"));
		difficultyFilterButton.addOption(EmoteClueDifficulty.Elite, new ImageIcon(EmoteClueImage.Ribbon.ELITE), this.getToolTipText(toolTipTextFormat, "elite"));
		difficultyFilterButton.addOption(EmoteClueDifficulty.Master, new ImageIcon(EmoteClueImage.Ribbon.MASTER), this.getToolTipText(toolTipTextFormat, "master"));
		return difficultyFilterButton;
	}

	private String getToolTipText(String format, String keyword)
	{
		return String.format("<html>%s</html>", String.format(format, String.format("<b>%s</b>", keyword)));
	}

	private void setSeparatorColor(EmoteClueDifficulty emoteClueDifficulty)
	{
		this.separator.setBorder(new MatteBorder(1, 0, 0, 0, emoteClueDifficulty == null ? this.emoteClueItemsPanelPalette.getSeparatorColor() : emoteClueDifficulty.getColor()));
	}

	public void search()
	{
		String name = this.requirementSearchData.getRequirementName();
		EmoteClueDifficulty emoteClueDifficulty = this.requirementSearchData.getRequirementDifficulty();
		RequirementStatus requirementStatus = this.requirementSearchData.getRequirementStatus();

		this.requirementContainer.filter(requirementPanel -> {
			EmoteClueItemPanel emoteClueItemPanel = (EmoteClueItemPanel) requirementPanel;
			Boolean containsName = emoteClueItemPanel.getName().toLowerCase().contains(name.toLowerCase());
			Boolean hasDifficulty = emoteClueDifficulty == null || Arrays.asList(emoteClueItemPanel.getDifficulties()).contains(emoteClueDifficulty);
			Boolean hasStatus = requirementStatus == null || emoteClueItemPanel.getStatus().equals(requirementStatus);
			return containsName && hasDifficulty && hasStatus;
		});
	}
}