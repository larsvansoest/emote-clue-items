package com.larsvansoest.runelite.clueitems.toolbar.item;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.Images;
import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementStatus;
import com.larsvansoest.runelite.clueitems.toolbar.requirement.header.RequirementPanelHeaderText;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.commons.lang3.ArrayUtils;

public class EmoteClueItemPanel extends RequirementPanel
{
	private static Dimension DIMENSION_ICON_QUANTITY = new Dimension(7, 15);
	private static JLabel[] getRibbons(EmoteClue... emoteClues) {
		return Arrays.stream(emoteClues).map(EmoteClue::getDifficulty).distinct().map(difficulty ->
		{
			JLabel ribbon = new JLabel();
			ribbon.setIcon(new ImageIcon(Images.getRibbon(difficulty)));
			return ribbon;
		}).toArray(JLabel[]::new);
	}

	public EmoteClueItemPanel(EmoteClueItem itemRequirement, EmoteClue... emoteClues) {
		this(itemRequirement.getCollectiveName(null), ArrayUtils.add(EmoteClueItemPanel.getRibbons(emoteClues), new RequirementPanelHeaderText(EmoteClueItemPanel.DIMENSION_ICON_QUANTITY, String.valueOf(emoteClues.length))));
	}

	private EmoteClueItemPanel(String name, JLabel... ribbonLabels) {
		super(name, ribbonLabels);
		super.setStatus(RequirementStatus.InComplete);
	}

}
