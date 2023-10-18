package me.szymzaldev.hmminecraftmod.mixin.gravestones;

import me.szymzaldev.hmminecraftmod.HMMinecraftMod;
import net.guavy.gravestones.Gravestones;
import net.guavy.gravestones.block.GravestoneBlock;
import net.guavy.gravestones.block.entity.GravestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GravestoneBlock.class)
public abstract class GravestoneBlockMixin extends Block {

    @Shadow public abstract void dropAllGrave(Level world, BlockPos pos);

    protected GravestoneBlockMixin(Properties properties) {
        super(properties);
    }

    // I have no idea how to do it without using functions which are deprecated
    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity block = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        List<ItemStack> items = super.getDrops(state, params);
        Item gravestone_item = BuiltInRegistries.ITEM.get(new ResourceLocation("gravestones", "gravestone"));
        items.removeIf((itemStack -> itemStack.getItem() == gravestone_item));
        if (block instanceof GravestoneBlockEntity gravestone) {
            items.addAll(gravestone.getItems());
        }
        return items;
    }
}
