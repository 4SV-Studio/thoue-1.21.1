package org.studio4sv.mixin;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.studio4sv.utils.xpConverter;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    @ModifyArg(method = "renderLabels",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 1)
    private Object[] customCost(Object[] p_237112_) {
        if (p_237112_ != null && p_237112_.length > 0 && p_237112_[0] instanceof Integer) {
            p_237112_[0] = xpConverter.LVLtoXP((Integer) p_237112_[0]);
        }
        return p_237112_;
    }

    @ModifyArg(method = "renderLabels",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I"),
            index = 4)
    private int customCostColor(int p_237100_) {
        return 0xFEBA60;
    }
}