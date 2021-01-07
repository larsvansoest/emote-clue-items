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

import com.larsvansoest.runelite.clueitems.EmoteClueItemsPlugin;
import java.awt.image.BufferedImage;
import net.runelite.client.util.ImageUtil;

public abstract class EmoteClueImage
{
	private static final String folder = "/icons";

	public static class Ribbon {
		private static final String folder = path(EmoteClueImage.folder, "ribbon");
		public static final BufferedImage ALL = bufferedImage(Ribbon.folder, "all.png");
		public static final BufferedImage BEGINNER = bufferedImage(Ribbon.folder, "beginner.png");
		public static final BufferedImage EASY = bufferedImage(Ribbon.folder, "easy.png");
		public static final BufferedImage MEDIUM = bufferedImage(Ribbon.folder, "medium.png");
		public static final BufferedImage HARD = bufferedImage(Ribbon.folder, "hard.png");
		public static final BufferedImage ELITE = bufferedImage(Ribbon.folder, "elite.png");
		public static final BufferedImage MASTER = bufferedImage(Ribbon.folder, "master.png");
	}

	public static class Toolbar {
		private static final String folder = path(EmoteClueImage.folder, "toolbar");
		public static class CheckSquare {
			private static final String folder = path(Toolbar.folder, "check-square");
			public static final BufferedImage ALL = bufferedImage(CheckSquare.folder, "all.png");
			public static final BufferedImage COMPLETE = bufferedImage(CheckSquare.folder, "complete.png");
			public static final BufferedImage INCOMPLETE = bufferedImage(CheckSquare.folder, "incomplete.png");
			public static final BufferedImage IN_PROGRESS = bufferedImage(CheckSquare.folder, "in-progress.png");
		}
		public static class Chevron {
			private static final String folder = path(Toolbar.folder, "chevron");
			public static final BufferedImage DOWN = bufferedImage(Chevron.folder, "down.png");
			public static final BufferedImage LEFT = bufferedImage(Chevron.folder, "left.png");
		}
		public static class Footer {
			private static final String folder = path(Toolbar.folder, "footer");
			public static final BufferedImage GITHUB = bufferedImage(Footer.folder, "github.png");
		}
	}

	private static BufferedImage bufferedImage(String folder, String name) {
		return ImageUtil.getResourceStreamFromClass(EmoteClueItemsPlugin.class, path(folder, name));
	}

	private static String path(String current, String next) {
		return String.format("%s/%s", current, next);
	}
}