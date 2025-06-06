package com.larsvansoest.runelite.clueitems.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;

/**
 * Contains and wraps all {@link net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit} used by {@link net.runelite.client.plugins.cluescrolls.clues.EmoteClue}, integrated in vendored {@link com.larsvansoest.runelite.clueitems.data.EmoteClue} class.
 * <p>
 * Source https://github.com/runelite/runelite/blob/master/runelite-client/src/main/java/net/runelite/client/plugins/cluescrolls/clues/emote/STASHUnit.java
 * </p>
 * <p>
 * Maintained up to 2253b25.
 * </p>
 *
 * @author Lars van Soest
 * @since 3.0.0
 */
@Getter
public enum StashUnit
{
	NEAR_A_SHED_IN_LUMBRIDGE_SWAMP("Lumbridge Swamp Shed", STASHUnit.NEAR_A_SHED_IN_LUMBRIDGE_SWAMP, Type.Hole),
	ON_THE_BRIDGE_TO_THE_MISTHALIN_WIZARDS_TOWER("Misthalin Wizards Tower", STASHUnit.ON_THE_BRIDGE_TO_THE_MISTHALIN_WIZARDS_TOWER, Type.Crate),
	DRAYNOR_VILLAGE_MARKET("Draynor Village Market", STASHUnit.DRAYNOR_VILLAGE_MARKET, Type.Hole),
	LIMESTONE_MINE("Limestone Mine", STASHUnit.LIMESTONE_MINE, Type.Rock),
	OUTSIDE_THE_LEGENDS_GUILD_GATES("Legends Guild", STASHUnit.OUTSIDE_THE_LEGENDS_GUILD_GATES, Type.Bush),
	MUDSKIPPER_POINT("Mudskipper Point", STASHUnit.MUDSKIPPER_POINT, Type.Hole),
	NEAR_THE_ENTRANA_FERRY_IN_PORT_SARIM("Port Sarim Entrana Ferry", STASHUnit.NEAR_THE_ENTRANA_FERRY_IN_PORT_SARIM, Type.Crate),
	AL_KHARID_SCORPION_MINE("Al Kharid Scorpion Mine", STASHUnit.AL_KHARID_SCORPION_MINE, Type.Rock),
	DRAYNOR_MANOR_BY_THE_FOUNTAIN("Draynor Manor Fountain", STASHUnit.DRAYNOR_MANOR_BY_THE_FOUNTAIN, Type.Rock),
	WHEAT_FIELD_NEAR_THE_LUMBRIDGE_WINDMILL("Lumbridge Windmill Wheat Field", STASHUnit.WHEAT_FIELD_NEAR_THE_LUMBRIDGE_WINDMILL, Type.Hole),
	CROSSROADS_NORTH_OF_DRAYNOR_VILLAGE("Draynor Village Crossroads", STASHUnit.CROSSROADS_NORTH_OF_DRAYNOR_VILLAGE, Type.Bush),
	RIMMINGTON_MINE("Rimmington Mine", STASHUnit.RIMMINGTON_MINE, Type.Rock),
	VARROCK_PALACE_LIBRARY("Varrock Palace Library", STASHUnit.VARROCK_PALACE_LIBRARY, Type.Crate),
	UPSTAIRS_IN_THE_ARDOUGNE_WINDMILL("Ardougne Windmill", STASHUnit.UPSTAIRS_IN_THE_ARDOUGNE_WINDMILL, Type.Crate),
	OUTSIDE_THE_FALADOR_PARTY_ROOM("Falador Party Room", STASHUnit.OUTSIDE_THE_FALADOR_PARTY_ROOM, Type.Bush),
	TAVERLEY_STONE_CIRCLE("Taverley Stone Circle", STASHUnit.TAVERLEY_STONE_CIRCLE, Type.Rock),
	CATHERBY_BEEHIVE_FIELD("Catherby Beehive Field", STASHUnit.CATHERBY_BEEHIVE_FIELD, Type.Bush),
	NEAR_THE_PARROTS_IN_ARDOUGNE_ZOO("Ardougne Zoo Parrots", STASHUnit.NEAR_THE_PARROTS_IN_ARDOUGNE_ZOO, Type.Bush),
	ROAD_JUNCTION_NORTH_OF_RIMMINGTON("Rimmington Road Junction", STASHUnit.ROAD_JUNCTION_NORTH_OF_RIMMINGTON, Type.Rock),
	OUTSIDE_THE_FISHING_GUILD("Fishing Guild (Outside)", STASHUnit.OUTSIDE_THE_FISHING_GUILD, Type.Hole),
	OUTSIDE_KEEP_LE_FAYE("Keep Le Faye", STASHUnit.OUTSIDE_KEEP_LE_FAYE, Type.Rock),
	ROAD_JUNCTION_SOUTH_OF_SINCLAIR_MANSION("Sinclaire Mansion Junction", STASHUnit.ROAD_JUNCTION_SOUTH_OF_SINCLAIR_MANSION, Type.Rock),
	OUTSIDE_THE_DIGSITE_EXAM_CENTRE("Digsite Exam Centre", STASHUnit.OUTSIDE_THE_DIGSITE_EXAM_CENTRE, Type.Bush),
	NEAR_THE_SAWMILL_OPERATORS_BOOTH("Sawmill Operators Booth", STASHUnit.NEAR_THE_SAWMILL_OPERATORS_BOOTH, Type.Bush),
	EMIRS_ARENA_TICKET_OFFICE("Emir's Arena Ticket Office", "Mubariz's room at Emir's Arena", STASHUnit.EMIRS_ARENA_TICKET_OFFICE, Type.Crate),
	OUTSIDE_VARROCK_PALACE_COURTYARD("Varrock Palace Courtyard", STASHUnit.OUTSIDE_VARROCK_PALACE_COURTYARD, Type.Bush),
	NEAR_HERQUINS_SHOP_IN_FALADOR("Falador Herquins Shop", STASHUnit.NEAR_HERQUINS_SHOP_IN_FALADOR, Type.Bush),
	SOUTH_OF_THE_GRAND_EXCHANGE("Varrock Grand Exchange", STASHUnit.SOUTH_OF_THE_GRAND_EXCHANGE, Type.Bush),
	AUBURYS_SHOP_IN_VARROCK("Varrock Aubury's Shop", STASHUnit.AUBURYS_SHOP_IN_VARROCK, Type.Crate),
	CENTRE_OF_CANIFIS("Canifis", STASHUnit.CENTRE_OF_CANIFIS, Type.Rock),
	MAUSOLEUM_OFF_THE_MORYTANIA_COAST("Morytania Coast Mausoleum", STASHUnit.MAUSOLEUM_OFF_THE_MORYTANIA_COAST, Type.Hole),
	EAST_OF_THE_BARBARIAN_VILLAGE_BRIDGE("Barbarian Village Bridge", STASHUnit.EAST_OF_THE_BARBARIAN_VILLAGE_BRIDGE, Type.Bush),
	SOUTH_OF_THE_SHRINE_IN_TAI_BWO_WANNAI_VILLAGE("Tai Bwo Wannai Shrine", STASHUnit.SOUTH_OF_THE_SHRINE_IN_TAI_BWO_WANNAI_VILLAGE, Type.Crate),
	CASTLE_WARS_BANK("Castle Wars Bank", STASHUnit.CASTLE_WARS_BANK, Type.Crate),
	BARBARIAN_OUTPOST_OBSTACLE_COURSE("Barbarian Outpost Course", "Barbarian Outpost obstacle course", STASHUnit.BARBARIAN_OUTPOST_OBSTACLE_COURSE, Type.Bush),
	GNOME_STRONGHOLD_BALANCING_ROPE("Gnome Stronghold Rope", STASHUnit.GNOME_STRONGHOLD_BALANCING_ROPE, Type.Crate),
	OUTSIDE_YANILLE_BANK("Yanille Bank", STASHUnit.OUTSIDE_YANILLE_BANK, Type.Rock),
	OBSERVATORY("Observatory", STASHUnit.OBSERVATORY, Type.Crate),
	OGRE_CAGE_IN_KING_LATHAS_TRAINING_CAMP("Lathas Camp Ogre Cage", "Ogre cage in the Ardougne Training Camp", STASHUnit.OGRE_CAGE_IN_KING_LATHAS_TRAINING_CAMP, Type.Hole),
	DIGSITE("Digsite", STASHUnit.DIGSITE, Type.Rock),
	HICKTONS_ARCHERY_EMPORIUM("Hickton's Archery Emporium", STASHUnit.HICKTONS_ARCHERY_EMPORIUM, Type.Crate),
	SHANTAY_PASS("Shantay Pass", STASHUnit.SHANTAY_PASS, Type.Crate),
	LUMBRIDGE_SWAMP_CAVES("Lumbridge Swamp Caves", STASHUnit.LUMBRIDGE_SWAMP_CAVES, Type.Rock),
	OUTSIDE_CATHERBY_BANK("Catherby Bank", STASHUnit.OUTSIDE_CATHERBY_BANK, Type.Bush),
	OUTSIDE_THE_SEERS_VILLAGE_COURTHOUSE("Seers Village Courthouse", STASHUnit.OUTSIDE_THE_SEERS_VILLAGE_COURTHOUSE, Type.Bush),
	OUTSIDE_HARRYS_FISHING_SHOP_IN_CATHERBY("Catherby Harry's Fishing Shop", STASHUnit.OUTSIDE_HARRYS_FISHING_SHOP_IN_CATHERBY, Type.Bush),
	TZHAAR_WEAPONS_STORE("TzHaar Weapons Store", STASHUnit.TZHAAR_WEAPONS_STORE, Type.Hole),
	NORTH_OF_EVIL_DAVES_HOUSE_IN_EDGEVILLE("Edgeville Evil Dave's House", STASHUnit.NORTH_OF_EVIL_DAVES_HOUSE_IN_EDGEVILLE, Type.Bush),
	WEST_OF_THE_SHAYZIEN_COMBAT_RING("Shayzien Combat Ring", "North of the Shayzien combat ring", STASHUnit.WEST_OF_THE_SHAYZIEN_COMBAT_RING, Type.Crate),
	ENTRANCE_OF_THE_ARCEUUS_LIBRARY("Arceuus Library", STASHUnit.ENTRANCE_OF_THE_ARCEUUS_LIBRARY, Type.Crate),
	OUTSIDE_DRAYNOR_VILLAGE_JAIL("Draynor Village Jail", STASHUnit.OUTSIDE_DRAYNOR_VILLAGE_JAIL, Type.Bush),
	FISHING_GUILD_BANK("Fishing Guild Bank", STASHUnit.FISHING_GUILD_BANK, Type.Crate),
	TOP_FLOOR_OF_THE_LIGHTHOUSE("Lighthouse Top Floor", STASHUnit.TOP_FLOOR_OF_THE_LIGHTHOUSE, Type.Crate),
	OUTSIDE_THE_GREAT_PYRAMID_OF_SOPHANEM("Great Pyramid of Sophanem", STASHUnit.OUTSIDE_THE_GREAT_PYRAMID_OF_SOPHANEM, Type.Hole),
	NOTERAZZOS_SHOP_IN_THE_WILDERNESS("Wilderness Noterazzo's Shop", STASHUnit.NOTERAZZOS_SHOP_IN_THE_WILDERNESS, Type.Hole),
	WEST_SIDE_OF_THE_KARAMJA_BANANA_PLANTATION("Karamja Banana Plantation", STASHUnit.WEST_SIDE_OF_THE_KARAMJA_BANANA_PLANTATION, Type.Crate),
	MOUNTAIN_CAMP_GOAT_ENCLOSURE("Mountain Camp Goat Enclosure", STASHUnit.MOUNTAIN_CAMP_GOAT_ENCLOSURE, Type.Rock),
	GNOME_GLIDER_ON_WHITE_WOLF_MOUNTAIN("White Wolf Mountain Glider", STASHUnit.GNOME_GLIDER_ON_WHITE_WOLF_MOUNTAIN, Type.Hole),
	SHILO_VILLAGE_BANK("Shilo Village Bank", STASHUnit.SHILO_VILLAGE_BANK, Type.Crate),
	INSIDE_THE_DIGSITE_EXAM_CENTRE("Digsite Exam Centre", STASHUnit.INSIDE_THE_DIGSITE_EXAM_CENTRE, Type.Crate),
	NORTHEAST_CORNER_OF_THE_KHARAZI_JUNGLE("Kharazi Jungle", STASHUnit.NORTHEAST_CORNER_OF_THE_KHARAZI_JUNGLE, Type.Hole),
	VOLCANO_IN_THE_NORTHEASTERN_WILDERNESS("Wilderness Volcano", STASHUnit.VOLCANO_IN_THE_NORTHEASTERN_WILDERNESS, Type.Rock),
	IN_THE_MIDDLE_OF_JIGGIG("Jiggig", STASHUnit.IN_THE_MIDDLE_OF_JIGGIG, Type.Rock),
	AGILITY_PYRAMID("Agility Pyramid", "Agility Pyramid", STASHUnit.AGILITY_PYRAMID, Type.Hole),
	HOSIDIUS_MESS("Hosidius Mess", STASHUnit.HOSIDIUS_MESS, Type.Crate),
	CHAPEL_IN_WEST_ARDOUGNE("West Ardougne Chapel", STASHUnit.CHAPEL_IN_WEST_ARDOUGNE, Type.Crate),
	NEAR_A_RUNITE_ROCK_IN_THE_FREMENNIK_ISLES("Fremennik Isles Runite Rock", STASHUnit.NEAR_A_RUNITE_ROCK_IN_THE_FREMENNIK_ISLES, Type.Rock),
	NEAR_A_LADDER_IN_THE_WILDERNESS_LAVA_MAZE("Wilderness Lava Maze", STASHUnit.NEAR_A_LADDER_IN_THE_WILDERNESS_LAVA_MAZE, Type.Rock),
	ENTRANCE_OF_THE_CAVE_OF_DAMIS("Cave of Damis", STASHUnit.ENTRANCE_OF_THE_CAVE_OF_DAMIS, Type.Hole),
	WARRIORS_GUILD_BANK("Warriors Guild Bank", STASHUnit.WARRIORS_GUILD_BANK, Type.Hole),
	SOUTHEAST_CORNER_OF_THE_MONASTERY("Monastery", STASHUnit.SOUTHEAST_CORNER_OF_THE_MONASTERY, Type.Crate),
	SOUTHEAST_CORNER_OF_THE_FISHING_PLATFORM("Fishing Platform", STASHUnit.SOUTHEAST_CORNER_OF_THE_FISHING_PLATFORM, Type.Crate),
	OUTSIDE_THE_SLAYER_TOWER_GARGOYLE_ROOM("Slayer Tower Gargoyle Room", STASHUnit.OUTSIDE_THE_SLAYER_TOWER_GARGOYLE_ROOM, Type.Crate),
	ON_TOP_OF_TROLLHEIM_MOUNTAIN("Trollheim Mountain (top)", STASHUnit.ON_TOP_OF_TROLLHEIM_MOUNTAIN, Type.Rock),
	FOUNTAIN_OF_HEROES("Fountain of Heroes", STASHUnit.FOUNTAIN_OF_HEROES, Type.Hole),
	ENTRANCE_OF_THE_CAVERN_UNDER_THE_WHIRLPOOL("Whirlpool Cavern", STASHUnit.ENTRANCE_OF_THE_CAVERN_UNDER_THE_WHIRLPOOL, Type.Rock),
	HALFWAY_DOWN_TROLLWEISS_MOUNTAIN("Trollheim Mountain (half-way)", STASHUnit.HALFWAY_DOWN_TROLLWEISS_MOUNTAIN, Type.Rock),
	SHAYZIEN_WAR_TENT("Shayzien War Tent", STASHUnit.SHAYZIEN_WAR_TENT, Type.Crate),
	OUTSIDE_THE_LEGENDS_GUILD_DOOR("Legends Guild Door", STASHUnit.OUTSIDE_THE_LEGENDS_GUILD_DOOR, Type.Bush),
	NEAR_THE_GEM_STALL_IN_ARDOUGNE_MARKET("Ardougne Market Gem Stall", STASHUnit.NEAR_THE_GEM_STALL_IN_ARDOUGNE_MARKET, Type.Hole),
	OUTSIDE_THE_BAR_BY_THE_FIGHT_ARENA("Fight Arena Bar", STASHUnit.OUTSIDE_THE_BAR_BY_THE_FIGHT_ARENA, Type.Crate),
	SOUTHEAST_CORNER_OF_LAVA_DRAGON_ISLE("Lava Dragon Isle", STASHUnit.SOUTHEAST_CORNER_OF_LAVA_DRAGON_ISLE, Type.Hole),
	NEAR_THE_PIER_IN_ZULANDRA("Zulandra Pier", STASHUnit.NEAR_THE_PIER_IN_ZULANDRA, Type.Hole),
	BARROWS_CHEST("Barrow's Chest", STASHUnit.BARROWS_CHEST, Type.Hole),
	WELL_OF_VOYAGE("Well of Voyage", STASHUnit.WELL_OF_VOYAGE, Type.Hole),
	NORTHERN_WALL_OF_CASTLE_DRAKAN("Castle Drakan North Wall", STASHUnit.NORTHERN_WALL_OF_CASTLE_DRAKAN, Type.Rock),
	_7TH_CHAMBER_OF_JALSAVRAH("7th Chamber of Jalsavrah", STASHUnit._7TH_CHAMBER_OF_JALSAVRAH, Type.Hole),
	SOUL_ALTAR("Soul Altar", STASHUnit.SOUL_ALTAR, Type.Hole),
	WARRIORS_GUILD_BANK_29047("Warriors Guild Bank", STASHUnit.WARRIORS_GUILD_BANK_29047, Type.Crate),
	ENTRANA_CHAPEL("Entrana Chapel", STASHUnit.ENTRANA_CHAPEL, Type.Crate),
	TZHAAR_GEM_STORE("TzHaar Gem Store", STASHUnit.TZHAAR_GEM_STORE, Type.Hole),
	TENT_IN_LORD_IORWERTHS_ENCAMPMENT("Lord Iorwerth's Encampment", STASHUnit.TENT_IN_LORD_IORWERTHS_ENCAMPMENT, Type.Crate),
	OUTSIDE_MUDKNUCKLES_HUT("Mudknuckle's Hut", STASHUnit.OUTSIDE_MUDKNUCKLES_HUT, Type.Rock),
	CENTRE_OF_THE_CATACOMBS_OF_KOUREND("Catacombs of Kourend", STASHUnit.CENTRE_OF_THE_CATACOMBS_OF_KOUREND, Type.Hole),
	KING_BLACK_DRAGONS_LAIR("King Black Dragon's Lair", STASHUnit.KING_BLACK_DRAGONS_LAIR, Type.Rock),
	OUTSIDE_KRIL_TSUTSAROTHS_ROOM("K'ril Tsutsaroth's room", STASHUnit.OUTSIDE_KRIL_TSUTSAROTHS_ROOM, Type.Hole),
	BY_THE_BEAR_CAGE_IN_VARROCK_PALACE_GARDENS("Varrock Palace Gardens", STASHUnit.BY_THE_BEAR_CAGE_IN_VARROCK_PALACE_GARDENS, Type.Bush),
	OUTSIDE_THE_WILDERNESS_AXE_HUT("Wilderness Axe Hut", STASHUnit.OUTSIDE_THE_WILDERNESS_AXE_HUT, Type.Hole),
	TOP_FLOOR_OF_THE_YANILLE_WATCHTOWER("Yanille Watchtower", STASHUnit.TOP_FLOOR_OF_THE_YANILLE_WATCHTOWER, Type.Crate),
	DEATH_ALTAR("Death Altar", STASHUnit.DEATH_ALTAR, Type.Hole),
	BEHIND_MISS_SCHISM_IN_DRAYNOR_VILLAGE("Draynor Village Miss Chism", STASHUnit.BEHIND_MISS_SCHISM_IN_DRAYNOR_VILLAGE, Type.Bush),
	NORTHWESTERN_CORNER_OF_THE_ENCHANTED_VALLEY("Enchanted Valley", STASHUnit.NORTHWESTERN_CORNER_OF_THE_ENCHANTED_VALLEY, Type.Bush),
	NORTH_OF_MOUNT_KARUULM("Mount Karuulm", STASHUnit.NORTH_OF_MOUNT_KARUULM, Type.Hole),
	TEMPLE_SOUTHEAST_OF_THE_BAZAAR("Temple in Civitas illa Fortis", "Outside the temple in Civitas illa Fortis", STASHUnit.TEMPLE_SOUTHEAST_OF_THE_BAZAAR, Type.Bush),
	CAM_TORUM_ENTRANCE("Cam torum entrance", "South of the gates to Cam Torum", STASHUnit.CAM_TORUM_ENTRANCE, Type.Hole),
	FORTIS_GRAND_MUSEUM("Fortis grand museum", "Near the entrance of the Civitas illa Fortis Grand Museum", STASHUnit.FORTIS_GRAND_MUSEUM, Type.Hole),
	GYPSY_TENT_ENTRANCE("Varrock Gypsy Tent", "Aris's tent", STASHUnit.GYPSY_TENT_ENTRANCE, Type.Bush),
	FINE_CLOTHES_ENTRANCE("Varrock Fine Clothes", "Iffie Nitter in Varrock", STASHUnit.FINE_CLOTHES_ENTRANCE, Type.Bush),
	BOB_AXES_ENTRANCE("Lumbridge Bob's Axes", "Bob's Brilliant Axes in Lumbridge", STASHUnit.BOB_AXES_ENTRANCE, Type.Bush),
	CRYSTALLINE_MAPLE_TREES("Crystalline Maple Trees", "North of Prifddinas by several maple trees", STASHUnit.CRYSTALLINE_MAPLE_TREES, Type.Hole),
	CHARCOAL_BURNERS("Charcoal Burners", "Near the Charcoal Burners", STASHUnit.CHARCOAL_BURNERS, Type.Crate),
	EAST_OF_THE_LEVEL_19_WILDERNESS_OBELISK("Wilderness Chaos Temple", "Chaos Temple in the south-eastern Wilderness", STASHUnit.EAST_OF_THE_LEVEL_19_WILDERNESS_OBELISK, Type.Rock);

