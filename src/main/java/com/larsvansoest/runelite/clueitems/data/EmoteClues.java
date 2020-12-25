package com.larsvansoest.runelite.clueitems.data;

import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.Difficulty;
import com.larsvansoest.runelite.clueitems.vendor.runelite.client.plugins.cluescrolls.clues.EmoteClue;

public abstract class EmoteClues
{
	public static final EmoteClueCollection beginner = ofDifficulty(Difficulty.Beginner);
	public static final EmoteClueCollection easy = ofDifficulty(Difficulty.Easy);
	public static final EmoteClueCollection medium = ofDifficulty(Difficulty.Medium);
	public static final EmoteClueCollection hard = ofDifficulty(Difficulty.Hard);
	public static final EmoteClueCollection elite = ofDifficulty(Difficulty.Elite);
	public static final EmoteClueCollection master = ofDifficulty(Difficulty.Master);

	private static final EmoteClueCollection ofDifficulty(Difficulty difficulty) {
		return new EmoteClueCollection(EmoteClue.CLUES.stream().filter(clue -> clue.getDifficulty() == difficulty).toArray(EmoteClue[]::new));
	}
}
