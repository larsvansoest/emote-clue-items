package com.larsvansoest.runelite.clueitems.util;

import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import java.awt.image.BufferedImage;
import net.runelite.client.util.ImageUtil;

public class ImageProvider
{
	private final BufferedImage allRibbonsLogo;
	private final BufferedImage beginnerRibbon;
	private final BufferedImage easyRibbon;
	private final BufferedImage mediumRibbon;
	private final BufferedImage hardRibbon;
	private final BufferedImage eliteRibbon;
	private final BufferedImage masterRibbon;
	private final BufferedImage chevronDown;
	private final BufferedImage chevronLeft;
	private final BufferedImage asterisk;
	private final BufferedImage allRibbons;
	private final BufferedImage checkSquareComplete;
	private final BufferedImage checkSquareInProgress;
	private final BufferedImage checkSquareUnfinished;
	private final BufferedImage checkSquareAll;

	public ImageProvider() {
		this.allRibbonsLogo = this.bufferedImage("/icons/icon.png");
		this.beginnerRibbon = this.bufferedImage("/icons/ribbons/beginner_ribbon.png");
		this.easyRibbon = this.bufferedImage("/icons/ribbons/easy_ribbon.png");
		this.mediumRibbon = this.bufferedImage("/icons/ribbons/medium_ribbon.png");
		this.hardRibbon = this.bufferedImage("/icons/ribbons/hard_ribbon.png");
		this.eliteRibbon = this.bufferedImage("/icons/ribbons/elite_ribbon.png");
		this.masterRibbon = this.bufferedImage("/icons/ribbons/master_ribbon.png");
		this.chevronDown = this.bufferedImage("/icons/chevron-down.png");
		this.chevronLeft = this.bufferedImage("/icons/chevron-left.png");
		this.asterisk = this.bufferedImage("/icons/asterisk.png");
		this.allRibbons = this.bufferedImage("/icons/ribbons/allribbons.png");
		this.checkSquareComplete = this.bufferedImage("/icons/check-square-complete.png");
		this.checkSquareInProgress = this.bufferedImage("/icons/check-square-inprogress.png");
		this.checkSquareUnfinished = this.bufferedImage("/icons/check-square-incomplete.png");
		this.checkSquareAll = this.bufferedImage("/icons/check-square-all.png");
	}

	private BufferedImage bufferedImage(String resourcePath) {
		return ImageUtil.getResourceStreamFromClass(this.getClass(), resourcePath);
	}

	public final BufferedImage getRibbon(Difficulty difficulty) {
		switch (difficulty) {
			case Beginner: return this.beginnerRibbon;
			case Easy: return this.easyRibbon;
			case Medium: return this.mediumRibbon;
			case Hard: return this.hardRibbon;
			case Elite: return this.eliteRibbon;
			case Master: return this.masterRibbon;
			default: throw new IllegalArgumentException();
		}
	}

	public BufferedImage getAllRibbonsLogo()
	{
		return this.allRibbonsLogo;
	}

	public final BufferedImage getBeginnerRibbon()
	{
		return this.beginnerRibbon;
	}

	public final BufferedImage getEasyRibbon()
	{
		return this.easyRibbon;
	}

	public final BufferedImage getMediumRibbon()
	{
		return this.mediumRibbon;
	}

	public final BufferedImage getHardRibbon()
	{
		return this.hardRibbon;
	}

	public final BufferedImage getEliteRibbon()
	{
		return this.eliteRibbon;
	}

	public final BufferedImage getMasterRibbon()
	{
		return this.masterRibbon;
	}

	public final BufferedImage getChevronDown()
	{
		return this.chevronDown;
	}

	public final BufferedImage getChevronLeft()
	{
		return this.chevronLeft;
	}

	public final BufferedImage getAsterisk()
	{
		return this.asterisk;
	}

	public final BufferedImage getAllRibbons() { return this.allRibbons; }

	public BufferedImage getCheckSquareComplete()
	{
		return this.checkSquareComplete;
	}

	public BufferedImage getCheckSquareInProgress()
	{
		return this.checkSquareInProgress;
	}

	public BufferedImage getCheckSquareUnfinished()
	{
		return this.checkSquareUnfinished;
	}

	public BufferedImage getCheckSquareAll()
	{
		return this.checkSquareAll;
	}
}
