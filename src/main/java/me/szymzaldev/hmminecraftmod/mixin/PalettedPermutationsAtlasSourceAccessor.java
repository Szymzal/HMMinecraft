package me.szymzaldev.hmminecraftmod.mixin;

import net.minecraft.client.texture.atlas.PalettedPermutationsAtlasSource;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@Mixin(PalettedPermutationsAtlasSource.class)
public interface PalettedPermutationsAtlasSourceAccessor {

    @Invoker("<init>")
    static PalettedPermutationsAtlasSource constructor(List<Identifier> textures, Identifier paletteKey, Map<String, Identifier> permutations) {
        throw new AssertionError();
    }

}
