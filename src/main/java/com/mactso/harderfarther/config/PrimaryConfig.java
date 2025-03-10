package com.mactso.harderfarther.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

//16.2 - 1.0.0.0 HarderFarther

import com.mactso.harderfarther.Main;


public class PrimaryConfig {
	
	//Intermediary - Gets delimited into actual list
	private static String dimensionOmitListString;
	private static String outpostListAsString;
	private static String lootItemsListString;
	private static String grimCitadelsListString;
	

	//	Harder Farther Control Values"."Debug Settings
	private static int debugLevel;

	//	Harder Farther Control Values"."Farm Limiter Settings
	private static int limitMobFarmsTimer;


	//	Harder Farther Control Values"."HarderFarther Settings
	private static boolean onlyOverworld;
	private static boolean makeMonstersHarderFarther;
	private static List<? extends String> dimensionOmitList;
	private static List<BlockPos> outpostBlockPosList;
	private static Vec3[] outpostVec3dArray;
	private static boolean useSpawnAsOutpost;
	private static int boostMaxDistance;
	private static int boostMinDistance;
	private static int safeDistance;
	private static int minimumSafeAltitude;
	private static int maximumSafeAltitude;
	private static int bonusLootEnchantmentLevelModifier;
	private static int maximumArmorDamage;


	//	Harder Farther Control Values"."Loot Settings
	private static boolean useLootDrops;
	private static int oddsDropExperienceBottle;
	private static List<? extends String> lootItemsList;


	//	Harder Farther Control Values"."Boost Settings
	private static int hpMaxBoost;
	private static int speedBoost;
	private static int atkDmgBoost;
	private static int knockbackBoost;


	//	Harder Farther Control Values"."Harder Over Time Settings
	private static boolean makeHarderOverTime;
	private static int maxHarderTimeMinutes;


	//	Harder Farther Control Values"."Grim Citadel Settings
	private static boolean useGrimCitadels;
	private static List<BlockPos> grimCitadelsBlockPosList;

	private static int grimCitadelsCount;
	private static int grimCitadelsRadius;
	private static int grimCitadelBonusDistance;
	private static int grimCitadelPlayerCurseDistance;
	private static int grimCitadelMaxBoostPercent;


	//	Harder Farther Control Values"."Grim Effects Settings
	private static boolean grimEffectTrees;
	private static boolean grimEffectAnimals;
	private static boolean grimEffectPigs;
	private static boolean grimEffectVillagers;
	private static int grimLifeheartPulseSeconds;

		//Client Effects

	private static double 	grimFogRedPercent;
	private static double 	grimFogGreenPercent;
	private static double 	grimFogBluePercent;


	//Placeholders
	public static final int KILLER_ANY   = 0;
	public static final int KILLER_MOB_OR_PLAYER = 1;
	public static final int KILLER_PLAYER = 2;


