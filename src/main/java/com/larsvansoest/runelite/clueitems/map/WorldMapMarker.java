package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldMapMarker extends WorldMapPoint
{
	private static final BufferedImage BACKGROUND_IMAGE = EmoteClueImages.RuneLite.CLUE_ARROW;
	private static final BufferedImage WORLD_IMAGE = new BufferedImage(BACKGROUND_IMAGE.getWidth(), BACKGROUND_IMAGE.getHeight(), BufferedImage.TYPE_INT_ARGB);
	private static final Point WORLD_IMAGE_POINT = new Point(WORLD_IMAGE.getWidth() / 2, WORLD_IMAGE.getHeight());
	private static final Graphics GRAPHICS = WORLD_IMAGE.getGraphics();

	private BufferedImage orb;

	public WorldMapMarker(final WorldPoint worldPoint, final BufferedImage image, final String name)
	{
		super(worldPoint, image);
		super.setSnapToEdge(true);
		super.setJumpOnClick(true);
		super.setName(name);

		this.orb = getOrbImage(0);
		this.setImage(image);
	}

	private static BufferedImage getOrbImage(final int rotation)
	{
		switch (rotation)
		{
			case 45:
				return EmoteClueImages.Orb.ORB_45;
			case 90:
				return EmoteClueImages.Orb.ORB_90;
			case 135:
				return EmoteClueImages.Orb.ORB_135;
			case 180:
				return EmoteClueImages.Orb.ORB_180;
			case 225:
				return EmoteClueImages.Orb.ORB_225;
			case 270:
				return EmoteClueImages.Orb.ORB_270;
			default:
				return EmoteClueImages.Orb.ORB_0;
		}
	}

	public void rotateOrb(final int rotation)
	{
		this.orb = getOrbImage(rotation);
	}

	public void setImage(final BufferedImage image)
	{
		GRAPHICS.drawImage(BACKGROUND_IMAGE, 0, 0, null);
		final int x = Math.max(BACKGROUND_IMAGE.getWidth() - image.getWidth() / 2, 0);
		final int y = Math.max((int) (BACKGROUND_IMAGE.getHeight() - image.getHeight() / 2.5), 0);
		GRAPHICS.drawImage(image, x, y, null);
	}

	@Override
	public void onEdgeSnap()
	{
		super.setImage(this.orb);
		super.setImagePoint(null);
	}

	@Override
	public void onEdgeUnsnap()
	{
		super.setImage(WORLD_IMAGE);
		super.setImagePoint(WORLD_IMAGE_POINT);
	}
}