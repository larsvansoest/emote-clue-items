package com.larsvansoest.runelite.clueitems.toolbar;

import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.data.EmoteClueMap;
import com.larsvansoest.runelite.clueitems.toolbar.item.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.toolbar.item.EmoteCluePanel;
import com.larsvansoest.runelite.clueitems.toolbar.item.StashUnitPanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;

public class PanelProvider
{
	private final Map<EmoteClueItem, EmoteClueItemPanel> emoteClueItemPanelMap;
	private final Map<STASHUnit, StashUnitPanel> stashUnitPanelMap;
	private final Map<EmoteClue, EmoteCluePanel> emoteCluePanelMap;

	public PanelProvider() {
		this.emoteClueItemPanelMap = EmoteClueMap.emoteClueItemMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new EmoteClueItemPanel(entry.getKey(), entry.getValue())));
		this.stashUnitPanelMap = EmoteClueMap.stashUnitMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new StashUnitPanel(entry.getKey(), entry.getValue())));
		this.emoteCluePanelMap = EmoteClue.CLUES.stream().collect(Collectors.toMap(Function.identity(), EmoteCluePanel::new));
	}

	public final EmoteClueItemPanel getEmoteClueItemPanel(EmoteClueItem emoteClueItem) {
		return this.emoteClueItemPanelMap.get(emoteClueItem);
	}

	public final StashUnitPanel getStashUnitPanel(STASHUnit stashUnit) {
		return this.stashUnitPanelMap.get(stashUnit);
	}

	public final EmoteCluePanel emoteCluePanel(EmoteClue emoteClue) {
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
