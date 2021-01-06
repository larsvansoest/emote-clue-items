package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry;

import com.larsvansoest.runelite.clueitems.util.ImageProvider;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;

public class StashUnitPanel extends RequirementPanel
{
	public StashUnitPanel(ImageProvider imageProvider, STASHUnit stashUnit, EmoteClue... emoteClues) {
		super(imageProvider, emoteClues[0].getLocationName());
	}

	public void addItemRequirementPanel(EmoteClueItemPanel itemRequirementPanel) {

	}

	public void addEmoteCluePanel(EmoteCluePanel emoteCluePanel) {

	}
}
