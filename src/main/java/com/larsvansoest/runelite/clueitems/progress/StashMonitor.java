package com.larsvansoest.runelite.clueitems.progress;

import com.larsvansoest.runelite.clueitems.data.StashUnit;
import lombok.RequiredArgsConstructor;
import net.runelite.client.config.ConfigManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provides utility to write and read stash fill status to Runelite's {@link net.runelite.client.config.ConfigManager}.
 *
 * @author Lars van Soest
 * @since 3.0.0
 */
@RequiredArgsConstructor
class StashMonitor
{
	private static final int[] STASH_IDS_ORDERED = Arrays.stream(StashUnit.values()).mapToInt(stashUnit -> stashUnit.getStashUnit().getObjectId()).sorted().toArray();
	private static final String STASH_IDS_ORDERED_FINGERPRINT = Arrays.stream(STASH_IDS_ORDERED).mapToObj(String::valueOf).collect(Collectors.joining("-"));

	// Configuration group and key previously used. Changed to a less risky string format.
	private static final String LEGACY_GROUP = "[EmoteClueItems]";
	private static final String LEGACY_FILLED_KEY = "STASHUnit fill statuses";
	private static final String LEGACY_FINGERPRINT_KEY = "_fingerprint";

	private static final String GROUP = "emote-clue-items";
	private static final String FILLED_KEY = "stashes-filled";
	private static final String FINGERPRINT_KEY = "stashes-fingerprint";

	private final ConfigManager config;

	/**
	 * Toggles STASHUnit filled status.
	 *
	 * @param stashUnit The STASHunit.
	 * @param filled    The filled status.
	 */
	public void setStashFilled(final StashUnit stashUnit, final boolean filled)
	{
		final String stashes = this.config.getRSProfileConfiguration(GROUP, FILLED_KEY);
		if (Objects.nonNull(stashes)) // validated, player is logged in.
		{
			final StringBuilder stashesBuilder = new StringBuilder(stashes);
			stashesBuilder.setCharAt(ArrayUtils.indexOf(STASH_IDS_ORDERED, stashUnit.getStashUnit().getObjectId()), filled ? 't' : 'f');
			this.config.setRSProfileConfiguration(GROUP, FILLED_KEY, stashesBuilder.toString());
		}
	}

	/**
	 * Returns whether a STASHUnit is filled.
	 *
	 * @param stashUnit The STASHUnit
	 * @return True if filled, false otherwise.
	 */
	public boolean getStashFilled(final StashUnit stashUnit)
	{
		final String stashes = this.config.getRSProfileConfiguration(GROUP, FILLED_KEY);
		if (Objects.isNull(stashes))
		{
			return false;
		}
		final char stash = stashes.charAt(ArrayUtils.indexOf(STASH_IDS_ORDERED, stashUnit.getStashUnit().getObjectId()));
		return stash == 't';
	}

	/**
	 * Synchronises STASHUnit filled status list with the cloud.
	 * <p>
	 * Verifies integrity of cloud data. Clears the data if STASHUnit id list was changed by RuneLite.
	 */
	public void validate()
	{
		this.migrateConfig();

		final String stashes = this.config.getRSProfileConfiguration(GROUP, FILLED_KEY);
		final String fingerPrint = this.config.getRSProfileConfiguration(GROUP, FINGERPRINT_KEY);
		if (Objects.isNull(stashes) || Objects.isNull(fingerPrint) || !fingerPrint.equals(STASH_IDS_ORDERED_FINGERPRINT) || stashes.length() != STASH_IDS_ORDERED.length)
		{
			this.config.setRSProfileConfiguration(GROUP, FILLED_KEY, StringUtils.repeat('f', STASH_IDS_ORDERED.length));
			this.config.setRSProfileConfiguration(GROUP, FINGERPRINT_KEY, STASH_IDS_ORDERED_FINGERPRINT);
		}
	}

	/**
	 * Moves config values from previously used keys to new keys.
	 * <p>
	 * Previously used keys were of risky format.
	 */
	@Deprecated
	private void migrateConfig()
	{
		final String legacyFilledConfig = this.config.getRSProfileConfiguration(LEGACY_GROUP, LEGACY_FILLED_KEY);
		final String filledConfig = this.config.getRSProfileConfiguration(GROUP, FILLED_KEY);

		if (Objects.nonNull(legacyFilledConfig) && Objects.isNull(filledConfig))
		{
			this.config.setRSProfileConfiguration(GROUP, FILLED_KEY, legacyFilledConfig);
		}

		final String legacyFingerprintConfig = this.config.getRSProfileConfiguration(LEGACY_GROUP, LEGACY_FINGERPRINT_KEY);
		final String fingerprintConfig = this.config.getRSProfileConfiguration(GROUP, FINGERPRINT_KEY);

		if (Objects.nonNull(legacyFingerprintConfig) && Objects.isNull(fingerprintConfig))
		{
			this.config.setRSProfileConfiguration(GROUP, FINGERPRINT_KEY, legacyFingerprintConfig.replace(',', '-'));
		}
	}
}