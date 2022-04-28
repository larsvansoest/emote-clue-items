package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;
import java.util.Objects;

public class EmoteClueItemsWorldOverlay extends Overlay
{
	private final Client client;
	@Setter
	private LocalPoint localPoint;

	public EmoteClueItemsWorldOverlay(final Client client)
	{
		this.setPosition(OverlayPosition.DYNAMIC);
		this.setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
	}

	@Override
	public Dimension render(final Graphics2D graphics)
	{
		if (Objects.nonNull(this.localPoint))
		{
			OverlayUtil.renderTileOverlay(this.client, graphics, this.localPoint, EmoteClueImages.getScroll(EmoteClueDifficulty.Master), EmoteClueDifficulty.Master.getColor());
		}

		return null;
	}
}
