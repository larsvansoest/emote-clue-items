package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.config.ConfigProvider;
import com.larsvansoest.runelite.clueitems.data.ItemsProvider;
import com.larsvansoest.runelite.clueitems.overlay.icons.IconProvider;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import static net.runelite.api.widgets.WidgetID.*;
import static net.runelite.api.widgets.WidgetID.DUEL_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.DUEL_INVENTORY_OTHER_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.PLAYER_TRADE_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID;
import static net.runelite.api.widgets.WidgetInfo.TO_GROUP;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.ImageComponent;

import javax.inject.Inject;
import java.util.HashSet;

/**
 * Extends {@link WidgetItemOverlay}. Scans and marks items required for emote clue scroll steps.
 */
public class EmoteClueItemOverlay extends WidgetItemOverlay
{
	private final ItemManager itemManager;
	private final ItemsProvider items;
	private final IconProvider icons;
	private final ConfigProvider config;

	private final Point point; // Single allocation, to be re-used every iteration.

	@Inject
	public EmoteClueItemOverlay(ItemManager itemManager, ConfigProvider config, ItemsProvider items, IconProvider icons)
	{
		this.itemManager = itemManager;
		this.config = config;
		this.items = items;
		this.icons = icons;
		this.point = new Point();

		super.showOnInterfaces(BANK_GROUP_ID);

		super.showOnInterfaces(DEPOSIT_BOX_GROUP_ID,
			BANK_INVENTORY_GROUP_ID,
			SHOP_INVENTORY_GROUP_ID,
			GRAND_EXCHANGE_INVENTORY_GROUP_ID,
			GUIDE_PRICES_INVENTORY_GROUP_ID,
			EQUIPMENT_INVENTORY_GROUP_ID,
			INVENTORY_GROUP_ID,
			SEED_VAULT_INVENTORY_GROUP_ID,
			DUEL_INVENTORY_GROUP_ID,
			DUEL_INVENTORY_OTHER_GROUP_ID,
			PLAYER_TRADE_SCREEN_GROUP_ID,
			PLAYER_TRADE_INVENTORY_GROUP_ID);

		super.showOnInterfaces(EQUIPMENT_GROUP_ID);
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget)
	{
		int widgetID = TO_GROUP(itemWidget.getWidget().getId());
		if (!config.interfaceSelected(widgetID))
		{
			return;
		}

		final int _id = this.itemManager.canonicalize(itemId);
		final Rectangle bounds = itemWidget.getCanvasBounds();
		final int x = bounds.x + bounds.width - 6;

		int y = bounds.y;

		y = this.renderClueItemDetection(graphics, this.items.getBeginnerItems(), this.icons.getRibbons().getBeginnerRibbon(), _id, x, y);
		y = this.renderClueItemDetection(graphics, this.items.getEasyItems(), this.icons.getRibbons().getEasyRibbon(), _id, x, y);
		y = this.renderClueItemDetection(graphics, this.items.getMediumItems(), this.icons.getRibbons().getMediumRibbon(), _id, x, y);
		y = this.renderClueItemDetection(graphics, this.items.getHardItems(), this.icons.getRibbons().getHardRibbon(), _id, x, y);
		y = this.renderClueItemDetection(graphics, this.items.getEliteItems(), this.icons.getRibbons().getEliteRibbon(), _id, x, y);
		this.renderClueItemDetection(graphics, this.items.getMasterItems(), this.icons.getRibbons().getMasterRibbon(), _id, x, y);
	}

	private int renderClueItemDetection(Graphics2D graphics, HashSet<Integer> items, ImageComponent component, int id, int x, int y)
	{
		return items.contains(id) ? (int) (y + renderRibbon(graphics, component, x, y).getHeight()) + 1 : y;
	}

	private Rectangle renderRibbon(Graphics2D graphics, ImageComponent ribbon, int x, int y)
	{
		this.point.setLocation(x, y);
		ribbon.setPreferredLocation(this.point);
		ribbon.render(graphics);
		return ribbon.getBounds();
	}
}
