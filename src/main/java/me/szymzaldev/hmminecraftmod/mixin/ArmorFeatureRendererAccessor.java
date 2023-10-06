package me.szymzaldev.hmminecraftmod.mixin;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HumanoidArmorLayer.class)
public interface ArmorFeatureRendererAccessor {

    @Accessor
    TextureAtlas getArmorTrimAtlas();

}
