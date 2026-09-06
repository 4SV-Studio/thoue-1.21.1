package org.studio4sv.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.joml.Vector3f;
import org.studio4sv.particle.FlashParticleOptions;

public class FlashParticle extends TextureSheetParticle {

    public static final ParticleRenderType TRANSLUCENT_NO_DEPTH = new ParticleRenderType() {
        @Override
        public BufferBuilder begin(Tesselator tesselator, TextureManager textures) {
            // Custom thoue:flash shader ignores the lightmap, so the particle
            // never turns black or disappears when the light layer flickers.
            if (!ShaderpackUtil.isShaderpackActive() && ClientShaders.flash != null) {
                RenderSystem.setShader(() -> ClientShaders.flash);
                // Disable fog influence: flashes hug the orbs, so never fade them.
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(2.0E7F);
                RenderSystem.setShaderFogColor(0.0F, 0.0F, 0.0F, 1.0F);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
            } else {
                RenderSystem.setShader(GameRenderer::getParticleShader);
            }
            RenderSystem.depthMask(false);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            // additive blend: SRC_ALPHA + ONE prevents dark fringes around
            // semi-transparent edges when many particles overlap.
            RenderSystem.blendFuncSeparate(0x0302, 1, 0x0302, 1);
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public boolean isTranslucent() {
            return false;
        }

        @Override
        public String toString() {
            return "THOUE_TRANSLUCENT_NO_DEPTH";
        }
    };

    protected FlashParticle(ClientLevel level, double x, double y, double z, FlashParticleOptions options, SpriteSet sprites) {
        super(level, x, y, z);
        this.pickSprite(sprites);
        Vector3f color = options.color();
        this.setColor(color.x(), color.y(), color.z());
        this.scale(0.33F);
        this.gravity = 0.05F;
        this.friction = 0.98F;
        this.lifetime = 60;
        this.setParticleSpeed(
                (this.random.nextDouble() - 0.5) * 0.006,
                -0.002,
                (this.random.nextDouble() - 0.5) * 0.006
        );
    }

    @Override
    public void tick() {
        super.tick();
        float f = (float) this.age / (float) this.lifetime;
        this.setAlpha(f < 0.6F ? 1.0F : 1.0F - (f - 0.6F) / 0.4F);
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return LightTexture.FULL_BRIGHT;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return TRANSLUCENT_NO_DEPTH;
    }

    public static class Provider implements ParticleProvider<FlashParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                FlashParticleOptions options,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xd,
                double yd,
                double zd
        ) {
            return new FlashParticle(level, x, y, z, options, this.sprites);
        }
    }
}