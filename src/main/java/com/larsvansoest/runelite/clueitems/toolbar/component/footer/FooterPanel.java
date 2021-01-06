package com.larsvansoest.runelite.clueitems.toolbar.component.footer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.util.LinkBrowser;

public class FooterPanel extends JPanel
{
	public FooterPanel(String pluginName, String pluginVersion, String gitHubUrl, ImageIcon gitHubIcon) {
		super(new GridBagLayout());

		Color color = ColorScheme.MEDIUM_GRAY_COLOR;
		Font font = FontManager.getRunescapeSmallFont();

		JSeparator separator = new JSeparator();
		separator.setBorder(new MatteBorder(1, 0, 0, 0, color));

		JLabel pluginNameLabel = this.getTextLabel(String.format("%s %s", pluginName, pluginVersion), font, color);
		JLabel gitHubLabel = new JLabel();
		gitHubLabel.setHorizontalAlignment(JLabel.LEFT);
		gitHubLabel.setVerticalAlignment(JLabel.CENTER);
		gitHubLabel.setIcon(gitHubIcon);
		gitHubLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				LinkBrowser.browse(gitHubUrl);
			}
		});

		// TODO: CC, illuminate icons (chevrons, github logo, searchbar, etc.)
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets.top = 15;
		c.insets.left = 20;
		c.insets.right = 20;
		super.add(separator, c);
		c.insets.top = 5;
		c.gridy++;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets.left = 1;
		c.insets.right = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.EAST;
		super.add(pluginNameLabel, c);
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		super.add(gitHubLabel, c);
	}

	private JLabel getTextLabel(String string, Font font, Color color) {
		JLabel label = new JLabel();
		label.setText(string);
		label.setHorizontalAlignment(JLabel.RIGHT);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(font);
		label.setForeground(color);
		return label;
	}
}
