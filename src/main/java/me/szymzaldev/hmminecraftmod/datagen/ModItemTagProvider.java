package me.szymzaldev.hmminecraftmod.datagen;

import me.szymzaldev.hmminecraftmod.Identifiers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        FabricTagBuilder tag = getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR);
        List<Item> armorItems = Identifiers.getArmorItems();
        for (Item armorItem : armorItems) {
            tag.add(armorItem);
        }
    }
}
