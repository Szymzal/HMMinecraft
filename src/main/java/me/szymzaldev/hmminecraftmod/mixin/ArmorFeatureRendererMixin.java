package me.szymzaldev.hmminecraftmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Make trims render properly using their own equipment slot trim pattern
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, A extends BipedEntityModel<T>> {
    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void init(FeatureRendererContext context, BipedEntityModel innerModel, BipedEntityModel outerModel, BakedModelManager bakery, CallbackInfo ci) {
        me.szymzaldev.hmminecraftmod.ArmorFeatureRendererAccessor.armorTrimsAtlas = ((ArmorFeatureRendererAccessor) this).getArmorTrimsAtlas();
    }

    @Inject(
            method = "renderArmor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/trim/ArmorTrim;getTrim(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/item/ItemStack;)Ljava/util/Optional;")
    )
    private void beforeGetTrim(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci, @Local LocalBooleanRef bl) {
        bl.set(armorSlot == EquipmentSlot.LEGS);
    }
}
