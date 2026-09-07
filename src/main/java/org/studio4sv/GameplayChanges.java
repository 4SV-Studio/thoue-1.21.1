package org.studio4sv;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = TheHeartofUniverseEngine.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameplayChanges {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        for (ServerPlayer player : serverLevel.players()) {
            if (player.getFoodData().getFoodLevel() != 20) {
                player.getFoodData().setFoodLevel(20);
            }
            if (player.getFoodData().getSaturationLevel() != 5.0F) {
                player.getFoodData().setSaturation(5.0F);
            }
        }

        GameRules gameRules = serverLevel.getGameRules();
        if (gameRules.getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            gameRules.getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, null);
        }
    }
}
