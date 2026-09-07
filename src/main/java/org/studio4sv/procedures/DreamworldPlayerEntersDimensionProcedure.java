package org.studio4sv.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.studio4sv.TheHeartofUniverseEngine;

public class DreamworldPlayerEntersDimensionProcedure {

    public static void execute(LevelAccessor world) {
        if (world instanceof ServerLevel serverLevel) {
            ResourceLocation island = ResourceLocation.fromNamespaceAndPath(TheHeartofUniverseEngine.MODID, "dream_island");
            StructureTemplate template = serverLevel.getStructureManager().getOrCreate(island);
            TheHeartofUniverseEngine.LOGGER.info("Placing structure {} at (-10,80,-7); size={}",
                    island, template.getSize());
            template.placeInWorld(
                    serverLevel,
                    new BlockPos(-10, 80, -7),
                    new BlockPos(-10, 80, -7),
                    new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                    serverLevel.random,
                    3
            );
        }
    }
}