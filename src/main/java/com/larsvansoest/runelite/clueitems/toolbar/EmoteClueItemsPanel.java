package com.larsvansoest.runelite.clueitems.toolbar;

import com.larsvansoest.runelite.clueitems.data.Images;
import com.larsvansoest.runelite.clueitems.toolbar.item.ItemRequirementPanel;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.item.SlotLimitationRequirement;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import net.runelite.client.ui.PluginPanel;
import org.apache.commons.lang3.ArrayUtils;

public class EmoteClueItemsPanel extends PluginPanel
{
	public EmoteClueItemsPanel()
	{
		EmoteClue.CLUES.stream().flatMap(emoteClue -> Arrays.stream(emoteClue.getItemRequirements()).filter(itemRequirement -> !(itemRequirement instanceof SlotLimitationRequirement)).flatMap(itemRequirement -> Stream.of(new AbstractMap.SimpleImmutableEntry<>(emoteClue, itemRequirement)))).collect(Collectors.toMap(entry -> entry.getValue().getName(), entry -> Stream.of(entry.getKey()).toArray(EmoteClue[]::new), ArrayUtils::addAll)).entrySet().stream().forEach(entry -> {
			String requirementName = entry.getKey();
			EmoteClue[] emoteClues = entry.getValue();
			super.add(new ItemRequirementPanel(new ImageIcon(Images.HARD_RIBBON), requirementName, emoteClues));
		});
	}
}
