package org.studio4sv.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.entity.ModEntities;
import org.studio4sv.entity.ThoMob;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderers {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        register(event, ModEntities.CURSED_BEAR.get(), "bear", "Head", "bear");
        register(event, ModEntities.CURSED_KNIGHT.get(), "knight", "Head", "knight");
        register(event, ModEntities.CURSED_GECKO.get(), "gecko", "Head", "gecko");
        register(event, ModEntities.CURSED_SNAKE.get(), "snake", null, "snake");
        register(event, ModEntities.CURSED_CROCODILE.get(), "crocodile", "Head", "crocodile");
        register(event, ModEntities.FOREST_SPIRIT.get(), "forest_spirit", "Head", "forest_spirit");
        register(event, ModEntities.CURSED_HUMAN.get(), "cursed_human", "Head", "cursed_human");
        register(event, ModEntities.SKINT_SLIME.get(), "skint_slime", null, "skint_slime");
        register(event, ModEntities.SKINTONIT_SLIME.get(), "skint_slime", null, "skintonit_slime");
        register(event, ModEntities.CURSED_VILLAGER.get(), "cursed_villager", "Head", "cursed_villager");
    }

    private static <T extends ThoMob> void register(EntityRenderersEvent.RegisterRenderers event,
                                                    net.minecraft.world.entity.EntityType<T> type,
                                                    String modelBase, String headBone, String textureName) {
        ThoEntityModel<T> model = new ThoEntityModel<T>(
                ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "geo/" + modelBase + ".geo.json"),
                ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "textures/entity/" + textureName + ".png"),
                ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "animations/" + modelBase + ".animation.json"),
                headBone);

        event.registerEntityRenderer(type, context -> new ThoEntityRenderer<T>(context, model));
    }
}
