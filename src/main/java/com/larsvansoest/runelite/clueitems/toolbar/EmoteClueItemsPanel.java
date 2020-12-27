package com.larsvansoest.runelite.clueitems.toolbar;

import com.larsvansoest.runelite.clueitems.toolbar.item.ItemRequirementPanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;
import net.runelite.api.Client;
import net.runelite.client.ui.PluginPanel;

public class EmoteClueItemsPanel extends PluginPanel
{
	public EmoteClueItemsPanel() {
		super();
		for (EmoteClue emoteClue : EmoteClue.CLUES) {
			for (ItemRequirement itemRequirement : emoteClue.getItemRequirements()) {
				super.add(new ItemRequirementPanel("temp"));
			}
		}
	}

	public final void init(Client client) {
		for (EmoteClue emoteClue : EmoteClue.CLUES) {
			for (ItemRequirement itemRequirement : emoteClue.getItemRequirements()) {
				super.add(new ItemRequirementPanel(itemRequirement.getCollectiveName(client)));
			}
		}
	}
}
