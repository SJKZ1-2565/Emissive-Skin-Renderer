package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleEngineMixin {

    @Inject(method = "addBlockBreakingParticles", at = @At("HEAD"), cancellable = true)
    public void crackParticle(BlockPos blockPos, Direction direction, CallbackInfo ci) {
        if (Minetils.CONFIG.getConfig().disableBlockParticle) {
            ci.cancel();
        }
    }

    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    public void destroyParticle(BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        if (Minetils.CONFIG.getConfig().disableBlockParticle) {
            ci.cancel();
        }
    }
}
