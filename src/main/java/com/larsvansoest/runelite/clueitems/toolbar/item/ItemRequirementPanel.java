package com.larsvansoest.runelite.clueitems.toolbar.item;

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

public class ItemRequirementPanel extends RequirementPanel
{
	private static Dimension DIMENSION_ICON_QUANTITY = new Dimension(7, 15);

	private final JLabel quantityLabel;
	private final DetailsPanel detailsPanel;

	public static JLabel[] getRibbons(EmoteClue... emoteClues) {
		return Arrays.stream(emoteClues).map(EmoteClue::getDifficulty).distinct().map(difficulty ->
		{
			JLabel ribbon = new JLabel();
			ribbon.setIcon(new ImageIcon(Images.getRibbon(difficulty)));
			return ribbon;
		}).toArray(JLabel[]::new);
	}

	public ItemRequirementPanel(String name, EmoteClue... emoteClues) {
		this(name, new DetailsPanel(), new RequirementPanelHeaderText(ItemRequirementPanel.DIMENSION_ICON_QUANTITY, String.valueOf(emoteClues.length)), getRibbons(emoteClues));
	}

	private ItemRequirementPanel(String name, DetailsPanel detailsPanel, JLabel quantityLabel, JLabel... ribbonLabels) {
		super(name, new DetailsPanel(), ArrayUtils.add(ribbonLabels, quantityLabel));
		super.setStatus(RequirementStatus.InComplete); // TODO: refactor
		this.quantityLabel = quantityLabel;
		this.detailsPanel = detailsPanel;
	}
}
