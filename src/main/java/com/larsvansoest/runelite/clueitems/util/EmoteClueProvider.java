package com.larsvansoest.runelite.clueitems.util;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import net.runelite.client.plugins.cluescrolls.clues.item.SlotLimitationRequirement;
import org.apache.commons.lang3.ArrayUtils;

public class EmoteClueProvider
{
	private final Map<Difficulty, EmoteClue[]> difficultyMap;
	private final Map<EmoteClueItem, EmoteClue[]> emoteClueItemMap;
	private final Map<STASHUnit, EmoteClue[]> stashUnitMap;

	public EmoteClueProvider(Collection<EmoteClue> emoteClueCollection) {
		this.difficultyMap = emoteClueCollection.stream().map(emoteClue -> new AbstractMap.SimpleImmutableEntry<>(emoteClue, emoteClue.getDifficulty())).collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getValue, entry -> new EmoteClue[]{entry.getKey()}, ArrayUtils::addAll));
		this.emoteClueItemMap = emoteClueCollection.stream().flatMap(emoteClue -> Arrays.stream(emoteClue.getItemRequirements()).filter(itemRequirement -> !(itemRequirement instanceof SlotLimitationRequirement)).map(itemRequirement -> new AbstractMap.SimpleImmutableEntry<>(emoteClue, (EmoteClueItem) itemRequirement))).collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getValue, entry -> new EmoteClue[]{entry.getKey()}, ArrayUtils::addAll));
		this.stashUnitMap = emoteClueCollection.stream().collect(Collectors.toMap(EmoteClue::getStashUnit, emoteClue -> new EmoteClue[] {emoteClue}, ArrayUtils::addAll));
	}

	public Map<Difficulty, EmoteClue[]> getDifficultyMap()
	{
		return this.difficultyMap;
	}

	public Map<EmoteClueItem, EmoteClue[]> getEmoteClueItemMap()
	{
		return this.emoteClueItemMap;
	}

	public Map<STASHUnit, EmoteClue[]> getStashUnitMap()
	{
		return this.stashUnitMap;
	}
}
