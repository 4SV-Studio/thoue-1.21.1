package org.studio4sv.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = "thoue", value = Dist.CLIENT)
public class HealthBarRenderer {
    private static final ResourceLocation EMPTY = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/empty.png");
    private static final ResourceLocation FULL = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/full.png");
    private static final ResourceLocation INTERMEDIATE = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/intermediate.png");
    private static final ResourceLocation ABSORPTION = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/absorption.png");
    private static final ResourceLocation POISON = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/poison.png");
    private static final ResourceLocation WITHER = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/wither.png");
    private static final ResourceLocation FROZEN = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/healthbars/frozen.png");

    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 9;

    private static float previousHealth = -1;
    private static float displayedIntermediateHealth = 0;

    @SubscribeEvent
    static void onRenderGuiPost(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.options.hideGui) return;

        float maxHealth = player.getMaxHealth();
        float health = player.getHealth();
        float absorption = player.getAbsorptionAmount();

        if (previousHealth < 0) {
            previousHealth = health;
            displayedIntermediateHealth = health;
        }

        if (health < previousHealth) {
            displayedIntermediateHealth = previousHealth;
        }

        if (displayedIntermediateHealth > health) {
            float diff = displayedIntermediateHealth - health;
            displayedIntermediateHealth -= Math.max(diff * 0.05f, 0.01f);
            if (displayedIntermediateHealth < health) {
                displayedIntermediateHealth = health;
            }
        } else {
            displayedIntermediateHealth = health;
        }

        previousHealth = health;

        ResourceLocation heartsTexture = FULL;
        if (player.hasEffect(MobEffects.POISON)) {
            heartsTexture = POISON;
        } else if (player.hasEffect(MobEffects.WITHER)) {
            heartsTexture = WITHER;
        } else if (player.getTicksFrozen() > 0) {
            heartsTexture = FROZEN;
        }

        GuiGraphics guiGraphics = event.getGuiGraphics();
        int centerX = guiGraphics.guiWidth() / 2;
        int barY = guiGraphics.guiHeight() - 32;

        drawBar(guiGraphics, EMPTY, centerX, barY, 1.0f);

        if (displayedIntermediateHealth > health && displayedIntermediateHealth > 0) {
            drawBar(guiGraphics, INTERMEDIATE, centerX, barY, displayedIntermediateHealth / maxHealth);
        }

        if (absorption > 0) {
            float totalPercent = Math.min((health + absorption) / maxHealth, 1.0f);
            drawBar(guiGraphics, ABSORPTION, centerX, barY, totalPercent);
        }

        if (health > 0) {
            drawBar(guiGraphics, heartsTexture, centerX, barY, health / maxHealth);
        }
    }

    private static void drawBar(GuiGraphics guiGraphics, ResourceLocation texture, int centerX, int y, float percent) {
        if (percent <= 0) return;
        int visibleWidth = Math.max(1, Math.min((int) (percent * BAR_WIDTH), BAR_WIDTH));
        float uOffset = (BAR_WIDTH - visibleWidth) / 2.0f;
        int x = centerX - visibleWidth / 2;
        guiGraphics.blit(texture, x, y, uOffset, 0.0f, visibleWidth, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
    }
}
