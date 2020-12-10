package com.larsvansoest.runelite.clueitems.overlay.interfaces;

import java.util.HashMap;
import java.util.Map;

import static com.larsvansoest.runelite.clueitems.overlay.interfaces.WidgetInterface.*;
import static net.runelite.api.widgets.WidgetID.*;

public class WidgetProvider
{
	private final Map<Integer, WidgetInterface> widgets;

	public WidgetProvider()
	{
		this.widgets = new HashMap<Integer, WidgetInterface>()
		{{
			put(BANK_GROUP_ID, BANK_GROUP);
			put(BANK_INVENTORY_GROUP_ID, BANK_INVENTORY_GROUP);
			put(DEPOSIT_BOX_GROUP_ID, DEPOSIT_BOX_GROUP);
			put(DUEL_INVENTORY_GROUP_ID, DUEL_INVENTORY_GROUP);
			put(DUEL_INVENTORY_OTHER_GROUP_ID, DUEL_INVENTORY_OTHER_GROUP);
			put(EQUIPMENT_GROUP_ID, EQUIPMENT_GROUP);
			put(EQUIPMENT_INVENTORY_GROUP_ID, EQUIPMENT_INVENTORY_GROUP);
			put(GRAND_EXCHANGE_INVENTORY_GROUP_ID, GRAND_EXCHANGE_INVENTORY_GROUP);
			put(GUIDE_PRICES_INVENTORY_GROUP_ID, GUIDE_PRICES_INVENTORY_GROUP);
			put(INVENTORY_GROUP_ID, INVENTORY_GROUP);
			put(PLAYER_TRADE_INVENTORY_GROUP_ID, PLAYER_TRADE_INVENTORY_GROUP);
			put(PLAYER_TRADE_SCREEN_GROUP_ID, PLAYER_TRADE_SCREEN_GROUP);
			put(SEED_VAULT_INVENTORY_GROUP_ID, SEED_VAULT_INVENTORY_GROUP);
			put(SHOP_INVENTORY_GROUP_ID, SHOP_INVENTORY_GROUP);
		}};
	}

	public WidgetInterface getWidgetInterface(int widgetID) throws IllegalArgumentException
	{
		return this.widgets.get(widgetID);
	}
}
