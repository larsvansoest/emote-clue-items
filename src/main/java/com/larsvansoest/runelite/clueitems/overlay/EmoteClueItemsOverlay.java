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

package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsConfig;
import com.larsvansoest.runelite.clueitems.data.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.progress.ProgressManager;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.ImageComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;

/**
 * Extends {@link WidgetItemOverlay}. Scans and marks items required for emote clue scroll steps.
 *
 * @author Lars van Soest
 * @since 1.0.4
 */
public class EmoteClueItemsOverlay extends WidgetItemOverlay
{
	private final EmoteClueItemsConfig config;
	private final ItemManager itemManager;
	private final ProgressManager progressManager;
	// Single object allocations, re-used every sequential iteration.
	private final WidgetData widgetData;
	private final Point point;

	@Inject
	public EmoteClueItemsOverlay(final ItemManager itemManager, final EmoteClueItemsConfig config, final ProgressManager progressManager)
	{
		this.itemManager = itemManager;
		this.progressManager = progressManager;

		this.config = config;
		this.widgetData = new WidgetData();
		this.point = new Point();

		super.showOnInterfaces(Arrays.stream(Widget.values()).mapToInt(widget -> widget.groupId).toArray());
	}

	@Override
	public void renderItemOverlay(final Graphics2D graphics, final int itemId, final WidgetItem itemWidget)
	{
		WidgetInspector.Inspect(itemWidget, this.widgetData, 3);
		final WidgetContainer widgetContainer = this.widgetData.getWidgetContainer();
		final WidgetContext widgetContext = this.widgetData.getWidgetContext();

		// Filter unsupported and turned off interfaces.
		if (widgetContext == null || widgetContainer == null || !this.interfaceGroupSelected(widgetContainer))
		{
			return;
		}

		EmoteClueItemsConfig.HighlightType mode = config.highlightType();
		// Highlighting type: none
		if (mode == EmoteClueItemsConfig.HighlightType.NONE)
		{
			return;
		}

		final Rectangle bounds = itemWidget.getCanvasBounds();
		final int x = bounds.x + bounds.width + this.getXOffset(widgetContainer, widgetContext);
		int y = bounds.y;
		final int item = this.itemManager.canonicalize(itemId);

		// Highlighting type: all
		if (mode == EmoteClueItemsConfig.HighlightType.ALL)
		{
			for (EmoteClueDifficulty diff : EmoteClueDifficulty.values())
			{
				y = this.renderClueItemDetection(graphics, diff, Ribbon.getDifficultyRibbon(diff), item, x, y);
			}
		}
		// Highlighting type: unstash
		else if (this.progressManager.isUnstash(itemId))
		{
			int diffFlags = this.progressManager.unstashDiffFlags(itemId);
			for (EmoteClueDifficulty diff : EmoteClueDifficulty.getDifficulties(diffFlags))
			{
				y = this.renderClueItemDetection(graphics, diff, Ribbon.getDifficultyRibbon(diff), item, x, y);
			}
		}
	}

	private boolean interfaceGroupSelected(final WidgetContainer widgetContainer)
	{
		switch (widgetContainer)
		{
			case Bank:
				return this.config.highlightBank();

			case DepositBox:
				return this.config.highlightDepositBox();

			case Inventory:
				return this.config.highlightInventory();

			case Equipment:
				return this.config.highlightEquipment();

			case Shop:
				return this.config.highlightShop();

			case KeptOnDeath:
				return this.config.highlightKeptOnDeath();

			case GuidePrices:
				return this.config.highlightGuidePrices();

			default:
				return false;
		}
	}

	private int getXOffset(final WidgetContainer widgetContainer, final WidgetContext widgetContext)
	{
		return widgetContainer == WidgetContainer.Equipment ? -10 : widgetContext == WidgetContext.Default ? -1 : -5;
	}

	private int renderClueItemDetection(
			final Graphics2D graphics, final EmoteClueDifficulty emoteClueDifficulty, final ImageComponent component, final int id, final int x, final int y)
	{
		return Arrays
				.stream(EmoteClueAssociations.DifficultyToEmoteClues.get(emoteClueDifficulty))
				.anyMatch(emoteClue -> Arrays.stream(emoteClue.getItemRequirements()).anyMatch(itemRequirement -> itemRequirement.fulfilledBy(id))) ? (int) (y + this
				.renderRibbon(graphics, component, x, y)
				.getHeight()) + 1 : y;
	}

	private Rectangle renderRibbon(final Graphics2D graphics, final ImageComponent ribbon, final int x, final int y)
	{
		this.point.setLocation(x, y);
		ribbon.setPreferredLocation(this.point);
		ribbon.render(graphics);
		return ribbon.getBounds();
	}

	static final class Ribbon
	{
		static final ImageComponent BEGINNER = new ImageComponent(EmoteClueImages.Ribbon.BEGINNER);
		static final ImageComponent EASY = new ImageComponent(EmoteClueImages.Ribbon.EASY);
		static final ImageComponent MEDIUM = new ImageComponent(EmoteClueImages.Ribbon.MEDIUM);
		static final ImageComponent HARD = new ImageComponent(EmoteClueImages.Ribbon.HARD);
		static final ImageComponent ELITE = new ImageComponent(EmoteClueImages.Ribbon.ELITE);
		static final ImageComponent MASTER = new ImageComponent(EmoteClueImages.Ribbon.MASTER);

		public static ImageComponent getDifficultyRibbon(EmoteClueDifficulty difficulty)
		{
			switch (difficulty)
			{
				case Beginner:
					return BEGINNER;
				case Easy:
					return EASY;
				case Medium:
					return MEDIUM;
				case Hard:
					return HARD;
				case Elite:
					return ELITE;
				case Master:
					return MASTER;
				default:
					return null;
			}
		}
	}
}
