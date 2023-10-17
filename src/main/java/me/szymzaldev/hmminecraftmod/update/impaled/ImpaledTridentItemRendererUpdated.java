package me.szymzaldev.hmminecraftmod.update.impaled;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ladysnake.impaled.client.render.entity.model.ImpaledTridentEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Collections;

public class ImpaledTridentItemRendererUpdated implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {

    private final ResourceLocation id;
    private final ResourceLocation tridentId;
    private final ResourceLocation texture;
    private final ModelLayerLocation modelLayer;
    private ItemRenderer itemRenderer;
    private ImpaledTridentEntityModel tridentModel;
    private BakedModel inventoryTridentModel;

    public ImpaledTridentItemRendererUpdated(ResourceLocation tridentId, ResourceLocation texture, ModelLayerLocation modelLayer) {
        this.id = new ResourceLocation(tridentId.getNamespace(), tridentId.getPath() + "_renderer");
        this.tridentId = tridentId;
        this.texture = texture;
        this.modelLayer = modelLayer;
    }

    public Collection<ResourceLocation> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext renderMode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        assert this.tridentModel != null;

        if (renderMode != ItemDisplayContext.GUI && renderMode != ItemDisplayContext.GROUND && renderMode != ItemDisplayContext.FIXED) {
            matrices.pushPose();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(vertexConsumers, this.tridentModel.renderType(this.texture), false, stack.hasFoil());
            this.tridentModel.renderToBuffer(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.popPose();
        } else {
            matrices.popPose();
            matrices.pushPose();
            this.itemRenderer.render(stack, renderMode, false, matrices, vertexConsumers, light, overlay, this.inventoryTridentModel);
        }
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Minecraft mc = Minecraft.getInstance();
        this.itemRenderer = mc.getItemRenderer();
        this.tridentModel = new ImpaledTridentEntityModel(mc.getEntityModels().bakeLayer(this.modelLayer));
        this.inventoryTridentModel = mc.getModelManager().getModel(Impaled.get_trident_in_inventory(this.tridentId));
    }

    @Override
    public ResourceLocation getFabricId() {
        return this.id;
    }
}
