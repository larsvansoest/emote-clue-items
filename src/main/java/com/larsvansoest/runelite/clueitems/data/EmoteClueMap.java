package com.larsvansoest.runelite.clueitems.data;

import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import net.runelite.client.plugins.cluescrolls.clues.item.SlotLimitationRequirement;
import org.apache.commons.lang3.ArrayUtils;

public abstract class EmoteClueMap
{
	public static final Map<Difficulty, EmoteClue[]> difficultyMap = EmoteClue.CLUES.stream().map(emoteClue -> new AbstractMap.SimpleImmutableEntry<>(emoteClue, emoteClue.getDifficulty())).collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getValue, entry -> new EmoteClue[]{entry.getKey()}, ArrayUtils::addAll));
	public static final Map<EmoteClueItem, EmoteClue[]> emoteClueItemMap = EmoteClue.CLUES.stream().flatMap(emoteClue -> Arrays.stream(emoteClue.getItemRequirements()).filter(itemRequirement -> !(itemRequirement instanceof SlotLimitationRequirement)).map(itemRequirement -> new AbstractMap.SimpleImmutableEntry<>(emoteClue, (EmoteClueItem) itemRequirement))).collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getValue, entry -> new EmoteClue[]{entry.getKey()}, ArrayUtils::addAll));
	public static final Map<STASHUnit, EmoteClue[]> stashUnitMap = EmoteClue.CLUES.stream().collect(Collectors.toMap(EmoteClue::getStashUnit, emoteClue -> new EmoteClue[] {emoteClue}, ArrayUtils::addAll));
}
