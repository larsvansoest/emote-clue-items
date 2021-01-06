package com.larsvansoest.runelite.clueitems.toolbar.component.searchbar;

import com.larsvansoest.runelite.clueitems.toolbar.EmoteClueItemsPanel;
import java.awt.event.KeyEvent;
import net.runelite.client.input.KeyListener;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.IconTextField;


public class SearchBarFactory
{
	private final EmoteClueItemsPanel emoteClueItemsPanel;

	public SearchBarFactory(EmoteClueItemsPanel emoteClueItemsPanel) {
		this.emoteClueItemsPanel = emoteClueItemsPanel;
	}

	public IconTextField build() {
		IconTextField searchBar = new IconTextField();
		searchBar.setIcon(IconTextField.Icon.SEARCH);
		searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
		searchBar.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				SearchBarFactory.this.search(searchBar);
			}
		});
		searchBar.addClearListener(() -> this.search(searchBar));
		return searchBar;
	}

	private void search(IconTextField searchBar) {
		this.emoteClueItemsPanel.search(searchBar.getText());
	}
}
