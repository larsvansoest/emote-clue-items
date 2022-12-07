package com.larsvansoest.runelite.clueitems.map;

import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import net.runelite.api.coords.WorldPoint;

public class StashUnitMapPoint extends WorldMapMarker
{
	private static final String NAME = "STASH (Emote Clue Items)";
	private final StashUnit stash;

	public StashUnitMapPoint(final WorldPoint worldPoint, final StashUnit stash, final boolean built)
	{
		super(worldPoint, EmoteClueItemsImages.Icons.RuneScape.StashUnit.get(stash.getType(), built, true), NAME);
		// TODO: include stash name in name.
		this.stash = stash;
	}

	public void setStashUnitBuildStatus(final boolean built)
	{
		super.setMarkerIcon(EmoteClueItemsImages.Icons.RuneScape.StashUnit.get(this.stash.getType(), built, true));
	}
}
