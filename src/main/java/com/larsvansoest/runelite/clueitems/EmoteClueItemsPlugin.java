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
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import com.larsvansoest.runelite.clueitems.map.StashUnitWorldMapMarker;
import com.larsvansoest.runelite.clueitems.overlay.EmoteClueItemsOverlay;
import com.larsvansoest.runelite.clueitems.overlay.Widget;
import com.larsvansoest.runelite.clueitems.overlay.WidgetInspector;
import com.larsvansoest.runelite.clueitems.progress.ProgressManager;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.swing.*;
import java.util.List;
import java.util.Objects;

/**
 * Main class of the plugin.
 * <p>
 * Provides the user with an overlay and item collection database to track {@link com.larsvansoest.runelite.clueitems.data.EmoteClue} requirement progression.
 *
 * @see com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPanel
 * @see com.larsvansoest.runelite.clueitems.overlay.EmoteClueItemsOverlay
 */
@Slf4j
@PluginDescriptor(name = "Emote Clue Items",
                  description = "Highlight required items for emote clue steps.",
                  tags = {"emote", "clue", "item", "items", "scroll"})
public class EmoteClueItemsPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private ClientThread clientThread;
	@Inject
	private EmoteClueItemsConfig config;
	@Inject
	private ConfigManager configManager;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private ItemManager itemManager;
	@Inject
	private ClientToolbar clientToolbar;
	@Inject
	private WorldMapPointManager worldMapPointManager;

	private StashUnitWorldMapMarker stashUnitWorldMapMarker = null;

	private EmoteClueItemsOverlay overlay;
	private NavigationButton navigationButton;
	private ProgressManager progressManager;
	private EmoteClueItemsPanel emoteClueItemsPanel;

	private boolean updateStashBuiltStatusOnNextGameTick;
	private boolean showUnopenedInterfaceNotification;

	private Integer cachedPlayerConstructionLevel;
	private boolean readWatsonOnNextGameTick;

	private boolean isPlayerLoggedIn;

	@Override
	protected void startUp()
	{
		final EmoteClueItemsPalette emoteClueItemsPalette = EmoteClueItemsPalette.RUNELITE;

		this.emoteClueItemsPanel = new EmoteClueItemsPanel(emoteClueItemsPalette,
				this.itemManager,
				this::onUserSetStashUnitFillStatus,
				this::addStashUnitMarkerToMap,
				this::removeStashUnitMarkerFromMap,
				"Emote Clue Items",
				"v4.3.5",
				"https://github.com/larsvansoest/emote-clue-items",
				"https://www.paypal.com/donate/?hosted_button_id=72AFNGL28LFEN"
		);

		this.progressManager = new ProgressManager(this.client,
				this.clientThread,
				this.configManager,
				this.config,
				this.itemManager,
				this.emoteClueItemsPanel::setEmoteClueItemQuantity,
				this.emoteClueItemsPanel::setEmoteClueItemCollectionLogStatus,
				this.emoteClueItemsPanel::setEmoteClueItemStatus
		);

		this.navigationButton = NavigationButton
				.builder()
				.tooltip("Emote Clue Items")
				.icon(ImageUtil.resizeCanvas(EmoteClueItemsImages.Icons.RuneScape.EmoteClue.Ribbon.ALL, 16, 16))
				.priority(7)
				.panel(this.emoteClueItemsPanel)
				.build();

		this.toggleCollectionLog(this.config.showNavigation());

		this.overlay = new EmoteClueItemsOverlay(this.client, this.clientThread, this.itemManager, this.config, this.progressManager);
		this.overlayManager.add(this.overlay);

		this.reset();
	}

	private void onUserSetStashUnitFillStatus(StashUnit stashUnit, Boolean filled) {
		this.progressManager.setStashUnitFilled(stashUnit, filled);
	}

	private void reset()
	{
		this.progressManager.reset();
		this.emoteClueItemsPanel.reset();

		final String loginDisclaimer = "To start display of progression, please login first.";
		for (final StashUnit stashUnit : StashUnit.values())
		{
			this.emoteClueItemsPanel.turnOnSTASHFilledButton(stashUnit);
			this.emoteClueItemsPanel.turnOffSTASHFilledButton(stashUnit, new ImageIcon(EmoteClueItemsImages.Icons.CheckSquare.WAITING), loginDisclaimer);
		}
		this.emoteClueItemsPanel.setEmoteClueItemGridDisclaimer(loginDisclaimer);
		this.emoteClueItemsPanel.setSTASHUnitGridDisclaimer(loginDisclaimer);

		this.updateStashBuiltStatusOnNextGameTick = false;
		this.readWatsonOnNextGameTick = false;
		this.showUnopenedInterfaceNotification = this.config.notifyUnopenedInterfaces();
		this.cachedPlayerConstructionLevel = null;

		if (this.client.getGameState() == GameState.LOGGED_IN)
		{
			this.onPlayerLoggedIn();
		}

		this.removeStashUnitMarkerFromMap();
	}

	private void addStashUnitMarkerToMap(final StashUnit stashUnit, final boolean built) {
		removeStashUnitMarkerFromMap();
		if (Objects.isNull(this.stashUnitWorldMapMarker)) {
			this.stashUnitWorldMapMarker = new StashUnitWorldMapMarker(stashUnit, built);
		}

		this.stashUnitWorldMapMarker.setStashUnit(stashUnit, built);
		this.worldMapPointManager.add(this.stashUnitWorldMapMarker);
		this.overlay.addWorldMarker(this.stashUnitWorldMapMarker);
	}

	private void removeStashUnitMarkerFromMap() {
		this.worldMapPointManager.remove(this.stashUnitWorldMapMarker);
		this.overlay.clearWorldMarkers();
	}

	private void updateStashUnitBuildStatuses()
	{
		this.clientThread.invoke(() ->
		{
			for (final StashUnit stashUnit : StashUnit.values())
			{
				this.client.runScript(ScriptID.WATSON_STASH_UNIT_CHECK, stashUnit.getStashUnit().getObjectId(), 0, 0, 0);
				final boolean built = this.client.getIntStack()[0] == 1;
				this.emoteClueItemsPanel.turnOnSTASHFilledButton(stashUnit);
				this.emoteClueItemsPanel.setSTASHUnitStatus(stashUnit, built, this.progressManager.getStashUnitFilled(stashUnit));
			}
		});
	}

	private void onPlayerLoggedIn()
	{
		this.progressManager.validateConfig();
		this.updateStashBuiltStatusOnNextGameTick = true;
		this.emoteClueItemsPanel.removeEmoteClueItemGridDisclaimer();
		this.emoteClueItemsPanel.removeSTASHUnitGridDisclaimer();
		this.clientThread.invoke(this::setupUnopenedInterfaceNotification);
		this.clientThread.invoke(() -> {
			int playerConstructionLevel = client.getBoostedSkillLevel(Skill.CONSTRUCTION);
			this.updatePlayerConstructionLevel(playerConstructionLevel);
		});
		this.emoteClueItemsPanel.enableMapLinks();
	}

	private void updatePlayerConstructionLevel(Integer level) {
		if (!Objects.equals(this.cachedPlayerConstructionLevel, level)) {
			this.cachedPlayerConstructionLevel = level;
			this.emoteClueItemsPanel.setPlayerConstructionLevel(level);
		}
	}

	private void setupUnopenedInterfaceNotification()
	{
		this.clientThread.invoke(() ->
		{
			if (this.client.getGameState() == GameState.LOGGED_IN)
			{
				this.emoteClueItemsPanel.removeEmoteClueItemGridDisclaimer();
				if (this.showUnopenedInterfaceNotification)
				{
					final List<String> unopenedInterfaces = this.progressManager.getUnopenedInterfaces();
					if (this.config.notifyUnopenedInterfaces() && unopenedInterfaces.size() > 0)
					{
						final String notification = String.format("Not all items may be displayed. Please open your %s first.", String.join(", ", unopenedInterfaces));
						this.emoteClueItemsPanel.setEmoteClueItemGridDisclaimer(notification, () ->
						{
							this.showUnopenedInterfaceNotification = false;
						});
					}
				}
			}
		});
	}

		private void toggleCollectionLog(final boolean visible)
	{
		if (visible)
		{
			this.clientToolbar.addNavigation(this.navigationButton);
		}
		else
		{
			this.clientToolbar.removeNavigation(this.navigationButton);
		}
	}

	@Subscribe
	protected void onWidgetLoaded(final WidgetLoaded event) {
		if (event.getGroupId() == Widget.WATSON_NOTICE_BOARD.groupId && this.config.autoSyncWatsonBoard()) {
			this.readWatsonOnNextGameTick = true;
		}
	}

	@Subscribe
	protected void onWidgetClosed(final WidgetClosed event) {
		if (event.getGroupId() == Widget.WATSON_NOTICE_BOARD.groupId) {
			this.readWatsonOnNextGameTick = false;
		}
	}

	@Subscribe
	protected void onGameStateChanged(final GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			this.reset();
			this.isPlayerLoggedIn = false;
		}
		if (!isPlayerLoggedIn &&
			event.getGameState() == GameState.LOGGED_IN)
		{
			this.onPlayerLoggedIn();
			this.isPlayerLoggedIn = true;
		}
	}

	@Subscribe
	protected void onStatChanged(final StatChanged event) {
		if (Objects.equals(event.getSkill(), Skill.CONSTRUCTION)) {
			this.updatePlayerConstructionLevel(event.getLevel());
		}
	}

	@Subscribe
	protected void onChatMessage(final ChatMessage event)
	{
		if (event.getType() == ChatMessageType.SPAM && event.getMessage().equals("You build a STASH unit."))
		{
			this.updateStashUnitBuildStatuses();
		}
	}

	@Subscribe
	protected void onItemContainerChanged(final ItemContainerChanged event)
	{
		this.progressManager.processInventoryChanges(event.getContainerId(), event.getItemContainer().getItems());
		this.clientThread.invoke(this::setupUnopenedInterfaceNotification);
	}

	@Subscribe
	public void onGameTick(final GameTick event)
	{
		if (this.updateStashBuiltStatusOnNextGameTick)
		{
			this.updateStashBuiltStatusOnNextGameTick = false;
			this.updateStashUnitBuildStatuses();
		}
		if (this.readWatsonOnNextGameTick)
		{
			final boolean readComplete = WidgetInspector.TryReadWatsonBoard(this.client, (difficulty -> (watsonLocation, filled) -> {
				StashUnit stashUnit = StashUnit.fromWatsonLocation(watsonLocation, difficulty);
				if(Objects.nonNull(stashUnit)) {
					this.progressManager.setStashUnitFilled(stashUnit, filled);
					this.emoteClueItemsPanel.setStashUnitFilledStatus(stashUnit, filled);
				}
			}));
			this.readWatsonOnNextGameTick = !readComplete;
		}
	}

	@Subscribe
	protected void onConfigChanged(final ConfigChanged event)
	{
		final String key = event.getKey();

		// Switching to a new config profile sets values to null;
		final boolean isNull = Objects.isNull(event.getNewValue());
		final boolean isTrue = !isNull && event.getNewValue().equals("true");

		// If null, use config default
		final boolean isTrueOrDefaultTrue = isNull || isTrue;
		final boolean isTrueOrDefaultFalse = !isNull && isTrue;

		switch (key)
		{
			case "TrackBank":
				this.progressManager.toggleBankTracking(isTrueOrDefaultTrue);
				this.setupUnopenedInterfaceNotification();
				break;
			case "TrackInventory":
				this.progressManager.toggleInventoryTracking(isTrueOrDefaultTrue);
				this.setupUnopenedInterfaceNotification();
				break;
			case "TrackEquipment":
				this.progressManager.toggleEquipmentTracking(isTrueOrDefaultTrue);
				this.setupUnopenedInterfaceNotification();
				break;
			case "TrackGroupStorage":
				this.progressManager.toggleGroupStorageTracking(isTrueOrDefaultFalse);
				this.setupUnopenedInterfaceNotification();
				break;
			case "NotifyUnopenedInterfaces":
				this.showUnopenedInterfaceNotification = isTrueOrDefaultTrue;
				this.setupUnopenedInterfaceNotification();
				break;
			case "ShowNavigation":
				this.toggleCollectionLog(isTrueOrDefaultTrue);
				break;
			case "AutoSyncWatsonBoard":
				final boolean isWatsonBoardOpen = Objects.nonNull(this.client.getWidget(493, 0));
				this.readWatsonOnNextGameTick = isWatsonBoardOpen && isTrueOrDefaultFalse;
				break;
			default:
				break;
		}
	}

	@Override
	protected void shutDown()
	{
		this.overlayManager.remove(this.overlay);
		this.clientToolbar.removeNavigation(this.navigationButton);
		this.removeStashUnitMarkerFromMap();
	}

	@Provides
	EmoteClueItemsConfig provideConfig(final ConfigManager configManager)
	{
		return configManager.getConfig(EmoteClueItemsConfig.class);
	}
}
