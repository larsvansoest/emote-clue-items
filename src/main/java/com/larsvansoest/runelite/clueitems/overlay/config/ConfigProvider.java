package com.larsvansoest.runelite.clueitems.overlay.config;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsConfig;
import com.larsvansoest.runelite.clueitems.overlay.interfaces.WidgetGroup;

public class ConfigProvider
{
	private final EmoteClueItemsConfig config;

	public ConfigProvider(EmoteClueItemsConfig config)
	{
		this.config = config;
	}

	public boolean interfaceGroupSelected(WidgetGroup widgetInterface)
	{
		switch (widgetInterface)
		{
			case Bank:
				return config.highlightBank();

			case Inventory:
				return config.highlightInventory();

			case Equipment:
				return config.highlightEquipment();

			default:
				return true;
		}
	}
}