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

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

/**
 * Provides static objects for each image used by {@link EmoteClueItemsPlugin}.
 *
 * @author Lars van Soest
 * @since 2.0.0
 */
public abstract class EmoteClueItemsImages
{
	private static final String folder = "/images";

	private static BufferedImage getBufferedImage(final String folder, final String name)
	{
		return ImageUtil.loadImageResource(EmoteClueItemsPlugin.class, path(folder, name));
	}

	private static String path(final String current, final String next)
	{
		return String.format("%s/%s", current, next);
	}

	public static final class RuneLite
	{
		private static final String folder = "/util";

		public static final BufferedImage CLUE_ARROW = getBufferedImage(RuneLite.folder, "clue_arrow.png");

		private RuneLite()
		{
		}
	}

	public static final class MapOrb
	{
		private static final String folder = path(EmoteClueItemsImages.folder, "map-orb");

		public static final BufferedImage ORB = getBufferedImage(MapOrb.folder, "orb.png");

		public static final BufferedImage ORB_0 = getBufferedImage(MapOrb.folder, "orb-0.png");
		public static final BufferedImage ORB_90 = getBufferedImage(MapOrb.folder, "orb-90.png");
		public static final BufferedImage ORB_180 = getBufferedImage(MapOrb.folder, "orb-180.png");
		public static final BufferedImage ORB_270 = getBufferedImage(MapOrb.folder, "orb-270.png");
		public static final BufferedImage ORB_45 = getBufferedImage(MapOrb.folder, "orb-45.png");
		public static final BufferedImage ORB_135 = getBufferedImage(MapOrb.folder, "orb-135.png");
		public static final BufferedImage ORB_225 = getBufferedImage(MapOrb.folder, "orb-225.png");
		public static final BufferedImage ORB_315 = getBufferedImage(MapOrb.folder, "orb-315.png");

		private MapOrb()
		{
		}

		public static BufferedImage get(final int rotation)
		{
			switch (rotation)
			{
				case 0:
					return EmoteClueItemsImages.MapOrb.ORB_0;
				case 45:
					return EmoteClueItemsImages.MapOrb.ORB_45;
				case 90:
					return EmoteClueItemsImages.MapOrb.ORB_90;
				case 135:
					return EmoteClueItemsImages.MapOrb.ORB_135;
				case 180:
					return EmoteClueItemsImages.MapOrb.ORB_180;
				case 225:
					return EmoteClueItemsImages.MapOrb.ORB_225;
				case 270:
					return EmoteClueItemsImages.MapOrb.ORB_270;
				case 315:
					return EmoteClueItemsImages.MapOrb.ORB_315;
				default:
					return EmoteClueItemsImages.MapOrb.ORB;
			}
		}
	}

	public static final class Icons
	{
		private static final String folder = path(EmoteClueItemsImages.folder, "icons");

		public static final BufferedImage CLOSE = getBufferedImage(Icons.folder, "close.png");
		public static final BufferedImage GITHUB = getBufferedImage(Icons.folder, "github.png");
		public static final BufferedImage QUESTION = getBufferedImage(Icons.folder, "question.png");

		private Icons()
		{
		}

		public static final class CheckSquare
		{
			private static final String folder = path(Icons.folder, "check-square");

			public static final BufferedImage UNKNOWN = getBufferedImage(CheckSquare.folder, "unknown.png");
			public static final BufferedImage COMPLETE = getBufferedImage(CheckSquare.folder, "complete.png");
			public static final BufferedImage INCOMPLETE = getBufferedImage(CheckSquare.folder, "incomplete.png");
			public static final BufferedImage INCOMPLETE_EMPTY = getBufferedImage(CheckSquare.folder, "incomplete-empty.png");
			public static final BufferedImage IN_PROGRESS = getBufferedImage(CheckSquare.folder, "in-progress.png");
			public static final BufferedImage WAITING = getBufferedImage(CheckSquare.folder, "waiting.png");
			public static final BufferedImage UNBUILT = getBufferedImage(CheckSquare.folder, "unbuilt.png");

			private CheckSquare()
			{
			}
		}

