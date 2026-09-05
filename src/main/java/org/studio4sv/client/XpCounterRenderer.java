package org.studio4sv.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = "thoue", value = Dist.CLIENT)
public class XpCounterRenderer {
    private static final ResourceLocation BG_TEXTURE = ResourceLocation.fromNamespaceAndPath("thoue", "textures/gui/xp/background.png");

    // ==================== POSITION CONFIG ====================
    // Базовый отступ фона от правого края экрана.
    // Уменьшается на SHIFT_PER_DIGIT за каждый новый разряд числа опыта.
    private static final int BG_RIGHT_MARGIN_BASE = 20 - 130;
    private static final int SHIFT_PER_DIGIT = -5;
    private static final int BG_BOTTOM_MARGIN = 40;
    // Текст: отступ от левого края фона (положительное — внутрь бара)
    private static final int TEXT_OFFSET_X = 20;
    // Текст: вертикальное смещение (0 — центр бара по высоте)
    private static final int TEXT_CENTER_OFFSET_Y = 1;
    // =========================================================

    // Размер текстуры (одновременно размер видимой картинки — бар 155x20)
    private static final int BG_WIDTH = 155;
    private static final int BG_HEIGHT = 20;

    @SubscribeEvent
    static void onRenderGuiPost(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        Font font = mc.font;

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        int level = mc.player.experienceLevel;
        float progress = mc.player.experienceProgress;
        int totalXp = getTotalXpToLevel(level) + (int) (progress * getXpNeededForNextLevel(level));

        String xpText = String.valueOf(totalXp);
        int digits = xpText.length();

        // Чем больше разрядов, тем дальше фон отодвигается от правого края
        int bgRightMargin = BG_RIGHT_MARGIN_BASE - (digits - 1) * SHIFT_PER_DIGIT;

        int bgX = screenWidth - BG_WIDTH - bgRightMargin;
        int bgY = screenHeight - BG_HEIGHT - BG_BOTTOM_MARGIN;

        // Текст выравнивается к ЛЕВОМУ краю фона и центрируется по высоте
        int textX = bgX + TEXT_OFFSET_X;
        int textY = bgY + (BG_HEIGHT - font.lineHeight) / 2 + TEXT_CENTER_OFFSET_Y;

        guiGraphics.blit(BG_TEXTURE, bgX, bgY, 0, 0, BG_WIDTH, BG_HEIGHT, BG_WIDTH, BG_HEIGHT);

        guiGraphics.drawString(font, xpText, textX, textY, 0xFFFFFF, true);
    }

    private static int getXpNeededForNextLevel(int level) {
        if (level <= 15) return 2 * level + 7;
        if (level <= 31) return 5 * level - 38;
        return 9 * level - 158;
    }

    private static int getTotalXpToLevel(int level) {
        if (level <= 15) return level * level + 6 * level;
        if (level <= 31) return (int) (2.5 * level * level - 40.5 * level + 360);
        return (int) (4.5 * level * level - 162.5 * level + 2220);
    }
}