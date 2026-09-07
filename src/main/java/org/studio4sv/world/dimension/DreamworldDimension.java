package org.studio4sv.world.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.procedures.DreamworldPlayerEntersDimensionProcedure;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID)
public class DreamworldDimension {

    public static final ResourceKey<Level> DREAMWORLD = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "dreamworld")
    );

    @EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class DreamworldSpecialEffectsHandler {

        @SubscribeEvent
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                    Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, false
            ) {
                @Override
                public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 color, float sunHeight) {
                    return color;
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return true;
                }
            };
            event.register(
                    ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "dreamworld"),
                    customEffect
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        TheHeartofUniverseEngine.LOGGER.info("PlayerChangedDimensionEvent: to={}, from={}",
                event.getTo(), event.getFrom());
        if (event.getTo() == DREAMWORLD) {
            TheHeartofUniverseEngine.LOGGER.info("Player entered thoue:dreamworld, placing island...");
            DreamworldPlayerEntersDimensionProcedure.execute(event.getEntity().level());
        }
    }
}