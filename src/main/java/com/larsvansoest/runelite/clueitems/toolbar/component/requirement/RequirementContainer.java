package com.larsvansoest.runelite.clueitems.toolbar.component.requirement;

import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.panel.RequirementPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class RequirementContainer extends JPanel
{
	private final GridBagConstraints c;
	private Collection<? extends RequirementPanel> requirementPanelCollection;

	public RequirementContainer(Collection<? extends RequirementPanel> requirementPanelCollection) {
		super(new GridBagLayout());
		this.c = new GridBagConstraints();
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.gridx = 0;
		this.c.gridy = 0;
		this.c.weightx = 1;
		this.load(requirementPanelCollection);
	}

	public void load(Collection<? extends RequirementPanel> requirementPanelCollection) {
		this.requirementPanelCollection = requirementPanelCollection;
		this.display(requirementPanelCollection.stream());
	}

	public void filter(Function<Object, Boolean> predicate) {
		this.display(this.requirementPanelCollection.stream().filter(predicate::apply));
	}

	public void setBorderColor(Color color)
	{
		super.setBorder(new MatteBorder(1, 0, 0, 0, color));
	}

	private void display(Stream<? extends RequirementPanel> requirementPanels) {
		super.removeAll();
		this.c.gridy = 0;
		requirementPanels.forEach(requirementPanel -> {
			super.add(requirementPanel, this.c);
			this.c.gridy++;
		});
		super.revalidate();
		super.repaint();
	}
}
