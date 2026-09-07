package org.studio4sv.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.studio4sv.entity.ThoAggressiveMob;
import org.studio4sv.entity.ThoMob;

public class CursedVillager extends ThoAggressiveMob {

    public CursedVillager(EntityType<? extends Monster> type, Level level) {
        super(type, level, "idle.animation", "walk.animation", "attack.animation");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return ThoMob.createThouAttributes(65.0D, 5.0D, 0.4D, 0.15D);
    }
}
