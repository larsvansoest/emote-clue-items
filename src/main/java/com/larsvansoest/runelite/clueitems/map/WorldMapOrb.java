package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.RenderOverview;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class WorldMapOrb
{
	private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	private static BufferedImage createBufferFromImage(BufferedImage image) {
		return ImageUtil.fillImage(image, TRANSPARENT);
	}

	private static void drawIcon(BufferedImage bufferedImage, BufferedImage background, BufferedImage icon, double devideX, double devideY) {
		Graphics g2d = bufferedImage.createGraphics();
		g2d.drawImage(background, 0, 0, null);
		if (Objects.nonNull(icon)) {
			final int x = Math.max((int)((background.getWidth() - icon.getWidth()) / devideX), 0);
			final int y = Math.max((int)((background.getHeight() - icon.getHeight()) / devideY), 0);
			g2d.drawImage(icon, x, y, null);
		}
		g2d.dispose();
	}

	private final WorldPoint worldPoint;

	private final BufferedImage orb = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB);
	private final BufferedImage orb0 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_0);
	private final BufferedImage orb90 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_90);
	private final BufferedImage orb180 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_180);
	private final BufferedImage orb270 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_270);
	private final BufferedImage orb45 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_45);
	private final BufferedImage orb135 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_135);
	private final BufferedImage orb225 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_225);
	private final BufferedImage orb315 = createBufferFromImage(EmoteClueItemsImages.MapOrb.ORB_315);

	public void setIcon(BufferedImage icon) {
		drawIcon(orb, EmoteClueItemsImages.MapOrb.ORB, icon, 2, 2);

		//horizontal edges
		drawIcon(orb0, EmoteClueItemsImages.MapOrb.ORB_0, icon, 2, 1.35);
		drawIcon(orb180, EmoteClueItemsImages.MapOrb.ORB_180, icon, 2, 3);

		//vertical edges
		drawIcon(orb90, EmoteClueItemsImages.MapOrb.ORB_90, icon, 5, 2);
		drawIcon(orb270, EmoteClueItemsImages.MapOrb.ORB_270, icon, 1.25, 2);

		//corners
		drawIcon(orb225, EmoteClueItemsImages.MapOrb.ORB_225, icon, 2, 2);
		drawIcon(orb45, EmoteClueItemsImages.MapOrb.ORB_45, icon, 2, 2);
		drawIcon(orb315, EmoteClueItemsImages.MapOrb.ORB_315, icon, 2, 2);
		drawIcon(orb135, EmoteClueItemsImages.MapOrb.ORB_135, icon, 2, 2);
	}

	@Getter
	private BufferedImage image = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);

	public WorldMapOrb(final WorldPoint worldPoint, final BufferedImage icon)
	{
		this.worldPoint = worldPoint;
		this.resetOrientation();
		this.setIcon(icon);
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

	private BufferedImage getBuffer(final int rotation) {
		switch (rotation)
		{
			case 0:
				return orb0;
			case 45:
				return orb45;
			case 90:
				return orb90;
			case 135:
				return orb135;
			case 180:
				return orb180;
			case 225:
				return orb225;
			case 270:
				return orb270;
			case 315:
				return orb315;
			default:
				return orb;
		}
	}

	private void setImage(final int rotation)
	{
		this.image = getBuffer(rotation);
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
