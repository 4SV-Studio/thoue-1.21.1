package org.studio4sv;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.studio4sv.world.dimension.DreamworldDimension;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DreamworldRules {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (serverLevel.dimension() != DreamworldDimension.DREAMWORLD) {
            return;
        }
        for (ServerPlayer player : serverLevel.players()) {
            if (player.getY() < 0.0) {
                player.teleportTo(serverLevel, player.getX(), 200.0, player.getZ(), player.getYRot(), player.getXRot());
                player.resetFallDistance();
                player.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity().level().dimension() == DreamworldDimension.DREAMWORLD) {
            event.setCanceled(true);
        }
    }
}