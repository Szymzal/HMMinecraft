package me.szymzaldev.hmminecraftmod.mixin;

import me.thegiggitybyte.sleepwarp.WarpEngine;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.sleeprework.handler.ServerPlayerSleepData;
import party.lemons.sleeprework.handler.SleepDataHolder;

// https://github.com/Lemonszz/SleepRework/issues/1
@Mixin(WarpEngine.class)
public class WarpDriveMixin {

    @Inject(method = "onEndTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;sendToDimension(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/registry/RegistryKey;)V", shift = At.Shift.AFTER))
    private void onEndTick(ServerWorld world, CallbackInfo ci) {
        for (var player : world.getPlayers().stream().filter(LivingEntity::isSleeping).toList()) {
            ServerPlayerSleepData playerSleepData = ((SleepDataHolder)player).getSleepData();
            // TODO: make a config for that
            playerSleepData.setTiredness(player, (float)(playerSleepData.getTiredness() - 0.0004));
        }
    }
}
