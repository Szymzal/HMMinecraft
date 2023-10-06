package me.szymzaldev.hmminecraftmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.szymzaldev.hmminecraftmod.ArmorFeatureRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.util.Identifier;
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
            at = @At(value = "INVOKE", target = "Lorg/betterx/bclib/client/render/HumanoidArmorRenderer;renderModel(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/model/BipedEntityModel;Lnet/minecraft/util/Identifier;FFF)V", shift = At.Shift.AFTER)
    )
    private void render(MatrixStack pose, VertexConsumerProvider buffer, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> parentModel, CallbackInfo ci, @Local(ordinal = 1) BipedEntityModel<LivingEntity> model) {
        boolean leggings = usesInnerModel(slot);
        ArmorMaterial material = ((ArmorItem)stack.getItem()).getMaterial();
        ArmorTrim.getTrim(entity.getWorld().getRegistryManager(), stack).ifPresent(trim -> {
            Identifier identifier = leggings ? trim.getLeggingsModelId(material) : trim.getGenericModelId(material);
            String path = identifier.getPath().replaceFirst("trims", "humanoid_renderer_trims");
            Identifier new_identifier = new Identifier(MOD_ID, path);
            Sprite sprite = ArmorFeatureRendererAccessor.armorTrimsAtlas.getSprite(new_identifier);
            VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(buffer.getBuffer(TexturedRenderLayers.getArmorTrims()));
            model.render(pose, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        });
    }
}
