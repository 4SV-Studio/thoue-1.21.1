package org.studio4sv;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PermanentNight {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        long day = serverLevel.getGameTime() / 24000L;
        if (serverLevel.getDayTime() % 24000L != 18000L) {
            serverLevel.setDayTime(18000L + day * 24000L);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onSpawnPlacementCheck(MobSpawnEvent.SpawnPlacementCheck event) {
        if (isDarkNaturalSpawn(event.getEntityType(), event.getSpawnType())) {
            event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        if (isDarkNaturalSpawn(event.getEntity().getType(), event.getSpawnType())) {
            event.setSpawnCancelled(true);
        }
    }

    private static boolean isDarkNaturalSpawn(EntityType<?> entityType, MobSpawnType spawnType) {
        return entityType.getCategory() == MobCategory.MONSTER
                && (spawnType == MobSpawnType.NATURAL || spawnType == MobSpawnType.PATROL);
    }
}
