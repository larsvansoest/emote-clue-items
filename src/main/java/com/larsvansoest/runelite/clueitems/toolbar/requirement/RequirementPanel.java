package com.larsvansoest.runelite.clueitems.toolbar.requirement;

import com.larsvansoest.runelite.clueitems.data.Images;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;

public abstract class RequirementPanel extends JPanel
{
	private static ImageIcon ICON_FOLD = new ImageIcon(Images.ARROW_DOWN_S_LINE);
	private static Border BORDER_FOLDED = new EmptyBorder(-3, 0, -3, 0);

	private final JLabel name;
	private final JLabel icon;
	private final JLabel fold;

	private RequirementStatus status;

	public RequirementPanel() {
		this.name = new JLabel();
		this.icon = new JLabel();
		this.fold = new JLabel();
		this.fold.setIcon(ICON_FOLD);
		this.add(this.name, this.icon, this.fold);
		super.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		super.setBorder(BORDER_FOLDED);
	}

	public final void setIcon(ImageIcon icon) {
		this.icon.setIcon(icon);
	}

	public final Icon getIcon() {
		return this.icon.getIcon();
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

	public final void add(JComponent... components) {
		for (Component component : components) {
			super.add(component);
		}
	}
}
