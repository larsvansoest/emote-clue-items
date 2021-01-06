package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.overlay.widget.ItemWidget;
import com.larsvansoest.runelite.clueitems.overlay.widget.ItemWidgetContainer;
import com.larsvansoest.runelite.clueitems.overlay.widget.ItemWidgetContext;
import com.larsvansoest.runelite.clueitems.overlay.widget.ItemWidgetData;
import com.larsvansoest.runelite.clueitems.overlay.widget.ItemWidgetInspector;
import com.larsvansoest.runelite.clueitems.util.ConfigProvider;
import com.larsvansoest.runelite.clueitems.util.EmoteClueProvider;
import com.larsvansoest.runelite.clueitems.util.ImageProvider;
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
	private final ImageComponent beginnerRibbon;
	private final ImageComponent easyRibbon;
	private final ImageComponent mediumRibbon;
	private final ImageComponent hardRibbon;
	private final ImageComponent eliteRibbon;
	private final ImageComponent masterRibbon;

	private final ItemManager itemManager;
	private final EmoteClueProvider emoteClueProvider;
	private final ConfigProvider configProvider;
	private final ItemWidgetInspector itemWidgetInspector;

	// Single object allocations, re-used every sequential iteration.
	private final ItemWidgetData itemWidgetData;
	private final Point point;

	@Inject
	public EmoteClueItemOverlay(ItemManager itemManager, EmoteClueProvider emoteClueProvider, ConfigProvider config, ImageProvider imageProvider)
	{
		this.beginnerRibbon = new ImageComponent(imageProvider.getBeginnerRibbon());
		this.easyRibbon = new ImageComponent(imageProvider.getEasyRibbon());
		this.mediumRibbon = new ImageComponent(imageProvider.getMediumRibbon());
		this.hardRibbon = new ImageComponent(imageProvider.getHardRibbon());
		this.eliteRibbon = new ImageComponent(imageProvider.getEliteRibbon());
		this.masterRibbon = new ImageComponent(imageProvider.getMasterRibbon());

		this.itemManager = itemManager;
		this.emoteClueProvider = emoteClueProvider;
		this.configProvider = config;
		this.itemWidgetInspector = new ItemWidgetInspector();

		this.itemWidgetData = new ItemWidgetData();
		this.point = new Point();

		super.showOnInterfaces(
			Arrays.stream(ItemWidget.values()).mapToInt(itemWidget -> itemWidget.groupId).toArray()
		);
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget)
	{
		this.itemWidgetInspector.inspect(itemWidget, this.itemWidgetData, 3);
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
		y = this.renderClueItemDetection(graphics, Difficulty.Beginner, this.beginnerRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Easy, this.easyRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Medium, this.mediumRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Hard, this.hardRibbon, item, x, y);
		y = this.renderClueItemDetection(graphics, Difficulty.Elite, this.eliteRibbon, item, x, y);
		this.renderClueItemDetection(graphics, Difficulty.Master, this.masterRibbon, item, x, y);
	}

	private int getXOffset(ItemWidgetContainer container, ItemWidgetContext context)
	{
		return container == ItemWidgetContainer.Equipment ? -10 : context == ItemWidgetContext.Default ? -1 : -5;
	}

	private int renderClueItemDetection(Graphics2D graphics, Difficulty difficulty, ImageComponent component, int id, int x, int y)
	{
		return Arrays.stream(this.emoteClueProvider.getDifficultyMap().get(difficulty)).anyMatch(emoteClue -> Arrays.stream(emoteClue.getItemRequirements()).anyMatch(itemRequirement -> itemRequirement.fulfilledBy(id))) ? (int) (y + this.renderRibbon(graphics, component, x, y).getHeight()) + 1 : y;
	}

	private Rectangle renderRibbon(Graphics2D graphics, ImageComponent ribbon, int x, int y)
	{
		this.point.setLocation(x, y);
		ribbon.setPreferredLocation(this.point);
		ribbon.render(graphics);
		return ribbon.getBounds();
	}
}
