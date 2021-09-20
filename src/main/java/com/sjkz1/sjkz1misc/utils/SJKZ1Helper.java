package com.sjkz1.sjkz1misc.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.*;

import java.awt.*;

public class SJKZ1Helper
{
	public static final MinecraftClient mc = MinecraftClient.getInstance();
	public static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/beacon_beam.png");
	public static void axolotlDance(ModelPart body,int ticks , float f)
	{
		float deltas = MinecraftClient.getInstance().getTickDelta();
		float j = MathHelper.sin(ticks +deltas * 2.5F);
		body.yaw = j;
	}

	public static void renderLabel(LivingEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,int i) {
		float ticks = (mc.player.age % 20 + mc.getTickDelta()) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);
		float health = entity.getHealth();
		String heart = String.valueOf(health) + " HP";
		double d = mc.getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
		if (!(d > 4096.0D)) {
			if (SJKZ1Misc.CONFIG.getConfig().showHealthStatus && !mc.options.hudHidden && !entity.isInvisible() && !(entity instanceof ArmorStandEntity)) {
				float f = entity.getHeight() + 0.5F;
				matrixStack.push();
				matrixStack.translate(0.0D, f, 0.0D);
				matrixStack.multiply(mc.getEntityRenderDispatcher().getRotation());
				matrixStack.scale(-0.025F, -0.025F, 0.025F);
				Matrix4f matrix4f = matrixStack.peek().getModel();
				float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
				int k = (int) (g * 255.0F) << 24;
				TextRenderer textRenderer = mc.textRenderer;
				float l = -textRenderer.getWidth(heart) / 2;
				textRenderer.draw(heart, l, -15, SJKZ1Misc.CONFIG.getConfig().healthStatusRainbowColor ? color.getRGB() : SJKZ1Misc.CONFIG.getConfig().healthStatusColor, false, matrix4f, vertexConsumerProvider, false, 0, i);
				matrixStack.pop();
			}
		}
	}
}

