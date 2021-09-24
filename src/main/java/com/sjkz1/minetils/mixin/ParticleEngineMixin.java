package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {

    @Inject(method = "crack",at = @At("HEAD"),cancellable = true)
    public void crackParticle(BlockPos blockPos, Direction direction, CallbackInfo ci)
    {
        if(Minetils.CONFIG.getConfig().disableBlockParticle)
        {
            ci.cancel();
        }
    }
    @Inject(method = "destroy",at = @At("HEAD"),cancellable = true)
    public void destroyParticle(BlockPos blockPos, BlockState blockState, CallbackInfo ci)
    {
        if(Minetils.CONFIG.getConfig().disableBlockParticle)
        {
            ci.cancel();
        }
    }
}
