package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class DescriptionPanel extends JPanel
{
	public DescriptionPanel(final EmoteClueItemsPalette palette, final String title, final String description)
	{
		super(new GridBagLayout());
		super.setBackground(palette.getFoldContentColor());

		final JLabel header = new JShadowedLabel(title);
		header.setFont(FontManager.getRunescapeSmallFont());
		header.setHorizontalAlignment(JLabel.LEFT);
		header.setForeground(palette.getPropertyNameColor());

		final JLabel content = new JLabel(String.format("<html><p style=\"width:100%%\">%s</p></html>", description));
		content.setFont(FontManager.getRunescapeSmallFont());
		content.setHorizontalAlignment(JLabel.LEFT);
		content.setForeground(palette.getPropertyValueColor());

		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		header.setBorder(new MatteBorder(0, 0, 1, 0, palette.getPropertyValueColor()));
		super.add(header, c);

		c.insets.top = 3;
		c.insets.bottom = 3;
		c.gridy++;
		super.add(content, c);
	}
}
