package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import net.runelite.api.coords.WorldPoint;
import net.runelite.http.api.worlds.WorldRegion;

import java.awt.image.BufferedImage;

public class StashUnitWorldMapMarker extends WorldMapMarker
{
	private static BufferedImage getIcon(final StashUnit stash, boolean built) {
		return EmoteClueItemsImages.Icons.RuneScape.StashUnit.get(stash.getType(), built, true);
	}

	private static String getName(final StashUnit stash) {
		return String.format("STASH (%s)", stash.getName());
	}

	public StashUnitWorldMapMarker(final StashUnit stash, final boolean built)
	{
		super(stash.getStashUnit().getWorldPoints()[0], getIcon(stash, built), getName(stash));
	}

	public void setStashUnit(final StashUnit stash, final boolean built) {
		super.setWorldPoint(stash.getStashUnit().getWorldPoints()[0]);
		super.setIcon(getIcon(stash, built));
		super.setName(getName(stash));
	}
}