	public static void initConfig() {
		final File configFile = getConfigFile();
		final Properties properties = new Properties();

		if (configFile.exists()) {
			try (FileInputStream stream = new FileInputStream(configFile)) {
				properties.load(stream);
			} catch (final IOException e) {
				Main.LOGGER.warn("[HarderFarther] Could not read property file '" + configFile.getAbsolutePath() + "'", e);
			}
		}


//		Harder Farther Control Values"."Debug Settings
		try {
			debugLevel = Integer.parseInt(properties.computeIfAbsent("debug_level", (a) -> "0").toString());
		} catch (final Exception e) {
			debugLevel = 0;
			Main.LOGGER.warn("[HarderFarther] Invalid configuration value for 'debug_level'. Using default value.");
		}


//		Harder Farther Control Values"."Farm Limiter Settings
		limitMobFarmsTimer = Integer.parseInt(properties.computeIfAbsent("limit_mob_farm_timer", (a) -> "32").toString());


//		Harder Farther Control Values"."HarderFarther Settings
		onlyOverworld = properties.computeIfAbsent("only_overworld", (a) -> "false").equals("true");
		dimensionOmitListString = properties.computeIfAbsent("dimension_omit_list", (a) -> "[\"minecraft:the_nether\", \"minecraft:the_end\"]").toString();
		outpostListAsString = properties.computeIfAbsent("outpost_list", (a) -> "[]").toString();
		useSpawnAsOutpost = properties.computeIfAbsent("is_spawn_an_outpost", (a) -> "true").equals("true");
		makeMonstersHarderFarther = properties.computeIfAbsent("make_monsters_harder_farther", (a) -> "true").equals("true");
		boostMaxDistance = Integer.parseInt(properties.computeIfAbsent("boost_max_distance", (a) -> "30000").toString());
		boostMinDistance = Integer.parseInt(properties.computeIfAbsent("boost_min_distance", (a) -> "1000").toString());
		safeDistance = Integer.parseInt(properties.computeIfAbsent("safe_distance", (a) -> "64").toString());
		minimumSafeAltitude = Integer.parseInt(properties.computeIfAbsent("minimal_safe_altitude", (a) -> "32").toString());
		maximumSafeAltitude = Integer.parseInt(properties.computeIfAbsent("maximum_safe_altitude", (a) -> "99").toString());
		maximumArmorDamage = Integer.parseInt(properties.computeIfAbsent("maximum_armor_damage", (a) -> "6").toString());


//		Harder Farther Control Values"."Loot Settings
		useLootDrops = properties.computeIfAbsent("use_loot_drops", (a) -> "true").equals("true");
		oddsDropExperienceBottle = Integer.parseInt(properties.computeIfAbsent("odds_drop_experience_bottle", (a) -> "33").toString());
		lootItemsListString = properties.computeIfAbsent("loot_items_list", (a) -> "[\"r,23,minecraft:netherite_scrap,1,1\", \"r,1,minecraft:nether_wart,1,2\", \"r,1,minecraft:music_disc_far,1,1\", \"u,2,minecraft:nether_wart,1,1\", \"u,3,minecraft:golden_carrot,1,1\", \"u,12,minecraft:diamond,1,1\", \"u,5,minecraft:emerald,1,3\", \"u,3,minecraft:oak_planks,1,5\", \"u,1,minecraft:book,1,1\", \"u,1,minecraft:gold_ingot,1,1\", \"u,2,minecraft:chicken,1,2\", \"u,5,minecraft:glowstone_dust,1,2\", \"u,1,minecraft:lead,1,1\", \"u,5,minecraft:stone_axe,1,2\", \"u,3,minecraft:stone_pickaxe,1,1\", \"u,1,minecraft:iron_axe,1,1\", \"u,1,minecraft:beetroot_seeds,1,1\", \"c,3,minecraft:leather_boots,1,1\", \"c,2,minecraft:gold_nugget,1,3\", \"c,2,minecraft:candle,1,2\", \"c,5,minecraft:baked_potato,1,2\", \"c,2,minecraft:fishing_rod,1,1\", \"c,5,minecraft:cooked_cod,1,3\", \"c,3,minecraft:string,1,2\", \"c,3,minecraft:iron_nugget,1,3\", \"c,3,minecraft:honey_bottle,1,2\", \"c,3,minecraft:stick,1,3\", \"c,1,minecraft:emerald,1,1\", \"c,1,minecraft:paper,1,2\"]").toString();


//		Harder Farther Control Values"."Boost Settings
		hpMaxBoost = Integer.parseInt(properties.computeIfAbsent("hp_max_boost", (a) -> "200").toString());
		speedBoost = Integer.parseInt(properties.computeIfAbsent("speed_boost", (a) -> "20").toString());
		atkDmgBoost = Integer.parseInt(properties.computeIfAbsent("atk_dmg_boost", (a) -> "100").toString());
		knockbackBoost = Integer.parseInt(properties.computeIfAbsent("knockback_boost", (a) -> "95").toString());


//		Harder Farther Control Values"."Harder Over Time Settings
		makeHarderOverTime = properties.computeIfAbsent("make_harder_over_time", (a) -> "false").equals("true");
		maxHarderTimeMinutes = Integer.parseInt(properties.computeIfAbsent("max_harder_time_minutes", (a) -> "720").toString());



//		Harder Farther Control Values"."Grim Citadel Settings
		useGrimCitadels = properties.computeIfAbsent("use_grim_citadels", (a) -> "true").equals("true");
		grimCitadelsListString = properties.computeIfAbsent("grim_citadels_list", (a) -> "[\"3600.3500\", \"3500.-100\", \"3500.-3550\", \"0.3596\", \"128.-3500\", \"-2970.3516\", \"-3517.80\", \"-3528.-3756\"]").toString();
		grimCitadelsCount = Integer.parseInt(properties.computeIfAbsent("grim_citadels_count", (a) -> "5").toString());
		grimCitadelsRadius = Integer.parseInt(properties.computeIfAbsent("grim_citadels_radius", (a) -> "5").toString());
		grimCitadelBonusDistance = Integer.parseInt(properties.computeIfAbsent("grim_citadel_bonus_distance", (a) -> "1750").toString());
		grimCitadelPlayerCurseDistance = Integer.parseInt(properties.computeIfAbsent("grim_citadel_player_curse_distance", (a) -> "1250").toString());
		grimCitadelMaxBoostPercent = Integer.parseInt(properties.computeIfAbsent("grim_citadel_max_boost_percent", (a) -> "96").toString());


//		Harder Farther Control Values"."Grim Effects Settings
		grimEffectTrees = properties.computeIfAbsent("grim_effect_trees", (a) -> "true").equals("true");
		grimEffectAnimals = properties.computeIfAbsent("grim_effect_animals", (a) -> "true").equals("true");
		grimEffectPigs = properties.computeIfAbsent("grim_effect_pigs", (a) -> "true").equals("true");
		grimEffectVillagers = properties.computeIfAbsent("grim_effect_villagers", (a) -> "true").equals("true");
		grimLifeheartPulseSeconds = Integer.parseInt(properties.computeIfAbsent("grim_life_heart_pulse_seconds", (a) -> "120").toString());
//			Grim Fog Color Settings
		grimFogRedPercent = Double.parseDouble(properties.computeIfAbsent("grim_fog_red_percent", (a) -> "0.95").toString());
		grimFogBluePercent = Double.parseDouble(properties.computeIfAbsent("grim_fog_blue_percent", (a) -> "0.05").toString());
		grimFogGreenPercent = Double.parseDouble(properties.computeIfAbsent("grim_fog_green_percent", (a) -> "0.05").toString());




		computeConfigValues();

		saveConfig();
	}

