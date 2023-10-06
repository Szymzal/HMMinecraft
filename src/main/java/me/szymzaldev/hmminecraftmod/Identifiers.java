package me.szymzaldev.hmminecraftmod;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static net.environmentz.init.ItemInit.*;

public class Identifiers {

    public static final String BETTEREND_ID = "betterend";

    private Identifiers() {}

    private static final Item[] ARMOR_ITEMS = new Item[] {
            WOLF_HELMET, WOLF_CHESTPLATE, WOLF_LEGGINGS, WOLF_BOOTS,
            WANDERER_HELMET, WANDERER_CHESTPLATE, WANDERER_LEGGINGS, WANDERER_BOOTS,
    };
    private static final ResourceLocation[] SLIM_ARMOR_IDENTIFIERS = new ResourceLocation[] {
            ResourceLocation.of(BETTEREND_ID + ":aeternium", ':'),
            ResourceLocation.of(BETTEREND_ID + ":thallasium", ':'),
            ResourceLocation.of(BETTEREND_ID + ":terminite", ':'),
            ResourceLocation.of(BETTEREND_ID + ":crystalite", ':')
    };

    private static List<ResourceLocation> getArmorIdentifiers(ResourceLocation[] armorIdentifiers) {
        ArrayList<ResourceLocation> identifiers = new ArrayList<>();
        String[] suffixes = new String[] {
                "_helmet",
                "_chestplate",
                "_leggings",
                "_boots",
        };

        for (ResourceLocation slimArmorIdentifier : armorIdentifiers) {
            for (int i = 0; i < 4; i++) {
                identifiers.add(new ResourceLocation(slimArmorIdentifier.getNamespace(), slimArmorIdentifier.getPath() + suffixes[i]));
            }
        }

        return identifiers;
    }

    public static List<Item> getArmorItems() {
        ArrayList<Item> itemList = new ArrayList<>(List.of(ARMOR_ITEMS));

        for (ResourceLocation armor_resource_location : getArmorIdentifiers(SLIM_ARMOR_IDENTIFIERS)) {
            Item item = BuiltInRegistries.ITEM.get(armor_resource_location);
            if (item != Items.AIR) {
                itemList.add(item);
            } else {
                HMMinecraftMod.LOGGER.error("Could not find armor: " + armor_resource_location.toString());
            }
        }

        return itemList;
    }
}
