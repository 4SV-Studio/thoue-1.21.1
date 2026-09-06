package org.studio4sv.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.particle.FlashParticleOptions;

@OnlyIn(Dist.CLIENT)
public class XpOrbRenderer extends EntityRenderer<ExperienceOrb> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            TheHeartofUniverseEngine.MODID, "textures/entity/experience_orb.png");

    private static final float SPIN_TICKS = 60.0F;
    private static final float FULL_ROTATION = (float) (Math.PI * 2.0);

    private static final int COLOR = 255;
    private static final int LIGHT = LightTexture.FULL_BRIGHT;

    // COLOR_WRITE (no depth write) + sortOnUpload=true: this quad is
    // translucent, and vanilla translucent RenderTypes never write depth -
    // that's precisely why water, glass panes, potion swirl, etc. all
    // layer correctly with each other. Writing depth here made every
    // fully-transparent texel of the orb's texture act as an opaque
    // occluder in the depth buffer, hiding anything translucent (water,
    // the flash particle) rendered behind it. sortOnUpload keeps the quad
    // properly back-to-front sorted against other translucent geometry now
    // that depth can't do that job for us.
    private static final RenderType ORB_RENDER_TYPE = RenderType.create(
            "thoue:orb",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> ClientShaders.orb))
                    .setTextureState(new RenderStateShard.TextureStateShard(TEXTURE, false, false))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .createCompositeState(false));

    private static RenderType getRenderType() {
        if (ClientShaders.orb != null) {
            return ORB_RENDER_TYPE;
        }
        return RenderType.itemEntityTranslucentCull(TEXTURE);
    }

    public XpOrbRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Override
    protected int getBlockLightLevel(ExperienceOrb orb, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(orb, pos) + 7, 0, 15);
    }

    @Override
    public void render(ExperienceOrb orb, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0F, 0.25F, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.scale(0.3F, 0.3F, 0.3F);

        float spin = (orb.tickCount + partialTick) / SPIN_TICKS * FULL_ROTATION;
        poseStack.mulPose(Axis.ZP.rotation(spin));

        VertexConsumer vertexconsumer = buffer.getBuffer(getRenderType());
        PoseStack.Pose pose = poseStack.last();
        vertex(vertexconsumer, pose, -0.5F, -0.5F, 0.0F, 1.0F);
        vertex(vertexconsumer, pose, 0.5F, -0.5F, 1.0F, 1.0F);
        vertex(vertexconsumer, pose, 0.5F, 0.5F, 1.0F, 0.0F);
        vertex(vertexconsumer, pose, -0.5F, 0.5F, 0.0F, 0.0F);

        poseStack.popPose();
        super.render(orb, entityYaw, partialTick, poseStack, buffer, packedLight);

        spawnOrbFlash(orb);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float u, float v) {
        if (ClientShaders.orb != null) {
            consumer.addVertex(pose, x, y, 0.0F)
                    .setUv(u, v)
                    .setColor(COLOR, COLOR, COLOR, COLOR)
                    .setLight(LIGHT);
        } else {
            consumer.addVertex(pose, x, y, 0.0F)
                    .setColor(COLOR, COLOR, COLOR, COLOR)
                    .setUv(u, v)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(LIGHT)
                    .setNormal(pose, 0.0F, 1.0F, 0.0F);
        }
    }

    private static void spawnOrbFlash(ExperienceOrb orb) {
        if (orb.level().isClientSide && orb.getRandom().nextFloat() < 0.065F) {
            double d0 = 0.15F;
            double d1 = orb.getRandom().nextDouble() * d0;
            double d2 = 2.0 * Math.PI * orb.getRandom().nextDouble();
            double d3 = 2.0 * orb.getRandom().nextDouble() - 1.0;
            double d4 = Math.sqrt(1.0 - d3 * d3) * d1;
            double d5 = Math.cos(d2) * d4;
            double d6 = Math.sin(d2) * d4;
            double d7 = d1 * d3;
            orb.level().addParticle(FlashParticleOptions.hex(0xFFC900),
                    orb.getX() + d5,
                    orb.getY() + 0.25 + d7,
                    orb.getZ() + d6,
                    0.0, 0.0, 0.0);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ExperienceOrb orb) {
        return TEXTURE;
    }
}