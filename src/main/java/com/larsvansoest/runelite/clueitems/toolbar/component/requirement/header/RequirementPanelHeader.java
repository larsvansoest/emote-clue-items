/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Lars van Soest
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.header;

import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementPanel;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RequirementPanelHeader extends JPanel
{
	private final EmoteClueItemsPanelPalette emoteClueItemsPanelPalette;
	private final RequirementPanelHeaderTextLabel name;
	private final RequirementPanel parent;
	private final HeaderFoldIcon foldIcon;
	private final GridBagConstraints c;
	private final LinkedList<JLabel> icons;

	public RequirementPanelHeader(RequirementPanel parent, EmoteClueItemsPanelPalette emoteClueItemsPanelPalette, Dimension dimension, String name)
	{
		this.emoteClueItemsPanelPalette = emoteClueItemsPanelPalette;
		this.name = new RequirementPanelHeaderTextLabel(dimension, name);
		this.foldIcon = new HeaderFoldIcon(false);
		this.parent = parent;
		this.icons = new LinkedList<>();

		super.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				RequirementPanelHeader.this.onClick();
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				RequirementPanelHeader.super.setBackground(RequirementPanelHeader.this.emoteClueItemsPanelPalette.getHoverColor());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				RequirementPanelHeader.this.setColor();
			}
		});
		super.setLayout(new GridBagLayout());

		this.setColor();

		this.c = new GridBagConstraints();
		this.c.fill = GridBagConstraints.BOTH;
		this.c.gridx = 0;
		this.c.insets.top = 3;
		this.c.insets.bottom = 1;
		this.c.weightx = 0;
		super.add(this.name, this.c);

		this.c.gridx++;
		this.c.weightx = 1;
		super.add(new JLabel(), this.c);

		this.c.weightx = 0;
		this.c.insets.left = 0;
		this.c.insets.right = 5;
		this.c.anchor = GridBagConstraints.EAST;
		this.addIcon(this.foldIcon);
	}

	private void onClick()
	{
		this.fold(this.parent.fold());
	}

	private void fold(Boolean expanded)
	{
		this.foldIcon.fold(expanded);
	}

	private void setColor()
	{
		super.setBackground(this.parent.isExpanded() ? this.emoteClueItemsPanelPalette.getSelectColor() : this.emoteClueItemsPanelPalette.getDefaultColor());
	}

	public final JLabel getNameLabel()
	{
		return this.name;
	}

	public final void addIcon(JLabel iconLabel) {
		this.icons.forEach(super::remove);
		this.icons.addFirst(iconLabel);
		this.c.gridx = 2;
		this.icons.forEach(label -> {
			super.add(label, this.c);
			this.c.gridx++;
		});
		super.revalidate();
		super.repaint();
	}
}
