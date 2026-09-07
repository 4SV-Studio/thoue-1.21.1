package org.studio4sv;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.studio4sv.item.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheHeartofUniverseEngine.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOB_SPAWN_EGGS =
            CREATIVE_MODE_TABS.register("mob_spawn_eggs", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab." + TheHeartofUniverseEngine.MODID + ".mob_spawn_eggs"))
                    .icon(() -> new ItemStack(ModItems.CURSED_BEAR_SPAWN_EGG.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CURSED_BEAR_SPAWN_EGG.get());
                        output.accept(ModItems.CURSED_KNIGHT_SPAWN_EGG.get());
                        output.accept(ModItems.CURSED_GECKO_SPAWN_EGG.get());
                        output.accept(ModItems.CURSED_SNAKE_SPAWN_EGG.get());
                        output.accept(ModItems.CURSED_CROCODILE_SPAWN_EGG.get());
                        output.accept(ModItems.FOREST_SPIRIT_SPAWN_EGG.get());
                        output.accept(ModItems.CURSED_HUMAN_SPAWN_EGG.get());
                        output.accept(ModItems.SKINT_SLIME_SPAWN_EGG.get());
                        output.accept(ModItems.SKINTONIT_SLIME_SPAWN_EGG.get());
                        output.accept(ModItems.CURSED_VILLAGER_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
