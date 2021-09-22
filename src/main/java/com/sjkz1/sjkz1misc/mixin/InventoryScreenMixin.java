package com.sjkz1.sjkz1misc.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Redirect(method = "renderBg",at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"))
    public void dsa(float f, float g, float h, float i)
    {
      if(Minecraft.getInstance().player != null) {
          float time = Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getDeltaFrameTime();
          RenderSystem.setShaderColor(makeFade(time), makeFade(time), makeFade(time), 1.0F);
      }
    }
    private float makeFade(float alpha)
    {
        return  Math.min(1.0F, (Mth.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }
}
