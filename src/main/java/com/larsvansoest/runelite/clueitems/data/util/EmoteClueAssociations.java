/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Lars van Soest
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.larsvansoest.runelite.clueitems.data.util;

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.runelite.client.plugins.cluescrolls.clues.item.SlotLimitationRequirement;
import org.apache.commons.lang3.ArrayUtils;

public abstract class EmoteClueAssociations
{
	public static Stream<EmoteClueItem> flattenSuccessors(EmoteClueItem emoteClueItem)
	{
		EmoteClueItem[] children = emoteClueItem.getChildren();
		return Stream.concat(
			Stream.of(emoteClueItem),
			children == null ? Stream.empty() : Arrays.stream(children)
		);
	}

	public static Map<EmoteClueDifficulty, EmoteClue[]> DifficultyToEmoteClues = EmoteClue.CLUES.stream()
		.map(emoteClue -> new AbstractMap.SimpleImmutableEntry<>(emoteClue, emoteClue.getEmoteClueDifficulty()))
		.collect(Collectors.toMap(
			AbstractMap.SimpleImmutableEntry::getValue,
			entry -> new EmoteClue[]{entry.getKey()},
			ArrayUtils::addAll
		));

	public static Map<EmoteClueItem, EmoteClue[]> EmoteClueItemParentToEmoteClues = EmoteClue.CLUES.stream()
		.flatMap(emoteClue -> Arrays.stream(emoteClue.getItemRequirements())
			.filter(itemRequirement -> !(itemRequirement instanceof SlotLimitationRequirement))
			.map(itemRequirement -> (EmoteClueItem) itemRequirement)
			.map(emoteClueItem -> new AbstractMap.SimpleImmutableEntry<>(emoteClue, emoteClueItem)))
		.collect(Collectors.toMap(
			AbstractMap.SimpleImmutableEntry::getValue,
			entry -> new EmoteClue[]{entry.getKey()},
			ArrayUtils::addAll
		));

	public static Map<EmoteClueItem, EmoteClueItem[]> EmoteClueItemParentToSuccessors = EmoteClueItemParentToEmoteClues.keySet().stream()
		.collect(Collectors.toMap(
			Function.identity(),
			parent -> EmoteClueAssociations.flattenSuccessors(parent).toArray(EmoteClueItem[]::new),
			ArrayUtils::addAll
		));

	public static Map<Integer, EmoteClueItem[]> ItemIdToEmoteClueItems = EmoteClueItemParentToSuccessors.entrySet().stream()
		.flatMap(entry -> Arrays.stream(entry.getValue())
			.map(successor -> new AbstractMap.SimpleImmutableEntry<EmoteClueItem, Integer>(entry.getKey(), successor.getItemId())))
		.collect(Collectors.toMap(
			Map.Entry::getValue,
			entry -> new EmoteClueItem[] { entry.getKey() },
			ArrayUtils::addAll
		));
}