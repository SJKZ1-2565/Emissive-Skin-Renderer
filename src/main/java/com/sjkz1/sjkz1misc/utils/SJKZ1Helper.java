package com.sjkz1.sjkz1misc.utils;

import java.awt.*;
import java.util.List;

import com.google.common.collect.Lists;
import com.sjkz1.sjkz1misc.SJKZ1Misc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

public class SJKZ1Helper
{
	private static final MinecraftClient mc = MinecraftClient.getInstance();
	public static void CatDance(ModelPart head,int ticks , float f)
	{
		float deltas = MinecraftClient.getInstance().getTickDelta();
		float j = MathHelper.sin(ticks +deltas);
		head.pivotY = j + 12;
		head.pitch = 0;
	}
	public static void axolotlDance(ModelPart head,ModelPart body,int ticks , float f)
	{
		float deltas = MinecraftClient.getInstance().getTickDelta();
		float j = MathHelper.sin(ticks +deltas);
		float k = MathHelper.cos(ticks +deltas);

		head.pivotY = j ;
		head.pivotX = k ;
		body.pivotX = k ;

		body.pitch = k;
	}

	public static void PlayerDance(ModelPart head,ModelPart hat,ModelPart leftArm,ModelPart rightArm,ModelPart rightSleeve,ModelPart leftSleeve,ModelPart body,ModelPart jacket,int ticks , float f)
	{
		body.setTransform(body.getTransform());
		head.setTransform(head.getTransform());
		leftArm.setTransform(leftArm.getTransform());
		rightArm.setTransform(rightArm.getTransform());
		if(SJKZ1Misc.dance) {
			float j = ticks / 60.0F;
			head.pivotX = MathHelper.sin(j * 10.0F);
			head.pivotY = MathHelper.sin(j * 40.0F) + 0.4F;
			rightArm.roll = 0.017453292F * (70.0F + MathHelper.cos(j * 40.0F) * 10.0F);
			leftArm.roll = rightArm.roll * -1.0F;
			rightArm.pivotY = MathHelper.sin(j * 40.0F) * 0.5F + 1.5F;
			leftArm.pivotY = MathHelper.sin(j * 40.0F) * 0.5F + 1.5F;
			body.pivotY = MathHelper.sin(j * 40.0F) * 0.35F;
		}
		leftSleeve.copyTransform(leftArm);
		rightSleeve.copyTransform(rightArm);
		hat.copyTransform(head);
		jacket.copyTransform(body);
	}

	public static void renderLable(LivingEntity entity, MatrixStack matrixStack) {
		String displayName = entity.getName().getString();
		float health = entity.getHealth();
		String heart = "\u2764 " +  String.format("%.1f", health);
		if (SJKZ1Misc.CONFIG.getConfig().showHealthStatus && !mc.options.hudHidden && !entity.isInvisible() && !(entity instanceof ArmorStandEntity)) {
			float f = entity.getHeight() + 0.5F;
			matrixStack.push();
			matrixStack.translate(0.0D, f, 0.0D);
			matrixStack.multiply(mc.getEntityRenderDispatcher().getRotation());
			matrixStack.scale(-0.025F, -0.025F, 0.025F);
			TextRenderer textRenderer = mc.textRenderer;
			float h = (float)(-textRenderer.getWidth(displayName) / 2);
			float l = (float)(-textRenderer.getWidth(heart) / 2);
			textRenderer.drawWithShadow(matrixStack,displayName,h,0,5635925);
			textRenderer.drawWithShadow(matrixStack,heart,l,-15,5635925);
			matrixStack.pop();
		}
	}

	public static void renderFPS(MatrixStack matrixStack)
	{
		if(SJKZ1Misc.CONFIG.getConfig().showFps)
		{
			int i = mc.getWindow().getScaledWidth();
			int j = 12;


			String fps = "FPS: "+ MinecraftClient.currentFps;


			int m = mc.textRenderer.getWidth(fps);
			int width = i / 2 - m / 2;
			int height = j + 9;
			if(MinecraftClient.currentFps <= 20)
			{
				mc.textRenderer.drawWithShadow(matrixStack, fps, width + -250, height / 2, 11141120);
			}
			else if(MinecraftClient.currentFps <= 50)
			{
				mc.textRenderer.drawWithShadow(matrixStack, fps, width + -250 , height / 2, 16777045);
			}
			else
			{
				mc.textRenderer.drawWithShadow(matrixStack, fps, width + -250, height / 2, 5635925);
			}
		}
	}
	public static int getColor()
	{
		float ticks = ((float)(mc.player.age % 20) + mc.getTickDelta()) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);
		return SJKZ1Misc.CONFIG.getConfig().rainbowColor ? color.getRGB() : SJKZ1Misc.CONFIG.getConfig().color;
	}

	public static class Helper
	{
		private final List<OrderedText> orderedTextList;

		public Helper(List<OrderedText> orderedTextList) {
			this.orderedTextList = orderedTextList;
		}

		public  List<OrderedText> getOrderText()
		{
			List<OrderedText> rawRules = Lists.newArrayList();

			if (this.orderedTextList != null)
			{
				for (OrderedText raw : this.orderedTextList)
				{
					rawRules.add(raw);
				}
			}
			return rawRules;
		}
	}
}