		public static final class Chevron
		{
			private static final String folder = path(Icons.folder, "chevron");

			public static final BufferedImage DOWN = getBufferedImage(Chevron.folder, "down.png");
			public static final BufferedImage LEFT = getBufferedImage(Chevron.folder, "left.png");

			private Chevron()
			{
			}
		}

		public static final class Location
		{
			private static final String folder = path(Icons.folder, "location");

			public static final BufferedImage PIN = getBufferedImage(Location.folder, "pin.png");
			public static final BufferedImage PIN_DELETE = getBufferedImage(Location.folder, "pin-delete.png");

			private Location()
			{
			}
		}

		public static final class RuneScape
		{
			private static final String folder = path(Icons.folder, "runescape");

			public static final BufferedImage INVENTORY = getBufferedImage(RuneScape.folder, "inventory.png");

			private RuneScape()
			{
			}

			public static final class EmoteClue
			{
				private static final String folder = path(RuneScape.folder, "emote-clue");

				private EmoteClue()
				{
				}

				public static final class Ribbon
				{
					private static final String folder = path(EmoteClue.folder, "ribbon");

					public static final BufferedImage ALL = getBufferedImage(Ribbon.folder, "all.png");
					public static final BufferedImage BEGINNER = getBufferedImage(Ribbon.folder, "beginner.png");
					public static final BufferedImage EASY = getBufferedImage(Ribbon.folder, "easy.png");
					public static final BufferedImage MEDIUM = getBufferedImage(Ribbon.folder, "medium.png");
					public static final BufferedImage HARD = getBufferedImage(Ribbon.folder, "hard.png");
					public static final BufferedImage ELITE = getBufferedImage(Ribbon.folder, "elite.png");
					public static final BufferedImage MASTER = getBufferedImage(Ribbon.folder, "master.png");

					private Ribbon()
					{
					}

					public static BufferedImage get(final EmoteClueDifficulty emoteClueDifficulty)
					{
						switch (emoteClueDifficulty)
						{
							case Beginner:
								return Ribbon.BEGINNER;
							case Easy:
								return Ribbon.EASY;
							case Medium:
								return Ribbon.MEDIUM;
							case Hard:
								return Ribbon.HARD;
							case Elite:
								return Ribbon.ELITE;
							case Master:
								return Ribbon.MASTER;
							default:
								throw new IllegalArgumentException();
						}
					}
				}

				public static final class Scroll
				{
					private static final String folder = path(EmoteClue.folder, "scroll");
					public static final BufferedImage BEGINNER = getBufferedImage(Scroll.folder, "beginner.png");
					public static final BufferedImage EASY = getBufferedImage(Scroll.folder, "easy.png");
					public static final BufferedImage MEDIUM = getBufferedImage(Scroll.folder, "medium.png");
					public static final BufferedImage HARD = getBufferedImage(Scroll.folder, "hard.png");
					public static final BufferedImage ELITE = getBufferedImage(Scroll.folder, "elite.png");
					public static final BufferedImage MASTER = getBufferedImage(Scroll.folder, "master.png");

					private Scroll()
					{
					}

					public static BufferedImage get(final EmoteClueDifficulty emoteClueDifficulty)
					{
						switch (emoteClueDifficulty)
						{
							case Beginner:
								return Scroll.BEGINNER;
							case Easy:
								return Scroll.EASY;
							case Medium:
								return Scroll.MEDIUM;
							case Hard:
								return Scroll.HARD;
							case Elite:
								return Scroll.ELITE;
							case Master:
								return Scroll.MASTER;
							default:
								throw new IllegalArgumentException();
						}
					}
				}
			}

			public static final class StashUnit
			{
				private static final String folder = path(RuneScape.folder, "stash-unit");

