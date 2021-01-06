package com.larsvansoest.runelite.clueitems.util;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsConfig;
import com.larsvansoest.runelite.clueitems.overlay.widget.ItemWidgetContainer;

public class ConfigProvider
{
	private final EmoteClueItemsConfig config;

	public ConfigProvider(EmoteClueItemsConfig config)
	{
		this.config = config;
	}

	public boolean interfaceGroupSelected(ItemWidgetContainer container)
	{
		switch (container)
		{
			case Bank:
				return this.config.highlightBank();

			case DepositBox:
				return this.config.highlightDepositBox();

			case Inventory:
				return this.config.highlightInventory();

			case Equipment:
				return this.config.highlightEquipment();

			case Shop:
				return this.config.highlightShop();

			case KeptOnDeath:
				return this.config.highlightKeptOnDeath();

			case GuidePrices:
				return this.config.highlightGuidePrices();

			default:
				return false;
		}
	}
}