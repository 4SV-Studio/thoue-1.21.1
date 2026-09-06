package org.studio4sv.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.studio4sv.TheHeartofUniverseEngine;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientShaders {

    public static ShaderInstance flash;

    public static ShaderInstance orb;

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) {
        // Custom core shader that renders particles WITHOUT sampling the block/sky
        // lightmap. The particle is therefore always fully bright, immune to any
        // transient light-layer/lightmap state that would otherwise black it out
        // (e.g. the frame an XP orb is picked up).
        try {
            event.registerShader(
                    new ShaderInstance(
                            event.getResourceProvider(),
                            ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "flash"),
                            DefaultVertexFormat.PARTICLE),
                    shader -> ClientShaders.flash = shader);
        } catch (Exception e) {
            TheHeartofUniverseEngine.LOGGER.error("Failed to register thoue:flash shader", e);
        }

        try {
            event.registerShader(
                    new ShaderInstance(
                            event.getResourceProvider(),
                            ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "orb"),
                            DefaultVertexFormat.PARTICLE),
                    shader -> ClientShaders.orb = shader);
        } catch (Exception e) {
            TheHeartofUniverseEngine.LOGGER.error("Failed to register thoue:orb shader", e);
        }
    }
}