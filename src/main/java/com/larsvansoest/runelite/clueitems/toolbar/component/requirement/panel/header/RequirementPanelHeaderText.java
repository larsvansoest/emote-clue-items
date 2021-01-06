package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel.header;

import java.awt.Dimension;
import javax.swing.JLabel;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

public class RequirementPanelHeaderText extends JShadowedLabel
{
	public RequirementPanelHeaderText(Dimension dimension, String text) {
		super.setHorizontalAlignment(JLabel.CENTER);
		super.setVerticalAlignment(JLabel.CENTER);
		super.setText(text);
		super.setPreferredSize(dimension);
		super.setMaximumSize(dimension);
		super.setMinimumSize(dimension);
		super.setFont(FontManager.getRunescapeSmallFont());
	}
}