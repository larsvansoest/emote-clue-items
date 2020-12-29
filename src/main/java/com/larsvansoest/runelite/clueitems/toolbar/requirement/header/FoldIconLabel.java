package com.larsvansoest.runelite.clueitems.toolbar.requirement.header;

import com.larsvansoest.runelite.clueitems.data.Images;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class FoldIconLabel extends JLabel
{
	private static final ImageIcon ICON_DOWN = new ImageIcon(Images.CHEVRON_DOWN);
	private static final ImageIcon ICON_LEFT = new ImageIcon(Images.CHEVRON_LEFT);

	public FoldIconLabel(Boolean expanded) {
		this.fold(expanded);
	}

	public void fold(Boolean expanded) {
		super.setIcon(expanded ? FoldIconLabel.ICON_DOWN : FoldIconLabel.ICON_LEFT);
	}
}
