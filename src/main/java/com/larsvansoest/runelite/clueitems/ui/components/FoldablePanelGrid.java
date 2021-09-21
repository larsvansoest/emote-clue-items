package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;

import java.util.Collection;
import java.util.Objects;

public class FoldablePanelGrid<T extends FoldablePanel> extends DataGrid<T>
{
	private FoldablePanel unfoldedPanel;

	public FoldablePanelGrid(final EmoteClueItemsPalette palette, final int minSearchBarHeight)
	{
		super(palette, minSearchBarHeight);
	}

	@Override
	public void load(final Collection<T> entries)
	{
		this.unfoldedPanel = null;
		for (final T entry : entries)
		{
			entry.setDisplayMode(FoldablePanel.DisplayMode.Default);
			entry.setOnHeaderMousePressed(() ->
			{
				if (entry.getExpanded())
				{
					entry.fold();
					this.unfoldedPanel = null;
				}
				else
				{
					if (Objects.nonNull(this.unfoldedPanel))
					{
						this.unfoldedPanel.fold();
					}
					entry.unfold();
					this.unfoldedPanel = entry;
				}
			});
		}
		super.load(entries);
	}

	@Override
	public void setVisible(final boolean visible)
	{
		if (!visible)
		{
			for (final T entry : super.entries)
			{
				entry.fold();
			}
		}
		else
		{
			for (final T entry : super.entries)
			{
				entry.setDisplayMode(FoldablePanel.DisplayMode.Default);
			}
		}
		super.setVisible(visible);
	}
}
