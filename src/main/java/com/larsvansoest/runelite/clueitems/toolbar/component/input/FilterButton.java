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

package com.larsvansoest.runelite.clueitems.toolbar.component.input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FilterButton<T> extends JPanel
{
	private final JLabel optionLabel;
	private final Queue<FilterButtonOption<T>> optionQueue;
	private final Runnable onChange;
	private final String defaultToolTip;

	private FilterButtonOption<T> currentOption;

	public FilterButton(T defaultValue, Icon defaultIcon, String defaultToolTip, Dimension dimension, Color defaultColor, Color hoverColor, int capacity, Runnable onChange)
	{
		super(new GridBagLayout());
		super.setPreferredSize(dimension);
		super.setMinimumSize(dimension);
		super.setMaximumSize(dimension);
		super.setBackground(defaultColor);
		super.setToolTipText(defaultToolTip);
		super.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				FilterButton.this.next();
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				FilterButton.super.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				FilterButton.super.setBackground(defaultColor);
			}
		});

		this.optionLabel = new JLabel();
		this.optionLabel.setHorizontalAlignment(JLabel.CENTER);
		this.optionLabel.setVerticalAlignment(JLabel.CENTER);
		this.optionLabel.setIcon(defaultIcon);

		this.optionQueue = new ArrayDeque<>(capacity);
		this.defaultToolTip = defaultToolTip;
		this.currentOption = new FilterButtonOption<>(defaultValue, defaultIcon, null);
		this.onChange = onChange;

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		super.add(this.optionLabel, c);
	}

	private void next()
	{
		FilterButtonOption<T> option = Objects.requireNonNull(this.optionQueue.poll());
		this.optionQueue.add(this.currentOption);
		this.currentOption = option;
		this.optionLabel.setIcon(option.getIcon());
		String toolTip = option.getToolTip();
		super.setToolTipText(toolTip == null ? this.defaultToolTip : toolTip);
		this.onChange.run();
	}

	public void addOption(T value, Icon icon)
	{
		this.addOption(value, icon, null);
	}

	public void addOption(T value, Icon icon, String toolTip)
	{
		this.optionQueue.add(new FilterButtonOption<>(value, icon, toolTip));
	}

	public T getSelectedValue()
	{
		return this.currentOption.getValue();
	}
}
