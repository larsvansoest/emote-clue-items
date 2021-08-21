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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Queue;
import java.util.*;
import java.util.function.Consumer;

public class CycleButton<T> extends JPanel
{
	private final JLabel optionLabel;
	private final Queue<Stage> stageQueue;
	private final String defaultToolTip;
	private Stage currentStage;
	private Map.Entry<T, Icon> currentValue;

	CycleButton(
			final EmoteClueItemsPalette emoteClueItemsPalette, final T primaryKey, final Icon primaryIcon, final Consumer<T> onSelectPrimary, final String defaultToolTip)
	{
		this(emoteClueItemsPalette, new AbstractMap.SimpleImmutableEntry<T, Icon>(primaryKey, primaryIcon), onSelectPrimary, null, null, defaultToolTip);
	}

	CycleButton(
			final EmoteClueItemsPalette emoteClueItemsPalette, final T primaryKey, final Icon primaryIcon, final Consumer<T> onSelectPrimary, final T secondaryKey, final Icon secondaryIcon,
			final Consumer<T> onSelectSecondary, final String defaultToolTip)
	{
		this(emoteClueItemsPalette,
				new AbstractMap.SimpleImmutableEntry<T, Icon>(primaryKey, primaryIcon),
				onSelectPrimary,
				new AbstractMap.SimpleImmutableEntry<T, Icon>(secondaryKey, secondaryIcon),
				onSelectSecondary,
				defaultToolTip
		);
	}

	CycleButton(
			final EmoteClueItemsPalette emoteClueItemsPalette, final Map.Entry<T, Icon> primary, final Consumer<T> onSelectPrimary, final Map.Entry<T, Icon> secondary,
			final Consumer<T> onSelectSecondary, final String defaultToolTip)
	{
		super(new GridBagLayout());
		super.setBackground(emoteClueItemsPalette.getDefaultColor());
		super.setToolTipText(defaultToolTip);
		super.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				CycleButton.this.next(e.getButton() == MouseEvent.BUTTON1);
			}

			@Override
			public void mouseEntered(final MouseEvent e)
			{
				CycleButton.super.setBackground(emoteClueItemsPalette.getHoverColor());
			}

			@Override
			public void mouseExited(final MouseEvent e)
			{
				CycleButton.super.setBackground(emoteClueItemsPalette.getDefaultColor());
			}
		});

		this.optionLabel = new JLabel();
		this.optionLabel.setHorizontalAlignment(JLabel.CENTER);
		this.optionLabel.setVerticalAlignment(JLabel.CENTER);
		this.optionLabel.setIcon(primary.getValue());

		this.stageQueue = new ArrayDeque<>();
		this.defaultToolTip = defaultToolTip;
		this.currentStage = new Stage(primary, onSelectPrimary, secondary, onSelectSecondary, defaultToolTip);
		this.currentValue = primary;
		onSelectPrimary.accept(primary.getKey());

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		super.add(this.optionLabel, c);
	}

	private void next(final Boolean isPrimaryMouseKey)
	{
		final Stage stage;
		final Consumer<T> runnable;
		if (isPrimaryMouseKey || Objects.isNull(this.currentStage.getSecondary()))
		{
			stage = Objects.requireNonNull(this.stageQueue.poll());
			if (stage != this.currentStage)
			{
				return;
			}
			this.stageQueue.add(this.currentStage);
			this.currentStage = stage;
			this.currentValue = stage.getPrimary();
			this.optionLabel.setIcon(stage.getPrimary().getValue());
			runnable = stage.getOnSelectPrimary();
		}
		else
		{
			stage = this.currentStage;
			final boolean isPrimaryValue = stage.primary == this.currentValue;
			this.currentValue = isPrimaryValue ? this.currentStage.getSecondary() : this.currentStage.getPrimary();
			this.optionLabel.setIcon(isPrimaryValue ? this.currentStage.getSecondary().getValue() : this.currentStage.getPrimary().getValue());
			runnable = isPrimaryValue ? stage.getOnSelectSecondary() : stage.getOnSelectPrimary();
		}
		final String toolTip = stage.getToolTip();
		super.setToolTipText(toolTip == null ? this.defaultToolTip : toolTip);
		runnable.accept(this.currentValue.getKey());
	}

	public void addOption(final T value, final Icon icon, final Consumer<T> onSelect, final String toolTip)
	{
		this.stageQueue.add(new Stage(new AbstractMap.SimpleImmutableEntry<>(value, icon), onSelect, null, null, toolTip));
	}

	public void addOption(
			final T primaryValue, final Icon primaryIcon, final Consumer<T> onSelectPrimary, final T secondaryValue, final Icon secondaryIcon, final Consumer<T> onSelectSecondary,
			final String toolTip)
	{
		this.stageQueue.add(new Stage(new AbstractMap.SimpleImmutableEntry<>(primaryValue, primaryIcon),
				onSelectPrimary,
				new AbstractMap.SimpleImmutableEntry<>(secondaryValue, secondaryIcon),
				onSelectSecondary,
				toolTip
		));
	}

	@RequiredArgsConstructor
	@Getter
	private final class Stage
	{
		private final Map.Entry<T, Icon> primary;
		private final Consumer<T> onSelectPrimary;
		private final Map.Entry<T, Icon> secondary;
		private final Consumer<T> onSelectSecondary;
		private final String toolTip;
	}
}