	private static File getConfigFile() {
		final File configDir = Platform.configDirectory().toFile();

		if (!configDir.exists()) {
			Main.LOGGER.warn("[Harder Farther] Could not access configuration directory: " + configDir.getAbsolutePath());
		}

		return new File(configDir, "HarderFarther.properties");
	}

	public static void saveConfig() {
		final File configFile = getConfigFile();
		final Properties properties = new Properties();


//		Harder Farther Control Values"."Debug Settings
		properties.put("debug_level", Integer.toString(debugLevel));


//		Harder Farther Control Values"."Farm Limiter Settings
		properties.put("limit_mob_farms_timer", Integer.toString(limitMobFarmsTimer));


//		Harder Farther Control Values"."HarderFarther Settings
		properties.put("only_overworld", Boolean.toString(onlyOverworld));
		properties.put("dimension_omit_list", dimensionOmitList.toString());
		properties.put("outpost_list", outpostListAsString);
		properties.put("is_spawn_an_outpost", Boolean.toString(useSpawnAsOutpost));
		properties.put("make_monsters_harder_farther", Boolean.toString(makeMonstersHarderFarther));
		properties.put("boost_max_distance", Integer.toString(boostMaxDistance));
		properties.put("boost_min_distance", Integer.toString(boostMinDistance));
		properties.put("safe_distance", Integer.toString(safeDistance));
		properties.put("maximum_armor_damage", Integer.toString(maximumArmorDamage));


//		Harder Farther Control Values"."Loot Settings
		properties.put("use_loot_drops", Boolean.toString(useLootDrops));
		properties.put("odds_drop_experience_bottle", Integer.toString(oddsDropExperienceBottle));
		properties.put("loot_items_list", lootItemsList.toString());


//		Harder Farther Control Values"."Boost Settings
		properties.put("hp_max_boost", Integer.toString(hpMaxBoost));
		properties.put("speed_boost", Integer.toString(speedBoost));
		properties.put("atk_dmg_boost", Integer.toString(atkDmgBoost));
		properties.put("knockback_boost", Integer.toString(knockbackBoost));


//		Harder Farther Control Values"."Harder Over Time Settings
		properties.put("make_harder_over_time", Boolean.toString(makeHarderOverTime));
		properties.put("max_harder_time_minutes", Integer.toString(knockbackBoost));


//		Harder Farther Control Values"."Grim Citadel Settings
		properties.put("use_grim_citadels", Boolean.toString(useGrimCitadels));
		properties.put("grim_citadels_list", grimCitadelsListString);
		properties.put("grim_citadels_count", Integer.toString(grimCitadelsCount));
		properties.put("grim_citadels_radius", Integer.toString(grimCitadelsRadius));
		properties.put("grim_citadel_bonus_distance", Integer.toString(grimCitadelBonusDistance));
		properties.put("grim_citadel_player_curse_distance", Integer.toString(grimCitadelPlayerCurseDistance));
		properties.put("grim_citadel_max_boost_percent", Integer.toString(grimCitadelMaxBoostPercent));


//		Harder Farther Control Values"."Grim Effects Settings
		properties.put("grim_effect_trees", Boolean.toString(grimEffectTrees));
		properties.put("grim_effect_animals", Boolean.toString(grimEffectAnimals));
		properties.put("grim_effect_pigs", Boolean.toString(grimEffectPigs));
		properties.put("grim_effect_villagers", Boolean.toString(grimEffectVillagers));
		properties.put("grim_life_heart_pulse_seconds", Integer.toString(grimLifeheartPulseSeconds));
//			Grim Fog Color Settings
		properties.put("grim_fog_red_percent", Double.toString(grimFogRedPercent));
		properties.put("grim_fog_blue_percent", Double.toString(grimFogBluePercent));
		properties.put("grim_fog_green_percent", Double.toString(grimFogGreenPercent));


		try (FileOutputStream stream = new FileOutputStream(configFile)) {
			properties.store(stream, "Harder Farther properties file");
		} catch (final IOException e) {
			Main.LOGGER.warn("[HarderFarther] Could not store property file '" + configFile.getAbsolutePath() + "'", e);
		}
	}

