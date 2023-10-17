package me.szymzaldev.hmminecraftmod.mixin.betterend;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HumanoidArmorLayer.class)
public interface HumanoidArmorLayerAccessor {

    @Accessor
    TextureAtlas getArmorTrimAtlas();

}
