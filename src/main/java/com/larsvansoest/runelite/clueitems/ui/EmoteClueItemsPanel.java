package com.larsvansoest.runelite.clueitems.ui;

import com.larsvansoest.runelite.clueitems.data.EmoteClue;
import com.larsvansoest.runelite.clueitems.data.EmoteClueAssociations;
import com.larsvansoest.runelite.clueitems.data.EmoteClueItem;
import com.larsvansoest.runelite.clueitems.progress.StashCacher;
import com.larsvansoest.runelite.clueitems.ui.clues.EmoteClueItemGrid;
import com.larsvansoest.runelite.clueitems.ui.clues.EmoteClueItemPanel;
import com.larsvansoest.runelite.clueitems.ui.clues.EmoteCluePanel;
import com.larsvansoest.runelite.clueitems.ui.components.*;
import com.larsvansoest.runelite.clueitems.ui.stashes.StashUnitGrid;
import com.larsvansoest.runelite.clueitems.ui.stashes.StashUnitPanel;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import net.runelite.client.plugins.cluescrolls.clues.item.ItemRequirements;
import net.runelite.client.plugins.cluescrolls.clues.item.SlotLimitationRequirement;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmoteClueItemsPanel extends PluginPanel
{
	private final Map<EmoteClueItem, EmoteClueItemPanel> itemPanelMap;
	private final Map<STASHUnit, StashUnitPanel> stashUnitPanelMap;
	private final Map<EmoteClue, EmoteCluePanel> emoteCluePanelMap;

	private final Map<EmoteClueItem, ItemSlotPanel> itemSlotPanelMap;

	private final EmoteClueItemGrid clueItemsGrid;
	private final StashUnitGrid STASHUnitGrid;

	public EmoteClueItemsPanel(
			final EmoteClueItemsPalette palette, final ItemManager itemManager, final StashCacher stashMonitor, final String pluginName, final String pluginVersion, final String gitHubUrl)
	{
		super();
		super.setLayout(new GridBagLayout());
		super.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Create item panels.
		this.itemPanelMap = EmoteClueAssociations.EmoteClueItemToEmoteClues
				.keySet()
				.stream()
				.collect(Collectors.toMap(Function.identity(), emoteClueItem -> new EmoteClueItemPanel(palette, emoteClueItem)));

		// Create an item slot panels for collection logs.
		this.itemSlotPanelMap = EmoteClueAssociations.ItemIdToEmoteClueItem
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getValue, entry -> new ItemSlotPanel(itemManager, entry.getKey(), entry.getValue().getCollectiveName())));

		// Create emote clue panels.
		this.emoteCluePanelMap = EmoteClue.CLUES.stream().collect(Collectors.toMap(Function.identity(), emoteClue -> new EmoteCluePanel(palette, emoteClue)));

		// Create STASHUnit panels.
        this.stashUnitPanelMap = Arrays
                .stream(STASHUnit.values())
                .collect(Collectors.toMap(Function.identity(), stash -> new StashUnitPanel(palette, stash, EmoteClueAssociations.STASHUnitToEmoteClues.get(stash)[0].getLocationName(), stashMonitor)));

        // Setup item panels.
        this.itemPanelMap.forEach((emoteClueItem, itemPanel) -> {
            final ItemCollectionPanel collectionPanel = new ItemCollectionPanel(palette, "Collection Log", 6);
	        itemPanel.addChild(collectionPanel);
	        this.addItemsToCollectionPanel(collectionPanel, emoteClueItem);
            collectionPanel.setHeaderColor(palette.getFoldHeaderTextColor()); // Header will not display collection progress.
	        Arrays.stream(EmoteClueAssociations.EmoteClueItemToEmoteClues.get(emoteClueItem)).map(emoteCluePanelMap::get).forEach(itemPanel::addChild);
        });

        // Setup STASHUnit panels.
		this.stashUnitPanelMap.forEach((stashUnit, stashUnitPanel) -> {
			final ItemCollectionPanel collectionPanel = new ItemCollectionPanel(palette,  "Required Items", 6);
			stashUnitPanel.addChild(collectionPanel);
			stashUnitPanel.setHeaderColor(palette.getFoldHeaderTextColor()); // Header will not display collection progress.
			for(EmoteClue emoteClue : EmoteClueAssociations.STASHUnitToEmoteClues.get(stashUnit)) {
				Arrays.stream(emoteClue.getItemRequirements()).filter(ir -> ir instanceof EmoteClueItem).forEach(emoteClueItem -> this.addItemsToCollectionPanel(collectionPanel, (EmoteClueItem)emoteClueItem));
				stashUnitPanel.addChild(this.emoteCluePanelMap.get(emoteClue));
			}
		});

		this.clueItemsGrid = new EmoteClueItemGrid(palette);
		this.clueItemsGrid.load(this.itemPanelMap.values());

		this.STASHUnitGrid = new StashUnitGrid(palette);
		this.STASHUnitGrid.load(this.stashUnitPanelMap.values());

		final TabMenu tabMenu = new TabMenu(palette, this.clueItemsGrid, "Items", "Emote Clue Items");
		tabMenu.addTab(this.STASHUnitGrid, "Stashes", "Stash Units", false, 1);

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;

		super.add(tabMenu, c);
		c.gridy++;
		super.add(this.clueItemsGrid, c);
		super.add(this.STASHUnitGrid, c);

		c.gridy++;
		c.insets = new Insets(10, 20, 0, 20);
		super.add(new FooterPanel(palette, pluginName, pluginVersion, gitHubUrl), c);
	}

	private void addItemsToCollectionPanel(final ItemCollectionPanel collectionPanel, final EmoteClueItem child)
	{
		final ItemSlotPanel childSlotPanel = this.itemSlotPanelMap.get(child);
		if (childSlotPanel != null)
		{
            collectionPanel.addItem(childSlotPanel);
			return;
		}

		final List<EmoteClueItem> successors = child.getChildren();
		if (successors != null)
		{
			for (final EmoteClueItem successor : successors)
			{
				this.addItemsToCollectionPanel(collectionPanel, successor);
			}
		}
	}

	/**
	 * Changes an item sprite to represent given quantity, if a mapping to {@link ItemSlotPanel} exists.
	 *
	 * @param emoteClueItem the {@link net.runelite.client.plugins.cluescrolls.clues.item.SingleItemRequirement} {@link EmoteClueItem} requirement containing the item sprite.
	 * @param quantity      the item quantity the item sprite should show.
	 */
	public void setItemSlotStatus(final EmoteClueItem emoteClueItem, final int quantity)
	{
		final ItemSlotPanel slotPanel = this.itemSlotPanelMap.get(emoteClueItem);
		if (slotPanel != null)
		{
			slotPanel.setQuantity(quantity);
		}
	}

	/**
	 * Changes an {@link EmoteClue} {@link EmoteClueItem} status panel to represent given {@link UpdatablePanel.Status} status, if a mapping to {@link EmoteClueItemPanel} exists.
	 *
	 * @param emoteClueItem the {@link EmoteClue} {@link EmoteClueItem} requirement to display.
	 * @param status        the desired {@link UpdatablePanel.Status} status to display.
	 */
	public void setEmoteClueItemStatus(final EmoteClueItem emoteClueItem, final UpdatablePanel.Status status)
	{
		final EmoteClueItemPanel itemPanel = this.itemPanelMap.get(emoteClueItem);
		if (itemPanel != null)
		{
			itemPanel.setStatus(status);
		}
	}

	public void turnOnSTASHFilledButton(final STASHUnit stashUnit)
	{
		final StashUnitPanel stashUnitPanel = this.stashUnitPanelMap.get(stashUnit);
		if (stashUnitPanel != null)
		{
			stashUnitPanel.turnOnFilledButton();
		}
	}

	public void turnOffSTASHFilledButton(final STASHUnit stashUnit, final Icon icon, final String toolTip)
	{
		final StashUnitPanel stashUnitPanel = this.stashUnitPanelMap.get(stashUnit);
		if (stashUnitPanel != null)
		{
			stashUnitPanel.turnOffFilledButton(icon, toolTip);
		}
	}

	public void setSTASHUnitStatus(final STASHUnit stashUnit, final boolean built, final boolean filled)
	{
		final StashUnitPanel stashUnitPanel = this.stashUnitPanelMap.get(stashUnit);
		if (stashUnitPanel != null)
		{
			if (!built)
			{
				stashUnitPanel.setBuilt(false);
			}
			else
			{
				stashUnitPanel.setBuilt(true);
				stashUnitPanel.setFilled(filled);
			}
		}
	}

	public void setEmoteClueItemGridDisclaimer(final String text)
	{
		this.clueItemsGrid.setDisclaimer(text);
	}

	public void setSTASHUnitGridDisclaimer(final String text)
	{
		this.STASHUnitGrid.setDisclaimer(text);
	}

	public void removeEmoteClueItemGridDisclaimer()
	{
		this.clueItemsGrid.removeDisclaimer();
	}

	public void removeSTASHUnitGridDisclaimer()
	{
		this.STASHUnitGrid.removeDisclaimer();
	}
}
