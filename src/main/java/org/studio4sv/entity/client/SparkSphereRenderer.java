package org.studio4sv.entity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.entity.custom.SparkSphere;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SparkSphereRenderer extends GeoEntityRenderer<SparkSphere> {

    public SparkSphereRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SparkSphereModel());
        this.addRenderLayer(new SparkSphereGlowLayer(this));
    }

    @Override
    public RenderType getRenderType(SparkSphere animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    private static class SparkSphereGlowLayer extends AutoGlowingGeoLayer<SparkSphere> {

        SparkSphereGlowLayer(GeoRenderer<SparkSphere> renderer) {
            super(renderer);
        }

        @Override
        protected RenderType getRenderType(SparkSphere animatable, @Nullable MultiBufferSource bufferSource) {
            ResourceLocation base = getTextureResource(animatable);
            ResourceLocation mask = AutoGlowingTexture.getEmissiveResource(base);

            if (Minecraft.getInstance().getResourceManager().getResource(mask).isEmpty())
                return null;

            return super.getRenderType(animatable, bufferSource);
        }
    }
}