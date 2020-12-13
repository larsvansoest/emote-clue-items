package com.larsvansoest.runelite.modules.itemwidgets;

import net.runelite.api.widgets.WidgetID;

public enum ItemWidgetInfo
{
	SHOP_ITEMS_CONTAINER(WidgetID.SHOP_GROUP_ID, ItemWidgetID.Shop.ITEMS_CONTAINER),
	EQUIPMENT_ITEM_SLOTS(ItemWidgetID.EQUIPMENT_ITEMS_GROUP_ID, ItemWidgetID.Equipment.ITEM_SLOTS);

	private final int groupId;
	private final int childId;

	ItemWidgetInfo(int groupId, int childId)
	{
		this.groupId = groupId;
		this.childId = childId;
	}

	public int getId()
	{
		return groupId << 16 | childId;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public int getChildId()
	{
		return childId;
	}

}
