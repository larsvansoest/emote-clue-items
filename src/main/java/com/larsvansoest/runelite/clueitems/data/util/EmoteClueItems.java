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

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;

public abstract class EmoteClueItems
{
	private static final Map<Integer, EmoteClueItem[]> ITEM_ID_MAP = EmoteClues.Associations.EMOTE_CLUE_ITEM.keySet().stream()
		.map(emoteClueItem -> new AbstractMap.SimpleImmutableEntry<List<Integer>, EmoteClueItem>(emoteClueItem.getItemIds(), emoteClueItem))
		.flatMap(entry -> entry.getKey().stream().map(itemId -> new AbstractMap.SimpleImmutableEntry<Integer, EmoteClueItem>(itemId, entry.getValue())))
		.collect(Collectors.toMap(
			AbstractMap.SimpleImmutableEntry::getKey,
			entry -> new EmoteClueItem[] { entry.getValue() },
			ArrayUtils::addAll
		));

	public static EmoteClueItem[] fromItemId(int itemId)
	{
		return ITEM_ID_MAP.get(itemId);
	}
}
