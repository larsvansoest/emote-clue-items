package com.larsvansoest.runelite.clueitems.toolbar.requirement;

import com.larsvansoest.runelite.clueitems.toolbar.requirement.header.RequirementPanelHeader;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public abstract class RequirementPanel extends JPanel
{
	private final RequirementPanelHeader requirementPanelHeader;

	private final JPanel foldContent;
	private final GridBagConstraints foldConstraints;

	private Boolean expanded;
	private RequirementStatus status;

	public RequirementPanel(String name, JLabel... icons) {
		this(false, name, icons);
	}

	public RequirementPanel(Boolean expanded, String name, JLabel... icons) {
		super(new GridBagLayout());
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		this.expanded = expanded;
		this.requirementPanelHeader = new RequirementPanelHeader(this, name, icons);

		this.foldContent = new JPanel(new GridBagLayout());
		this.foldConstraints = new GridBagConstraints();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.requirementPanelHeader, c);

		c.gridy++;
		this.foldContent.setVisible(this.expanded);
		super.add(this.foldContent, c);
	}

	public final RequirementStatus getStatus() {
		return this.status;
	}

	public final void setStatus(RequirementStatus status) {
		this.requirementPanelHeader.getNameLabel().setForeground(status.colour);
		this.status = status;
	}

	public final Boolean fold() {
		this.expanded = !this.expanded;
		this.foldContent.setVisible(this.expanded);
		return this.expanded;
	}

	public final void addChild(JPanel content) {
		this.foldContent.add(content, this.foldConstraints);
		this.foldConstraints.gridy++;
	}

	public final Boolean isExpanded()
	{
		return this.expanded;
	}
}