package org.studio4sv.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.studio4sv.TheHeartofUniverseEngine;
import org.studio4sv.entity.custom.CursedBear;
import org.studio4sv.entity.custom.CursedCrocodile;
import org.studio4sv.entity.custom.CursedGecko;
import org.studio4sv.entity.custom.CursedHuman;
import org.studio4sv.entity.custom.CursedKnight;
import org.studio4sv.entity.custom.CursedSnake;
import org.studio4sv.entity.custom.CursedVillager;
import org.studio4sv.entity.custom.ForestSpirit;
import org.studio4sv.entity.custom.SkintSlime;
import org.studio4sv.entity.custom.SkintonitSlime;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, TheHeartofUniverseEngine.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CursedBear>> CURSED_BEAR =
            ENTITY_TYPES.register("bear", () -> EntityType.Builder
                    .of(CursedBear::new, MobCategory.MONSTER)
                    .sized(1.4f, 1.7f)
                    .build(TheHeartofUniverseEngine.MODID + ":bear"));

    public static final DeferredHolder<EntityType<?>, EntityType<CursedKnight>> CURSED_KNIGHT =
            ENTITY_TYPES.register("knight", () -> EntityType.Builder
                    .of(CursedKnight::new, MobCategory.MONSTER)
                    .sized(0.75f, 2.1f)
                    .build(TheHeartofUniverseEngine.MODID + ":knight"));

    public static final DeferredHolder<EntityType<?>, EntityType<CursedGecko>> CURSED_GECKO =
            ENTITY_TYPES.register("gecko", () -> EntityType.Builder
                    .of(CursedGecko::new, MobCategory.MONSTER)
                    .sized(0.6f, 0.5f)
                    .build(TheHeartofUniverseEngine.MODID + ":gecko"));

    public static final DeferredHolder<EntityType<?>, EntityType<CursedSnake>> CURSED_SNAKE =
            ENTITY_TYPES.register("snake", () -> EntityType.Builder
                    .of(CursedSnake::new, MobCategory.MONSTER)
                    .sized(0.5f, 0.4f)
                    .build(TheHeartofUniverseEngine.MODID + ":snake"));

    public static final DeferredHolder<EntityType<?>, EntityType<CursedCrocodile>> CURSED_CROCODILE =
            ENTITY_TYPES.register("crocodile", () -> EntityType.Builder
                    .of(CursedCrocodile::new, MobCategory.MONSTER)
                    .sized(1.1f, 0.6f)
                    .build(TheHeartofUniverseEngine.MODID + ":crocodile"));

    public static final DeferredHolder<EntityType<?>, EntityType<ForestSpirit>> FOREST_SPIRIT =
            ENTITY_TYPES.register("forest_spirit", () -> EntityType.Builder
                    .of(ForestSpirit::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.3f)
                    .build(TheHeartofUniverseEngine.MODID + ":forest_spirit"));

    public static final DeferredHolder<EntityType<?>, EntityType<CursedHuman>> CURSED_HUMAN =
            ENTITY_TYPES.register("cursed_human", () -> EntityType.Builder
                    .of(CursedHuman::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(TheHeartofUniverseEngine.MODID + ":cursed_human"));

    public static final DeferredHolder<EntityType<?>, EntityType<SkintSlime>> SKINT_SLIME =
            ENTITY_TYPES.register("skint_slime", () -> EntityType.Builder
                    .of(SkintSlime::new, MobCategory.MONSTER)
                    .sized(0.9f, 0.7f)
                    .build(TheHeartofUniverseEngine.MODID + ":skint_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<SkintonitSlime>> SKINTONIT_SLIME =
            ENTITY_TYPES.register("skintonit_slime", () -> EntityType.Builder
                    .of(SkintonitSlime::new, MobCategory.MONSTER)
                    .sized(0.9f, 0.7f)
                    .build(TheHeartofUniverseEngine.MODID + ":skintonit_slime"));

    public static final DeferredHolder<EntityType<?>, EntityType<CursedVillager>> CURSED_VILLAGER =
            ENTITY_TYPES.register("cursed_villager", () -> EntityType.Builder
                    .of(CursedVillager::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(TheHeartofUniverseEngine.MODID + ":cursed_villager"));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
