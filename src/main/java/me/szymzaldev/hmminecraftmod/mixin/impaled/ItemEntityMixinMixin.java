package me.szymzaldev.hmminecraftmod.mixin.impaled;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemEntity.class, priority = 1500)
public abstract class ItemEntityMixinMixin extends Entity {

    public ItemEntityMixinMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @TargetHandler(
            mixin = "ladysnake.sincereloyalty.mixin.ItemEntityMixin",
            name = "tickItem"
    )
    @Redirect(
            method = "@MixinSquared:Handler",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/item/ItemEntity;level:Lnet/minecraft/world/level/Level;")
    )
    private Level sz_get_level(ItemEntity instance) {
        return instance.level();
    }

}
