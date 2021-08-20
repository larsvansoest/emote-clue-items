package com.larsvansoest.runelite.clueitems.ui;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.ui.content.requirement.RequirementPanelProvider;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.swing.*;
import java.awt.*;

public class EmoteClueItemsPanel extends PluginPanel
{
	private final Palette palette;
	private final ClueItemsPanel clueItemsPanel;
	private final StashUnitPanel stashUnitPanel;
	private final MaterialTabGroup tabGroup;

	public EmoteClueItemsPanel(final Palette palette, final RequirementPanelProvider requirementPanelProvider)
	{
		super();
		super.setLayout(new GridBagLayout());
		super.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.palette = palette;

		this.clueItemsPanel = new ClueItemsPanel(palette, requirementPanelProvider);
		this.stashUnitPanel = new StashUnitPanel(palette);

		this.tabGroup = new MaterialTabGroup();
		this.tabGroup.setLayout(new GridLayout(0, 6, 7, 7));

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

		super.add(this.tabGroup, c);
		c.gridy++;
		super.add(this.clueItemsPanel, c);
		super.add(this.stashUnitPanel, c);

		c.gridy = 1;
		final MaterialTab itemsTab = new MaterialTab(new ImageIcon(EmoteClueImages.Toolbar.Footer.GITHUB), this.tabGroup, null);
		itemsTab.setOnSelectEvent(() ->
		{
			this.stashUnitPanel.setVisible(false);
			this.clueItemsPanel.setVisible(true);

			super.repaint();
			super.revalidate();
			return true;
		});
		this.tabGroup.addTab(itemsTab);
		final MaterialTab stashTab = new MaterialTab(new ImageIcon(EmoteClueImages.Toolbar.Footer.GITHUB), this.tabGroup, null);
		stashTab.setOnSelectEvent(() ->
		{
			this.clueItemsPanel.setVisible(false);
			this.stashUnitPanel.setVisible(true);

			super.repaint();
			super.revalidate();
			return true;
		});
		this.tabGroup.addTab(stashTab);
	}
}
