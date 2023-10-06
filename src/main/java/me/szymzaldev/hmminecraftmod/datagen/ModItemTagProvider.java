package me.szymzaldev.hmminecraftmod.datagen;

import me.szymzaldev.hmminecraftmod.Identifiers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        FabricTagBuilder tag = getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR);
        List<Item> armorItems = Identifiers.getArmorItems();
        for (Item armorItem : armorItems) {
            tag.add(armorItem);
        }
    }
}
