package com.larsvansoest.runelite.clueitems.ui.stashes;

import com.larsvansoest.runelite.clueitems.data.*;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.components.CycleButton;
import com.larsvansoest.runelite.clueitems.ui.components.DataGrid;
import com.larsvansoest.runelite.clueitems.ui.components.FoldablePanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class StashUnitPanel extends FoldablePanel
{
	private final CycleButton filledButton;
	private final int filledButtonComplete;
	private final int filledButtonInComplete;
	private final EmoteClueItemsPalette palette;
	@Getter
	private boolean filled;
	@Getter
	private boolean built;
	private Color headerColorBeforeTurnOff;
	private boolean filledButtonTurnedOn;
	@Getter
	private final EmoteClueDifficulty[] difficulties;
	@Getter
	private final int quantity;

	public StashUnitPanel(final EmoteClueItemsPalette palette, final StashUnit stash, final BiConsumer<StashUnit, Boolean> onStashFillStatusChanged)
	{
		super(palette, stash.getName(), 160);
		this.palette = palette;
		this.filled = true;
		final String toolTipTextFormat = "Set stash unit as %s.";
		this.filledButtonTurnedOn = false;
		this.filledButton = new CycleButton(palette, new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.INCOMPLETE_EMPTY), () ->
		{
			if (this.filledButtonTurnedOn)
			{
				onStashFillStatusChanged.accept(stash, false);
				super.setStatus(Status.InComplete);
				this.filled = false;
			}
		}, DataGrid.getToolTipText(toolTipTextFormat, "filled"));
		this.filledButtonInComplete = 0;
		this.filledButtonComplete = this.filledButton.addOption(new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.COMPLETE), () ->
		{
			if (this.filledButtonTurnedOn)
			{
				onStashFillStatusChanged.accept(stash, true);
				super.setStatus(Status.Complete);
				this.filled = true;
			}
		}, DataGrid.getToolTipText(toolTipTextFormat, "empty"));
		this.filledButton.setOpaque(false);
		super.addLeft(this.filledButton, new Insets(0, 0, 0, 0), 10, 10);

		final EmoteClue[] emoteClues = EmoteClueAssociations.STASHUnitToEmoteClues.get(stash);
		this.quantity = emoteClues.length;
		this.difficulties = Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().toArray(EmoteClueDifficulty[]::new);
		final Insets insets = new Insets(2, 0, 2, 5);
		Arrays.stream(this.difficulties).map(EmoteClueImages::getRibbon).map(ImageIcon::new).map(JLabel::new).forEach(label -> super.addRight(label, insets, 0, 0));
		super.addRight(new JLabel(String.valueOf(this.quantity)), insets, 0, 0);
	}

	public void turnOffFilledButton(final Icon icon, final String toolTip)
	{
		if (this.filledButtonTurnedOn)
		{
			this.filledButton.turnOff(icon, toolTip);
			this.filledButtonTurnedOn = this.filledButton.isTurnedOn();
			this.headerColorBeforeTurnOff = super.getHeaderColor();
			super.setHeaderColor(this.palette.getFoldHeaderTextColor());
		}
	}

	public void turnOnFilledButton()
	{
		if (!this.filledButtonTurnedOn)
		{
			this.filledButton.turnOn();
			this.filledButtonTurnedOn = this.filledButton.isTurnedOn();
			super.setHeaderColor(this.headerColorBeforeTurnOff);
			this.headerColorBeforeTurnOff = null;
		}
	}

	public void setBuilt(final boolean built)
	{
		if (!built)
		{
			this.turnOffFilledButton(new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNBUILT), "Please build the STASH unit in-game first.");
		}
		else
		{
			this.turnOnFilledButton();
		}
		this.built = built;
	}

	public void setFilled(final boolean filled)
	{
		this.filledButton.cycleToStage(filled ? this.filledButtonComplete : this.filledButtonInComplete);
		this.filled = filled;
	}
}
