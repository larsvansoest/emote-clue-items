package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirement;

import java.util.HashMap;

public class ItemRequirementCollectionPanel extends ItemCollectionPanel
{
	private final HashMap<ItemRequirement, UpdatablePanel.Status> requirementStatusMap;

	public ItemRequirementCollectionPanel(final EmoteClueItemsPalette palette, final String name, final int slotRowSize){
		super(palette, name, slotRowSize);
		this.requirementStatusMap = new HashMap<>();
	}

	public void addRequirement(ItemRequirement itemRequirement) {
		this.requirementStatusMap.put(itemRequirement, Status.Unknown);
	}

	public void setRequirementStatus(ItemRequirement itemRequirement, UpdatablePanel.Status status) {
		if(this.requirementStatusMap.containsKey(itemRequirement)) {
			this.requirementStatusMap.put(itemRequirement, status);
		}
		super.setStatus(this.requirementStatusMap.values().stream().allMatch(Status.Complete::equals) ? Status.Complete : Status.InComplete);
	}
}
