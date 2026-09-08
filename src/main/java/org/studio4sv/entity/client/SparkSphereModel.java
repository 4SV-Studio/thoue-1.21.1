package org.studio4sv.entity.client;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.entity.custom.SparkSphere;
import software.bernie.geckolib.model.GeoModel;

public class SparkSphereModel extends GeoModel<SparkSphere> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "geo/spark_sphere.geo.json");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "textures/entity/spark_sphere.png");
    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "animations/spark_sphere.animation.json");

    @Override
    public ResourceLocation getModelResource(SparkSphere animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(SparkSphere animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(SparkSphere animatable) {
        return ANIMATION;
    }
}
