package com.larsvansoest.runelite.clueitems.data;

import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.Arrays;

public class EmoteClueCollection
{
	private final EmoteClue[] collection;

	public EmoteClueCollection(EmoteClue[] collection) {
		this.collection = collection;
	}

	public final Boolean containsItem(int itemId) {
		return Arrays.stream(this.collection).flatMap(emoteClue -> Arrays.stream(emoteClue.getItemRequirements())).anyMatch(itemRequirement -> itemRequirement.fulfilledBy(itemId));
	}
}
