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
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

public abstract class RequirementPanel extends JPanel
{
	private static ImageIcon ICON_FOLD = new ImageIcon(Images.ARROW_DOWN_S_LINE);

	private final JLabel name;
	//private final JLabel icon;

	private RequirementStatus status;

	public RequirementPanel(ImageIcon icon, String name, EmoteClue... emoteClues) {
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
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
		Arrays.stream(emoteClues).map(EmoteClue::getDifficulty).distinct().forEach(difficulty ->
		{
			c.gridx++;
			JLabel tier = new JLabel();
			tier.setIcon(new ImageIcon(Images.getRibbon(difficulty)));
			super.add(tier, c);
		});

		c.gridx++;
		JLabel fold = new JLabel();
		fold.setIcon(ICON_FOLD);
		super.add(fold, c);
	}

	public final void setName(String name) {
		this.name.setText(name);
	}

	public final String getName() {
		return this.name.getName();
	}

	public final RequirementStatus getStatus() {
		return this.status;
	}

	public final void setStatus(RequirementStatus status) {
		this.name.setForeground(status.colour);
		this.status = status;
	}
}