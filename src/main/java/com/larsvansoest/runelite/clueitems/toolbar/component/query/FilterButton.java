package com.larsvansoest.runelite.clueitems.toolbar.component.query;

import com.larsvansoest.runelite.clueitems.toolbar.EmoteClueItemsPanel;
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
import net.runelite.client.ui.ColorScheme;

public class FilterButton<T> extends JPanel
{
	private final EmoteClueItemsPanel emoteClueItemsPanel;
	private final JLabel optionLabel;
	private final Queue<FilterButtonOption<T>> optionQueue;
	private FilterButtonOption<T> currentOption;

	public FilterButton(EmoteClueItemsPanel emoteClueItemsPanel, T defaultValue, Icon defaultIcon, Dimension dimension, Color defaultBorderColor, int capacity)
	{
		super(new GridBagLayout());
		super.setPreferredSize(dimension);
		super.setMinimumSize(dimension);
		super.setMaximumSize(dimension);
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
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
				FilterButton.super.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				FilterButton.super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			}
		});

		if (defaultBorderColor != null)
		{
			emoteClueItemsPanel.setBorderColor(defaultBorderColor);
		}
		this.emoteClueItemsPanel = emoteClueItemsPanel;

		this.currentOption = new FilterButtonOption<>(defaultValue, defaultIcon, defaultBorderColor);
		this.optionLabel = new JLabel();
		this.optionLabel.setHorizontalAlignment(JLabel.CENTER);
		this.optionLabel.setVerticalAlignment(JLabel.CENTER);
		this.optionLabel.setIcon(this.currentOption.getIcon());
		this.optionQueue = new ArrayDeque<>(capacity);

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

		if (option.getBorderColor() != null)
		{
			this.emoteClueItemsPanel.setBorderColor(option.getBorderColor());
		}
		this.emoteClueItemsPanel.search();
	}

	public void addOption(T value, Icon icon, Color borderColor)
	{
		this.optionQueue.add(new FilterButtonOption<>(value, icon, borderColor));
	}

	public T getSelectedValue()
	{
		return this.currentOption.getValue();
	}
}
