package org.studio4sv.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.studio4sv.particle.FlashParticleOptions;

@Mixin(ExperienceOrbRenderer.class)
public class ExperienceOrbRendererMixin {

    private static final double SPIN_TICKS = 72.0;

    // Blended (not cutout) so the rotating quad's edges stay smooth instead
    // of alpha-test-flickering, while opaqueAlpha() below still keeps it
    // looking fully solid.
    private static final RenderType OPAQUE_ORB =
            RenderType.itemEntityTranslucentCull(ResourceLocation.withDefaultNamespace("textures/entity/experience_orb.png"));

    // Vanilla picks the texture frame from the orb's XP value (getIcon). The
    // mod's custom orb atlas has a much darker "tiny" frame (index 0) for 1-2 XP
    // orbs, which reads as a white->dim flicker. Always draw a bright gold frame
    // so every orb (any XP) looks uniformly bright.
    //
    // require = 1: if this target signature is wrong for your MC version /
    // mappings, the game will now CRASH AT LAUNCH with a clear message
    // instead of silently no-oping. That crash message tells us exactly
    // what to fix.
    @Redirect(method = "render", require = 1,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;getIcon()I"))
    private int brightIcon(ExperienceOrb orb) {
        return 5;
    }

    // Vanilla emits the orb with 50% alpha (128), which makes it look washed-out
    // and blends with whatever is behind it. Render it fully opaque so it stays
    // a solid bright sprite.
    @ModifyConstant(method = "vertex", require = 1,
            constant = @Constant(intValue = 128))
    private static int opaqueAlpha(int original) {
        return 255;
    }

    @ModifyArg(method = "render", require = 1,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"),
            index = 0)
    private RenderType opaqueOrb(RenderType original) {
        // Opaque-looking, no translucency artifacts: the orb reads as a
        // solid glowing sprite.
        return OPAQUE_ORB;
    }

    // These four target the 4 corner calls to the private static vertex()
    // helper within render() (one per quad vertex). require = 4 means:
    // if fewer (or more) than exactly 4 matching call sites are found,
    // the game crashes at launch instead of quietly skipping some/all of
    // them - which is what was almost certainly happening before and is
    // why the vanilla red/blue color pulse ("яркий -> тускнеет -> яркий")
    // kept showing through despite these overrides being present in code.
    @ModifyArg(method = "render", require = 4,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 4)
    private int whiteR(int p_254283_) {
        return 255;
    }

    @ModifyArg(method = "render", require = 4,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 5)
    private int whiteG(int p_254566_) {
        return 255;
    }

    @ModifyArg(method = "render", require = 4,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 6)
    private int whiteB(int p_253882_) {
        return 255;
    }

    @ModifyArg(method = "render", require = 4,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 9)
    private int fullBright(int p_254372_) {
        return LightTexture.FULL_BRIGHT;
    }

    @ModifyArg(method = "render", require = 4,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 3)
    private float centerY(float p_254066_) {
        return p_254066_ - 0.25F;
    }

    @Inject(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V",
                    shift = At.Shift.AFTER))
    private void spin(ExperienceOrb p_114599_, float p_114600_, float p_114601_, PoseStack p_114602_, MultiBufferSource p_114603_, int p_114604_, CallbackInfo ci) {
        float f = (float) (((double) p_114599_.tickCount + (double) p_114600_) / SPIN_TICKS * Math.PI * 2.0);
        p_114602_.translate(0.0F, 0.25F, 0.0F);
        p_114602_.mulPose(Axis.ZP.rotation(f));
    }

    @Inject(method = "render",
            at = @At("RETURN"))
    private void spawnOrbFlash(ExperienceOrb p_114599_, float p_114600_, float p_114601_, PoseStack p_114602_, MultiBufferSource p_114603_, int p_114604_, CallbackInfo ci) {
        if (p_114599_.level().isClientSide && p_114599_.getRandom().nextFloat() < 0.065F) {
            double d0 = 0.15F;
            double d1 = p_114599_.getRandom().nextDouble() * d0;
            double d2 = 2.0 * Math.PI * p_114599_.getRandom().nextDouble();
            double d3 = 2.0 * p_114599_.getRandom().nextDouble() - 1.0;
            double d4 = Math.sqrt(1.0 - d3 * d3) * d1;
            double d5 = Math.cos(d2) * d4;
            double d6 = Math.sin(d2) * d4;
            double d7 = d1 * d3;
            p_114599_.level().addParticle(FlashParticleOptions.hex(0xFFC900),
                    p_114599_.getX() + d5,
                    p_114599_.getY() + 0.25 + d7,
                    p_114599_.getZ() + d6,
                    0.0, 0.0, 0.0);
        }
    }
}