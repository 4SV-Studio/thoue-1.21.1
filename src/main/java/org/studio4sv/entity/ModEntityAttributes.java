package org.studio4sv.entity;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
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

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CURSED_BEAR.get(), CursedBear.createAttributes().build());
        event.put(ModEntities.CURSED_KNIGHT.get(), CursedKnight.createAttributes().build());
        event.put(ModEntities.CURSED_GECKO.get(), CursedGecko.createAttributes().build());
        event.put(ModEntities.CURSED_SNAKE.get(), CursedSnake.createAttributes().build());
        event.put(ModEntities.CURSED_CROCODILE.get(), CursedCrocodile.createAttributes().build());
        event.put(ModEntities.FOREST_SPIRIT.get(), ForestSpirit.createAttributes().build());
        event.put(ModEntities.CURSED_HUMAN.get(), CursedHuman.createAttributes().build());
        event.put(ModEntities.SKINT_SLIME.get(), SkintSlime.createAttributes().build());
        event.put(ModEntities.SKINTONIT_SLIME.get(), SkintonitSlime.createAttributes().build());
        event.put(ModEntities.CURSED_VILLAGER.get(), CursedVillager.createAttributes().build());
    }
}
