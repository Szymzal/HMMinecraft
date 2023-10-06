package me.szymzaldev.hmminecraftmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSimiWidget.class)
public class AbstractSimiWidgetMixin {

//    @Redirect(
//            method = "render",
//            at = @At(value = "INVOKE", target = "")
//    )

}
