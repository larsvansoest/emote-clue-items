package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.data.EmoteClueMap;
import com.larsvansoest.runelite.clueitems.data.Images;
import com.larsvansoest.runelite.clueitems.overlay.config.ConfigProvider;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidget;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgetContainer;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgetContext;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgetData;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgets;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import javax.inject.Inject;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.ImageComponent;

/**
 * Extends {@link WidgetItemOverlay}. Scans and marks items required for emote clue scroll steps.
 */
public class EmoteClueItemOverlay extends WidgetItemOverlay
{
	static private final ImageComponent beginnerRibbon = new ImageComponent(Images.BEGINNER_RIBBON);
	static private final ImageComponent easyRibbon = new ImageComponent(Images.EASY_RIBBON);
	static private final ImageComponent mediumRibbon = new ImageComponent(Images.MEDIUM_RIBBON);
	static private final ImageComponent hardRibbon = new ImageComponent(Images.HARD_RIBBON);
	static private final ImageComponent eliteRibbon = new ImageComponent(Images.ELITE_RIBBON);
	static private final ImageComponent masterRibbon = new ImageComponent(Images.MASTER_RIBBON);

	private final ItemManager itemManager;
	private final ConfigProvider configProvider;

	// Single object allocations, re-used every sequential iteration.
	private final ItemWidgetData itemWidgetData;
	private final Point point;

	@Inject
	public EmoteClueItemOverlay(ItemManager itemManager, ConfigProvider config)
	{
		this.itemManager = itemManager;
		this.configProvider = config;

		this.itemWidgetData = new ItemWidgetData();
		this.point = new Point();

		super.showOnInterfaces(
			Arrays.stream(ItemWidget.values()).mapToInt(itemWidget -> itemWidget.groupId).toArray()
		);
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget)
	{
		ItemWidgets.Inspect(itemWidget, this.itemWidgetData, 3);
		ItemWidgetContainer container = this.itemWidgetData.getContainer();
		ItemWidgetContext context = this.itemWidgetData.getContext();

		// Filter unsupported and turned off interfaces.
		if (context == null || container == null || !this.configProvider.interfaceGroupSelected(container))
		{
			return;
		}

		final int item = this.itemManager.canonicalize(itemId);

		final Rectangle bounds = itemWidget.getCanvasBounds();
		final int x = bounds.x + bounds.width + this.getXOffset(container, context);
		int y = bounds.y;
		y = this.renderClueItemDetection(graphics, Difficulty.Beginner, EmoteClueItemOverlay.beginnerRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Easy, EmoteClueItemOverlay.easyRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Medium, EmoteClueItemOverlay.mediumRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Hard, EmoteClueItemOverlay.hardRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Elite, EmoteClueItemOverlay.eliteRibbon, item, x, y);
		this.renderClueItemDetection(graphics, Difficulty.Master, EmoteClueItemOverlay.masterRibbon, item, x, y);
	}

	private int getXOffset(ItemWidgetContainer container, ItemWidgetContext context)
	{
		return container == ItemWidgetContainer.Equipment ? -10 : context == ItemWidgetContext.Default ? -1 : -5;
	}

	private int renderClueItemDetection(Graphics2D graphics, Difficulty difficulty, ImageComponent component, int id, int x, int y)
	{
		return Arrays.stream(EmoteClueMap.difficultyMap.get(difficulty)).anyMatch(emoteClue -> Arrays.stream(emoteClue.getItemRequirements()).anyMatch(itemRequirement -> itemRequirement.fulfilledBy(id))) ? (int) (y + this.renderRibbon(graphics, component, x, y).getHeight()) + 1 : y;
	}

	private Rectangle renderRibbon(Graphics2D graphics, ImageComponent ribbon, int x, int y)
	{
		this.point.setLocation(x, y);
		ribbon.setPreferredLocation(this.point);
		ribbon.render(graphics);
		return ribbon.getBounds();
	}
}
