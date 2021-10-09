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

package com.larsvansoest.runelite.clueitems.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains values for all {@link EmoteClue} difficulties.
 *
 * @author Lars van Soest
 * @since 2.0.0
 */
@Getter
@RequiredArgsConstructor
public enum EmoteClueDifficulty
{
	Beginner(1, new Color(169, 158, 157)),
	Easy(2, new Color(9, 96, 13)),
	Medium(4, new Color(86, 150, 153)),
	Hard(8, new Color(131, 55, 152)),
	Elite(16, new Color(189, 165, 24)),
	Master(32, new Color(155, 48, 38));

	private final Color color;
	@Getter
	private int val;

	EmoteClueDifficulty(int val, Color color)
	{
		this.color = color;
		this.val = val;
	}

	public static List<EmoteClueDifficulty> getDifficulties(int val)
	{
		List<EmoteClueDifficulty> difficulties = new ArrayList<EmoteClueDifficulty>();
		for (EmoteClueDifficulty d : values())
		{
			if ((val & d.getVal()) != 0)
				difficulties.add(d);
		}
		return difficulties;
	}

	public static boolean isBeginner(int val)
	{
		return ((val & Beginner.getVal()) != 0);
	}
	public static boolean isEasy(int val)
	{
		return ((val & Easy.getVal()) != 0);
	}
	public static boolean isMedium(int val)
	{
		return ((val & Medium.getVal()) != 0);
	}
	public static boolean isHard(int val)
	{
		return ((val & Hard.getVal()) != 0);
	}
	public static boolean isElite(int val)
	{
		return ((val & Elite.getVal()) != 0);
	}
	public static boolean isMaster(int val)
	{
		return ((val & Master.getVal()) != 0);
	}
}
