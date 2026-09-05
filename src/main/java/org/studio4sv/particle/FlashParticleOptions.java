package org.studio4sv.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.studio4sv.ModParticles;

public class FlashParticleOptions implements ParticleOptions {

    public static final MapCodec<FlashParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(FlashParticleOptions::color))
                    .apply(instance, FlashParticleOptions::new));

    public static final StreamCodec<ByteBuf, FlashParticleOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, FlashParticleOptions::color,
            FlashParticleOptions::new);

    private final Vector3f color;

    public FlashParticleOptions(Vector3f color) {
        this.color = color;
    }

    public static FlashParticleOptions hex(int rgb) {
        return new FlashParticleOptions(Vec3.fromRGB24(rgb).toVector3f());
    }

    public Vector3f color() {
        return this.color;
    }

    @Override
    public ParticleType<FlashParticleOptions> getType() {
        return ModParticles.FLASH.get();
    }
}