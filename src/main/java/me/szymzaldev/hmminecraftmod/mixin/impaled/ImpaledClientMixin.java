package me.szymzaldev.hmminecraftmod.mixin.impaled;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import ladysnake.impaled.client.ImpaledClient;
import me.szymzaldev.hmminecraftmod.update.impaled.Impaled;
import me.szymzaldev.hmminecraftmod.update.impaled.ImpaledTridentEntityRendererUpdated;
import me.szymzaldev.hmminecraftmod.update.impaled.ImpaledTridentItemRendererUpdated;
import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ImpaledClient.class)
public class ImpaledClientMixin {

//    @Shadow @Final public static ModelLayerLocation ATLAN;

    @Inject(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;getNamespace()Ljava/lang/String;", shift = At.Shift.BEFORE)
    )
    private void share_trident_id(CallbackInfo ci, @Local(ordinal = 0) ResourceLocation trident_id, @Share("trident_id") LocalRef<ResourceLocation> trident_id_ref) {
        trident_id_ref.set(trident_id);
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/model/ModelLoadingRegistry;registerModelProvider(Lnet/fabricmc/fabric/api/client/model/ExtraModelProvider;)V"),
            remap = false
    )
    private void get_model_resource_location(ModelLoadingRegistry instance, ExtraModelProvider original_arg, Operation<Void> original, @Local(ordinal = 0) ResourceLocation trident_id) {
        instance.registerModelProvider(((manager, out) -> out.accept(Impaled.get_trident_in_inventory(trident_id))));
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/Registry;field_11142:Lnet/minecraft/core/DefaultedMappedRegistry;")
    )
    private DefaultedMappedRegistry get_registry(Operation<DefaultedMappedRegistry> original) {
        return Impaled.field_11142();
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lladysnake/impaled/client/ImpaledTridentItemRenderer;<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/model/geom/ModelLayerLocation;)V")
    )
    private void new_renderer(ImpaledClient instance, ResourceLocation arg_1, ResourceLocation arg_2, ModelLayerLocation arg_3, Operation<Void> original, @Share("impaled_trident_renderer") LocalRef<ImpaledTridentItemRendererUpdated> impaled_trident_renderer) {
        impaled_trident_renderer.set(new ImpaledTridentItemRendererUpdated(arg_1, arg_2, arg_3));
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/resource/ResourceManagerHelper;registerReloadListener(Lnet/fabricmc/fabric/api/resource/IdentifiableResourceReloadListener;)V"),
            remap = false
    )
    private void register_new_renderer_reload_listener(ResourceManagerHelper instance, IdentifiableResourceReloadListener arg_1, Operation<Void> original, @Share("imapaled_trident_renderer") LocalRef<ImpaledTridentItemRendererUpdated> impaled_trident_renderer) {
        instance.registerReloadListener(impaled_trident_renderer.get());
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/rendering/v1/BuiltinItemRendererRegistry;register(Lnet/minecraft/world/level/ItemLike;Lnet/fabricmc/fabric/api/client/rendering/v1/BuiltinItemRendererRegistry$DynamicItemRenderer;)V")
    )
    private void register_new_renderer(BuiltinItemRendererRegistry instance, ItemLike arg_1, BuiltinItemRendererRegistry.DynamicItemRenderer arg_2, Operation<Void> original, @Share("imapaled_trident_renderer") LocalRef<ImpaledTridentItemRendererUpdated> impaled_trident_renderer) {
        instance.register(arg_1, impaled_trident_renderer.get());
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/rendering/v1/EntityRendererRegistry;register(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", ordinal = 0)
    )
    private void register_new_entity_renderer(EntityType arg_1, EntityRendererProvider arg_2, Operation<Void> original, @Local(ordinal = 1) ResourceLocation texture, @Local(ordinal = 0) ModelLayerLocation model_layer) {
        EntityRendererRegistry.register(arg_1, context -> new ImpaledTridentEntityRendererUpdated(context, texture, model_layer));
    }

    @WrapOperation(
            method = "onInitializeClient",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/rendering/v1/EntityRendererRegistry;register(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", ordinal = 1)
    )
    private void register_new_entity_guardian_renderer(EntityType arg_1, EntityRendererProvider arg_2, Operation<Void> original) {
        EntityRendererRegistry.register(arg_1, context -> new ImpaledTridentEntityRendererUpdated(context, new ResourceLocation("impaled", "textures/entity/guardian_trident.png"), ModelLayers.TRIDENT));
    }

//    @Inject(
//            method = "onInitializeClient",
//            cancellable = true,
//            at = @At(value = "HEAD"),
//            remap = false
//    )
//    private void on_initialize_client(CallbackInfo ci) {
//        EntityModelLayerRegistry.registerModelLayer(ATLAN, ImpaledTridentEntityModel::getAtlanTexturedModelData);
//
//        for (ImpaledTridentItem item : ImpaledItems.ALL_TRIDENTS) {
//            ResourceLocation tridentId = BuiltInRegistries.ITEM.getKey(item);
//            String var10002 = tridentId.getNamespace();
//            String var10003 = tridentId.getPath();
//            ResourceLocation texture = new ResourceLocation(var10002, "textures/entity/" + var10003 + ".png");
//            ModelLayerLocation modelLayer = item == ImpaledItems.ATLAN ? ATLAN : ModelLayers.TRIDENT;
//            ImpaledTridentItemRendererUpdated tridentItemRenderer = new ImpaledTridentItemRendererUpdated(tridentId, texture, modelLayer);
//            ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(tridentItemRenderer);
//            BuiltinItemRendererRegistry.INSTANCE.register(item, tridentItemRenderer);
//            EntityRendererRegistry.register(item.getEntityType(), (ctx) -> new ImpaledTridentEntityRendererUpdated(ctx, texture, modelLayer));
//            FabricModelPredicateProviderRegistry.register(item, new ResourceLocation("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
//            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(Impaled.get_trident_in_inventory(tridentId)));
//        }
//
//        EntityRendererRegistry.register(ImpaledEntityTypes.GUARDIAN_TRIDENT, (ctx) -> new ImpaledTridentEntityRenderer(ctx, new ResourceLocation("impaled", "textures/entity/guardian_trident.png"), ModelLayers.TRIDENT));
//        ci.cancel();
//    }

}
