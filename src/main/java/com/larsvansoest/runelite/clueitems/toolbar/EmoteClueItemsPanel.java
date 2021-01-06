package com.larsvansoest.runelite.clueitems.toolbar;

import com.larsvansoest.runelite.clueitems.toolbar.component.filter.FilterButton;
import com.larsvansoest.runelite.clueitems.toolbar.component.query.SearchBarFactory;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementContainer;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel.RequirementStatus;
import com.larsvansoest.runelite.clueitems.util.ImageProvider;
import com.larsvansoest.runelite.clueitems.util.RequirementPanelProvider;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

public class EmoteClueItemsPanel extends PluginPanel
{
	private final RequirementContainer requirementContainer;
	private final IconTextField searchBar;
	private final FilterButton<RequirementStatus> requirementStatusFilterButton;
	private final FilterButton<Difficulty> difficultyFilterButton;

	public EmoteClueItemsPanel(RequirementPanelProvider panelProvider, ImageProvider imageProvider)
	{
		this.requirementContainer = new RequirementContainer(panelProvider.getEmoteClueItemPanels());

		super.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		super.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.searchBar = new SearchBarFactory(this).build();

		this.requirementStatusFilterButton = new FilterButton<>(this, null, new ImageIcon(imageProvider.getCheckSquareAll()), new Dimension(25, 20), null, 10);
		this.requirementStatusFilterButton.addOption(RequirementStatus.InComplete, new ImageIcon(imageProvider.getCheckSquareUnfinished()), null);
		this.requirementStatusFilterButton.addOption(RequirementStatus.InProgress, new ImageIcon(imageProvider.getCheckSquareInProgress()), null);
		this.requirementStatusFilterButton.addOption(RequirementStatus.Complete, new ImageIcon(imageProvider.getCheckSquareComplete()), null);

		this.difficultyFilterButton = new FilterButton<>(this, null, new ImageIcon(imageProvider.getAllRibbons()), new Dimension(25, 20),  ColorScheme.BRAND_ORANGE, 7);
		this.difficultyFilterButton.addOption(Difficulty.Beginner, new ImageIcon(imageProvider.getBeginnerRibbon()), Difficulty.Beginner.getColor());
		this.difficultyFilterButton.addOption(Difficulty.Easy, new ImageIcon(imageProvider.getEasyRibbon()), Difficulty.Easy.getColor());
		this.difficultyFilterButton.addOption(Difficulty.Medium, new ImageIcon(imageProvider.getMediumRibbon()), Difficulty.Medium.getColor());
		this.difficultyFilterButton.addOption(Difficulty.Hard, new ImageIcon(imageProvider.getHardRibbon()), Difficulty.Hard.getColor());
		this.difficultyFilterButton.addOption(Difficulty.Elite, new ImageIcon(imageProvider.getEliteRibbon()), Difficulty.Elite.getColor());
		this.difficultyFilterButton.addOption(Difficulty.Master, new ImageIcon(imageProvider.getMasterRibbon()), Difficulty.Master.getColor());

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		//super.add(materialTabGroup, c);
		//c.gridy++;
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
		super.add(this.requirementContainer, c);
	}

	public void search() {
		String name = this.searchBar.getText();
		Difficulty difficulty = this.difficultyFilterButton.getSelectedValue();
		RequirementStatus requirementStatus = this.requirementStatusFilterButton.getSelectedValue();
		this.requirementContainer.filter(requirementPanel -> {
			EmoteClueItemPanel emoteClueItemPanel = (EmoteClueItemPanel) requirementPanel;
			Boolean containsName = emoteClueItemPanel.getName().toLowerCase().contains(name.toLowerCase());
			Boolean hasDifficulty = difficulty == null || Arrays.stream(emoteClueItemPanel.getDifficulties()).anyMatch(difficulty::equals);
			Boolean hasStatus = requirementStatus == null || emoteClueItemPanel.getStatus().equals(requirementStatus);
			return containsName && hasDifficulty && hasStatus;
		});
	}

	public void setBorderColor(Color color) {
		this.requirementContainer.setBorderColor(color);
	}
}