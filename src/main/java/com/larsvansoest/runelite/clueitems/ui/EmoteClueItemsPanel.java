package com.larsvansoest.runelite.clueitems.ui;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.ui.clues.EmoteClueItemsGrid;
import com.larsvansoest.runelite.clueitems.ui.components.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.components.FooterPanel;
import com.larsvansoest.runelite.clueitems.ui.stashes.StashUnitPanel;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EmoteClueItemsPanel extends PluginPanel
{
	private final MaterialTabGroup tabGroup;
	private final ArrayList<JPanel> tabPanels;

	public EmoteClueItemsPanel(final EmoteClueItemsPalette palette, final EmoteClueItemsGrid requirementPanelProvider)
	{
		super();
		super.setLayout(new GridBagLayout());
		super.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.tabGroup = new MaterialTabGroup();
		this.tabGroup.setLayout(new GridLayout(0, 6, 7, 7));
		this.tabPanels = new ArrayList<>();

		final EmoteClueItemsGrid clueItemsGrid = new EmoteClueItemsGrid(palette);
		final StashUnitPanel stashUnitPanel = new StashUnitPanel(palette);

		this.addTab(new ImageIcon(EmoteClueImages.Toolbar.Footer.GITHUB), clueItemsGrid);
		this.addTab(new ImageIcon(EmoteClueImages.Toolbar.Footer.GITHUB), stashUnitPanel);

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

		super.add(this.tabGroup, c);
		c.gridy++;
		super.add(clueItemsGrid, c);
		super.add(stashUnitPanel, c);

		c.gridy++;
		super.add(new FooterPanel(palette, ))
	}

	private void addTab(final ImageIcon icon, final JPanel visiblePanel)
	{
		final MaterialTab tab = new MaterialTab(icon, this.tabGroup, null);
		tab.setOnSelectEvent(() ->
		{
			for (final JPanel tabPanel : this.tabPanels)
			{
				tabPanel.setVisible(tabPanel == visiblePanel);
			}
			return true;
		});
		this.tabPanels.add(visiblePanel);
		this.tabGroup.addTab(tab);
	}
}
