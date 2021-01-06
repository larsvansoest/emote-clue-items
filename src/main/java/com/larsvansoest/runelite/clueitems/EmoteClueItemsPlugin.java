package com.larsvansoest.runelite.clueitems;

import com.google.inject.Provides;
import com.larsvansoest.runelite.clueitems.overlay.EmoteClueItemOverlay;
import com.larsvansoest.runelite.clueitems.util.ConfigProvider;
import com.larsvansoest.runelite.clueitems.toolbar.EmoteClueItemsPanel;
import com.larsvansoest.runelite.clueitems.util.EmoteClueProvider;
import com.larsvansoest.runelite.clueitems.util.ImageProvider;
import com.larsvansoest.runelite.clueitems.util.RequirementPanelProvider;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
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
		ImageProvider imageProvider = new ImageProvider();
		ConfigProvider configProvider = new ConfigProvider(this.config);
		EmoteClueProvider emoteClueProvider = new EmoteClueProvider(EmoteClue.CLUES);
		RequirementPanelProvider panelProvider = new RequirementPanelProvider(imageProvider, emoteClueProvider);

		this.overlay = new EmoteClueItemOverlay(this.itemManager, emoteClueProvider, configProvider, imageProvider);
		this.overlayManager.add(this.overlay);

		final EmoteClueItemsPanel emoteClueItemsPanel = new EmoteClueItemsPanel(panelProvider, imageProvider);

		this.navigationButton = NavigationButton.builder()
			.tooltip("Emote Clue Items")
			.icon(imageProvider.getAllRibbonsLogo())
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
