package com.larsvansoest.runelite.clueitems.ui.components;

import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * Displays a button to open a URL in the web browser.
 * <p>
 * Supports text and images.
 */
public class LinkButton extends JShadowedLabel {
    /**
     * Creates the link button with an image.
     * <p>
     * The image is illuminated on hover with a factor of 2 by default.
     * @param image The image displayed by the link button.
     * @param url The url that should be opened by the web browser
     * @param tooltip The tooltip that is displayed when hovering over the button.
     */
    public LinkButton (final Image image, final String url, final String tooltip) {
        this(image, url, tooltip, 2);
    }

    /**
     * Creates the link button with an image.
     * <p>
     * The image is illuminated on hover.
     * @param image The image displayed by the link button.
     * @param url The url that should be opened by the web browser
     * @param tooltip The tooltip that is displayed when hovering over the button.
     * @param luminancePercentage The scale of the image illumination on hover.
     */
    public LinkButton (final Image image, final String url, final String tooltip, final float luminancePercentage) {
        final ImageIcon icon = new ImageIcon(image);
        final ImageIcon illuminatedIcon = new ImageIcon(ImageUtil.luminanceScale(image, luminancePercentage));
        super.setToolTipText(tooltip);
        super.setIcon(icon);
        super.setHorizontalAlignment(SwingConstants.CENTER);
        super.setVerticalAlignment(SwingConstants.CENTER);
        super.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                LinkBrowser.browse(url);
            }

            @Override
            public void mouseEntered(final MouseEvent e)
            {
                LinkButton.super.setIcon(illuminatedIcon);
            }

            @Override
            public void mouseExited(final MouseEvent e)
            {
                LinkButton.super.setIcon(icon);
            }
        });
    }

    /**
     * Creates the link button with text.
     * <p>
     * The text is underlined on hover.
     * @param text The text displayed by the link button.
     * @param url The url that should be opened by the web browser
     * @param tooltip The tooltip that is displayed when hovering over the button.
     * @param font The font of the text displayed by the button.
     */
    public LinkButton(final String text, final String url, final String tooltip, final Font font) {
        super.setText(text);
        super.setToolTipText(tooltip);

        Map attributes = LinkButton.super.getFont().getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font underlinedFont = font.deriveFont(attributes);

        super.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                LinkBrowser.browse(url);
            }

            @Override
            public void mouseEntered(final MouseEvent e)
            {
                LinkButton.super.setFont(underlinedFont);
            }

            @Override
            public void mouseExited(final MouseEvent e)
            {
                LinkButton.super.setFont(font);
            }
        });
    }

}
