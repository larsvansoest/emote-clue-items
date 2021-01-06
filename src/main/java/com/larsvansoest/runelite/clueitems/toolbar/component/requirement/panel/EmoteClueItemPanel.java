package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel.header.RequirementPanelHeaderText;
import com.larsvansoest.runelite.clueitems.util.ImageProvider;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.commons.lang3.ArrayUtils;

public class EmoteClueItemPanel extends RequirementPanel
{
	private final Difficulty[] difficulties;

	public EmoteClueItemPanel(ImageProvider imageProvider, EmoteClueItem itemRequirement, EmoteClue... emoteClues)
	{
		this(imageProvider, itemRequirement.getCollectiveName(null), emoteClues, Arrays.stream(emoteClues).map(EmoteClue::getDifficulty).distinct().sorted(Difficulty.Comparator.reversed()).toArray(Difficulty[]::new));
	}

	private EmoteClueItemPanel(ImageProvider imageProvider, String name, EmoteClue[] emoteClues, Difficulty[] difficulties)
	{
		super(imageProvider, name, ArrayUtils.add(
			Arrays.stream(difficulties).map(difficulty -> {
				JLabel ribbon = new JLabel();
				ribbon.setIcon(new ImageIcon(imageProvider.getRibbon(difficulty)));
				return ribbon;
			}).toArray(JLabel[]::new),
			new RequirementPanelHeaderText(new Dimension(7, 15), String.valueOf(emoteClues.length))));
		super.setStatus(RequirementStatus.InComplete);
		this.difficulties = difficulties;
	}

	public Difficulty[] getDifficulties()
	{
		return this.difficulties;
	}
}
