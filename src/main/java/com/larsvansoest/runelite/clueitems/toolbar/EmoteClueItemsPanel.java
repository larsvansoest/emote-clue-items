package com.larsvansoest.runelite.clueitems.toolbar;

import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementContainer;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel.RequirementPanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.searchbar.SearchBarFactory;
import com.larsvansoest.runelite.clueitems.util.ImageProvider;
import com.larsvansoest.runelite.clueitems.util.RequirementPanelProvider;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import javax.swing.ImageIcon;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

public class EmoteClueItemsPanel extends PluginPanel
{
	private RequirementPanelProvider panelProvider;
	private RequirementContainer requirementContainer;

	public EmoteClueItemsPanel(RequirementPanelProvider panelProvider, ImageProvider imageProvider)
	{
		this.panelProvider = panelProvider;

		this.requirementContainer = new RequirementContainer(panelProvider.getStashUnitPanels());

		MaterialTabGroup materialTabGroup = new MaterialTabGroup();
		this.addTabButton(materialTabGroup, new ImageIcon(imageProvider.getBeginnerRibbon()), this.requirementContainer, panelProvider.getEmoteClueItemPanels());
		this.addTabButton(materialTabGroup, new ImageIcon(imageProvider.getMediumRibbon()), this.requirementContainer, panelProvider.getStashUnitPanels());
		this.addTabButton(materialTabGroup, new ImageIcon(imageProvider.getEasyRibbon()), this.requirementContainer, panelProvider.getEmoteCluePanels());

		super.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		IconTextField searchBar = new SearchBarFactory(this).build();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		super.add(materialTabGroup, c);

		c.gridy++;
		super.add(searchBar, c);

		c.gridy++;
		super.add(this.requirementContainer, c);
	}

	public void search(String name) {
		this.requirementContainer.filter(requirement -> ((RequirementPanel) requirement).getName().toLowerCase().contains(name.toLowerCase()));
	}

	private void addTabButton(MaterialTabGroup tabGroup, ImageIcon icon, RequirementContainer requirementContainer, Collection<? extends RequirementPanel> requirementPanels) {
		MaterialTab tab = new MaterialTab(icon, tabGroup, null);
		tab.setOnSelectEvent(() -> {
			requirementContainer.load(requirementPanels);
			return true;
		});
		tabGroup.addTab(tab);
	}
}