	private static void computeConfigValues() {

		debugLevel = Mth.clamp(debugLevel, 0, 2);
		boostMaxDistance = boostMaxDistance > 0 ? boostMaxDistance : 0;
		boostMaxDistance = boostMaxDistance > 0 ? boostMaxDistance : 0;

		dimensionOmitList = List.of(PrimaryConfig.dimensionOmitListString.substring(1, PrimaryConfig.dimensionOmitListString.length() - 1).split(", "));
		lootItemsList = List.of(PrimaryConfig.lootItemsListString.substring(1, PrimaryConfig.lootItemsListString.length() - 1).split(", "));
		grimCitadelsBlockPosList = getBlockPositions(List.of(PrimaryConfig.grimCitadelsListString.substring(1, PrimaryConfig.grimCitadelsListString.length() - 1).split(", ")));
		outpostBlockPosList = getBlockPositions(List.of(PrimaryConfig.outpostListAsString.substring(1, PrimaryConfig.outpostListAsString.length() - 1).split(", ")));
		outpostVec3dArray = convertOutpostBlockPos(outpostBlockPosList);


	}

	public static int getDebugLevel() {
		return debugLevel;
	}

	public static void setDebugLevel(int newValue) {
		if (newValue <0 || newValue > 2) // TODO: this should be redundant
			newValue = 0;
		debugLevel = newValue;
	}

	public static boolean isOnlyOverworld() {
		return onlyOverworld;
	}

	public static boolean isUseLootDrops() {
		return useLootDrops;
	}

	public static void setUseLootDrops(boolean newValue) {
		PrimaryConfig.useLootDrops = newValue;
		saveConfig();
	}

