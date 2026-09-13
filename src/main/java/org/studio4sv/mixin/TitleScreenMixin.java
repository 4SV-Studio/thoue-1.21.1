package org.studio4sv.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    private static final ResourceLocation STATIC_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/title/background/background.png");

    @Inject(method = "renderPanorama", at = @At("HEAD"), cancellable = true)
    private void tho$renderStaticBackground(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        int guiWidth = minecraft.getWindow().getGuiScaledWidth();
        int guiHeight = minecraft.getWindow().getGuiScaledHeight();
        guiGraphics.blit(STATIC_BACKGROUND, 0, 0, guiWidth, guiHeight, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
        ci.cancel();
    }
}