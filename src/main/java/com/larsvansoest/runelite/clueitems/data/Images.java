package com.larsvansoest.runelite.clueitems.data;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsPlugin;
import java.awt.image.BufferedImage;
import net.runelite.client.util.ImageUtil;

public abstract class Images
{
	public static final BufferedImage CLIMBING_BOOTS_LOGO = bufferedImage("/icons/icon.png");
	public static final BufferedImage BEGINNER_RIBBON = bufferedImage("/icons/ribbons/beginner_ribbon.png");
	public static final BufferedImage EASY_RIBBON = bufferedImage("/icons/ribbons/easy_ribbon.png");
	public static final BufferedImage MEDIUM_RIBBON = bufferedImage("/icons/ribbons/medium_ribbon.png");
	public static final BufferedImage HARD_RIBBON = bufferedImage("/icons/ribbons/hard_ribbon.png");
	public static final BufferedImage ELITE_RIBBON = bufferedImage("/icons/ribbons/elite_ribbon.png");
	public static final BufferedImage MASTER_RIBBON = bufferedImage("/icons/ribbons/master_ribbon.png");
	public static final BufferedImage ARROW_DOWN_S_LINE = bufferedImage("/icons/arrow-down-s-line.png");

	private static BufferedImage bufferedImage(String resourcePath) {
		return ImageUtil.getResourceStreamFromClass(EmoteClueItemsPlugin.class, resourcePath);
	}
}