	public static int getBonusLootEnchantmentLevelModifier() {
		return bonusLootEnchantmentLevelModifier;
	}

	public static void setBonusLootEnchantmentLevelModifier(int bonusLootEnchantmentLevelModifier) {
		PrimaryConfig.bonusLootEnchantmentLevelModifier = bonusLootEnchantmentLevelModifier;
	}

	public static boolean isDimensionOmitted(String dimensionName) {
			return dimensionOmitListString.contains(dimensionName);
	}

	public static int getBoostMaxDistance() {
		return boostMaxDistance;
	}

	public static void setBoostMaxDistance(int modifierMaxDistance) {
		PrimaryConfig.boostMaxDistance = modifierMaxDistance;
	}

	public static int getBoostMinDistance() {
		if (boostMinDistance >= boostMaxDistance) {
			return boostMaxDistance - 1;
		}
		return boostMinDistance;
	}

	public static void setBoostMinDistance(int boostMinDistance) {
		PrimaryConfig.boostMinDistance = boostMinDistance;
	}

	public static int getSafeDistance() {
		return safeDistance;
	}

	public static void setSafeDistance(int safeDistance) {
		PrimaryConfig.safeDistance = safeDistance;
	}

	public static int getMaximumArmorDamage(){
		return maximumArmorDamage;
	}

	public static boolean isHpMaxBoosted() {
		if (hpMaxBoost > 0) return true;
		return false;
	}

	public static boolean isSpeedBoosted() {
		if (speedBoost > 0) return true;
		return false;
	}

	public static boolean isAtkDmgBoosted() {
		if (atkDmgBoost > 0) return true;
		return false;
	}

	public static boolean isKnockBackBoosted() {
		if (knockbackBoost > 0) return true;
		return false;
	}

	public static int getHpMaxBoost() {
		return hpMaxBoost;
	}

	public static int getSpeedBoost() {
		return speedBoost;
	}

	public static int getAtkDmgBoost() {
		return atkDmgBoost;
	}

	public static int getKnockBackMod() {
		return knockbackBoost;
	}

	public static float getHpMaxPercent() {
		return (float) (hpMaxBoost/100);
	}

	public static float getSpeedPercent()  {
		return ((float)speedBoost/100);
	}

	public static float getAtkPercent()  {
		return (float) (atkDmgBoost/100);
	}

	public static float getKnockBackPercent() {
		return (float) (knockbackBoost/100);
	}
	
	public static int getMobFarmingLimitingTimer() {
		return limitMobFarmsTimer;
	}
	
	public static boolean isMakeHarderOverTime() {
		return makeHarderOverTime;
	}

	public static void setMakeHarderOverTime(boolean newValue) {
		PrimaryConfig.makeHarderOverTime = newValue;
		saveConfig();
	}

	public static int getMaxHarderTimeMinutes() {
		return maxHarderTimeMinutes;
	}

	public static void setMaxHarderTimeMinutes(int newValue) {
		PrimaryConfig.maxHarderTimeMinutes = newValue;
		saveConfig();
	}



	public static boolean isMakeMonstersHarderFarther() {
		return makeMonstersHarderFarther;
	}


	public static int getMinimumSafeAltitude() {
		return minimumSafeAltitude;
	}

	public static int getMaximumSafeAltitude() {
		return maximumSafeAltitude;
	}


	public static int getOddsDropExperienceBottle() {
		return oddsDropExperienceBottle;
	}

	public static boolean isUseGrimCitadels() {
		return useGrimCitadels;
	}
	
	public static int getGrimCitadelsRadius() {
		return grimCitadelsRadius;
	}

	public static void setGrimCitadelsRadius(int grimCitadelsRadius) {
		PrimaryConfig.grimCitadelsRadius = grimCitadelsRadius;
		saveConfig();
	}

	public static int getGrimCitadelMaxBoostValue() {
		return grimCitadelMaxBoostPercent;
	}

	public static float getGrimCitadelMaxBoostPercent() {
		return (float)(grimCitadelMaxBoostPercent)/100;
	}
	
