package com.larsvansoest.runelite.clueitems.toolbar.item;

import com.larsvansoest.runelite.clueitems.toolbar.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;

public class EmoteCluePanel extends RequirementPanel
{
	private final EmoteClue emoteClue;

	public EmoteCluePanel(EmoteClue emoteClue) {
		super(emoteClue.getLocationName());
		this.emoteClue = emoteClue;
	}

	public void addStashUnitPanel(StashUnitPanel stashUnitPanel) {

	}

	public void addItemRequirementPanel(EmoteClueItemPanel itemRequirementPanel) {

	}
}
