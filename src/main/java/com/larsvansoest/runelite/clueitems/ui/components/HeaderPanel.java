package com.larsvansoest.runelite.clueitems.ui.components;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a plugin panel header with the title and useful links.
 */
public class HeaderPanel extends JPanel {

    /**
     * Creates a plugin panel header.
     * @param title The title of the plugin panel.
     * @param version The version of the plugin.
     * @param gitHubUrl A URL to the GitHub repository page.
     * @param payPalUrl A donation url.
     */
    public HeaderPanel(final String title, final String version, final String gitHubUrl, final String payPalUrl) {
        super(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets.bottom = 9;

        c.insets.top = 6;
        super.add(getTitle(title), c);
        c.weightx = 0;
        c.insets.left = 11;
        c.gridx++;

        final String patchNotesUrl = String.format("%s/releases/tag/%s", gitHubUrl, version);
        super.add(new LinkButton(version, patchNotesUrl, "View patch notes.", FontManager.getRunescapeFont()), c);
        c.gridx++;

        c.insets.top = 4;
        super.add(new LinkButton(EmoteClueItemsImages.Icons.GITHUB, gitHubUrl, "Visit GitHub webpage."), c);
        c.gridx++;

        super.add(new LinkButton(EmoteClueItemsImages.Icons.PAYPAL, payPalUrl, "Buy me a coffee!", 1.3f), c);
    }

    private JLabel getTitle(final String title) {
        final JLabel label = new JShadowedLabel(title);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        return label;
    }
}
