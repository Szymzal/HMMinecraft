package me.szymzaldev.hmminecraftmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.ArmorItem;

import static net.environmentz.init.ItemInit.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.registerArmor((ArmorItem) WOLF_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) WOLF_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) WOLF_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) WOLF_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) WANDERER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) WANDERER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) WANDERER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) WANDERER_BOOTS);
    }
}
