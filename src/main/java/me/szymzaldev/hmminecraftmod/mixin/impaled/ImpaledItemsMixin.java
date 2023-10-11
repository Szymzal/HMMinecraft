package me.szymzaldev.hmminecraftmod.mixin.impaled;

import ladysnake.impaled.common.init.ImpaledItems;
import ladysnake.impaled.common.item.ImpaledTridentItem;
import me.szymzaldev.hmminecraftmod.update.Impaled;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ImpaledItems.class)
public class ImpaledItemsMixin {

    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/CreativeModeTab;field_7929:Lnet/minecraft/world/item/CreativeModeTab;")
    )
    private static CreativeModeTab field_7929_1() {
        return Impaled.field_7929();
    }

    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/CreativeModeTab;field_7916:Lnet/minecraft/world/item/CreativeModeTab;")
    )
    private static CreativeModeTab field_7916_1() {
        return Impaled.field_7916();
    }

    @Redirect(
            method = "init",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;method_7892(Lnet/minecraft/world/item/CreativeModeTab;)Lnet/minecraft/world/item/Item$Properties;")
    )
    private static Item.Properties method_7892(Item.Properties instance, CreativeModeTab creativeModeTab) {
        Impaled.registerCreativeTab(creativeModeTab);
        return instance;
    }

    @Inject(
            method = "registerItem",
            at = @At(value = "HEAD")
    )
    private static void applyCreativeTab_1(Item item, String name, CallbackInfoReturnable<Item> cir) {
        Impaled.applyCreativeTab(item);
    }

    @Inject(
            method = "registerTrident",
            at = @At(value = "HEAD"),
            remap = false
    )
    private static void applyCreativeTab_2(ImpaledTridentItem item, String name, boolean registerDispenserBehavior, CallbackInfoReturnable<ImpaledTridentItem> cir) {
        Impaled.applyCreativeTab(item);
    }

    @Redirect(
            method = "registerTrident",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/Registry;field_11142:Lnet/minecraft/core/DefaultedMappedRegistry;")
    )
    private static DefaultedMappedRegistry field_11142_1() {
        return Impaled.field_11142();
    }

    @Redirect(
            method = "registerItem",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/Registry;field_11142:Lnet/minecraft/core/DefaultedMappedRegistry;")
    )
    private static DefaultedMappedRegistry field_11142_2() {
        return Impaled.field_11142();
    }

    @Redirect(
            method = "registerTrident",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;register(Lnet/minecraft/core/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static <T> T register_1(Registry<? super T> registry, String name, T value) {
        return Impaled.register(registry, name, value);
    }

    @Redirect(
            method = "registerItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;register(Lnet/minecraft/core/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static <T> T register_2(Registry<? super T> registry, String name, T value) {
        return Impaled.register(registry, name, value);
    }

}
