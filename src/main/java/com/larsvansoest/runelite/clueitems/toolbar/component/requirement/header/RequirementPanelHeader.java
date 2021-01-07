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
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RequirementPanelHeader extends JPanel
{
	private final EmoteClueItemsPanelPalette emoteClueItemsPanelPalette;

	private final RequirementPanel parent;
	private final RequirementPanelHeaderText name;
	private final HeaderFoldIcon foldIcon;

	public RequirementPanelHeader(RequirementPanel parent, EmoteClueItemsPanelPalette emoteClueItemsPanelPalette, Dimension dimension, String name, JLabel... icons)
	{
		this.emoteClueItemsPanelPalette = emoteClueItemsPanelPalette;
		this.parent = parent;
		this.name = new RequirementPanelHeaderText(dimension, name);
		this.foldIcon = new HeaderFoldIcon(false);

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

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.insets.top = 3;
		c.insets.bottom = 1;
		c.weightx = 0;
		super.add(this.name, c);

		c.gridx++;
		c.weightx = 1;
		super.add(new JLabel(), c);

		c.gridx++;
		c.weightx = 0;
		c.insets.left = 0;
		c.insets.right = 5;
		c.anchor = GridBagConstraints.EAST;
		Arrays.stream(icons).forEach(icon -> {
			c.gridx++;
			super.add(icon, c);
		});

		c.gridx++;
		super.add(this.foldIcon, c);
		this.setColor();
		this.fold(parent.isExpanded());
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
}
