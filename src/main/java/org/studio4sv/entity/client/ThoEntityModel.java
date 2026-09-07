package org.studio4sv.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.studio4sv.entity.ThoMob;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import javax.annotation.Nullable;

/**
 * GeckoLib model for THOUE mobs. Backed by explicit resource paths so the asset
 * layout ({@code geo/<name>.geo.json}, {@code animations/<name>.animation.json},
 * {@code textures/entity/<name>.png}) can be wired up regardless of registry id.
 */
public class ThoEntityModel<T extends ThoMob> extends GeoModel<T> {

    private final ResourceLocation modelResource;
    private final ResourceLocation textureResource;
    private final ResourceLocation animationResource;
    private final String headBone;

    public ThoEntityModel(ResourceLocation geo, ResourceLocation texture, ResourceLocation anim,
                          @Nullable String headBone) {
        this.modelResource = geo;
        this.textureResource = texture;
        this.animationResource = anim;
        this.headBone = headBone;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return this.modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return this.textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return this.animationResource;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        if (this.headBone == null) {
            return;
        }

        GeoBone head = getAnimationProcessor().getBone(this.headBone);

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
