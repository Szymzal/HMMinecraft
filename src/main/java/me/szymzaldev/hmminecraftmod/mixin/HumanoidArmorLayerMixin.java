package me.szymzaldev.hmminecraftmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Make trims render properly using their own equipment slot trim pattern
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void init(RenderLayerParent renderer, HumanoidModel innerModel, HumanoidModel outerModel, ModelManager modelManager, CallbackInfo ci) {
        me.szymzaldev.hmminecraftmod.ArmorFeatureRendererAccessor.armorTrimAtlas = ((HumanoidArmorLayerAccessor) this).getArmorTrimAtlas();
    }

    @Inject(
            method = "renderArmorPiece",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/armortrim/ArmorTrim;getTrim(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/item/ItemStack;)Ljava/util/Optional;")
    )
    private void beforeGetTrim(PoseStack poseStack, MultiBufferSource buffer, LivingEntity livingEntity, EquipmentSlot slot, int packedLight, HumanoidModel model, CallbackInfo ci, @Local LocalBooleanRef bl) {
        bl.set(slot == EquipmentSlot.LEGS);
    }
}
