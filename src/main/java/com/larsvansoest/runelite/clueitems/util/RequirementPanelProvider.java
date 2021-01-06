package com.larsvansoest.runelite.clueitems.util;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry.EmoteCluePanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry.StashUnitPanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;

public class RequirementPanelProvider
{
	private final Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemPanelMap;
	private final Map<STASHUnit, StashUnitPanel> stashUnitPanelMap;
	private final Map<EmoteClue, EmoteCluePanel> emoteCluePanelMap;

	private final EmoteClueProvider emoteClueProvider;

	public RequirementPanelProvider(ImageProvider imageProvider, EmoteClueProvider emoteClueProvider) {
		this.emoteClueItemPanelMap = emoteClueProvider.getEmoteClueItemMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new EmoteClueItemPanel(imageProvider, entry.getKey(), entry.getValue())));
		this.stashUnitPanelMap = emoteClueProvider.getStashUnitMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new StashUnitPanel(imageProvider, entry.getKey(), entry.getValue())));
		this.emoteCluePanelMap = EmoteClue.CLUES.stream().collect(Collectors.toMap(Function.identity(), emoteClue -> new EmoteCluePanel(imageProvider, emoteClue)));
		this.emoteClueProvider = emoteClueProvider;
	}

	public final EmoteClueItemPanel getEmoteClueItemPanel(EmoteClueItem emoteClueItem) {
		return this.emoteClueItemPanelMap.get(emoteClueItem);
	}

	public final StashUnitPanel getStashUnitPanel(STASHUnit stashUnit) {
		return this.stashUnitPanelMap.get(stashUnit);
	}

	public final EmoteCluePanel getEmoteCluePanel(EmoteClue emoteClue) {
		return this.emoteCluePanelMap.get(emoteClue);
	}

	public final Collection<EmoteClueItemPanel> getEmoteClueItemPanels() {
		return this.emoteClueItemPanelMap.values();
	}

	public final Collection<StashUnitPanel> getStashUnitPanels() {
		return this.stashUnitPanelMap.values();
	}

	public final Collection<EmoteCluePanel> getEmoteCluePanels() {
		return this.emoteCluePanelMap.values();
	}
}
