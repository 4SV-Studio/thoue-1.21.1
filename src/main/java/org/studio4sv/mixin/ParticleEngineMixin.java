package org.studio4sv.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {

    // The patched ParticleEngine turns the light layer OFF at the end of its
    // render pass. Anything queued into the main buffer source later in the same
    // frame (e.g. the ItemPickupParticle copy of an XP orb) is then drawn with a
    // missing lightmap and renders black for a few ticks while an orb is picked
    // up. Re-enabling the light layer right after the pass restores the original
    // behaviour and keeps those late draws bright.
    @Inject(method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;Ljava/util/function/Predicate;)V",
            at = @At("RETURN"))
    private void thoue$keepLightLayerOn(LightTexture light, Camera camera, float partialTicks, Frustum frustum, Predicate<ParticleRenderType> predicate, CallbackInfo ci) {
        light.turnOnLightLayer();
    }
}