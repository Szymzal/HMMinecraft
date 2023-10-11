package me.szymzaldev.hmminecraftmod.mixin.impaled;

import com.llamalad7.mixinextras.sugar.Local;
import ladysnake.impaled.client.ImpaledClient;
import me.szymzaldev.hmminecraftmod.HMMinecraftMod;
import me.szymzaldev.hmminecraftmod.update.Impaled;
import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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

    @ModifyArg(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/model/ModelLoadingRegistry;registerModelProvider(Lnet/fabricmc/fabric/api/client/model/ExtraModelProvider;)V"),
            index = 0,
            remap = false
    )
    private ExtraModelProvider newAppender(ExtraModelProvider appender, @Local(ordinal = 0) ResourceLocation tridentId) {
        return ((manager, out) -> {
            ResourceLocation trident_in_inventory = tridentId.withPath(tridentId.getPath() + "_in_inventory");
            HMMinecraftMod.LOGGER.info("Trident in inventory: {}", trident_in_inventory);
            out.accept(new ModelResourceLocation(trident_in_inventory, "inventory"));
        });
    }

}
