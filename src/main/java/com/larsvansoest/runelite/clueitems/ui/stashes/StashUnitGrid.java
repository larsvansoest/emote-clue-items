package com.larsvansoest.runelite.clueitems.ui.stashes;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.components.DataGrid;
import com.larsvansoest.runelite.clueitems.ui.components.FoldablePanelGrid;

import javax.swing.*;

public class StashUnitGrid extends FoldablePanelGrid<StashUnitPanel>
{
	public StashUnitGrid(final EmoteClueItemsPalette palette)
	{
		super(palette);
		this.createFilledFilterButton();
	}

	private void createFilledFilterButton()
	{
		final String toolTipTextFormat = "Toggle show %s stashes.";
		super.addFilter("filled", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNKNOWN), DataGrid.getToolTipText(toolTipTextFormat, "all"), $ -> true);
		super.addFilter("filled", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNBUILT), DataGrid.getToolTipText(toolTipTextFormat, "not built"), stashUnitPanel -> !stashUnitPanel.isBuilt());
		super.addFilter(
				"filled",
				new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.INCOMPLETE_EMPTY),
				DataGrid.getToolTipText(toolTipTextFormat, "empty"),
				stashUnitPanel -> stashUnitPanel.isBuilt() && !stashUnitPanel.isFilled()
		);
		super.addFilter(
				"filled",
				new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.COMPLETE),
				DataGrid.getToolTipText(toolTipTextFormat, "filled"),
				stashUnitPanel -> stashUnitPanel.isBuilt() && stashUnitPanel.isFilled()
		);
	}
}