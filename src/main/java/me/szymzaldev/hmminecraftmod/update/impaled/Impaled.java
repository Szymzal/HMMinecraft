package me.szymzaldev.hmminecraftmod.update.impaled;

import me.szymzaldev.hmminecraftmod.HMMinecraftMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class Impaled {

    private static CreativeModeTab creative_tab;

    public static void registerCreativeTab(CreativeModeTab tab) {
        creative_tab = tab;
    }

    public static void applyCreativeTab(Item item) {
        if (creative_tab != null) {
            ItemGroupEvents.modifyEntriesEvent(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(creative_tab).get()).register(context -> {
                context.accept(item);
            });
            creative_tab = null;
        } else {
            HMMinecraftMod.LOGGER.error("Hola hola! I don't know where to put it!");
        }
    }

    private Impaled() {}

    public static ResourceKey<Registry<Item>> field_25108() {
        return Registries.ITEM;
    }

    public static DefaultedMappedRegistry field_11142() {
        return (DefaultedMappedRegistry) BuiltInRegistries.ITEM;
    }

    public static DefaultedMappedRegistry field_11145() {
        return (DefaultedMappedRegistry) BuiltInRegistries.ENTITY_TYPE;
    }

    public static CreativeModeTab field_7929() {
        return BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.INGREDIENTS);
    }

    public static CreativeModeTab field_7916() {
        return BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.COMBAT);
    }

    public static <T> T register(Registry<? super T> registry, String name, T value) {
        return Registry.register(registry, name, value);
    }

    public static ModelResourceLocation get_trident_in_inventory(ResourceLocation trident_id) {
        ResourceLocation trident_in_inventory = trident_id.withPath(trident_id.getPath() + "_in_inventory");
        return new ModelResourceLocation(trident_in_inventory, "inventory");
    }

}
