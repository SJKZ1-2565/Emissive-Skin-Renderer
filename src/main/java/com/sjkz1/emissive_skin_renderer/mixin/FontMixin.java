package com.sjkz1.emissive_skin_renderer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(Font.class)
public abstract class FontMixin {
    @Shadow
    protected abstract int drawInternal(FormattedCharSequence formattedCharSequence, float f, float g, int i, Matrix4f matrix4f, boolean bl);

    @Shadow
    public abstract int drawInBatch(FormattedCharSequence formattedCharSequence, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource multiBufferSource, boolean bl2, int j, int k);

    @Inject(method = "drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I", at = @At("HEAD"), cancellable = true)
    public void drawString(PoseStack poseStack, Component component, float f, float g, int i, CallbackInfoReturnable<Integer> cir) {
        int color = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F));
        for (String s : EmissiveSkinRenderer.SPECIAL_MEMBER) {
            if (component.getString().equals(s)) {
                cir.setReturnValue(this.drawInternal(component.getVisualOrderText(), f, g, color, poseStack.last().pose(), true));
            }
        }
    }

    @Inject(method = "drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I", at = @At("HEAD"), cancellable = true)
    public void drawInBatch(Component component, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource multiBufferSource, boolean bl2, int j, int k, CallbackInfoReturnable<Integer> cir) {
        int color = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F));
        for (String s : EmissiveSkinRenderer.SPECIAL_MEMBER) {
            if (component.getString().equals(s)) {
                cir.setReturnValue(this.drawInBatch(component.getVisualOrderText(), f, g, color, bl, matrix4f, multiBufferSource, bl2, j, k));
            }
        }
    }
}
