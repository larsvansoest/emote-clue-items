package com.larsvansoest.runelite.clueitems.progress;

import lombok.RequiredArgsConstructor;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StashCacher
{
	private static final int[] STASH_IDS_ORDERED = Arrays.stream(STASHUnit.values()).mapToInt(STASHUnit::getObjectId).sorted().toArray();
	private static final String STASH_IDS_ORDERED_FINGERPRINT = Arrays.stream(STASH_IDS_ORDERED).mapToObj(String::valueOf).collect(Collectors.joining(","));
	private static final String FINGERPRINT_KEY = "_fingerprint";

	private final String group;
	private final String key;
	private final ConfigManager config;

	public void setStashFilled(final STASHUnit stashUnit, final boolean filled)
	{
		final StringBuilder stashesBuilder = new StringBuilder(this.config.getRSProfileConfiguration(this.group, this.key));
		stashesBuilder.setCharAt(ArrayUtils.indexOf(STASH_IDS_ORDERED, stashUnit.getObjectId()), filled ? '1' : '0');
		this.config.setRSProfileConfiguration(this.group, this.key, stashesBuilder.toString());
	}

	public boolean getStashFilled(final STASHUnit stashUnit)
	{
		final String stashes = this.config.getRSProfileConfiguration(this.group, this.key);
		return stashes.charAt(ArrayUtils.indexOf(STASH_IDS_ORDERED, stashUnit.getObjectId())) == '1';
	}

	public void validate()
	{
		final String stashes = this.config.getRSProfileConfiguration(this.group, this.key);
		final String fingerPrint = this.config.getRSProfileConfiguration(this.group, FINGERPRINT_KEY);
		if (Objects.isNull(stashes) || Objects.isNull(fingerPrint) || !fingerPrint.equals(STASH_IDS_ORDERED_FINGERPRINT) || stashes.length() != STASH_IDS_ORDERED.length)
		{
			this.config.setRSProfileConfiguration(this.group, this.key, StringUtils.repeat('0', STASH_IDS_ORDERED.length));
			this.config.setRSProfileConfiguration(this.group, FINGERPRINT_KEY, STASH_IDS_ORDERED_FINGERPRINT);
		}
	}
}