package me.szymzaldev.hmminecraftmod.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// https://github.com/Globox1997/EnvironmentZ/issues/105
@Mixin(value = AnvilMenu.class, priority = 1500)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    protected AnvilMenuMixin(int containerID, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuType.ANVIL, containerID, playerInventory, access);
    }

    @TargetHandler(
            mixin = "net.environmentz.mixin.AnvilScreenHandlerMixin",
            name = "updateResultMixin"
    )
    @Redirect(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getTag()Lnet/minecraft/nbt/CompoundTag;"
            )
    )
    private CompoundTag getTag(ItemStack item) {
        CompoundTag itemTag = item.getTag();
        return itemTag == null ? new CompoundTag() : itemTag;
    }
}
