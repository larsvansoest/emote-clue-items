package com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues;

import java.awt.Color;
import java.util.Comparator;

public enum Difficulty
{
	Beginner(new Color(169, 158, 157)),

	Easy(new Color(9, 96, 13)),

	Medium(new Color(86, 150, 153)),

	Hard(new Color(131, 55, 152)),

	Elite(new Color(189, 165, 24)),

	Master(new Color(155, 48, 38));

	private final Color color;

	Difficulty(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return this.color;
	}

	public static Comparator<Difficulty> Comparator = (d1, d2) ->
		d1 == Difficulty.Master ? 1 : d2 == Difficulty.Master ? -1
			: d1 == Difficulty.Elite ? 1 : d2 == Difficulty.Elite ? -1
			: d1 == Difficulty.Hard ? 1 : d2 == Difficulty.Hard ? -1
			: d1 == Difficulty.Medium ? 1 : d2 == Difficulty.Medium ? -1
			: d1 == Difficulty.Easy ? 1 : d2 == Difficulty.Easy ? -1
			: d1 == Difficulty.Beginner ? 1 : d2 == Difficulty.Beginner ? -1
			: 0;
}
