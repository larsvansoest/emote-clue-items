package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import lombok.Getter;

/**
 * A requirement progression visualisation {@link javax.swing.JPanel} extension with changeable {@link Status} representation.
 *
 * @author Lars van Soest
 * @see FoldablePanel
 * @since 2.0.0
 */
public class RequirementPanel extends FoldablePanel implements UpdatablePanel
{
	@Getter
	private Status status;

	public RequirementPanel(final EmoteClueItemsPalette emoteClueItemsPalette, final String name, final int headerNameWidth, final int headerMinHeight)
	{
		super(emoteClueItemsPalette, name, headerNameWidth, headerMinHeight);
	}

	public void setStatus(final Status status)
	{
		super.setHeaderColor(status.colour);
		this.status = status;
	}
}