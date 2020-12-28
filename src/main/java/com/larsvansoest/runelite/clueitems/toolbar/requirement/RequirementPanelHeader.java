package com.larsvansoest.runelite.clueitems.toolbar.requirement;

import com.larsvansoest.runelite.clueitems.data.Images;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

class RequirementPanelHeader extends JPanel
{
	private static final Color DEFAULT_COLOR = ColorScheme.DARKER_GRAY_COLOR;
	private static final Color HOVER_COLOR = ColorScheme.DARKER_GRAY_HOVER_COLOR;
	private static final ImageIcon ICON_DOWN = new ImageIcon(Images.CHEVRON_DOWN);
	private static final ImageIcon ICON_LEFT = new ImageIcon(Images.CHEVRON_LEFT);

	private final RequirementPanel parent;
	private final JLabel name;
	private final JLabel fold;

	public RequirementPanelHeader(RequirementPanel parent, String name, JLabel... ribbons) {
		this.parent = parent;

		super.setBackground(RequirementPanelHeader.DEFAULT_COLOR);
		super.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				RequirementPanelHeader.this.parent.fold();
				RequirementPanelHeader.this.fold.setIcon(RequirementPanelHeader.this.parent.isExpanded() ? RequirementPanelHeader.ICON_DOWN : RequirementPanelHeader.ICON_LEFT);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				RequirementPanelHeader.this.setBackground(RequirementPanelHeader.HOVER_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				RequirementPanelHeader.this.setBackground(RequirementPanelHeader.DEFAULT_COLOR);
			}
		});

		super.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		c.insets.left = 5;
		c.insets.top = 2;
		c.insets.bottom = 2;
		c.weightx = 1;
		this.name = new JShadowedLabel();
		this.name.setText(name);
		this.name.setFont(FontManager.getRunescapeSmallFont());
		super.add(this.name, c);

		c.insets.left = 0;
		c.insets.right = 5;
		c.weightx = 0;
		c.anchor = GridBagConstraints.EAST;
		Arrays.stream(ribbons).forEach(ribbon -> {
			c.gridx++;
			super.add(ribbon, c);
		});

		c.gridx++;
		fold = new JLabel();
		fold.setIcon(ICON_LEFT);
		super.add(fold, c);
	}

	public final JLabel getNameLabel() {
		return this.name;
	}
}
