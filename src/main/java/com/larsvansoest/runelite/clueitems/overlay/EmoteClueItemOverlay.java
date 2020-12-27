package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.data.EmoteClueCollection;
import com.larsvansoest.runelite.clueitems.data.EmoteClues;
import com.larsvansoest.runelite.clueitems.data.Images;
import com.larsvansoest.runelite.clueitems.overlay.config.ConfigProvider;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidget;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgetContainer;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgetContext;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgetData;
import com.larsvansoest.runelite.clueitems.overlay.widgets.ItemWidgets;
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
	private final ItemManager itemManager;
	private final ConfigProvider configProvider;

	// Single object allocations, re-used every sequential iteration.
	private final Point point;
	private final ItemWidgetData itemWidgetData;

	private final ImageComponent beginnerRibbon;
	private final ImageComponent easyRibbon;
	private final ImageComponent mediumRibbon;
	private final ImageComponent hardRibbon;
	private final ImageComponent eliteRibbon;
	private final ImageComponent masterRibbon;

	@Inject
	public EmoteClueItemOverlay(ItemManager itemManager, ConfigProvider config)
	{
		this.itemManager = itemManager;
		this.configProvider = config;
		this.point = new Point();
		this.itemWidgetData = new ItemWidgetData();

		this.beginnerRibbon = new ImageComponent(Images.BEGINNER_RIBBON);
		this.easyRibbon = new ImageComponent(Images.EASY_RIBBON);
		this.mediumRibbon = new ImageComponent(Images.MEDIUM_RIBBON);
		this.hardRibbon = new ImageComponent(Images.HARD_RIBBON);
		this.eliteRibbon = new ImageComponent(Images.ELITE_RIBBON);
		this.masterRibbon = new ImageComponent(Images.MASTER_RIBBON);

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
		y = this.renderClueItemDetection(graphics, EmoteClues.beginner, this.beginnerRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, EmoteClues.easy, this.easyRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, EmoteClues.medium, this.mediumRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, EmoteClues.hard, this.hardRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, EmoteClues.elite, this.eliteRibbon, item, x, y);
		this.renderClueItemDetection(graphics, EmoteClues.master, this.masterRibbon, item, x, y);
	}

	private int getXOffset(ItemWidgetContainer container, ItemWidgetContext context)
	{
		return container == ItemWidgetContainer.Equipment ? -10 : context == ItemWidgetContext.Default ? -1 : -5;
	}

	private int renderClueItemDetection(Graphics2D graphics, EmoteClueCollection emoteClues, ImageComponent component, int id, int x, int y)
	{
		return emoteClues.containsItem(id) ? (int) (y + this.renderRibbon(graphics, component, x, y).getHeight()) + 1 : y;
	}

	private Rectangle renderRibbon(Graphics2D graphics, ImageComponent ribbon, int x, int y)
	{
		this.point.setLocation(x, y);
		ribbon.setPreferredLocation(this.point);
		ribbon.render(graphics);
		return ribbon.getBounds();
	}
}
