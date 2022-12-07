package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldMapMarker extends WorldMapPoint
{
	private static final BufferedImage BACKGROUND_IMAGE = EmoteClueItemsImages.RuneLite.CLUE_ARROW;
	private static final BufferedImage WORLD_IMAGE = new BufferedImage(BACKGROUND_IMAGE.getWidth(), BACKGROUND_IMAGE.getHeight(), BufferedImage.TYPE_INT_ARGB);
	private static final Point WORLD_IMAGE_POINT = new Point(WORLD_IMAGE.getWidth() / 2, WORLD_IMAGE.getHeight());
	private static final Graphics GRAPHICS = WORLD_IMAGE.getGraphics();

	private final WorldMapOrb orb;
	private boolean edgeSnapped;

	public WorldMapMarker(final WorldPoint worldPoint, final BufferedImage image, final String name)
	{
		super(worldPoint, image);
		super.setSnapToEdge(true);
		super.setJumpOnClick(true);
		super.setName(name);

		this.orb = new WorldMapOrb(worldPoint);
		this.setMarkerIcon(image);

		this.edgeSnapped = false;
	}

	public void rotateOrb(final Client client)
	{
		this.orb.updateOrientation(client);
		if (this.edgeSnapped)
		{
			super.setImage(this.orb.getImage());
		}
	}

	public void setMarkerIcon(final BufferedImage image)
	{
		GRAPHICS.drawImage(BACKGROUND_IMAGE, 0, 0, null);
		final int x = Math.max((BACKGROUND_IMAGE.getWidth() - image.getWidth()) / 2, 0);
		final int y = Math.max((int)((BACKGROUND_IMAGE.getHeight() - image.getHeight()) / 2.5), 0);
		GRAPHICS.drawImage(image, x, y, null);
	}

	@Override
	public void onEdgeSnap()
	{
		this.edgeSnapped = true;
		this.orb.resetOrientation();
		super.setImagePoint(null);
	}

	@Override
	public void onEdgeUnsnap()
	{
		this.edgeSnapped = false;
		super.setImage(WORLD_IMAGE);
		super.setImagePoint(WORLD_IMAGE_POINT);
	}
}