package com.larsvansoest.runelite.clueitems.overlay.interfaces;

import static com.larsvansoest.runelite.clueitems.overlay.interfaces.WidgetGroup.Bank;
import static com.larsvansoest.runelite.clueitems.overlay.interfaces.WidgetGroup.Equipment;
import static com.larsvansoest.runelite.clueitems.overlay.interfaces.WidgetGroup.Inventory;

public enum WidgetInterface
{
	BANK_GROUP(Bank, -5),
	DEPOSIT_BOX_GROUP(Bank, -5),
	BANK_INVENTORY_GROUP(Inventory, -5),
	SHOP_INVENTORY_GROUP(Inventory, -5),
	GRAND_EXCHANGE_INVENTORY_GROUP(Inventory, -5),
	GUIDE_PRICES_INVENTORY_GROUP(Inventory, -5),
	EQUIPMENT_INVENTORY_GROUP(Inventory, -5),
	INVENTORY_GROUP(Inventory, -1),
	SEED_VAULT_INVENTORY_GROUP(Inventory, -5),
	DUEL_INVENTORY_GROUP(Inventory, -5),
	DUEL_INVENTORY_OTHER_GROUP(Inventory, -5),
	PLAYER_TRADE_SCREEN_GROUP(Inventory, -5),
	PLAYER_TRADE_INVENTORY_GROUP(Inventory, -5),
	EQUIPMENT_GROUP(Equipment, -10);

	WidgetInterface(WidgetGroup group, int offset){
		this.group = group;
		this.offset = offset;
	}

	public final WidgetGroup group;
	public final int offset;
}