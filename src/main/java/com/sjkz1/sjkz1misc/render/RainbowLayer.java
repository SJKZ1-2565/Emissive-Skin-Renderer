package com.sjkz1.sjkz1misc.render;

import java.awt.Color;

import com.sjkz1.sjkz1misc.SJKZ1Misc;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.MathHelper;

public class RainbowLayer<T extends Entity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{



	public RainbowLayer(FeatureRendererContext<T, M> context)
	{
		super(context);
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		float ticks = ((float)(entity.age % 20) + tickDelta) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);

		if (!entity.isInvisible() && SJKZ1Misc.dance && !(entity instanceof AbstractClientPlayerEntity)) {
			VertexConsumer inveterate = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));
			this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, color.getRed(), color.getGreen(), color.getBlue(), 1.0F);
		}
	}

}
