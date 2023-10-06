package me.szymzaldev.hmminecraftmod.datagen;

import me.szymzaldev.hmminecraftmod.Identifiers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        List<Item> armorItems = Identifiers.getArmorItems();
        for (Item armorItem : armorItems) {
            itemModelGenerator.generateArmorTrims((ArmorItem) armorItem);
        }
    }
}
