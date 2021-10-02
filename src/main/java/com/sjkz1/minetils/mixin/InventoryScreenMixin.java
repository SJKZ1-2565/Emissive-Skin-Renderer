package com.sjkz1.minetils.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Redirect(method = "drawBackground",at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"))
    public void dsa(float f, float g, float h, float i)
    {
      if(MinecraftClient.getInstance().player != null) {
          float time = MinecraftClient.getInstance().player.age + MinecraftClient.getInstance().getTickDelta();
          RenderSystem.setShaderColor(makeFade(time), makeFade(time), makeFade(time), 1.0F);
      }
    }
    private float makeFade(float alpha)
    {
        return  Math.min(1.0F, (MathHelper.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }
}
