package com.mactso.harderfarther.events;

import com.mactso.harderfarther.config.MyConfig;
import com.mactso.harderfarther.utility.Utility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


// this method only *limits* xp drops that happen to past.  it is part of the farm limiter.

public class ExperienceDropEventHandler {

	public static long tickTimer = 0;

	@SubscribeEvent
	public void onMonsterDrops(LivingExperienceDropEvent event) {
		
		LivingEntity le = event.getEntity();
		if (le == null)   {
			return;
		}
		
		if (le.getWorld().isClient()) {
			return;
		}
		
		if (!(le instanceof MobEntity)) {
			return;
		}
		
		if (le instanceof AnimalEntity) {
			return;
		}
		
		ServerWorld serverLevel = (ServerWorld) le.world;
		
		if (closeToWorldSpawn(serverLevel, le))
			return;
		
		if (tickTimer > serverLevel.getTime()) {
			Utility.debugMsg(2, le, "Mob Died inside no bonus loot frame.");
			return;
		}
		tickTimer = serverLevel.getTime() + (long) 20; // no boosted XP for 1 seconds after a kill.

	}

	private boolean closeToWorldSpawn(ServerWorld serverLevel, LivingEntity le) {

		Vec3d spawnVec = new Vec3d(serverLevel.getLevelProperties().getSpawnX(), serverLevel.getLevelProperties().getSpawnY(),
				serverLevel.getLevelProperties().getSpawnZ());

		BlockPos pos = le.getBlockPos();
		Vec3d eventVec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
		
		if (eventVec.distanceTo(spawnVec) < MyConfig.getSafeDistance()*8)
			return true;

		return false;
	}
}
