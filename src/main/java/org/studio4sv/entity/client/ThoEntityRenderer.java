package org.studio4sv.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.studio4sv.entity.ThoMob;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Renderer for THOUE GeckoLib mobs. Uses the supplied model.
 */
public class ThoEntityRenderer<T extends ThoMob> extends GeoEntityRenderer<T> {

    public ThoEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }
}
