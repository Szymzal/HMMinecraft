package me.szymzaldev.hmminecraftmod.update.impaled;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ladysnake.impaled.client.render.entity.model.ImpaledTridentEntityModel;
import ladysnake.impaled.common.entity.ImpaledTridentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ImpaledTridentEntityRendererUpdated extends EntityRenderer<ImpaledTridentEntity> {
    private final ImpaledTridentEntityModel model;
    private final ResourceLocation texture;

    public ImpaledTridentEntityRendererUpdated(EntityRendererProvider.Context context, ResourceLocation texture, ModelLayerLocation modelLayer) {
        super(context);
        this.model = new ImpaledTridentEntityModel(context.bakeLayer(modelLayer));
        this.texture = texture;
    }

    public void render(ImpaledTridentEntity impaledTridentEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
//        matrixStack.mulPose((new Vec3(0.0f, 1.0f, 0.0f)).(Mth.lerp(g, impaledTridentEntity.yRotO, impaledTridentEntity.getYRot()) - 90.0F));
//        Vector3f test = new Vector3f(0.0f, 1.0f, 0.0f);
//        test.
//        matrixStack.mulPose(Vector3f.field_20707.method_23214(Mth.lerp(g, impaledTridentEntity.xRotO, impaledTridentEntity.getXRot()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(vertexConsumerProvider, this.model.renderType(this.getTextureLocation(impaledTridentEntity)), false, impaledTridentEntity.isFoil());
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(impaledTridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ImpaledTridentEntity entity) {
        return this.texture;
    }
}
