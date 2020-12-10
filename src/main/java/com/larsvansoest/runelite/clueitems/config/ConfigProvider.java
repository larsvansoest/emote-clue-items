package com.larsvansoest.runelite.clueitems.config;

import static net.runelite.api.widgets.WidgetID.*;
import static net.runelite.api.widgets.WidgetID.EQUIPMENT_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.PLAYER_TRADE_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.PLAYER_TRADE_SCREEN_GROUP_ID;

public class ConfigProvider
{
	private final EmoteClueItemsConfig config;

	public ConfigProvider(EmoteClueItemsConfig config) {
		this.config = config;
	}

	public boolean interfaceSelected(int widgetID)
	{
		switch (widgetID)
		{
			case BANK_GROUP_ID:
				return config.highlightBank();

			case BANK_INVENTORY_GROUP_ID:
			case SHOP_INVENTORY_GROUP_ID:
			case GRAND_EXCHANGE_INVENTORY_GROUP_ID:
			case GUIDE_PRICES_INVENTORY_GROUP_ID:
			case EQUIPMENT_INVENTORY_GROUP_ID:
			case INVENTORY_GROUP_ID:
			case SEED_VAULT_INVENTORY_GROUP_ID:
			case DUEL_INVENTORY_GROUP_ID:
			case DUEL_INVENTORY_OTHER_GROUP_ID:
			case PLAYER_TRADE_SCREEN_GROUP_ID:
			case PLAYER_TRADE_INVENTORY_GROUP_ID:
				return config.highlightInventory();

			case EQUIPMENT_GROUP_ID:
				return config.highlightEquipment();

			default:
				return true;
		}
	}
}
