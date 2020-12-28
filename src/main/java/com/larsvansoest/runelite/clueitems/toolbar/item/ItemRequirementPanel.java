package com.larsvansoest.runelite.clueitems.toolbar.item;

import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementStatus;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import javax.swing.ImageIcon;

public class ItemRequirementPanel extends RequirementPanel
{
	public ItemRequirementPanel(ImageIcon icon, String name, EmoteClue... emoteClues) {
		super(icon, name, emoteClues);
		super.setName(name);
		super.setStatus(RequirementStatus.InComplete);
	}
}
