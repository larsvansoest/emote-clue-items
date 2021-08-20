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

package com.larsvansoest.runelite.clueitems;

import com.larsvansoest.runelite.clueitems.clues.Difficulty;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

/**
 * Static facade which provides utilities to get images from the {@link Image} class.
 *
 * @author Lars van Soest
 * @since 2.0.0
 */
public abstract class Images
{
	public static BufferedImage getRibbon(final Difficulty difficulty)
	{
		switch (difficulty)
		{
			case Beginner:
				return Image.Ribbon.BEGINNER;
			case Easy:
				return Image.Ribbon.EASY;
			case Medium:
				return Image.Ribbon.MEDIUM;
			case Hard:
				return Image.Ribbon.HARD;
			case Elite:
				return Image.Ribbon.ELITE;
			case Master:
				return Image.Ribbon.MASTER;
			default:
				throw new IllegalArgumentException();
		}
	}

	public static BufferedImage getScroll(final Difficulty difficulty)
	{
		switch (difficulty)
		{
			case Beginner:
				return Image.Toolbar.Requirement.Scroll.BEGINNER;
			case Easy:
				return Image.Toolbar.Requirement.Scroll.EASY;
			case Medium:
				return Image.Toolbar.Requirement.Scroll.MEDIUM;
			case Hard:
				return Image.Toolbar.Requirement.Scroll.HARD;
			case Elite:
				return Image.Toolbar.Requirement.Scroll.ELITE;
			case Master:
				return Image.Toolbar.Requirement.Scroll.MASTER;
			default:
				throw new IllegalArgumentException();
		}
	}

	public static BufferedImage illuminate(final BufferedImage bufferedImage, final float scale)
	{
		return ImageUtil.luminanceScale(bufferedImage, scale);
	}

	public static BufferedImage resizeCanvas(final BufferedImage bufferedImage, final int width, final int height)
	{
		return ImageUtil.resizeCanvas(bufferedImage, width, height);
	}
}
