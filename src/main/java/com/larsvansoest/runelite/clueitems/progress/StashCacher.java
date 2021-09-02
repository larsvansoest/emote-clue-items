package com.larsvansoest.runelite.clueitems.progress;

import com.larsvansoest.runelite.clueitems.data.EmoteClueAssociations;
import lombok.RequiredArgsConstructor;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@RequiredArgsConstructor
public class StashCacher
{
	private final String group;
	private final String key;
	private final ConfigManager config;

	public void setStashFilled(final STASHUnit stashUnit, final boolean filled)
	{
		this.configure();
		final StringBuilder stashesBuilder = new StringBuilder(this.config.getRSProfileConfiguration(this.group, this.key));
		stashesBuilder.setCharAt(ArrayUtils.indexOf(EmoteClueAssociations.STASHUnitIdsOrdered, stashUnit.getObjectId()), filled ? '1' : '0');
		this.config.setRSProfileConfiguration(this.group, this.key, stashesBuilder.toString());
	}

	public boolean getStashFilled(final STASHUnit stashUnit)
	{
		this.configure();
		final String stashes = this.config.getRSProfileConfiguration(this.group, this.key);
		return stashes.charAt(ArrayUtils.indexOf(EmoteClueAssociations.STASHUnitIdsOrdered, stashUnit.getObjectId())) == '1';
	}

	private void configure()
	{
		final String stashes = this.config.getRSProfileConfiguration(this.group, this.key);
		if (Objects.isNull(stashes) || stashes.length() != EmoteClueAssociations.STASHUnitIdsOrdered.length)
		{
			this.config.setRSProfileConfiguration(this.group, this.key, StringUtils.repeat('0', EmoteClueAssociations.STASHUnitIdsOrdered.length));
		}
	}
}