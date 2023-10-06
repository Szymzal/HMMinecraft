package me.szymzaldev.hmminecraftmod.mixin;

import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorFeatureRenderer.class)
public interface ArmorFeatureRendererAccessor {

    @Accessor
    SpriteAtlasTexture getArmorTrimsAtlas();

}
