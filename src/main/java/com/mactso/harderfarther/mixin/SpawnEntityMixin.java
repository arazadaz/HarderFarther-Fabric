package com.mactso.harderfarther.mixin;

import com.mactso.harderfarther.api.DifficultyCalculator;
import com.mactso.harderfarther.config.MobConfig;
import com.mactso.harderfarther.mixinInterfaces.IExtendedMobEntity;
import com.mactso.harderfarther.mixinInterfaces.IExtendedServerWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


//This commented out code is incompatible with the carpet mod since they target the same method.
/*@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {


    private static boolean areListInitialized = false;

    private static ArrayList<Float> difficultySectionNumbers = new ArrayList<>();
    private static ArrayList<List<String>> difficultySectionMobs = new ArrayList<>();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnEntityAndPassengers(Lnet/minecraft/entity/Entity;)V"), method = "Lnet/minecraft/world/SpawnHelper;spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V")
    private static void harderfarther$RemoveEntityChunkSpawm(ServerWorld instance, Entity entity) {

        if (!areListInitialized) {

            MobConfig.getDifficultySections().forEach(section -> {
                difficultySectionNumbers.add(section.getLeft());
                difficultySectionMobs.add(section.getRight());
            });


            areListInitialized = true;
        }
        //end of listInitialization

















        ServerWorld world = (ServerWorld)entity.getWorld();

        if (world.getRegistryKey() == World.OVERWORLD) {

            BlockPos pos = entity.getBlockPos();
            String entityIdentifier = entity.getType().toString().substring(7);
            entityIdentifier = entityIdentifier.replace(".", ":");


            float difficulty = DifficultyCalculator.getDistanceDifficultyHere(world, new Vec3d(pos.getX(), pos.getY(), pos.getZ())) * 100;

            int[] choosenAreaIndex = {-1};
            difficultySectionNumbers.forEach(difficultySectionNumber -> {
                if (difficulty >= difficultySectionNumber) choosenAreaIndex[0]++;
            });


            //default to alllow all mobs if list is empty. - .isEmpty doesn't work as it seems initialized with empty strings.
            if (difficultySectionMobs.get(choosenAreaIndex[0]).get(0).equals("")) {
                world.spawnEntityAndPassengers(entity);
            }

            if (difficultySectionMobs.get(choosenAreaIndex[0]).contains(entityIdentifier)) {
                world.spawnEntityAndPassengers(entity);
            }else{
                //System.out.println("Canceled: " + entityIdentifier);
            }
        }else{
            world.spawnEntityAndPassengers(entity);
        }

    }
}*/


@Mixin(ServerLevelAccessor.class)
public interface SpawnEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "addFreshEntityWithPassengers", cancellable = true)
    private void harderfarther$RemoveEntityNaturalAndChunkSpawn(Entity entity, CallbackInfo ci) {

        if (this instanceof ServerLevel && (entity instanceof Mob)) {

            MobSpawnType spawnReason = ((IExtendedMobEntity)entity).getSpawnReason();

            //Catches natural properly, but chunk spawning still happens. Not sure why, but not my biggest priority.
            if( (spawnReason == MobSpawnType.NATURAL || spawnReason == MobSpawnType.CHUNK_GENERATION )){



                //Start of main logic
                if ( !((IExtendedServerWorld)this).areListInitialized() ) {

                    MobConfig.getDifficultySections().forEach(section -> {
                        ((IExtendedServerWorld)this).getDifficultySectionNumbers().add(section.getA());
                        ((IExtendedServerWorld)this).getDifficultySectionMobs().add(section.getB());
                    });


                    ((IExtendedServerWorld)this).setListInitialized();
                }
                //end of listInitialization







                ServerLevel world = (ServerLevel) entity.level();

                //Start of overworld logic
                if (world.dimension() == Level.OVERWORLD) {

                    BlockPos pos = entity.blockPosition();
                    String entityIdentifier = entity.getType().toString().substring(7);
                    entityIdentifier = entityIdentifier.replace(".", ":");


                    float difficulty = DifficultyCalculator.getDistanceDifficultyHere(world, new Vec3(pos.getX(), pos.getY(), pos.getZ())) * 100;

                    int[] choosenAreaIndex = {-1};
                    ((IExtendedServerWorld)this).getDifficultySectionNumbers().forEach(difficultySectionNumber -> {
                        if (difficulty >= difficultySectionNumber) choosenAreaIndex[0]++;
                    });


                    //default to alllow all mobs if list is empty. - .isEmpty doesn't work as it seems initialized with empty strings.
                    if (((IExtendedServerWorld)this).getDifficultySectionMobs().get(choosenAreaIndex[0]).get(0).equals("")) {
                        return;  //do nothing
                    }

                    if ( !((IExtendedServerWorld)this).getDifficultySectionMobs().get(choosenAreaIndex[0]).contains(entityIdentifier) ) {
                        ci.cancel();
                    }
                }
                //End of Overworld logic

                //End of main logic
            }

        }

    }

}