package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel;

import com.larsvansoest.runelite.clueitems.util.ImageProvider;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;

public class EmoteCluePanel extends RequirementPanel
{
	private final EmoteClue emoteClue;

	public EmoteCluePanel(ImageProvider imageProvider, EmoteClue emoteClue) {
		super(imageProvider, emoteClue.getLocationName());
		this.emoteClue = emoteClue;
	}

	public void addStashUnitPanel(StashUnitPanel stashUnitPanel) {

	}

	public void addItemRequirementPanel(EmoteClueItemPanel itemRequirementPanel) {

	}
}
