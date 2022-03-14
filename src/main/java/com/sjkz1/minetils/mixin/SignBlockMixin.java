package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AbstractSignBlock.class)
public class SignBlockMixin {

    @Inject(method = "onUse",at = @At("HEAD"))
    public void use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<ActionResult> cir)
    {
        if(playerEntity.isSneaking() && playerEntity.getStackInHand(hand).isEmpty())
        {
            playerEntity.openEditSignScreen((SignBlockEntity)world.getBlockEntity(blockPos));
        }
    }
}
