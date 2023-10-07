package me.szymzaldev.hmminecraftmod.mixin.impaled;

import ladysnake.impaled.client.ImpaledClient;
import me.szymzaldev.hmminecraftmod.update.Impaled;
import net.minecraft.core.DefaultedMappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ImpaledClient.class)
public class ImpaledClientMixin {

    @Redirect(
            method = "onInitializeClient",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/Registry;field_11142:Lnet/minecraft/core/DefaultedMappedRegistry;")
    )
    private DefaultedMappedRegistry field_11142() {
        return Impaled.field_11142();
    }

}
