package com.larsvansoest.runelite.clueitems.toolbar.item;

import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

class DetailsPanel extends JPanel
{
	public DetailsPanel() {
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		JLabel temp = new JLabel();
		temp.setText("     ");
		super.add(temp);
	}
}
