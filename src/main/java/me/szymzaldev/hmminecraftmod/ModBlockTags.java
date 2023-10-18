package me.szymzaldev.hmminecraftmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> NOT_BREAKABLE_BY_WATER = create("not_breakable_by_water");

    private ModBlockTags() {}

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(name));
    }

}
