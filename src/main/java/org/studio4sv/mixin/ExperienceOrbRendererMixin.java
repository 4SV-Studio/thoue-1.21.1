package org.studio4sv.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.world.entity.ExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbRenderer.class)
public class ExperienceOrbRendererMixin {

    private static final double SPIN_TICKS = 72.0;

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 4)
    private int whiteR(int p_254283_) {
        return 255;
    }

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 5)
    private int whiteG(int p_254566_) {
        return 255;
    }

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 6)
    private int whiteB(int p_253882_) {
        return 255;
    }

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ExperienceOrbRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFIIIFFI)V"),
            index = 9)
    private int fullBright(int p_254372_) {
        return LightTexture.FULL_BRIGHT;
    }

    @ModifyArg(method = "render",
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
}
