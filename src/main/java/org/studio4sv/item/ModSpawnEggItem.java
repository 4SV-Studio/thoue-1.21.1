package org.studio4sv.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

/**
 * Spawn egg used to summon THOUE mobs. Keeps a hard reference to the entity type
 * so it can be spawned without a lookup by tag/colour.
 */
public class ModSpawnEggItem extends SpawnEggItem {

    private final EntityType<?> type;

    public ModSpawnEggItem(EntityType<? extends Mob> type, int primaryColor, int secondaryColor,
                           Properties properties) {
        super((EntityType<? extends Mob>) type, primaryColor, secondaryColor, properties);
        this.type = type;
    }

    @Override
    public EntityType<?> getType(ItemStack stack) {
        return this.type;
    }
}
