package org.studio4sv.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, TheHeartofUniverseEngine.MODID);

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_BEAR_SPAWN_EGG =
            ITEMS.register("bear_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_BEAR.get(), 0x3A2A1A, 0x6B5136, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_KNIGHT_SPAWN_EGG =
            ITEMS.register("knight_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_KNIGHT.get(), 0x606060, 0x2E2E3E, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_GECKO_SPAWN_EGG =
            ITEMS.register("gecko_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_GECKO.get(), 0x2E5D3A, 0x5DAF57, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_SNAKE_SPAWN_EGG =
            ITEMS.register("snake_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_SNAKE.get(), 0x2E4A24, 0x5E9A4A, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_CROCODILE_SPAWN_EGG =
            ITEMS.register("crocodile_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_CROCODILE.get(), 0x2A4A36, 0x4E7A5A, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> FOREST_SPIRIT_SPAWN_EGG =
            ITEMS.register("forest_spirit_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.FOREST_SPIRIT.get(), 0x3A4A2A, 0x6E8A4E, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_HUMAN_SPAWN_EGG =
            ITEMS.register("cursed_human_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_HUMAN.get(), 0x4A3A2A, 0x7A5A3A, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> SKINT_SLIME_SPAWN_EGG =
            ITEMS.register("skint_slime_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.SKINT_SLIME.get(), 0x1E3A5A, 0x3E7ABE, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> SKINTONIT_SLIME_SPAWN_EGG =
            ITEMS.register("skintonit_slime_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.SKINTONIT_SLIME.get(), 0x2A1E3A, 0x6E3EBE, new Item.Properties()));

    public static final DeferredHolder<Item, ModSpawnEggItem> CURSED_VILLAGER_SPAWN_EGG =
            ITEMS.register("cursed_villager_spawn_egg", () -> new ModSpawnEggItem(
                    ModEntities.CURSED_VILLAGER.get(), 0x4A3A2A, 0x6A4A2A, new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
