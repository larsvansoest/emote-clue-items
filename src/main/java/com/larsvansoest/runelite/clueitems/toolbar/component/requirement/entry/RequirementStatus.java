package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry;

import java.awt.Color;
import net.runelite.client.ui.ColorScheme;

public enum RequirementStatus
{
	Complete(ColorScheme.PROGRESS_COMPLETE_COLOR),

	InProgress(ColorScheme.PROGRESS_INPROGRESS_COLOR),

	InComplete(ColorScheme.LIGHT_GRAY_COLOR);

	public final Color colour;

	RequirementStatus(Color colour) {
		this.colour = colour;
	}
}
