package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry.header;

import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.entry.RequirementPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public class RequirementPanelHeader extends JPanel
{
	private static final Color COLOR_DEFAULT = ColorScheme.DARKER_GRAY_COLOR;
	private static final Color COLOR_SELECT = ColorScheme.DARK_GRAY_HOVER_COLOR;
	private static final Color COLOR_HOVER = ColorScheme.DARKER_GRAY_HOVER_COLOR;

	private static final Dimension DIMENSION_NAME = new Dimension(155, 15);

	private final RequirementPanel parent;
	private final RequirementPanelHeaderText name;
	private final FoldIconLabel foldIcon;

	public RequirementPanelHeader(RequirementPanel parent, String name, ImageIcon iconDown, ImageIcon iconLeft, JLabel... icons)
	{
		this.parent = parent;
		this.name = new RequirementPanelHeaderText(DIMENSION_NAME, name);
		this.foldIcon = new FoldIconLabel(false, iconDown, iconLeft);

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
				RequirementPanelHeader.super.setBackground(RequirementPanelHeader.COLOR_HOVER);
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
		super.setBackground(this.parent.isExpanded() ? RequirementPanelHeader.COLOR_SELECT : RequirementPanelHeader.COLOR_DEFAULT);
	}

	public final JLabel getNameLabel()
	{
		return this.name;
	}
}
