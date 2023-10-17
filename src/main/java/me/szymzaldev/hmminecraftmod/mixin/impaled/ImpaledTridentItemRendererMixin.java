package me.szymzaldev.hmminecraftmod.mixin.impaled;

import ladysnake.impaled.client.ImpaledTridentItemRenderer;
import me.szymzaldev.hmminecraftmod.update.impaled.Impaled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ImpaledTridentItemRenderer.class)
public class ImpaledTridentItemRendererMixin {

    @Shadow private BakedModel inventoryTridentModel;

    @Shadow @Final private ResourceLocation tridentId;

    @Inject(
            method = "onResourceManagerReload",
            cancellable = true,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelManager;getModel(Lnet/minecraft/client/resources/model/ModelResourceLocation;)Lnet/minecraft/client/resources/model/BakedModel;", opcode = Opcodes.PUTFIELD, shift = At.Shift.BEFORE)
    )
    private void inventoryTridentModel(ResourceManager manager, CallbackInfo ci) {
        this.inventoryTridentModel = Minecraft.getInstance().getModelManager().getModel(Impaled.get_trident_in_inventory(this.tridentId));
        ci.cancel();
    }

}
