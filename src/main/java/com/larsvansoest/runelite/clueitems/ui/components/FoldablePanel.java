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

package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FoldablePanel extends UpdatablePanel
{
	private final EmoteClueItemsPalette emoteClueItemsPalette;
	private final JLabel foldIcon;
	private final JShadowedLabel statusHeaderName;
	private final JPanel foldContent;
	private final ArrayList<UpdatablePanel> foldContentElements;
	private final ArrayList<FoldablePanel> foldContentFoldablePanels;
	private final ArrayList<JLabel> headerLabels;
	private final GridBagConstraints foldConstraints;
	private final JPanel header;
	private final Runnable onHeaderMousePressed;
	private Boolean expanded;

	public FoldablePanel(final EmoteClueItemsPalette emoteClueItemsPalette, final String name, final Runnable onHeaderMousePressed)
	{
		super.setLayout(new GridBagLayout());
		super.setBackground(emoteClueItemsPalette.getDefaultColor());
		super.setName(name);

		this.emoteClueItemsPalette = emoteClueItemsPalette;
		this.onHeaderMousePressed = onHeaderMousePressed;
		this.foldContent = new JPanel(new GridBagLayout());

		this.foldContent.setBackground(emoteClueItemsPalette.getFoldContentColor());
		this.foldConstraints = new GridBagConstraints();
		this.foldConstraints.fill = GridBagConstraints.BOTH;
		this.foldConstraints.weightx = 1;
		this.foldConstraints.insets = new Insets(0, 5, 5, 5);

		this.foldContentElements = new ArrayList<>();
		this.foldContentFoldablePanels = new ArrayList<>();
		this.foldIcon = new JLabel(FOLD_ICONS.LEFT);
		this.statusHeaderName = this.getHeaderText(name);
		this.headerLabels = new ArrayList<>();
		this.header = this.getHeader();
		this.paintHeaderLabels();

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.header, c);

		c.gridy++;
		super.add(this.foldContent, c);
	}

	private final JPanel getHeader()
	{
		final JPanel header = new JPanel();
		header.setBackground(this.emoteClueItemsPalette.getDefaultColor());
		header.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				FoldablePanel.this.onHeaderMousePressed.run();
			}

			@Override
			public void mouseEntered(final MouseEvent e)
			{
				header.setBackground(FoldablePanel.this.emoteClueItemsPalette.getHoverColor());
			}

			@Override
			public void mouseExited(final MouseEvent e)
			{
				header.setBackground(FoldablePanel.this.expanded ? FoldablePanel.this.emoteClueItemsPalette.getSelectColor() : FoldablePanel.this.emoteClueItemsPalette.getDefaultColor());
			}
		});

		return header;
	}

	public void addChild(final FoldablePanel child)
	{
		this.foldContentElements.add(child);
		this.foldContentFoldablePanels.add((child));
	}

	public void addChild(final UpdatablePanel child)
	{
		this.foldContentElements.add(child);
	}

	public void fold()
	{
		this.foldContentFoldablePanels.forEach(FoldablePanel::fold);
		this.foldContent.setVisible(false);
		this.header.setBackground(this.emoteClueItemsPalette.getDefaultColor());
		// Detach panels to enable re-use of panels under more than one foldable panel.
		this.foldContentElements.forEach(this.foldContent::remove);
		this.foldIcon.setIcon(FOLD_ICONS.LEFT);
		this.expanded = false;
		super.revalidate();
		super.repaint();
	}

	public void unfold()
	{
		this.foldConstraints.gridy = 0;
		for (int i = 0; i < this.foldContentElements.size(); i++)
		{
			this.foldConstraints.insets.top = i == 0 ? 5 : 0;
			this.foldContent.add(this.foldContentElements.get(i), this.foldConstraints);
			this.foldConstraints.gridy++;
			this.header.setBackground(this.emoteClueItemsPalette.getSelectColor());
		}
		this.foldContent.setVisible(true);
		this.foldIcon.setIcon(FOLD_ICONS.DOWN);
		this.expanded = true;
		super.revalidate();
		super.repaint();
	}

	public void setStatus(final Status status)
	{
		this.statusHeaderName.setForeground(status.colour);
	}

	public final void addIcon(final JLabel iconLabel)
	{
		this.headerLabels.add(iconLabel);
		this.paintHeaderLabels();
	}

	private final void paintHeaderLabels()
	{
		this.header.removeAll();
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets.top = 2;
		c.insets.bottom = 2;
		c.insets.left = 5;
		c.weightx = 1;
		this.header.add(this.statusHeaderName, c);
		c.gridx++;
		c.weightx = 0;
		for (final JLabel label : this.headerLabels)
		{
			this.header.add(label, c);
			c.gridx++;
		}
		c.anchor = GridBagConstraints.EAST;
		this.header.add(this.foldIcon, c);
		super.revalidate();
		super.repaint();
	}

	private final JShadowedLabel getHeaderText(final String text)
	{
		final JShadowedLabel label = new JShadowedLabel(text);
		label.setFont(FontManager.getRunescapeSmallFont());
		label.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		return label;
	}

	private final static class FOLD_ICONS
	{
		static ImageIcon DOWN = new ImageIcon(EmoteClueImages.Toolbar.Chevron.DOWN);
		static ImageIcon LEFT = new ImageIcon(EmoteClueImages.Toolbar.Chevron.LEFT);
	}
}
