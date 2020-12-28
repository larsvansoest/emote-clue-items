package com.larsvansoest.runelite.clueitems.toolbar.item;

import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementStatus;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import javax.swing.ImageIcon;

public class ItemRequirementPanel extends RequirementPanel
{
	public ItemRequirementPanel(ImageIcon icon, String name, Difficulty... difficulties) {
		super(icon, name, difficulties);
		super.setName(name);
		super.setStatus(RequirementStatus.InComplete);
	}
}
