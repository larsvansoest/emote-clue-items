package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.overlay.config.ConfigProvider;
import com.larsvansoest.runelite.clueitems.data.ItemsProvider;
import com.larsvansoest.runelite.clueitems.overlay.icons.IconProvider;
import com.larsvansoest.runelite.modules.itemwidgets.ItemWidgetContainer;
import com.larsvansoest.runelite.modules.itemwidgets.ItemWidgetContext;
import com.larsvansoest.runelite.modules.itemwidgets.ItemWidgetData;
import com.larsvansoest.runelite.modules.itemwidgets.ItemWidgets;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
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
	private final ItemsProvider itemsProvider;
	private final IconProvider iconProvider;
	private final ConfigProvider configProvider;

	private final Point point; // Single allocation, to be re-used every iteration.
	private final ItemWidgetData itemWidgetData;

	@Inject
	public EmoteClueItemOverlay(ItemManager itemManager, ConfigProvider config, ItemsProvider itemsProvider, IconProvider icons)
	{
		this.itemManager = itemManager;
		this.configProvider = config;
		this.itemsProvider = itemsProvider;
		this.iconProvider = icons;
		this.point = new Point();
		this.itemWidgetData = new ItemWidgetData();

		super.showOnInterfaces(
			WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.BANK_ITEM_CONTAINER.getGroupId(),
			WidgetInfo.BANK_EQUIPMENT_CONTAINER.getGroupId(),
			WidgetInfo.BARROWS_REWARD_INVENTORY.getGroupId(),
			WidgetInfo.CLUE_SCROLL_REWARD_ITEM_CONTAINER.getGroupId(),
			WidgetInfo.DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.EQUIPMENT.getGroupId(),
			WidgetInfo.EQUIPMENT_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.GUIDE_PRICES_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.GUIDE_PRICES_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.INVENTORY.getGroupId(),
			WidgetInfo.RUNE_POUCH_ITEM_CONTAINER.getGroupId(),
			WidgetInfo.SEED_VAULT_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.SMITHING_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.SEED_VAULT_ITEM_CONTAINER.getGroupId(),
			WidgetInfo.SHOP_INVENTORY_ITEMS_CONTAINER.getGroupId(),
			WidgetInfo.SHOP_ITEMS_CONTAINER.getGroupId()
		);
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget)
	{
		ItemWidgets.Inspect(itemWidget, this.itemWidgetData);
		ItemWidgetContainer container = this.itemWidgetData.getContainer();
		ItemWidgetContext context = this.itemWidgetData.getContext();

		// filter unsupported or turned off interfaces.
		if (context == null || container == null || !configProvider.interfaceGroupSelected(container))
		{
			return;
		}

		final int item = this.itemManager.canonicalize(itemId);
		final Rectangle bounds = itemWidget.getCanvasBounds();

		final int x = bounds.x + bounds.width + getXOffset(container, context);

		int y = bounds.y;
		y = this.renderClueItemDetection(graphics, this.itemsProvider.getBeginnerItems(), this.iconProvider.getRibbons().getBeginnerRibbon(), item, x, y);
		y = this.renderClueItemDetection(graphics, this.itemsProvider.getEasyItems(), this.iconProvider.getRibbons().getEasyRibbon(), item, x, y);
		y = this.renderClueItemDetection(graphics, this.itemsProvider.getMediumItems(), this.iconProvider.getRibbons().getMediumRibbon(), item, x, y);
		y = this.renderClueItemDetection(graphics, this.itemsProvider.getHardItems(), this.iconProvider.getRibbons().getHardRibbon(), item, x, y);
		y = this.renderClueItemDetection(graphics, this.itemsProvider.getEliteItems(), this.iconProvider.getRibbons().getEliteRibbon(), item, x, y);
		this.renderClueItemDetection(graphics, this.itemsProvider.getMasterItems(), this.iconProvider.getRibbons().getMasterRibbon(), item, x, y);
	}

	private int getXOffset(ItemWidgetContainer container, ItemWidgetContext context)
	{
		return container == ItemWidgetContainer.Equipment ? -10 : context == ItemWidgetContext.Default ? -1 : -5;
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
