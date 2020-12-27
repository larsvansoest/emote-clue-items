package com.larsvansoest.runelite.clueitems;

import com.google.inject.Provides;
import com.larsvansoest.runelite.clueitems.data.Images;
import com.larsvansoest.runelite.clueitems.overlay.EmoteClueItemOverlay;
import com.larsvansoest.runelite.clueitems.overlay.config.ConfigProvider;
import com.larsvansoest.runelite.clueitems.toolbar.EmoteClueItemsPanel;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Emote Clue Items",
	description = "Highlight required items for emote clue steps.",
	tags = {"emote", "clue", "item", "items", "scroll"}
)
public class EmoteClueItemsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private EmoteClueItemsConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ClientToolbar clientToolbar;

	private EmoteClueItemOverlay overlay;
	private NavigationButton navigationButton;

	@Override
	protected void startUp()
	{
		ConfigProvider configProvider = new ConfigProvider(this.config);

		this.overlay = new EmoteClueItemOverlay(this.itemManager, configProvider);
		this.overlayManager.add(this.overlay);

		final EmoteClueItemsPanel emoteClueItemsPanel = new EmoteClueItemsPanel();

		this.navigationButton = NavigationButton.builder()
			.tooltip("Emote Clue Items")
			.icon(Images.CLIMBING_BOOTS_LOGO)
			.priority(7)
			.panel(emoteClueItemsPanel)
			.build();

		this.clientToolbar.addNavigation(this.navigationButton);
	}

	@Override
	protected void shutDown()
	{
		this.overlayManager.remove(this.overlay);
		this.clientToolbar.removeNavigation(this.navigationButton);
	}

	@Provides
	EmoteClueItemsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EmoteClueItemsConfig.class);
	}
}
