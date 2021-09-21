package com.larsvansoest.runelite.clueitems.ui.stashes;

import com.larsvansoest.runelite.clueitems.data.*;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import com.larsvansoest.runelite.clueitems.ui.components.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class StashUnitPanel extends FoldablePanel
{
	private final CycleButton filledButton;
	private final int filledButtonComplete;
	private final int filledButtonInComplete;
	private final EmoteClueItemsPalette palette;
	@Getter
	private final EmoteClueDifficulty[] difficulties;
	@Getter
	private final int quantity;
	private final ImageIcon stashBuiltIcon;
	private final ImageIcon stashNotBuiltIcon;
	private final JLabel stashUnitImage;
	@Getter
	private boolean filled;
	@Getter
	private boolean built;
	private Color headerColorBeforeTurnOff;
	private boolean filledButtonTurnedOn;


	public StashUnitPanel(final EmoteClueItemsPalette palette, final StashUnit stash, final BiConsumer<StashUnit, Boolean> onStashFillStatusChanged)
	{
		super(palette, stash.getName(), 160, 20);
		this.palette = palette;

		this.stashBuiltIcon = this.getBuiltStashIcon(stash.getType());
		this.stashNotBuiltIcon = this.getNotBuiltStashIcon(stash.getType());
		this.stashUnitImage = new JLabel(this.stashNotBuiltIcon);

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

		super.addLeft(this.filledButton, new Insets(0, 1, 0, 0), 10, 10, DisplayMode.All);

		final EmoteClue[] emoteClues = EmoteClueAssociations.STASHUnitToEmoteClues.get(stash);
		this.quantity = emoteClues.length;
		this.difficulties = Arrays.stream(emoteClues).map(EmoteClue::getEmoteClueDifficulty).distinct().toArray(EmoteClueDifficulty[]::new);
		final Insets insets = new Insets(2, 0, 2, 5);
		Arrays.stream(this.difficulties).map(EmoteClueImages::getRibbon).map(ImageIcon::new).map(JLabel::new).forEach(label -> super.addRight(label, insets, 0, 0, DisplayMode.Default));
		super.addRight(new JLabel(String.valueOf(this.quantity)), insets, 0, 0, DisplayMode.Default);
		super.addChild(this.getDetailsPanel(palette, this.difficulties[0], stash.getType()), DisplayMode.All);
	}

	private ImageIcon getBuiltStashIcon(final StashUnit.Type type)
	{
		final BufferedImage stashUnitImage;
		switch (type)
		{
			case Bush:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.BUSH_BUILT;
				break;
			case Hole:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.HOLE_BUILT;
				break;
			case Rock:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.ROCK_BUILT;
				break;
			default:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.CRATE_BUILT;
				break;
		}
		return new ImageIcon(stashUnitImage);
	}

	private ImageIcon getNotBuiltStashIcon(final StashUnit.Type type)
	{
		final BufferedImage stashUnitImage;
		switch (type)
		{
			case Bush:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.BUSH;
				break;
			case Hole:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.HOLE;
				break;
			case Rock:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.ROCK;
				break;
			default:
				stashUnitImage = EmoteClueImages.Toolbar.StashUnit.CRATE;
				break;
		}
		return new ImageIcon(stashUnitImage);
	}

	private JPanel getDetailsPanel(final EmoteClueItemsPalette palette, final EmoteClueDifficulty difficulty, final StashUnit.Type type)
	{
		final JPanel detailsPanel = new JPanel(new GridBagLayout());
		detailsPanel.setBackground(palette.getFoldContentColor());
		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;

		c.insets.top = 5;
		c.insets.right = 5;
		final StashUnit.DifficultyRequirements difficultyRequirements = StashUnit.DifficultyRequirements.valueOf(difficulty.name());
		c.gridy++;
		detailsPanel.add(new PropertyPanel(palette, "Difficulty", difficulty.name()), c);
		c.gridy++;
		detailsPanel.add(new PropertyPanel(palette, "Construction lvl", String.valueOf(difficultyRequirements.getConstructionLvl())), c);
		c.gridy++;
		c.insets.top = 5;
		detailsPanel.add(new DescriptionPanel(palette, "Build materials", difficultyRequirements.getConstructionItems()), c);

		c.gridy = 0;
		c.gridx = 1;
		c.gridheight = 4;
		detailsPanel.add(this.stashUnitImage, c);

		return detailsPanel;
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
		if (built)
		{
			this.turnOnFilledButton();
			this.stashUnitImage.setIcon(this.stashBuiltIcon);
		}
		else
		{
			this.turnOffFilledButton(new ImageIcon(EmoteClueImages.Toolbar.CheckSquare.UNBUILT), "Please build the STASH unit in-game first.");
			this.stashUnitImage.setIcon(this.stashNotBuiltIcon);
		}
		this.built = built;
	}

	public void setFilled(final boolean filled)
	{
		this.filledButton.cycleToStage(filled ? this.filledButtonComplete : this.filledButtonInComplete);
		this.filled = filled;
	}
}