package com.mactso.harderfarther.command;

import java.util.Iterator;
import java.util.List;

import com.mactso.harderfarther.config.PrimaryConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import com.mactso.harderfarther.manager.GrimCitadelManager;
import com.mactso.harderfarther.manager.HarderTimeManager;
import com.mactso.harderfarther.manager.LootManager;
import com.mactso.harderfarther.network.SyncFogToClientsPacket;
import com.mactso.harderfarther.utility.Utility;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class HarderFartherCommands {


	private static void printColorInfo(ServerPlayer p) {

		String chatMessage = "\nFog Color Current Values";
		Utility.sendBoldChat(p, chatMessage, ChatFormatting.DARK_GREEN);
		chatMessage = "R (" + PrimaryConfig.getGrimFogRedPercent() + ")" + " G (" + PrimaryConfig.getGrimFogGreenPercent() + ")"
				+ " B (" + PrimaryConfig.getGrimFogBluePercent() + ")";
		Utility.sendChat(p, chatMessage, ChatFormatting.GREEN);

	}

	private static void printGrimEffectsInfo(ServerPlayer p) {

		Utility.sendBoldChat(p, "\nGrim Effects Info", ChatFormatting.DARK_GREEN);
		if (PrimaryConfig.isUseGrimCitadels()) {
			String chatMessage = (
					"\n  Effect Villagers ..............................: " + PrimaryConfig.isGrimEffectVillagers()
					+ "\n  Effect Trees .....................................: " + PrimaryConfig.isGrimEffectTrees()
					+ "\n  Effect Animals ...................................: " + PrimaryConfig.isGrimEffectAnimals()
					+ "\n  Effect Pigs ..........................................: " + PrimaryConfig.isGrimEffectPigs()
				);

			Utility.sendChat(p, chatMessage, ChatFormatting.GREEN);
		} else {
			Utility.sendChat(p, "\n  Grim Citadels Disabled", ChatFormatting.DARK_GREEN);
		}

	}

	private static void printGrimInfo(ServerPlayer p) {
		BlockPos pPos = p.blockPosition();
		float grimDifficulty = Math.min(PrimaryConfig.getGrimCitadelMaxBoostValue(),
				100*GrimCitadelManager.getGrimDifficulty((LivingEntity) p));

		Utility.sendBoldChat(p, "\nGrim Citadel Information", ChatFormatting.DARK_GREEN);
		if (PrimaryConfig.isUseGrimCitadels()) {
			String chatMessage = ("   Grim Citadels are Enabled" 
					+ "\n   Nearest Grim Citadel ................................: "
					+ (int) Math.sqrt(GrimCitadelManager.getClosestGrimCitadelDistanceSq(pPos)) + " meters at "
					+ "\n    " + GrimCitadelManager.getClosestGrimCitadelPos(pPos)
					+ "\n   Aura Range ......................................................: "
					+ PrimaryConfig.getGrimCitadelBonusDistance() + " blocks." 
					+ "\n   Player Curse Range ................................: "
					+ PrimaryConfig.getGrimCitadelPlayerCurseDistance() + " blocks."
					+ "\n   Maximum Difficulty .......................................: "
					+ PrimaryConfig.getGrimCitadelMaxBoostValue() + "%"
					+ "\n   Current Difficulty ......................................: "
					+ grimDifficulty + "%"
					+ "\n   Minimum Number of Grim Citadels.......: "
					+ PrimaryConfig.getGrimCitadelsCount()
					+ "\n   Citadel Radius  ..............................................: "
					+ GrimCitadelManager.getGrimRadius());
			Utility.sendChat(p, chatMessage, ChatFormatting.GREEN);
		} else {
			Utility.sendChat(p, "\n  Grim Citadels Disabled", ChatFormatting.DARK_GREEN);
		}

	}

	private static void printGrimMusicInfo(ServerPlayer p) {
		String chatMessage = "\nMusic Attribution";
		Utility.sendBoldChat(p, chatMessage, ChatFormatting.DARK_GREEN);
		chatMessage = "Attribution Tags for Ambient Music\n"
				+ "\n"
				+ "Lake of Destiny by Darren Curtis | https://www.darrencurtismusic.com/\n"
				+ "Music promoted by https://www.chosic.com/free-music/all/\n"
				+ "Creative Commons Attribution 3.0 Unported License\n"
				+ "https://creativecommons.org/licenses/by/3.0/\n"
				+ "\n"
				+ "Dusty Memories by Darren Curtis | https://www.darrencurtismusic.com/\n"
				+ "Music promoted by https://www.chosic.com/free-music/all/\n"
				+ "Creative Commons Attribution 3.0 Unported License\n"
				+ "https://creativecommons.org/licenses/by/3.0/\n"
				+ " \n"
				+ "Labyrinth of Lost Dreams by Darren Curtis | https://www.darrencurtismusic.com/\n"
				+ "Music promoted on https://www.chosic.com/free-music/all/\n"
				+ "Creative Commons Attribution 3.0 Unported (CC BY 3.0)\n"
				+ "https://creativecommons.org/licenses/by/3.0/\n"
				+ " \n"
				+ "\n"
				+ "";
		Utility.sendChat(p, chatMessage, ChatFormatting.GREEN);
		
	}
	
	
	
	private static void printInfo(ServerPlayer p) {

		String dimensionName = p.level().dimension().location().toString();

		String chatMessage = "\nDimension: " + dimensionName + "\n Current Values";
		Utility.sendBoldChat(p, chatMessage, ChatFormatting.DARK_GREEN);

		chatMessage = "  Harder Max Distance From Spawn....: " + PrimaryConfig.getBoostMaxDistance() + " blocks."
				+ "\n  Spawn Safe Distance ..................................: " + PrimaryConfig.getSafeDistance()
				+ " blocks." + "\n  Debug Level .......................................................: "
				+ PrimaryConfig.getDebugLevel() + "\n  Only In Overworld .........................................: "
				+ PrimaryConfig.isOnlyOverworld() + "\n  Grim Citadels Active .....................................: "
				+ PrimaryConfig.isUseGrimCitadels();
		Utility.sendChat(p, chatMessage, ChatFormatting.GREEN);

	}
	
	public static void register(){

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

			dispatcher.register(Commands.literal("harderfarther").requires((source) ->
							{
								return source.hasPermission(3);
							}
					)
					.then(Commands.literal("setDebugLevel").then(
							Commands.argument("debugLevel", IntegerArgumentType.integer(0, 2)).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setDebugLevel(p, IntegerArgumentType.getInteger(ctx, "debugLevel"));
							})))
					// update or add a speed value for the block the player is standing on.
					.then(Commands.literal("setBonusRange").then(
							Commands.argument("bonusRange", IntegerArgumentType.integer(500, 6000)).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setBonusRange(p, IntegerArgumentType.getInteger(ctx, "bonusRange"));
							})))
					.then(Commands.literal("setUseGrimCitadels").then(
							Commands.argument("useGrimCitadels", BoolArgumentType.bool()).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setUseGrimCitadels(p, BoolArgumentType.getBool(ctx, "useGrimCitadels"));
							})))
					.then(Commands.literal("setGrimCitadelsRadius").then(
							Commands.argument("grimCitadelsRadius", IntegerArgumentType.integer(4, 11)).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setGrimCitadelsRadius(p, IntegerArgumentType.getInteger(ctx, "grimCitadelsRadius"));
							})))
					.then(Commands.literal("setMakeHarderOverTime").then(
							Commands.argument("setMakeHarderOverTime", BoolArgumentType.bool()).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setMakeHarderOverTime(p, BoolArgumentType.getBool(ctx, "setMakeHarderOverTime"));
							})))
					.then(Commands.literal("setMaxHarderTimeMinutes").then(
							Commands.argument("setMaxHarderTimeMinutes", IntegerArgumentType.integer(20, 28800)).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setMaxHarderTimeMinutes(p, IntegerArgumentType.getInteger(ctx, "setMaxHarderTimeMinutes"));
							})))
					.then(Commands.literal("setXpBottleChance").then(
							Commands.argument("xpBottleChance", IntegerArgumentType.integer(0, 33)).executes(ctx -> {
								ServerPlayer p = ctx.getSource().getPlayerOrException();
								return setOddsDropExperienceBottle(p, IntegerArgumentType.getInteger(ctx, "xpBottleChance"));
							})))
					.then(Commands.literal("chunkReport").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						p.level().gatherChunkSourceStats();
						Utility.sendChat(p, "\nChunk\n" + p.level().gatherChunkSourceStats(), ChatFormatting.GREEN);
						return 1;
					}))
					.then(Commands.literal("grimReport").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						String report = "Grim Citadels at : " + GrimCitadelManager.getCitadelListAsString();
						Utility.sendChat(p, report, ChatFormatting.GREEN);
						return 1;
					})).then(Commands.literal("lootReport").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						String report = LootManager.report();
						Utility.sendBoldChat(p, "\nLoot Report", ChatFormatting.GOLD);
						Utility.sendChat(p, report, ChatFormatting.YELLOW);
						reportUseLootDrop(p);
						reportOddsXpBottleDrop(p);
						reportGrimLifeHeartPulseSeconds(p);
						return 1;
					})).then(Commands.literal("info").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						printInfo(p);
						return 1;
						// return 1;
					})).then(Commands.literal("grimEffectsInfo").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						printGrimEffectsInfo(p);
						return 1;
					})).then(Commands.literal("grimInfo").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						printGrimInfo(p);
						return 1;
					})).then(Commands.literal("timeInfo").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						printTimeInfo(p);
						return 1;
					})).then(Commands.literal("colorInfo").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						printColorInfo(p);
						return 1;
					})).then(Commands.literal("boostInfo").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						String chatMessage = "\nHarder Farther Maximum Monster Boosts";
						Utility.sendBoldChat(p, chatMessage, ChatFormatting.DARK_GREEN);

						chatMessage = "  Monster Health ..........................: " + PrimaryConfig.getHpMaxBoost() + " %."
								+ "\n  Damage ..............................................: " + PrimaryConfig.getAtkDmgBoost()
								+ " %." + "\n  Movement .........................................: "
								+ PrimaryConfig.getSpeedBoost() + " %." + "\n  KnockBack Resistance .........: "
								+ PrimaryConfig.getKnockBackMod() + " %.";
						Utility.sendChat(p, chatMessage, ChatFormatting.GREEN);
						return 1;
					}))
					.then(Commands.literal("musicInfo").executes(ctx -> {
						ServerPlayer p = ctx.getSource().getPlayerOrException();
						printGrimMusicInfo(p);
						return 1;
					})).then(Commands.literal("setFogColors")
							.then(Commands.argument("R", IntegerArgumentType.integer(0, 100))
									.then(Commands.argument("G", IntegerArgumentType.integer(0, 100))
											.then(Commands.argument("B", IntegerArgumentType.integer(0, 100))
													.executes(ctx -> {
														ServerPlayer p = ctx.getSource().getPlayerOrException();
														int r = IntegerArgumentType.getInteger(ctx, "R");
														int g = IntegerArgumentType.getInteger(ctx, "G");
														int b = IntegerArgumentType.getInteger(ctx, "B");
														return setFogColors(p, r, g, b);
													}))))
					));
		});

	}


	private static int setMakeHarderOverTime(ServerPlayer p, boolean b) {
		PrimaryConfig.setMakeHarderOverTime(b);
		printTimeInfo(p);
		return 1;
	}

	
	private static int setMaxHarderTimeMinutes(ServerPlayer p, int newValue) {
		PrimaryConfig.setMaxHarderTimeMinutes(newValue);
		printTimeInfo(p);
		return 1;
	}

	private static void printTimeInfo(ServerPlayer p) {
		long time = p.level().getChunk(p.blockPosition()).getInhabitedTime() / 1200;
		float timeDifficulty = 100* HarderTimeManager.getTimeDifficulty((ServerLevel)p.level(), (LivingEntity) p);
		Utility.sendBoldChat(p,"\nTime Info ", ChatFormatting.AQUA);
		Utility.sendChat(p, "  Make Harder Over Time .........: " + PrimaryConfig.isMakeHarderOverTime() +".", ChatFormatting.AQUA);
		Utility.sendChat(p, "  Maximum Difficulty .......................: " + PrimaryConfig.getMaxHarderTimeMinutes() +" minutes.", ChatFormatting.AQUA);
		Utility.sendChat(p, "  Current Difficulty ......................: " + timeDifficulty +" %.", ChatFormatting.AQUA);
			Utility.sendChat(p, "  Current Chunk Age ...................: " + time+" minutes.", ChatFormatting.AQUA);

	}

	
	private static void reportUseLootDrop(ServerPlayer p) {
		Utility.sendChat(p, "  Use Loot Drops ...................: " + PrimaryConfig.isUseLootDrops() +".", ChatFormatting.YELLOW);
	}
	
	private static void reportOddsXpBottleDrop(ServerPlayer p) {
		Utility.sendChat(p, "  OddsXpBottleDrop ..............: " + PrimaryConfig.getOddsDropExperienceBottle() +"%", ChatFormatting.YELLOW);
	}

	private static void reportGrimLifeHeartPulseSeconds(ServerPlayer p) {
		Utility.sendChat(p, "  Life Heart Pulse Rate......: " + "roughly " + PrimaryConfig.getGrimLifeheartPulseSeconds() +" seconds +/- 50%.", ChatFormatting.YELLOW);
	}
	
	public static int setBonusRange(ServerPlayer p, int newRange) {
		PrimaryConfig.setBonusRange(newRange);
		printGrimInfo(p);
		return 1;
	}

	public static int setDebugLevel(ServerPlayer p, int newDebugLevel) {
		PrimaryConfig.setDebugLevel(newDebugLevel);
		printInfo(p);
		return 1;
	}


	private static int setFogColors(ServerPlayer p, int r, int g, int b) {
		PrimaryConfig.setGrimFogRedPercent(r);
		PrimaryConfig.setGrimFogGreenPercent(g);
		PrimaryConfig.setGrimFogBluePercent(b);
		updateGCFogToAllClients ((ServerLevel)p.level(), (double)r/100, (double)g/100, (double)b/100);
		printColorInfo(p);
		return 1;
	}
	
	public static int setUseGrimCitadels(ServerPlayer p, boolean newValue) {
		PrimaryConfig.setUseGrimCitadels(newValue);
		printGrimInfo(p);
		return 1;
	}

	private static int setGrimCitadelsRadius(ServerPlayer p, int radius) {
		PrimaryConfig.setGrimCitadelsRadius(radius);
		printGrimInfo(p);
		return 0;
	}
	
	public static int setOddsDropExperienceBottle(ServerPlayer p, int newOdds) {
		PrimaryConfig.setOddsDropExperienceBottle(newOdds);
		reportOddsXpBottleDrop(p);
		return 1;
	}
	
	private static void updateGCFogToAllClients(ServerLevel level, double r , double g , double b) {
		List<ServerPlayer> allPlayers = level.getServer().getPlayerList().getPlayers();
		Iterator<ServerPlayer> apI = allPlayers.iterator();

		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(r);
		buf.writeDouble(g);
		buf.writeDouble(b);

		while (apI.hasNext()) { // sends to all players online.
			ServerPlayer sp = apI.next();
			ServerPlayNetworking.send((ServerPlayer) sp, SyncFogToClientsPacket.GAME_PACKET_SYNC_FOG_COLOR_S2C, buf);
		}
	}
	
	String subcommand = "";
	
	String value = "";

}
