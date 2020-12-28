package com.larsvansoest.runelite.clueitems.toolbar.requirement;

import com.larsvansoest.runelite.clueitems.data.Images;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public abstract class RequirementPanel extends JPanel
{
	private RequirementStatus status;
	private final RequirementPanelHeader header;

	private Boolean expanded;
	private JLabel temp;

	public RequirementPanel(ImageIcon icon, String name, EmoteClue... emoteClues) {
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		super.setLayout(new GridBagLayout());

		this.header = new RequirementPanelHeader(this, name, Arrays.stream(emoteClues).map(EmoteClue::getDifficulty).distinct().map(difficulty -> {
			JLabel ribbon = new JLabel();
			ribbon.setIcon(new ImageIcon(Images.getRibbon(difficulty)));
			return ribbon;
		}).toArray(JLabel[]::new));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.header, c);

		c.gridy++;
		this.expanded = false;
		temp = new JLabel();
		temp.setText("Lorem Ipsum");
		temp.setVisible(this.expanded);
		super.add(temp, c);
	}

	public final RequirementStatus getStatus() {
		return this.status;
	}

	public final void setStatus(RequirementStatus status) {
		this.header.getNameLabel().setForeground(status.colour);
		this.status = status;
	}

	public final void fold() {
		this.expanded = !this.expanded;
		temp.setVisible(this.expanded);
	}

	public final Boolean isExpanded() {
		return this.expanded;
	}
}