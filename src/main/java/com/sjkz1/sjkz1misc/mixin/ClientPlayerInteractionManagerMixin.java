package com.sjkz1.sjkz1misc.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "breakBlock",at = @At("HEAD"))
    public void test(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir)
        {
//            World world = MinecraftClient.getInstance().world;
//            BlockState blockState = world.getBlockState(blockPos);
//            Block block = blockState.getBlock();
//            MinecraftClient client = MinecraftClient.getInstance();
//            Hand hand = client.player.getActiveHand();
//            if(block instanceof CropBlock)
//            {
//                client.interactionManager
//                ItemStack itemStack = client.player.getStackInHand(hand);
//               if(itemStack.getItem() instanceof AliasedBlockItem)
//               {
//                  System.out.println("Trying placing!");
//               }
//            }
        }
}
