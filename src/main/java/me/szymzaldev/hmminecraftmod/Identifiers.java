package me.szymzaldev.hmminecraftmod;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.betterx.betterend.item.CrystaliteBoots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.environmentz.init.ItemInit.*;

public class Identifiers {

    public static final String BETTEREND_ID = "betterend";

    private Identifiers() {}

    private static final Item[] ARMOR_ITEMS = new Item[] {
            WOLF_HELMET, WOLF_CHESTPLATE, WOLF_LEGGINGS, WOLF_BOOTS,
            WANDERER_HELMET, WANDERER_CHESTPLATE, WANDERER_LEGGINGS, WANDERER_BOOTS,
    };
    private static final Identifier[] SLIM_ARMOR_IDENTIFIERS = new Identifier[] {
            Identifier.of(BETTEREND_ID, "aeternium"),
            Identifier.of(BETTEREND_ID, "thallasium"),
            Identifier.of(BETTEREND_ID, "terminite"),
            Identifier.of(BETTEREND_ID, "crystalite")
    };

    private static List<Identifier> getArmorIdentifiers(Identifier[] armorIdentifiers) {
        ArrayList<Identifier> identifiers = new ArrayList<>();
        String[] suffixes = new String[] {
                "_helmet",
                "_chestplate",
                "_leggings",
                "_boots",
        };

        for (Identifier slimArmorIdentifier : armorIdentifiers) {
            for (int i = 0; i < 4; i++) {
                identifiers.add(new Identifier(slimArmorIdentifier.getNamespace(), slimArmorIdentifier.getPath() + suffixes[i]));
            }
        }

        return identifiers;
    }

    public static List<Item> getArmorItems() {
        ArrayList<Item> itemList = new ArrayList<>(List.of(ARMOR_ITEMS));

        for (Identifier armorIdentifier : getArmorIdentifiers(SLIM_ARMOR_IDENTIFIERS)) {
            Item item = Registries.ITEM.get(armorIdentifier);
            if (item != Items.AIR) {
                itemList.add(item);
            } else {
                HMMinecraftMod.LOGGER.error("Could not find armor: " + armorIdentifier.toString());
            }
        }

        return itemList;
    }
}