	StashUnit(String name, STASHUnit stashUnit, Type type) {
		this.name = name;
		this.watsonLocation = formatWatsonLocation(this.name());
		this.stashUnit = stashUnit;
		this.type = type;
	}

	StashUnit(String name, String watsonLocation, STASHUnit stashUnit, Type type) {
		this.name = name;
		this.watsonLocation = formatWatsonLocation(watsonLocation);
		this.stashUnit = stashUnit;
		this.type = type;
	}

	/**
	 * Returns the StashUnit corresponding to the text on the Watson notice board.
	 * @param watsonLocation - The text from the watson notice board.
	 * @return the StashUnit, null if no match was found.
	 */
	public static StashUnit fromWatsonLocation(String watsonLocation, EmoteClueDifficulty difficulty) {
		// warrior guild STASHes have the same board name
		if (watsonLocation.equals("Warriors' Guild bank")) {
			return difficulty.equals(EmoteClueDifficulty.Elite) ? WARRIORS_GUILD_BANK : WARRIORS_GUILD_BANK_29047;
		}
		for(StashUnit stashUnit : StashUnit.values()) {
			if (stashUnit.getWatsonLocation().equals(formatWatsonLocation(watsonLocation))) {
				return stashUnit;
			}
		}
		return null;
	}

	private static String formatWatsonLocation(String watsonLocation) {
		return watsonLocation.replace("'", "").replace("-", "").replace("_", " ").trim().toLowerCase();
	}

	private final String name;
	private final String watsonLocation;
	private final STASHUnit stashUnit;
	private final Type type;

	/**
	 * Contains {@link com.larsvansoest.runelite.clueitems.data.StashUnit} build requirement data for each {@link com.larsvansoest.runelite.clueitems.data.StashUnit.Type}.
	 */
	@Getter
	@RequiredArgsConstructor
	public enum DifficultyRequirements
	{
		Beginner(12, "2 planks, 10 nails"),
		Easy(27, "2 planks, 10 nails"),
		Medium(42, "2 oak planks, 10 nails"),
		Hard(55, "2 teak planks, 10 nails"),
		Elite(77, "2 mahogany planks, 10 nails"),
		Master(88, "2 mahogany planks, 1 gold leaf, 10 nails");

		private final int constructionLvl;
		private final String constructionItems;
	}

	/**
	 * Represents the object type of the {@link com.larsvansoest.runelite.clueitems.data.StashUnit}.
	 */
	public enum Type
	{
		Bush(),
		Crate(),
		Hole(),
		Rock()
	}
}
