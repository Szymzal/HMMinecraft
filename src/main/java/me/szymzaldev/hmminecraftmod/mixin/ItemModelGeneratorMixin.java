package me.szymzaldev.hmminecraftmod.mixin;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.environmentz.init.ItemInit.WANDERER_ARMOR_MATERIAL;

// Make models of modded armor 2 base layers instead of 1
@Mixin(ItemModelGenerator.class)
public class ItemModelGeneratorMixin {

    @Redirect(
            method = "registerArmor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getMaterial()Lnet/minecraft/item/ArmorMaterial;", ordinal = 0)
    )
    private ArmorMaterial getMaterial(ArmorItem instance) {
        if (instance.getMaterial() == WANDERER_ARMOR_MATERIAL) {
            return ArmorMaterials.LEATHER;
        }
        return instance.getMaterial();
    }

    @Redirect(
            method = "registerArmor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getMaterial()Lnet/minecraft/item/ArmorMaterial;", ordinal = 2)
    )
    private ArmorMaterial getMaterial2(ArmorItem instance) {
        return getMaterial(instance);
    }
}
