package com.larsvansoest.runelite.clueitems.toolbar;

import net.runelite.client.ui.PluginPanel;

public class EmoteClueItemsPanel extends PluginPanel
{
	private PanelProvider panelProvider;

	public EmoteClueItemsPanel()
	{
		this.panelProvider = new PanelProvider();
		this.panelProvider.getEmoteClueItemPanels().forEach(super::add);
	}
}