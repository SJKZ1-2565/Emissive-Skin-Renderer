package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.utils.AncientDebrisBlock;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.windows.POINT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @Inject(method = "register", at = @At(value = "INVOKE" , target = "Lnet/minecraft/util/registry/Registry;register(Lnet/minecraft/util/registry/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
    private static void register(String string, Block block, CallbackInfoReturnable<Block> cir) {
        if(string.equals("ancient_debris")) {
            cir.setReturnValue(Registry.register(Registry.BLOCK,string,new AncientDebrisBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.BLACK).requiresTool().strength(30.0F, 1200.0F).sounds(BlockSoundGroup.ANCIENT_DEBRIS))));
            System.out.println("Registered!!!!");
        }
    }
}
