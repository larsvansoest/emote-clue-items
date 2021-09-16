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
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class FoldablePanel extends UpdatablePanel
{
	private final EmoteClueItemsPalette emoteClueItemsPalette;
	private final JLabel foldIcon;
	private final JShadowedLabel statusHeaderName;
	private final JPanel foldContent;
	private final ArrayList<JComponent> foldContentElements;
	private final ArrayList<FoldablePanel> foldContentFoldablePanels;
	private final ArrayList<HeaderElement> leftHeaderElements;
	private final ArrayList<HeaderElement> rightHeaderElements;
	private final JPanel header;
	@Setter
	@Getter
	private int foldContentLeftInset;
	@Setter
	@Getter
	private int foldContentRightInset;
	@Setter
	@Getter
	private int foldContentBottomInset;
	@Setter
	@Getter
	private Integer fixedFoldContentTopInset;
	@Setter
	@Getter
	private Runnable onHeaderMousePressed;
	@Getter
	private Boolean expanded;
	@Getter
	private Status status;

	public FoldablePanel(final EmoteClueItemsPalette emoteClueItemsPalette, final String name, final int headerNameWidth)
	{
		super.setLayout(new GridBagLayout());
		super.setBackground(emoteClueItemsPalette.getDefaultColor());
		super.setName(name);

		this.expanded = false;

		this.emoteClueItemsPalette = emoteClueItemsPalette;
		this.foldContent = new JPanel(new GridBagLayout());

		this.foldContent.setBackground(emoteClueItemsPalette.getFoldContentColor());

		this.foldContentElements = new ArrayList<>();
		this.foldContentFoldablePanels = new ArrayList<>();
		this.foldIcon = new JLabel(FOLD_ICONS.LEFT);
		this.statusHeaderName = this.getHeaderText(name, headerNameWidth);
		this.leftHeaderElements = new ArrayList<>();
		this.rightHeaderElements = new ArrayList<>();
		this.header = this.getHeader();
		this.paintHeaderLabels();

		this.onHeaderMousePressed = () ->
		{
			if (this.expanded)
			{
				this.fold();
			}
			else
			{
				this.unfold();
			}
		};
		this.foldContentLeftInset = 5;
		this.foldContentRightInset = 5;
		this.foldContentBottomInset = 5;
		this.fixedFoldContentTopInset = null;

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
		final JPanel header = new JPanel(new GridBagLayout());
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
		this.foldContentFoldablePanels.add(child);
	}

	public void addChild(final JComponent child)
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
		this.foldContent.revalidate();
		this.foldContent.repaint();
	}

	public void unfold()
	{
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.insets = new Insets(0, this.foldContentLeftInset, this.foldContentBottomInset, this.foldContentRightInset);
		c.gridy = 0;
		for (int i = 0; i < this.foldContentElements.size(); i++)
		{
			c.insets.top = Objects.nonNull(this.fixedFoldContentTopInset) ? this.fixedFoldContentTopInset : i == 0 ? 5 : 0;
			this.foldContent.add(this.foldContentElements.get(i), c);
			c.gridy++;
		}
		this.header.setBackground(this.emoteClueItemsPalette.getSelectColor());
		this.foldContent.setVisible(true);
		this.foldIcon.setIcon(FOLD_ICONS.DOWN);
		this.expanded = true;
		this.foldContent.revalidate();
		this.foldContent.repaint();
	}

	public void setStatus(final Status status)
	{
		this.setHeaderColor(status.colour);
		this.status = status;
	}

	public Color getHeaderColor()
	{
		return this.statusHeaderName.getForeground();
	}

	public void setHeaderColor(final Color colour)
	{
		this.statusHeaderName.setForeground(colour);
	}

	public final void addRight(final CycleButton cycleButton, final Insets insets, final int ipadX, final int ipadY)
	{
		this.rightHeaderElements.add(new HeaderElement(cycleButton, insets, ipadX, ipadY));
		this.paintHeaderLabels();
	}

	public final void addRight(final JLabel iconLabel, final Insets insets, final int ipadX, final int ipadY)
	{
		this.rightHeaderElements.add(new HeaderElement(iconLabel, insets, ipadX, ipadY));
		this.paintHeaderLabels();
	}

	public final void addLeft(final CycleButton cycleButton, final Insets insets, final int ipadX, final int ipadY)
	{
		this.leftHeaderElements.add(new HeaderElement(cycleButton, insets, ipadX, ipadY));
		this.paintHeaderLabels();
	}

	public final void addLeft(final JLabel iconLabel, final Insets insets, final int ipadX, final int ipadY)
	{
		this.leftHeaderElements.add(new HeaderElement(iconLabel, insets, ipadX, ipadY));
		this.paintHeaderLabels();
	}

	private final void paintHeaderLabels()
	{
		this.header.removeAll();
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;

		// Add left icons & buttons
		this.addHeaderElements(this.leftHeaderElements, c);

		c.insets = new Insets(2, 0, 2, 0);
		this.header.add(this.statusHeaderName, c);
		c.weightx = 1;

		c.gridx++;
		this.header.add(new JLabel(), c);
		c.weightx = 0;

		c.gridx++;
		this.addHeaderElements(this.rightHeaderElements, c);

		c.insets = new Insets(6, 0, 6, 5);
		this.header.add(this.foldIcon, c);
		super.revalidate();
		super.repaint();
	}

	private void addHeaderElements(final ArrayList<HeaderElement> headerElements, final GridBagConstraints c)
	{
		final Insets previousInsets = c.insets;
		final int previousIpadX = c.ipadx;
		final int previousIpadY = c.ipady;
		for (final HeaderElement headerElement : headerElements)
		{
			c.insets = headerElement.getInsets();
			c.ipadx = headerElement.getIpadX();
			c.ipady = headerElement.getIpadY();
			this.header.add(headerElement.getElement(), c);
			c.gridx++;
		}
		c.insets = previousInsets;
		c.ipadx = previousIpadX;
		c.ipady = previousIpadY;
	}

	private JShadowedLabel getHeaderText(final String text, final int fixedWidth)
	{
		final JShadowedLabel label = new JShadowedLabel(text);
		final Dimension size = new Dimension(fixedWidth, label.getHeight());
		label.setMinimumSize(size);
		label.setPreferredSize(size);
		label.setMaximumSize(size);

		label.setFont(FontManager.getRunescapeSmallFont());
		label.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	private final static class FOLD_ICONS
	{
		static ImageIcon DOWN = new ImageIcon(EmoteClueImages.Toolbar.Chevron.DOWN);
		static ImageIcon LEFT = new ImageIcon(EmoteClueImages.Toolbar.Chevron.LEFT);
	}

	@Getter
	@RequiredArgsConstructor
	private static class HeaderElement
	{
		private final JComponent element;
		private final Insets insets;
		private final int ipadX;
		private final int ipadY;
	}
}
