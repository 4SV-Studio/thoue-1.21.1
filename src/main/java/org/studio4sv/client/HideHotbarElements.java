package org.studio4sv.client;

import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = "thoue", value = Dist.CLIENT)
public class HideHotbarElements {
    private static final Set<ResourceLocation> HIDDEN_LAYERS = Set.of(
            ResourceLocation.withDefaultNamespace("player_health"),
            ResourceLocation.withDefaultNamespace("armor_level"),
            ResourceLocation.withDefaultNamespace("food_level"),
            ResourceLocation.withDefaultNamespace("air_level"),
            ResourceLocation.withDefaultNamespace("experience_bar"),
            ResourceLocation.withDefaultNamespace("experience_level")
    );

    @SubscribeEvent
    static void onRenderGuiLayerPre(RenderGuiLayerEvent.Pre event) {
        if (HIDDEN_LAYERS.contains(event.getName())) {
            event.setCanceled(true);
        }
    }
}
