package me.szymzaldev.hmminecraftmod.datagen;

import me.szymzaldev.hmminecraftmod.HMMinecraftMod;
import me.szymzaldev.hmminecraftmod.mixin.PalettedPermutationsAtlasSourceAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.atlas.*;
import net.minecraft.data.DataOutput;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import static me.szymzaldev.hmminecraftmod.HMMinecraftMod.MOD_ID;

public class AtlasProvider extends FabricCodecDataProvider<List<AtlasSource>> {
    protected AtlasProvider(FabricDataOutput dataOutput) {
        super(dataOutput, DataOutput.OutputType.RESOURCE_PACK, "atlases", AtlasSourceManager.LIST_CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, List<AtlasSource>> provider) {
        ArrayList<TrimPattern> trims_to_modify = new ArrayList<>();
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "coast"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "dune"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "eye"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "host"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "raiser"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "rib"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "sentry"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "shaper"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "silence"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "snout"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "spire"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "tide"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "vex"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "ward"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "wayfinder"));
        trims_to_modify.add(new TrimPattern(ModId.Minecraft, "wild"));

        ArrayList<TrimPattern> leggings = new ArrayList<>();
        for (TrimPattern trim_pattern : trims_to_modify) {
            leggings.add(new TrimPattern(trim_pattern.getModId(), trim_pattern.getFile() + "_leggings"));
        }
        trims_to_modify.addAll(leggings);

        ArrayList<Identifier> textures = new ArrayList<>();
        for (TrimPattern trim_pattern : trims_to_modify) {
            ModContainer minecraft_container = FabricLoader.getInstance().getModContainer(trim_pattern.getModIdString()).get();
            Path path = minecraft_container.findPath(trim_pattern.getTrimsPath()).get();
            String file_name = String.valueOf(path.getFileName());
            try {
                InputStream in = Files.newInputStream(path);
                NativeImage original_image = NativeImage.read(in);
                BufferedImage image = new BufferedImage(original_image.getWidth(), original_image.getHeight() + 16, BufferedImage.TYPE_INT_ARGB);
                for (int x = 0; x < original_image.getWidth(); x++) {
                    for (int y = 0; y < original_image.getHeight(); y++) {
                        long strange_color = Integer.toUnsignedLong(original_image.getColor(x, y));
                        long r = strange_color & 0xff;
                        long g = (strange_color & (0xff << 8)) >> 8;
                        long b = (strange_color & (0xff << 16)) >> 16;
                        long a = (strange_color & (0xffL << 24)) >> 24;
                        Color color = new Color((int)r, (int)g, (int)b, (int)a);
                        image.setRGB(x, y, color.getRGB());
                    }
                }

                boolean is_leggings = file_name.endsWith("_leggings.png");

                // Leggings and Boots
                int x_reversed = 4;
                int last_x = 0;
                for (int x = 0; x < 16; x++) {
                    x_reversed -= 1;
                    if (x_reversed == last_x - 1) {
                        last_x = x;
                        x_reversed = x + 3;
                    }
                    for (int y = 16; y < 32; y++) {
                        int color = image.getRGB(x, y);
                        int destination_x = x_reversed;
                        if (x < 4) {
                            destination_x += 8;
                        } else if (x < 12 && x > 7) {
                            destination_x -= 8;
                        }

                        image.setRGB(destination_x, y + 16, color);
                    }
                }

                if (!is_leggings) {
                    // Chestplate arms
                    for (int x = 40; x < 56; x++) {
                        for (int y = 10; y < 26; y++) {
                            int color = image.getRGB(x, y);
                            image.setRGB(x, y + 16, color);
                        }
                    }
                }

                String file_name_1 = file_name.replace(".png", "");
                String identifier = "humanoid_renderer_trims/" + trim_pattern.getTrimsOutputPath() + file_name_1;
                String file_path = "../../src/main/resources/assets/" + MOD_ID + "/textures/" + identifier + ".png";
                textures.add(new Identifier(MOD_ID, identifier));

                File file = new File(file_path);
                file.mkdirs();
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException e) {
                    HMMinecraftMod.LOGGER.error(e.toString());
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // TODO: Read from minecraft mod
        HashMap<String, Identifier> permutations = new HashMap<>();
        permutations.put("quartz", new Identifier("minecraft", "trims/color_palettes/quartz"));
        permutations.put("iron", new Identifier("minecraft", "trims/color_palettes/iron"));
        permutations.put("gold", new Identifier("minecraft", "trims/color_palettes/gold"));
        permutations.put("diamond", new Identifier("minecraft", "trims/color_palettes/diamond"));
        permutations.put("netherite", new Identifier("minecraft", "trims/color_palettes/netherite"));
        permutations.put("redstone", new Identifier("minecraft", "trims/color_palettes/redstone"));
        permutations.put("copper", new Identifier("minecraft", "trims/color_palettes/copper"));
        permutations.put("emerald", new Identifier("minecraft", "trims/color_palettes/emerald"));
        permutations.put("lapis", new Identifier("minecraft", "trims/color_palettes/lapis"));
        permutations.put("amethyst", new Identifier("minecraft", "trims/color_palettes/amethyst"));
        permutations.put("iron_darker", new Identifier("minecraft", "trims/color_palettes/iron_darker"));
        permutations.put("gold_darker", new Identifier("minecraft", "trims/color_palettes/gold_darker"));
        permutations.put("diamond_darker", new Identifier("minecraft", "trims/color_palettes/diamond_darker"));
        permutations.put("netherite_darker", new Identifier("minecraft", "trims/color_palettes/netherite_darker"));

        provider.accept(new Identifier("minecraft", "armor_trims"), List.of(PalettedPermutationsAtlasSourceAccessor.constructor(
                textures,
                new Identifier("minecraft", "trims/color_palettes/trim_palette"),
                permutations
        )));
    }

    @Override
    public String getName() {
        return "Custom Atlases";
    }

    protected class TrimPattern {
        private final ModId mod_id;
        private final String file;

        protected TrimPattern(ModId mod_id, String file) {
            this.mod_id = mod_id;
            this.file = file;
        }

        protected String getTrimsPath() {
            return switch (mod_id) {
                case Minecraft -> "assets/minecraft/textures/trims/models/armor/" + file + ".png";
            };
        }

        protected String getTrimsOutputPath() {
            return switch (mod_id) {
                case Minecraft -> "models/armor/";
            };
        }

        protected ModId getModId() {
            return this.mod_id;
        }

        protected String getModIdString() {
            return switch (mod_id) {
                case Minecraft -> "minecraft";
            };
        }

        protected String getFile() {
            return this.file;
        }
    }

    protected enum ModId {
        Minecraft
    }
}
