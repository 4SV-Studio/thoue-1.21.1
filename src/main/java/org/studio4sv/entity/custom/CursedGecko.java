package org.studio4sv.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.studio4sv.entity.ThoAggressiveMob;
import org.studio4sv.entity.ThoMob;

public class CursedGecko extends ThoAggressiveMob {

    public CursedGecko(EntityType<? extends Monster> type, Level level) {
        super(type, level, "idle.animation", "walk.animation", "attack.animation");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return ThoMob.createThouAttributes(45.0D, 2.0D, 0.75D, 0.25D);
    }
}
