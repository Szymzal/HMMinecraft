package me.szymzaldev.hmminecraftmod.mixin.impaled;

import ladysnake.impaled.common.init.ImpaledEntityTypes;
import me.szymzaldev.hmminecraftmod.update.impaled.Impaled;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ImpaledEntityTypes.class)
public class ImpaledEntityTypesMixin {

    @Redirect(
            method = "register",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/Registry;field_11145:Lnet/minecraft/core/DefaultedMappedRegistry;")
    )
    private static DefaultedMappedRegistry field_11145_1() {
        return Impaled.field_11145();
    }

    @Redirect(
            method = "register",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;register(Lnet/minecraft/core/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static <T> T register_1(Registry<? super T> registry, String name, T value) {
        return Impaled.register(registry, name, value);
    }
}
