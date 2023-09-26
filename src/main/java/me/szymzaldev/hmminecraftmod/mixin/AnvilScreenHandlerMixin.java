package me.szymzaldev.hmminecraftmod.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// https://github.com/Globox1997/EnvironmentZ/issues/105
@Mixin(value = AnvilScreenHandler.class, priority = 1500)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    protected AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @TargetHandler(
            mixin = "net.environmentz.mixin.AnvilScreenHandlerMixin",
            name = "updateResultMixin"
    )
    @Redirect(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getNbt()Lnet/minecraft/nbt/NbtCompound;"
            )
    )
    private NbtCompound getNbt(ItemStack item) {
        NbtCompound itemNbt = item.getNbt();
        return itemNbt == null ? new NbtCompound() : itemNbt;
    }
}
