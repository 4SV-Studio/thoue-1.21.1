package org.studio4sv.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMenuBackgroundMixin {

    private static final ResourceLocation STATIC_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/title/background/background.png");

    @Inject(method = "renderPanorama", at = @At("HEAD"), cancellable = true)
    private void tho$renderStaticBackground(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();
        guiGraphics.blit(STATIC_BACKGROUND, 0, 0, guiWidth, guiHeight, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
        ci.cancel();
    }
}