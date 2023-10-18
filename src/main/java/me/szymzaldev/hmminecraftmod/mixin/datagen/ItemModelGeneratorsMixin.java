package me.szymzaldev.hmminecraftmod.mixin.datagen;

import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.environmentz.init.ItemInit.WANDERER_ARMOR_MATERIAL;

// Make models of modded armor 2 base layers instead of 1
@Mixin(ItemModelGenerators.class)
public abstract class ItemModelGeneratorsMixin {

    @Redirect(
            method = "generateArmorTrims",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorItem;getMaterial()Lnet/minecraft/world/item/ArmorMaterial;", ordinal = 0)
    )
    private ArmorMaterial getMaterial(ArmorItem instance) {
        if (instance.getMaterial() == WANDERER_ARMOR_MATERIAL) {
            return ArmorMaterials.LEATHER;
        }
        return instance.getMaterial();
    }

    @Redirect(
            method = "generateArmorTrims",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorItem;getMaterial()Lnet/minecraft/world/item/ArmorMaterial;", ordinal = 2)
    )
    private ArmorMaterial getMaterial2(ArmorItem instance) {
        return getMaterial(instance);
    }
}
