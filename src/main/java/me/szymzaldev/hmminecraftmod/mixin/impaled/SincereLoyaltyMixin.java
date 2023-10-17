package me.szymzaldev.hmminecraftmod.mixin.impaled;

import ladysnake.sincereloyalty.SincereLoyalty;
import me.szymzaldev.hmminecraftmod.update.impaled.Impaled;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SincereLoyalty.class)
public class SincereLoyaltyMixin {

    @Redirect(
            method = "<clinit>",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/Registry;field_25108:Lnet/minecraft/resources/ResourceKey;")
    )
    private static ResourceKey<Registry<Item>> field_25108() {
        return Impaled.field_25108();
    }

}
