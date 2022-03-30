package com.sjkz1.minetils.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin {

    @Shadow
    protected abstract boolean isPlayerInRange(World world, BlockPos blockPos);

    @Shadow
    public int spawnDelay;

    @Inject(method = "clientTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At("TAIL"))
    public void clientTick(World world, BlockPos blockPos, CallbackInfo ci) {
        if (isPlayerInRange(world, blockPos) && this.spawnDelay >= -1) {
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of(Formatting.DARK_RED + "Spawn Delay: " + StringHelper.formatTicks(this.spawnDelay) + "s"), false);
            if (this.spawnDelay <= -1) {
                MinecraftClient.getInstance().inGameHud.overlayRemaining = 0;
            }

        }
    }
}
