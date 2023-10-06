package me.szymzaldev.hmminecraftmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.szymzaldev.hmminecraftmod.ArmorFeatureRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.betterx.bclib.client.render.HumanoidArmorRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.szymzaldev.hmminecraftmod.HMMinecraftMod.MOD_ID;

@Mixin(HumanoidArmorRenderer.class)
public abstract class HumanoidArmorRendererMixin implements ArmorRenderer {
    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot equipmentSlot);

    @Inject(
            method = "render",
            at = @At(value = "INVOKE", target = "Lorg/betterx/bclib/client/render/HumanoidArmorRenderer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/resources/ResourceLocation;FFF)V", shift = At.Shift.AFTER)
    )
    private void render(PoseStack pose, MultiBufferSource buffer, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> parentModel, CallbackInfo ci, @Local(ordinal = 1) HumanoidModel<LivingEntity> model) {
        boolean leggings = usesInnerModel(slot);
        ArmorMaterial material = ((ArmorItem)stack.getItem()).getMaterial();
        ArmorTrim.getTrim(entity.level().registryAccess(), stack).ifPresent(trim -> {
            ResourceLocation identifier = leggings ? trim.innerTexture(material) : trim.outerTexture(material);
            String path = identifier.getPath().replaceFirst("trims", "humanoid_renderer_trims");
            ResourceLocation new_resource_location = new ResourceLocation(MOD_ID, path);
            TextureAtlasSprite textureAtlasSprite = ArmorFeatureRendererAccessor.armorTrimsAtlas.getSprite(new_resource_location);
            VertexConsumer vertexConsumer = textureAtlasSprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet()));
            model.renderToBuffer(pose, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        });
    }
}
