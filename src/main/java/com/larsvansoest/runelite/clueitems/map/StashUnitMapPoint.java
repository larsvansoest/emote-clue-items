package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import net.runelite.api.coords.WorldPoint;

public class StashUnitMapPoint extends WorldMapMarker
{
	private static final String NAME = "STASH (Emote Clue Items)";
	private final StashUnit stash;

	public StashUnitMapPoint(final WorldPoint worldPoint, final StashUnit stash, final boolean built)
	{
		super(worldPoint, EmoteClueImages.getStashUnit(stash.getType(), built, true), NAME);
		this.stash = stash;
	}

	public void setStashUnitBuildStatus(final boolean built)
	{
		super.setImage(EmoteClueImages.getStashUnit(this.stash.getType(), built, true));
	}
}
