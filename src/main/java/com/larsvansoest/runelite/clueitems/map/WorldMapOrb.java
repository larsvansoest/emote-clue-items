package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.RenderOverview;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldMapOrb
{
	private static final BufferedImage ORB_IMAGE = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);

	private final WorldPoint worldPoint;

	@Getter
	private BufferedImage image = ORB_IMAGE;

	public WorldMapOrb(final WorldPoint worldPoint)
	{
		this.worldPoint = worldPoint;
		this.resetOrientation();
	}

	private static Point mapWorldPointToGraphicsPoint(final Client client, final WorldPoint worldPoint)
	{
		final RenderOverview ro = client.getRenderOverview();

		if (!ro.getWorldMapData().surfaceContainsPosition(worldPoint.getX(), worldPoint.getY()))
		{
			return null;
		}

		final float pixelsPerTile = ro.getWorldMapZoom();

		final Widget map = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);
		if (map != null)
		{
			final Rectangle worldMapRect = map.getBounds();

			final int widthInTiles = (int) Math.ceil(worldMapRect.getWidth() / pixelsPerTile);
			final int heightInTiles = (int) Math.ceil(worldMapRect.getHeight() / pixelsPerTile);

			final Point worldMapPosition = ro.getWorldMapPosition();

			//Offset in tiles from anchor sides
			final int yTileMax = worldMapPosition.getY() - heightInTiles / 2;
			final int yTileOffset = (yTileMax - worldPoint.getY() - 1) * -1;
			final int xTileOffset = worldPoint.getX() + widthInTiles / 2 - worldMapPosition.getX();

			int xGraphDiff = ((int) (xTileOffset * pixelsPerTile));
			int yGraphDiff = (int) (yTileOffset * pixelsPerTile);

			//Center on tile.
			yGraphDiff -= pixelsPerTile - Math.ceil(pixelsPerTile / 2);
			xGraphDiff += pixelsPerTile - Math.ceil(pixelsPerTile / 2);

			yGraphDiff = worldMapRect.height - yGraphDiff;
			yGraphDiff += (int) worldMapRect.getY();
			xGraphDiff += (int) worldMapRect.getX();

			return new Point(xGraphDiff, yGraphDiff);
		}
		return null;
	}

	private static Rectangle getWorldMapClipArea(final Client client)
	{
		final Widget widget = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);
		return widget == null ? null : widget.getBounds();
	}

	public void resetOrientation()
	{
		this.setImage(-1);
	}

	public void updateOrientation(final Client client)
	{
		final Rectangle mapViewArea = getWorldMapClipArea(client);
		final Point drawPoint = mapWorldPointToGraphicsPoint(client, this.worldPoint);
		if (mapViewArea != null && drawPoint != null && !mapViewArea.contains(drawPoint.getX(), drawPoint.getY()))
		{
			final int rotation = this.getRotation(drawPoint.getX(),
					drawPoint.getY(),
					(int) mapViewArea.getMinX(),
					(int) mapViewArea.getMaxX(),
					(int) mapViewArea.getMinY(),
					(int) mapViewArea.getMaxY()
			);
			this.setImage(rotation);
		}
	}

	private void setImage(final int rotation)
	{
		// TODO: how to clear the image?
		this.image.getGraphics().clearRect(0, 0, this.image.getWidth(), this.image.getHeight());
		this.image.getGraphics().drawImage(EmoteClueItemsImages.MapOrb.get(rotation), 0, 0, null);
		this.image.getGraphics().drawImage(EmoteClueItemsImages.Icons.RuneScape.StashUnit.get(StashUnit.Type.Crate, false, true), 0, 0, null);
	}

	private int getRotation(final int x, final int y, final int minX, final int maxX, final int minY, final int maxY)
	{
		if (x <= minX)
		{
			return this.getLeftRotation(y, minY, maxY);
		}
		if (x <= maxX)
		{
			return this.getCenterRotation(y, minY, maxY);
		}
		return this.getRightRotation(y, minY, maxY);
	}

	private int getLeftRotation(final double y, final double minY, final double maxY)
	{
		if (y <= minY)
		{
			return 315;
		}
		if (y <= maxY)
		{
			return 270;
		}
		return 225;
	}

	private int getCenterRotation(final double y, final double minY, final double maxY)
	{
		if (y <= minY)
		{
			return 0;
		}
		return 180;
	}

	private int getRightRotation(final int y, final int minY, final int maxY)
	{
		if (y <= minY)
		{
			return 45;
		}
		if (y <= maxY)
		{
			return 90;
		}
		return 135;
	}
}
