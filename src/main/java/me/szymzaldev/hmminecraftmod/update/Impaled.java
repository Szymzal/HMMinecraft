package me.szymzaldev.hmminecraftmod.update;

import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class Impaled {

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

    public static void test() {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, "name", EntityType.PIG);
    }

}
