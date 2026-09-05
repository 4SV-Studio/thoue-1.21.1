package org.studio4sv;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.studio4sv.particle.FlashParticleOptions;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, TheHeartofUniverseEngine.MODID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<FlashParticleOptions>> FLASH = PARTICLES.register("flash",
            () -> new ParticleType<FlashParticleOptions>(false) {
                @Override
                public MapCodec<FlashParticleOptions> codec() {
                    return FlashParticleOptions.CODEC;
                }

                @Override
                public StreamCodec<? super RegistryFriendlyByteBuf, FlashParticleOptions> streamCodec() {
                    return FlashParticleOptions.STREAM_CODEC;
                }
            });
}