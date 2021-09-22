package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.block.AncientDebrisBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @Inject(method = "register", at = @At(value = "INVOKE" , target = "Lnet/minecraft/util/registry/Registry;register(Lnet/minecraft/util/registry/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
    private static void register(String string, Block block, CallbackInfoReturnable<Block> cir) {
        if (string.equals("ancient_debris")) {
            cir.setReturnValue(Registry.register(Registry.BLOCK, string, new AncientDebrisBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(30.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS))));
        }
    }

}
