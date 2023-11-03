package me.szymzaldev.hmminecraftmod.mixin.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.simibubi.create.compat.emi.CreateSlotWidget;
import dev.emi.emi.api.stack.FluidEmiStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CreateSlotWidget.class)
public class CreateSlotWidgetMixin {

    @Inject(
            method = "getTooltip",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/compat/emi/CreateSlotWidget;addCreateAmount(Ljava/util/List;Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;)V", shift = At.Shift.BEFORE)
    )
    private void addNBT(int mouseX, int mouseY, CallbackInfoReturnable<List<ClientTooltipComponent>> cir, @Local LocalRef<FluidVariant> variant) {
        if (((CreateSlotWidget)((Object)this)).getStack() instanceof FluidEmiStack fluid_emi_stack) {
            variant.set(FluidVariant.of(variant.get().getFluid(), fluid_emi_stack.getNbt()));
        }
    }

}
