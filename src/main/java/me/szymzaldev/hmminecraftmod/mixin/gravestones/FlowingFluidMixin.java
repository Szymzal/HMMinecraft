package me.szymzaldev.hmminecraftmod.mixin.gravestones;

import me.szymzaldev.hmminecraftmod.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowingFluid.class)
public abstract class FlowingFluidMixin {

    @Inject(
            method = "canHoldFluid",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;blocksMotion()Z"),
            cancellable = true
    )
    private void make_blocks_not_breakable_with_water(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModBlockTags.NOT_BREAKABLE_BY_WATER)) {
            cir.setReturnValue(false);
        }
    }

}
