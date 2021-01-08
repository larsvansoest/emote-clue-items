/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Lars van Soest
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.larsvansoest.runelite.clueitems;

import com.google.inject.Provides;
import com.larsvansoest.runelite.clueitems.data.EmoteClueImage;
import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.data.util.EmoteClueRequirements;
import com.larsvansoest.runelite.clueitems.overlay.EmoteClueItemOverlay;
import com.larsvansoest.runelite.clueitems.toolbar.EmoteClueItemsPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementPanelProvider;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import java.util.Arrays;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
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
	private RequirementPanelProvider requirementPanelProvider;
	private EmoteClueItemsPanel emoteClueItemsPanel;

	@Override
	protected void startUp()
	{
		ConfigProvider configProvider = new ConfigProvider(this.config);
		this.overlay = new EmoteClueItemOverlay(this.itemManager, configProvider);
		this.overlayManager.add(this.overlay);

		EmoteClueItemsPanelPalette emoteClueItemsPalette = EmoteClueItemsPanelPalette.DARK;
		this.requirementPanelProvider = new RequirementPanelProvider(emoteClueItemsPalette);
		this.emoteClueItemsPanel = new EmoteClueItemsPanel(emoteClueItemsPalette, this.requirementPanelProvider);

		this.navigationButton = NavigationButton.builder()
			.tooltip("Emote Clue Items")
			.icon(EmoteClueImages.resizeCanvas(EmoteClueImage.Ribbon.ALL, 16, 16))
			.priority(7)
			.panel(this.emoteClueItemsPanel)
			.build();

		this.clientToolbar.addNavigation(this.navigationButton);
	}

	@Subscribe
	protected void onItemContainerChanged(ItemContainerChanged event) {
		//private static final int INVENTORY = 93;
		//private static final int BANK = 95;
		if(event.getContainerId() == 93 || event.getContainerId() == 95) {
			Arrays.stream(EmoteClueRequirements.Monitor(event.getItemContainer().getItems())).forEach(requirement -> this.requirementPanelProvider.updateRequirementPanels(requirement, RequirementStatus.Complete));
			this.emoteClueItemsPanel.search();
		}
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
