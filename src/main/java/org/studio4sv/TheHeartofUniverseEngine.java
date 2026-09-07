package org.studio4sv;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.studio4sv.entity.ModEntities;
import org.studio4sv.item.ModItems;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TheHeartofUniverseEngine.MODID)
public class TheHeartofUniverseEngine {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "thoue";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public TheHeartofUniverseEngine(IEventBus modEventBus, ModContainer modContainer) {
        // Register the Deferred Register for particle types
        ModParticles.PARTICLES.register(modEventBus);

        // Register entities, items and creative mode tab
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}