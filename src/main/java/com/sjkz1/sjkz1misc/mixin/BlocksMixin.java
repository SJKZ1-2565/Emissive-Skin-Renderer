package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.block.AncientDebrisBlock;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @Inject(method = "register", at = @At(value = "INVOKE" , target = "Lnet/minecraft/util/registry/Registry;register(Lnet/minecraft/util/registry/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
    private static void register(String string, Block block, CallbackInfoReturnable<Block> cir) {
        if (string.equals("ancient_debris")) {
            cir.setReturnValue(Registry.register(Registry.BLOCK, string, new AncientDebrisBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.BLACK).requiresTool().strength(30.0F, 1200.0F).sounds(BlockSoundGroup.ANCIENT_DEBRIS))));
        }
        if(string.equals("beacon"))
        {
            cir.setReturnValue(Registry.register(Registry.BLOCK,string, new BeaconBlock(AbstractBlock.Settings.of(Material.GLASS, MapColor.DIAMOND_BLUE).sounds(BlockSoundGroup.GLASS).strength(3.0F).luminance((blockStatex) -> 15).nonOpaque().solidBlock(Blocks::never))));
        }
        if(string.equals("tripwire_hook"))
        {
            cir.setReturnValue(Registry.register(Registry.BLOCK,string, new TripwireHookBlock(AbstractBlock.Settings.of(Material.DECORATION).sounds(BlockSoundGroup.WOOD).noCollision())));
        }
        if(string.endsWith("_leaves"))
        {
            cir.setReturnValue(Registry.register(Registry.BLOCK,string, new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never))));
        }
    }

}
