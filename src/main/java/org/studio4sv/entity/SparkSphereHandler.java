package org.studio4sv.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.entity.custom.SparkSphere;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SparkSphereHandler {

    @SubscribeEvent
    public static void onPlayerXpDrop(LivingExperienceDropEvent event) {
        if (event.getEntity() instanceof Player && !event.getEntity().level().isClientSide) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        if (event.getEntity().level().isClientSide) return;

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        int xpLevel = oldPlayer.experienceLevel;
        float xpProgress = oldPlayer.experienceProgress;

        if (xpLevel > 0 || xpProgress > 0.0F) {
            SparkSphere sphere = new SparkSphere(
                    ModEntities.SPARK_SPHERE.get(),
                    newPlayer.level(),
                    newPlayer.getUUID(),
                    xpLevel,
                    xpProgress
            );
            sphere.setPos(oldPlayer.getX(), oldPlayer.getY() + 0.7, oldPlayer.getZ());
            newPlayer.level().addFreshEntity(sphere);
        }
    }
}