	public static void setGrimCitadelMaxBoostPercent(int newValue) {
		PrimaryConfig.grimCitadelMaxBoostPercent = newValue;
		saveConfig();
	}
	
	public static int getGrimCitadelsCount() {
		return grimCitadelsCount;
	}

	public static int getGrimCitadelBonusDistance() {
		return grimCitadelBonusDistance;
	}
	
	public static int getGrimCitadelBonusDistanceSq() {
		return grimCitadelBonusDistance*grimCitadelBonusDistance;
	}
	
	public static int getGrimCitadelPlayerCurseDistance() {
		return grimCitadelPlayerCurseDistance;
	}

	public static int getGrimCitadelPlayerCurseDistanceSq() {
		return grimCitadelPlayerCurseDistance * grimCitadelBonusDistance;
	}

	public static List<BlockPos> getGrimCitadelsBlockPosList() {
		return grimCitadelsBlockPosList;
	}

	public static void setGrimCitadelsBlockPosList(List<BlockPos> grimCitadelsBlockPosList) {
		PrimaryConfig.grimCitadelsBlockPosList = grimCitadelsBlockPosList;
	}
	
	public static boolean isGrimEffectTrees() {
		return grimEffectTrees;
	}

	public static void setGrimEffectTrees(boolean grimEffectTrees) {
		PrimaryConfig.grimEffectTrees = grimEffectTrees;
	}

	public static boolean isGrimEffectAnimals() {
		return grimEffectAnimals;
	}
	
	public static boolean isGrimEffectPigs() {
		return grimEffectPigs;
	}

	public static boolean isGrimEffectVillagers() {
		return grimEffectVillagers;
	}
	
	public static int getGrimLifeheartPulseSeconds() {
		return grimLifeheartPulseSeconds;
	}

	public static double getGrimFogRedPercent() {
		return grimFogRedPercent;
	}

	public static double getGrimFogBluePercent() {
		return grimFogBluePercent;
	}

	public static double getGrimFogGreenPercent() {
		return grimFogGreenPercent;
	}

	public static Vec3[] getOutpostPositions(){
		return outpostVec3dArray.clone();
	}

	public static boolean isSpawnAnOutpost(){
		return useSpawnAsOutpost;
	}
	
	public static void setGrimFogRedPercent(double grimFogRedPercent) {
		PrimaryConfig.grimFogRedPercent = grimFogRedPercent/100;
		saveConfig();
	}
	public static void setGrimFogGreenPercent(double grimFogGreenPercent) {
		PrimaryConfig.grimFogGreenPercent = grimFogGreenPercent/100;
		saveConfig();
	}

	public static void setGrimFogBluePercent(double grimFogBluePercent) {
		PrimaryConfig.grimFogBluePercent = grimFogBluePercent/100;
		saveConfig();
	}

	public static void setUseGrimCitadels(boolean newValue) {
		useGrimCitadels = newValue;
		saveConfig();
	}

	public static void setBonusRange(int newRange) {
		grimCitadelBonusDistance = newRange;
		grimCitadelPlayerCurseDistance = newRange;
		saveConfig();
	}

	public static void setOddsDropExperienceBottle(int newOdds) {
		oddsDropExperienceBottle = newOdds;
		saveConfig();
	}

	private static List<BlockPos> getBlockPositions(List<? extends String> list) {

		List< BlockPos> returnList = new ArrayList<>();
		for (String pos : list) {
			if (pos.length() > 0) {
				pos = pos.substring(1);
				pos = pos.replace("\"", "");
				String[] posParts = pos.split("\\.", 2);
				int x = Integer.valueOf(posParts[0]);
				int y = -1;
				int z = Integer.valueOf(posParts[1]);
				returnList.add(new BlockPos(x, y, z));
			}
		}
		return returnList;
	}

	private static Vec3[] convertOutpostBlockPos(List<BlockPos> list){

		Vec3[] returnArray = new Vec3[list.size() +1];
		int iterator = 1;
		for (BlockPos pos : list) {

			int x = pos.getX();
			int y = -1;
			int z = pos.getZ();
			returnArray[iterator] = (new Vec3(x, y, z));

			iterator++;
		}
		return returnArray;
	}

}

