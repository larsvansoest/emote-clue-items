package com.larsvansoest.runelite.clueitems.ui.stashes;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.progress.StashMonitor;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.components.CycleButton;
import com.larsvansoest.runelite.clueitems.ui.components.DataGrid;
import com.larsvansoest.runelite.clueitems.ui.components.FoldablePanel;
import lombok.Getter;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;

import javax.swing.*;
import java.awt.*;

public class StashUnitPanel extends FoldablePanel
{
	@Getter
	private final boolean filled;

	@Getter
	private final boolean built;

	public StashUnitPanel(final EmoteClueItemsPalette palette, final STASHUnit stash, final String name, final StashMonitor stashMonitor)
	{
		super(palette, name, 180);
		this.filled = true;
		this.built = false;
		final String toolTipTextFormat = "Set stash unit as %s.";
		final CycleButton filled = new CycleButton(palette, new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNKNOWN), () ->
		{
		}, "Please login first.");
		filled.addOption(new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.INCOMPLETE_EMPTY), () ->
		{
			stashMonitor.setStashFilled(stash, false);
			super.setStatus(Status.InComplete);
		}, DataGrid.getToolTipText(toolTipTextFormat, "filled"));
		filled.addOption(new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.COMPLETE), () ->
		{
			stashMonitor.setStashFilled(stash, true);
			super.setStatus(Status.Complete);
		}, DataGrid.getToolTipText(toolTipTextFormat, "empty"));
		filled.setOpaque(false);
		//filled.turnOff(new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNBUILT), "Please build the STASH unit in-game first.");
		super.addLeft(filled, new Insets(0, 0, 0, 0), 10, 10);
	}
}
