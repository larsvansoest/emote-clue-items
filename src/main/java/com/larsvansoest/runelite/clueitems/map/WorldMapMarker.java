package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldMapMarker extends WorldMapPoint
{
	private final BufferedImage marker = EmoteClueItemsImages.Utils.createBufferFromImage(EmoteClueItemsImages.RuneLite.CLUE_ARROW);
	private final Point markerPoint = new Point(marker.getWidth() / 2, marker.getHeight());
	private final WorldMapOrb orb;

	public WorldMapMarker(final WorldPoint worldPoint, final BufferedImage icon, final String name)
	{
		super(worldPoint, icon);
		super.setSnapToEdge(true);
		super.setJumpOnClick(true);
		super.setName(name);

		this.orb = new WorldMapOrb(worldPoint, icon);
		this.setIcon(icon);

	}

	public void rotateOrb(final Client client)
	{
		this.orb.updateOrientation(client);
		if (super.isCurrentlyEdgeSnapped())
		{
			super.setImage(this.orb.getImage());
		}
	}

	public void setIcon(final BufferedImage icon)
	{
		this.orb.setIcon(icon);
		EmoteClueItemsImages.Utils.drawIconWithBackground(this.marker, EmoteClueItemsImages.RuneLite.CLUE_ARROW, icon, 2, 2.5);
	}

	@Override
	public void onEdgeSnap()
	{
		this.orb.resetOrientation();
		super.setImagePoint(null);
	}

	@Override
	public void onEdgeUnsnap()
	{
		super.setImage(this.marker);
		super.setImagePoint(this.markerPoint);
	}

	public void setWorldPoint(WorldPoint worldPoint) {
		super.setWorldPoint(worldPoint);
		this.orb.setWorldPoint(worldPoint);
	}

}