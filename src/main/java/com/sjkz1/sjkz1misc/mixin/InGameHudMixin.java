package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper
{
	@Inject(method = "render",at = @At("TAIL"))
	public void render(MatrixStack matrixStack, float f, CallbackInfo info)
	{
		SJKZ1Helper.renderFPS(matrixStack);
	}
}
