package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry.header;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class FoldIconLabel extends JLabel
{
	private final ImageIcon iconDown;
	private final ImageIcon iconLeft;

	public FoldIconLabel(Boolean expanded, ImageIcon iconDown, ImageIcon iconLeft) {
		this.iconDown = iconDown;
		this.iconLeft = iconLeft;
		this.fold(expanded);
	}

	public void fold(Boolean expanded) {
		super.setIcon(expanded ? this.iconDown : this.iconLeft);
	}
}
