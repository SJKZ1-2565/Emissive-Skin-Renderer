package com.sjkz1.sjkz1misc.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.awt.*;

public class SJKZ1Helper
{
	public static final Minecraft mc = Minecraft.getInstance();

	public static void axolotlDance(ModelPart body, int ticks , float f)
	{
		float deltas = mc.getFrameTime();
		float j = Mth.sin(ticks +deltas * 2.5F);
		body.y = j;
	}

	public static void renderLabel(LivingEntity entity, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		float ticks = (mc.player.tickCount % 20 + mc.getFrameTime()) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);
		float health = entity.getHealth();
		String heart = String.valueOf(health) + " HP";
		double d = mc.getEntityRenderDispatcher().distanceToSqr(entity);
		if (!(d > 4096.0D)) {
			if (SJKZ1Misc.CONFIG.getConfig().showHealthStatus && !mc.options.hideGui && !entity.isInvisible() && !(entity instanceof ArmorStand)) {
				float f = SJKZ1Misc.CONFIG.getConfig().yPositionHealthStatus + 0.5F;
				matrixStack.pushPose();
				matrixStack.translate(0.0D, f, 0.0D);
				matrixStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
				matrixStack.scale(-0.025F, -0.025F, 0.025F);
				Matrix4f matrix4f = matrixStack.last().pose();
				float g = mc.options.getBackgroundOpacity(0.25F);
				int k = (int) (g * 255.0F) << 24;
				Font textRenderer = mc.font;
				float l = -textRenderer.width(heart) / 2;
				textRenderer.drawInBatch(heart, l, -15, SJKZ1Misc.CONFIG.getConfig().healthStatusRainbowColor ? color.getRGB() : SJKZ1Misc.CONFIG.getConfig().healthStatusColor, true, matrix4f, vertexConsumerProvider, false, 0, i);
				matrixStack.popPose();
			}
		}
	}
	public static void renderLabelTamed(AbstractHorse entity, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		float ticks = (mc.player.tickCount % 20 + mc.getFrameTime()) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);
		boolean tamed = entity.isTamed();
		String tamedHorseText = tamed ? ChatFormatting.GREEN + "Tamed" :  ChatFormatting.RED + "Untamed";
		double d = mc.getEntityRenderDispatcher().distanceToSqr(entity);
		if (!(d > 4096.0D)) {
			if (SJKZ1Misc.CONFIG.getConfig().showTamedHorse && !mc.options.hideGui && !entity.isInvisible()) {
				float f = SJKZ1Misc.CONFIG.getConfig().yPositionHorseDisplay + 0.5F;
				matrixStack.pushPose();
				matrixStack.translate(0.0D, f, 0.0D);
				matrixStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
				matrixStack.scale(-0.025F, -0.025F, 0.025F);
				Matrix4f matrix4f = matrixStack.last().pose();
				float g = mc.options.getBackgroundOpacity(0.25F);
				int k = (int) (g * 255.0F) << 24;
				Font textRenderer = mc.font;
				float l = -textRenderer.width(tamedHorseText) / 2;
				textRenderer.drawInBatch(tamedHorseText, l, -15, i, true, matrix4f, vertexConsumerProvider, false, 0, i);
				matrixStack.popPose();
			}
		}
	}
}

