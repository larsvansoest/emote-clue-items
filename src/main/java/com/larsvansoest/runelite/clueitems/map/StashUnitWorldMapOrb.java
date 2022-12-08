package com.larsvansoest.runelite.clueitems.map;


import com.larsvansoest.runelite.clueitems.EmoteClueItemsImages;
import com.larsvansoest.runelite.clueitems.data.StashUnit;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StashUnitWorldMapOrb extends WorldMapOrb {
    @Getter
    private BufferedImage image;

    public StashUnitWorldMapOrb(WorldPoint worldPoint) {
        super(worldPoint, null);
    }

    @Override
    public void updateOrientation(final Client client)
    {
        super.updateOrientation(client);
    }
}
