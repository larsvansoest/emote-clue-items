package com.larsvansoest.runelite.modules.itemwidgets;

public enum ItemWidget
{
	EQUIPMENT(387, 0),

	EQUIPMENT_EQUIPMENT(84, 0),

	EQUIPMENT_INVENTORY(85, 0),

	INVENTORY(149, 0),

	BANK(12, 12),

	BANK_EQUIPMENT(12, 68),

	BANK_INVENTORY(15, 3),

	SHOP(300, 16),

	SHOP_INVENTORY(301, 0),

	GUIDE_PRICES(464, 2),

	GUIDE_PRICES_INVENTORY(238, 0),

	KEPT_ON_DEATH(4, 5);

	private final int id;
	private final int groupId;
	private final int childId;

	ItemWidget(int groupId, int childId)
	{
		this.id = groupId << 16 | childId;
		this.groupId = groupId;
		this.childId = childId;
	}

	public int getId()
	{
		return id;
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