				public static final BufferedImage BUSH = getBufferedImage(StashUnit.folder, "bush.png");
				public static final BufferedImage BUSH_SMALL = getBufferedImage(StashUnit.folder, "bush-small.png");
				public static final BufferedImage BUSH_BUILT = getBufferedImage(StashUnit.folder, "bush-built.png");
				public static final BufferedImage BUSH_BUILT_SMALL = getBufferedImage(StashUnit.folder, "bush-built-small.png");
				public static final BufferedImage CRATE = getBufferedImage(StashUnit.folder, "crate.png");
				public static final BufferedImage CRATE_SMALL = getBufferedImage(StashUnit.folder, "crate-small.png");
				public static final BufferedImage CRATE_BUILT = getBufferedImage(StashUnit.folder, "crate-built.png");
				public static final BufferedImage CRATE_BUILT_SMALL = getBufferedImage(StashUnit.folder, "crate-built-small.png");
				public static final BufferedImage HOLE = getBufferedImage(StashUnit.folder, "hole.png");
				public static final BufferedImage HOLE_SMALL = getBufferedImage(StashUnit.folder, "hole-small.png");
				public static final BufferedImage HOLE_BUILT = getBufferedImage(StashUnit.folder, "hole-built.png");
				public static final BufferedImage HOLE_BUILT_SMALL = getBufferedImage(StashUnit.folder, "hole-built-small.png");
				public static final BufferedImage ROCK = getBufferedImage(StashUnit.folder, "rock.png");
				public static final BufferedImage ROCK_SMALL = getBufferedImage(StashUnit.folder, "rock-small.png");
				public static final BufferedImage ROCK_BUILT = getBufferedImage(StashUnit.folder, "rock-built.png");
				public static final BufferedImage ROCK_BUILT_SMALL = getBufferedImage(StashUnit.folder, "rock-built-small.png");

				private StashUnit()
				{
				}

				public static BufferedImage get(final com.larsvansoest.runelite.clueitems.data.StashUnit.Type type, final boolean built, final boolean small)
				{
					if (built)
					{
						if (small)
						{
							return getBuiltSmall(type);
						}
						return getBuilt(type);
					}
					if (small)
					{
						return getSmall(type);
					}
					return get(type);
				}

				private static BufferedImage get(final com.larsvansoest.runelite.clueitems.data.StashUnit.Type type)
				{
					switch (type)
					{
						case Bush:
							return StashUnit.BUSH;
						case Hole:
							return StashUnit.HOLE;
						case Rock:
							return StashUnit.ROCK;
						default:
							return StashUnit.CRATE;
					}
				}

				private static BufferedImage getBuilt(final com.larsvansoest.runelite.clueitems.data.StashUnit.Type type)
				{
					switch (type)
					{
						case Bush:
							return StashUnit.BUSH_BUILT;
						case Hole:
							return StashUnit.HOLE_BUILT;
						case Rock:
							return StashUnit.ROCK_BUILT;
						default:
							return StashUnit.CRATE_BUILT;
					}
				}

				private static BufferedImage getSmall(final com.larsvansoest.runelite.clueitems.data.StashUnit.Type type)
				{
					switch (type)
					{
						case Bush:
							return StashUnit.BUSH_SMALL;
						case Hole:
							return StashUnit.HOLE_SMALL;
						case Rock:
							return StashUnit.ROCK_SMALL;
						default:
							return StashUnit.CRATE_SMALL;
					}
				}

				private static BufferedImage getBuiltSmall(final com.larsvansoest.runelite.clueitems.data.StashUnit.Type type)
				{
					switch (type)
					{
						case Bush:
							return StashUnit.BUSH_BUILT_SMALL;
						case Hole:
							return StashUnit.HOLE_BUILT_SMALL;
						case Rock:
							return StashUnit.ROCK_BUILT_SMALL;
						default:
							return StashUnit.CRATE_BUILT_SMALL;
					}
				}
			}
		}

		public static final class SortType
		{
			private static final String folder = path(Icons.folder, "sort-type");

			public static final BufferedImage NAME_ASCENDING = getBufferedImage(SortType.folder, "name-ascending.png");
			public static final BufferedImage NAME_DESCENDING = getBufferedImage(SortType.folder, "name-descending.png");
			public static final BufferedImage QUANTITY_ASCENDING = getBufferedImage(SortType.folder, "quantity-ascending.png");
			public static final BufferedImage QUANTITY_DESCENDING = getBufferedImage(SortType.folder, "quantity-descending.png");

			private SortType()
			{
			}
		}
	}
}