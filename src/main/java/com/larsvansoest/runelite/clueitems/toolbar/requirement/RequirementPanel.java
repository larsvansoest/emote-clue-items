package com.larsvansoest.runelite.clueitems.toolbar.requirement;

import com.larsvansoest.runelite.clueitems.toolbar.requirement.header.RequirementPanelHeader;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public abstract class RequirementPanel extends JPanel
{
	private final RequirementPanelHeader header;

	private final JPanel foldContent;

	private Boolean expanded;
	private RequirementStatus status;

	public RequirementPanel(String name, JPanel foldContent, JLabel... icons) {
		this(false, name, foldContent, icons);
	}

	public RequirementPanel(Boolean expanded, String name, JPanel foldContent, JLabel... icons) {
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		super.setLayout(new GridBagLayout());

		this.expanded = expanded;
		this.foldContent = foldContent;
		this.header = new RequirementPanelHeader(this, name, icons);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.header, c);

		c.gridy++;
		foldContent.setVisible(this.expanded);
		super.add(foldContent, c);
	}

	public JPanel getFoldContent()
	{
		return this.foldContent;
	}

	public final RequirementStatus getStatus() {
		return this.status;
	}

	public final void setStatus(RequirementStatus status) {
		this.header.getNameLabel().setForeground(status.colour);
		this.status = status;
	}

	public final Boolean fold() {
		this.expanded = !this.expanded;
		foldContent.setVisible(this.expanded);
		return this.expanded;
	}

	public final Boolean isExpanded()
	{
		return this.expanded;
	}
}