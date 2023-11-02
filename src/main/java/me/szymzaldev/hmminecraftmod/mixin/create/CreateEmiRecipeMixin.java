package me.szymzaldev.hmminecraftmod.mixin.create;

import com.simibubi.create.compat.emi.recipes.CreateEmiRecipe;
import dev.emi.emi.api.stack.EmiStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreateEmiRecipe.class)
public class CreateEmiRecipeMixin {

    @Inject(
            method = "fluidStack",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void fluidStackWithNBT(io.github.fabricators_of_create.porting_lib.util.FluidStack stack, CallbackInfoReturnable<EmiStack> cir) {
        cir.setReturnValue(EmiStack.of(stack.getFluid(), stack.getTag(), stack.getAmount()));
    }

}
