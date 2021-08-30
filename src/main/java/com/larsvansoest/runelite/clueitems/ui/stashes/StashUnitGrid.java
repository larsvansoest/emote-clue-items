package com.larsvansoest.runelite.clueitems.ui.stashes;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.progress.StashMonitor;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.components.DataGrid;
import com.larsvansoest.runelite.clueitems.ui.components.FoldablePanelGrid;

import javax.swing.*;

public class StashUnitGrid extends FoldablePanelGrid<StashUnitPanel>
{
	private final StashMonitor stashMonitor;

	public StashUnitGrid(final EmoteClueItemsPalette palette, final StashMonitor stashMonitor)
	{
		super(palette);
		this.createFilledFilterButton();

		super.setDisclaimer("To track STASH fill status, use the checkboxes to the left.");

		this.stashMonitor = stashMonitor;
	}

	private void createFilledFilterButton()
	{
		final String toolTipTextFormat = "Toggle show %s stashes.";
		super.addFilter("filled", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNKNOWN), DataGrid.getToolTipText(toolTipTextFormat, "all"), $ -> true);
		super.addFilter("filled", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNBUILT), DataGrid.getToolTipText(toolTipTextFormat, "not built"), StashUnitPanel::isBuilt);
		super.addFilter("filled", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.INCOMPLETE_EMPTY), DataGrid.getToolTipText(toolTipTextFormat, "empty"), StashUnitPanel::isFilled);
		super.addFilter("filled", new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.COMPLETE), DataGrid.getToolTipText(toolTipTextFormat, "filled"), StashUnitPanel::isFilled);
	}
}
