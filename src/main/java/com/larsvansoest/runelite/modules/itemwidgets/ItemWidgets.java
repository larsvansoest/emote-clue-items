package com.larsvansoest.runelite.modules.itemwidgets;

import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

public abstract class ItemWidgets
{
	public static void Inspect(final WidgetItem widgetItem, final ItemWidgetData widgetDataRef)
	{
		Widget widget = widgetItem.getWidget();

		widgetDataRef.setContainer(null);
		widgetDataRef.setContext(null);

		while (widget != null)
		{
			widget = widget.getParent();
			int id = widget.getId();

			if (id == WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InBank);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.BANK_ITEM_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InBank);
				widgetDataRef.setContainer(ItemWidgetContainer.Bank);
				return;
			}
			else if (id == WidgetInfo.BANK_EQUIPMENT_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InBank);
				widgetDataRef.setContainer(ItemWidgetContainer.Equipment);
				return;
			}
			else if (id == WidgetInfo.BARROWS_REWARD_INVENTORY.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InBarrowsReward);
				widgetDataRef.setContainer(ItemWidgetContainer.BarrowsReward);
				return;
			}
			else if (id == WidgetInfo.CLUE_SCROLL_REWARD_ITEM_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InClueScrollReward);
				widgetDataRef.setContainer(ItemWidgetContainer.ClueScrollReward);
				return;
			}
			else if (id == WidgetInfo.DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InDepositBox);
				widgetDataRef.setContainer(ItemWidgetContainer.DepositBox);
				return;
			}
			else if (id == WidgetInfo.EQUIPMENT.getId())
			{
				widgetDataRef.setContext(ItemWidgetContext.Default);
				widgetDataRef.setContainer(ItemWidgetContainer.Equipment);
				return;
			}
			else if (id == WidgetInfo.EQUIPMENT_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InEquipment);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InGrandExchange);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.GUIDE_PRICES_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InGuidePrices);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.GUIDE_PRICES_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InGuidePrices);
				widgetDataRef.setContainer(ItemWidgetContainer.GuidePrices);
				return;
			}
			else if (id == WidgetInfo.INVENTORY.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.Default);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.RUNE_POUCH_ITEM_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InRunePouch);
				widgetDataRef.setContainer(ItemWidgetContainer.RunePouch);
				return;
			}
			else if (id == WidgetInfo.SEED_VAULT_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InSeedVault);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.SMITHING_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InSmithing);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.SEED_VAULT_ITEM_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InSeedVault);
				widgetDataRef.setContainer(ItemWidgetContainer.SeedVault);
				return;
			}
			else if (id == WidgetInfo.SHOP_INVENTORY_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InShop);
				widgetDataRef.setContainer(ItemWidgetContainer.Inventory);
				return;
			}
			else if (id == WidgetInfo.SHOP_ITEMS_CONTAINER.getId()) {
				widgetDataRef.setContext(ItemWidgetContext.InShop);
				widgetDataRef.setContainer(ItemWidgetContainer.Shop);
				return;
			}
		}
		return;
	}
}