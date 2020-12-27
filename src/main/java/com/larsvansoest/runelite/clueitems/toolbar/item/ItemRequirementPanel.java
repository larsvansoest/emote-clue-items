package com.larsvansoest.runelite.clueitems.toolbar.item;

import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementStatus;

public class ItemRequirementPanel extends RequirementPanel
{
	public ItemRequirementPanel(String name) {
		super();
		super.setName(name);
		super.setStatus(RequirementStatus.InComplete);
	}
}
