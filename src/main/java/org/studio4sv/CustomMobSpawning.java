package org.studio4sv;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import org.studio4sv.entity.ModEntities;
import org.studio4sv.entity.ThoMob;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID)
public class CustomMobSpawning {

    private static final List<Supplier<EntityType<?>>> CUSTOM_MOB_SUPPLIERS = List.of(
            ModEntities.CURSED_BEAR::get,
            ModEntities.CURSED_KNIGHT::get,
            ModEntities.CURSED_GECKO::get,
            ModEntities.CURSED_SNAKE::get,
            ModEntities.CURSED_CROCODILE::get,
            ModEntities.FOREST_SPIRIT::get,
            ModEntities.CURSED_HUMAN::get,
            ModEntities.SKINT_SLIME::get,
            ModEntities.SKINTONIT_SLIME::get,
            ModEntities.CURSED_VILLAGER::get
    );

    @SubscribeEvent
    public static void onSpawnPlacementCheck(MobSpawnEvent.SpawnPlacementCheck event) {
        if (isNaturalMonsterSpawn(event.getEntityType(), event.getSpawnType())
                && isCustomMobType(event.getEntityType())) {
            event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
        }
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        if (isNaturalMonsterSpawn(event.getEntity().getType(), event.getSpawnType())
                && !(event.getEntity() instanceof ThoMob)) {
            event.setSpawnCancelled(true);
            if (event.getLevel().getRandom().nextFloat() >= 0.3F) {
                spawnPack(event);
            }
        }
    }

    private static boolean isNaturalMonsterSpawn(EntityType<?> entityType, MobSpawnType spawnType) {
        return entityType.getCategory() == MobCategory.MONSTER
                && (spawnType == MobSpawnType.NATURAL || spawnType == MobSpawnType.PATROL);
    }

    private static boolean isCustomMobType(EntityType<?> entityType) {
        for (Supplier<EntityType<?>> supplier : CUSTOM_MOB_SUPPLIERS) {
            if (supplier.get() == entityType) {
                return true;
            }
        }
        return false;
    }

    private static void spawnPack(FinalizeSpawnEvent event) {
        ServerLevel level = (ServerLevel) event.getLevel();
        EntityType<?> type = CUSTOM_MOB_SUPPLIERS
                .get(level.random.nextInt(CUSTOM_MOB_SUPPLIERS.size()))
                .get();
        int count = 2 + level.random.nextInt(4);

        double spawnX = event.getX();
        double spawnY = event.getY();
        double spawnZ = event.getZ();

        for (int i = 0; i < count; i++) {
            Mob mob = (Mob) type.create(level);
            if (mob == null) {
                continue;
            }
            placeNear(mob, spawnX, spawnY, spawnZ, level);
            mob.finalizeSpawn(level, event.getDifficulty(), event.getSpawnType(), null);
            level.addFreshEntity(mob);
        }
    }

    private static void placeNear(Mob mob, double x, double y, double z, ServerLevel level) {
        for (int attempt = 0; attempt < 8; attempt++) {
            double px = x + (level.random.nextDouble() - 0.5) * 4.0;
            double pz = z + (level.random.nextDouble() - 0.5) * 4.0;
            mob.setPos(px, y, pz);
            if (level.isUnobstructed(mob)) {
                return;
            }
        }
        mob.setPos(x, y, z);
    }
}