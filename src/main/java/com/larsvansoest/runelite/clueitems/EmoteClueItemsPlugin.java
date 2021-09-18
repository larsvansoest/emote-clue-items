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
import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import com.larsvansoest.runelite.clueitems.overlay.EmoteClueItemsOverlay;
import com.larsvansoest.runelite.clueitems.progress.ProgressManager;
import com.larsvansoest.runelite.clueitems.progress.StashManager;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

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
	private EmoteClueItemsOverlay overlay;
	private NavigationButton navigationButton;
	private ProgressManager progressManager;
	private EmoteClueItemsPanel emoteClueItemsPanel;
	private StashManager stashManager;

	@Override
	protected void startUp()
	{
		this.overlay = new EmoteClueItemsOverlay(this.itemManager, this.config);
		this.overlayManager.add(this.overlay);

		final EmoteClueItemsPalette emoteClueItemsPalette = EmoteClueItemsPalette.RUNELITE;
		this.stashManager = new StashManager("[EmoteClueItems]", "STASHUnit fill statuses", this.configManager);
		this.emoteClueItemsPanel = new EmoteClueItemsPanel(emoteClueItemsPalette, this.itemManager, this.stashManager, "Emote Clue Items", "v3.0.0", "https://github.com/larsvansoest/emote-clue-items");

		this.navigationButton = NavigationButton
				.builder()
				.tooltip("Emote Clue Items")
				.icon(EmoteClueImages.resizeCanvas(EmoteClueImages.Ribbon.ALL, 16, 16))
				.priority(7)
				.panel(this.emoteClueItemsPanel)
				.build();

		this.clientToolbar.addNavigation(this.navigationButton);

		this.progressManager = new ProgressManager(this.emoteClueItemsPanel, this.client, this.clientThread, this.stashManager);
	}

	@Subscribe
	protected void onItemContainerChanged(final ItemContainerChanged event)
	{
		this.progressManager.handleEmoteClueItemChanges(event);
		if (event.getContainerId() == 95)
		{
			this.emoteClueItemsPanel.removeEmoteClueItemGridDisclaimer();
			this.emoteClueItemsPanel.removeSTASHUnitGridDisclaimer();
			this.emoteClueItemsPanel.setSTASHUnitGridDisclaimer("To track STASH fill status, use the checkboxes to the left.");
			this.progressManager.handleSTASHUnits();
		}
		// TODO match on any pin-required container to unlock stash tracking.
	}

	@Subscribe
	protected void onGameStateChanged(final GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			this.progressManager.reset();
			final String loginDisclaimer = "To start display of progression, please open your bank once.";
			this.emoteClueItemsPanel.setEmoteClueItemGridDisclaimer(loginDisclaimer);
			this.emoteClueItemsPanel.setSTASHUnitGridDisclaimer(loginDisclaimer);
		}
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			this.stashManager.validate();
		}
	}

	@Subscribe
	protected void onConfigChanged(final ConfigChanged event)
	{
		if (event.getKey().equals("DisplayProgressPanel"))
		{
			if (event.getNewValue().equals("false"))
			{
				this.clientToolbar.removeNavigation(this.navigationButton);
			}
			else
			{
				this.clientToolbar.addNavigation(this.navigationButton);
			}
		}
	}

	@Subscribe
	protected void onCommandExecuted(final CommandExecuted event)
	{
		switch (event.getCommand())
		{
			case "debug":
				this.progressManager.stashManager.setStashFilled(StashUnit.GYPSY_TENT_ENTRANCE, true);
				break;
			case "clear":
				for (int i = 0; i < 5; i++)
				{
					this.client.addChatMessage(ChatMessageType.CONSOLE, "", "", "sender-debug");
				}
				break;
			case "callscript":
				this.clientThread.invokeLater(() ->
				{
					final int[] intStackPrior = this.client.getIntStack().clone();
					final String[] stringStackPrior = this.client.getStringStack().clone();
					this.client.runScript(
							Integer.valueOf(event.getArguments()[0]),
							StashUnit.GYPSY_TENT_ENTRANCE.getStashUnit().getObjectId(),
							Integer.valueOf(event.getArguments()[1]),
							Integer.valueOf(event.getArguments()[2]),
							Integer.valueOf(event.getArguments()[3])
					);
					final int[] intStackAfter = this.client.getIntStack().clone();
					final String[] stringStackAfter = this.client.getStringStack().clone();
					if (intStackPrior.length != intStackAfter.length || stringStackPrior.length != stringStackAfter.length)
					{
						this.client.addChatMessage(ChatMessageType.CONSOLE, "", "Unequal size", "sender-debug");
					}
					else
					{
						for (int i = 0; i < intStackPrior.length; i++)
						{
							if (intStackPrior[i] != intStackAfter[i])
							{
								this.client.addChatMessage(ChatMessageType.CONSOLE, "", "Int " + i + " changed: " + intStackPrior[i] + " -> " + intStackAfter[i], "sender-debug");
							}
						}
						for (int i = 0; i < stringStackPrior.length; i++)
						{
							if (!stringStackPrior[i].equals(stringStackAfter[i]))
							{
								this.client.addChatMessage(ChatMessageType.CONSOLE, "", "String " + i + " changed: " + stringStackPrior[i] + " -> " + stringStackPrior[i], "sender-debug");
							}
						}
					}
				});
				break;
		}
	}

	@Override
	protected void shutDown()
	{
		this.overlayManager.remove(this.overlay);
		this.clientToolbar.removeNavigation(this.navigationButton);
	}

	@Provides
	EmoteClueItemsConfig provideConfig(final ConfigManager configManager)
	{
		return configManager.getConfig(EmoteClueItemsConfig.class);
	}
}
