package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import com.larsvansoest.runelite.clueitems.ui.EmoteClueItemsPalette;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * A button with a location pin icon and a "maplink" label.
 * <p>
 * Clicking the button marks the location on the ground and on the RuneScape world map.
 *
 * @author Lars van Soest
 * @since 4.1.0
 */
public class MapLinkButton extends JPanel
{
	private static final String LABEL_TEXT = "maplink";
	private static final Icon PIN_ICON = new ImageIcon(EmoteClueItemsImages.Icons.Location.PIN);

	private static final Icon PIN_DELETE_ICON = new ImageIcon(EmoteClueItemsImages.Icons.Location.PIN_DELETE);

	private static final Font LABEL_FONT = FontManager.getRunescapeSmallFont();
	private static final Font LABEL_FONT_UNDERLINED = MapLinkButton.getUnderLinedFont(MapLinkButton.LABEL_FONT);

	private final JLabel label;

	private final JLabel pinLabel;

	private boolean showDelete;

	/**
	 * Creates the button.
	 *
	 * @param palette Colour scheme for the button.
	 */
	public MapLinkButton(final EmoteClueItemsPalette palette, final Runnable onClick, final Runnable onClickDelete) // TODO: Add location parameter and highlight it on click.
	{
		super(new GridBagLayout());
		super.setBackground(palette.getFoldContentColor());

		final Color labelColor = palette.getPropertyValueColor();
		final Color labelHoverColor = palette.getPropertyNameColor();
		this.label = this.getMapLinkLabel(labelColor);
		this.pinLabel = new JLabel();
		this.setShowDelete(false);

		super.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				if (MapLinkButton.this.showDelete) {
					onClickDelete.run();
				}
				else {
					onClick.run();
				}
				MapLinkButton.this.setShowDelete(!MapLinkButton.this.showDelete);
				super.mousePressed(e);
			}

			@Override
			public void mouseEntered(final MouseEvent e)
			{
				MapLinkButton.this.label.setForeground(labelHoverColor);
				MapLinkButton.this.label.setFont(MapLinkButton.LABEL_FONT_UNDERLINED);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(final MouseEvent e)
			{
				MapLinkButton.this.label.setForeground(labelColor);
				MapLinkButton.this.label.setFont(MapLinkButton.LABEL_FONT);
				super.mouseExited(e);
			}
		});

		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		super.add(this.pinLabel, c);
		c.insets.left = 5;
		c.weightx = 1;
		c.gridx++;
		super.add(this.label, c);
	}

	private static Font getUnderLinedFont(final Font font)
	{
		final Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		return font.deriveFont(attributes);
	}

	private JLabel getMapLinkLabel(final Color textColor)
	{
		final JLabel label = new JLabel();
		label.setText(MapLinkButton.LABEL_TEXT);
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(LABEL_FONT);
		label.setForeground(textColor);
		return label;
	}

	public void setShowDelete(boolean show) {
		this.showDelete = show;
		this.pinLabel.setIcon(show ? PIN_DELETE_ICON : PIN_ICON);
	}
}
