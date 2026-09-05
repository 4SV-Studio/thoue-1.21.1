package org.studio4sv.mixin;

import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.studio4sv.utils.xpConverter;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;",
                    ordinal = 1),
            index = 1)
    private Object[] customCost(Object[] p_237112_) {
        Object[] modified = new Object[p_237112_.length];
        for (int i = 0; i < p_237112_.length; i++) {
            if (p_237112_[i] instanceof Integer) {
                modified[i] = xpConverter.LVLtoXP((Integer) p_237112_[i]);
            } else {
                modified[i] = p_237112_[i];
            }
        }
        return modified;
    }

    @ModifyArg(method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;width(Ljava/lang/String;)I"),
            index = 0)
    private String customButtonVarCost(String p_281403_) {
        if (p_281403_ != null) {
            try {
                return String.valueOf(xpConverter.LVLtoXP(Integer.parseInt(p_281403_)));
            } catch (NumberFormatException ignored) {
                return p_281403_;
            }
        }
        return null;
    }

    @ModifyArg(method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"),
            index = 1)
    private String customButtonCost(String p_281403_) {
        if (p_281403_ != null) {
            try {
                return String.valueOf(xpConverter.LVLtoXP(Integer.parseInt(p_281403_)));
            } catch (NumberFormatException ignored) {
                return p_281403_;
            }
        }
        return null;
    }

    @ModifyArg(method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"),
            index = 4)
    private int customLevelColor(int p_281401_) {
        return 0xFEBA60;
    }
}