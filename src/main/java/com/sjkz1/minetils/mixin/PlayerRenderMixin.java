package com.sjkz1.minetils.mixin;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.render.GlowingLayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
	private int closeEyeTicks = 20;
	PlayerRenderMixin()
	{
		super(null, null, 0);
	}

	@Inject(method = "<init>",at = @At("RETURN"))
	public void init(EntityRendererFactory.Context context, boolean bl, CallbackInfo ci)
	{
		this.addFeature(new GlowingLayer<>((PlayerEntityRenderer) (Object) this));
	}


	@Inject(method = "renderArm", at = @At("TAIL"))
	private void renderArm(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, ModelPart mainHand, ModelPart sleeve, CallbackInfo ci) {

		float time = abstractClientPlayerEntity.age;
		mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture())), i, OverlayTexture.DEFAULT_UV);
			for (String id : Minetils.SPECIAL_MEMBER) {
					RenderLayer GLOWING_LAYER = RenderLayer.getEyes(GlowingLayer.getPath() != null ? GlowingLayer.getPath() : abstractClientPlayerEntity.getSkinTexture());
					VertexConsumer inveterate = vertexConsumerProvider.getBuffer(GLOWING_LAYER);

					if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().contains(id) && Minetils.CONFIG.getConfig().glowingSkin) {
						sleeve.render(matrixStack, inveterate, i, OverlayTexture.DEFAULT_UV,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
						mainHand.render(matrixStack, inveterate, i, OverlayTexture.DEFAULT_UV,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
					}
					if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().equals("TornNgern") && Minetils.CONFIG.getConfig().glowingSkin) {
						float ticks = (time % 360 + MinecraftClient.getInstance().getTickDelta()) / 360.0F;
						Color color = Color.getHSBColor(ticks,0.9f,1);
						sleeve.render(matrixStack, inveterate, i, OverlayTexture.DEFAULT_UV,color.getRed(),color.getGreen(),color.getBlue(),GlowingLayer.makeFade(time));
						mainHand.render(matrixStack, inveterate, i, OverlayTexture.DEFAULT_UV,color.getRed(),color.getGreen(),color.getBlue(),GlowingLayer.makeFade(time));
					}
			}
	}

	@Inject(method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;",at = @At(value = "RETURN"),cancellable = true)
	private void render(AbstractClientPlayerEntity abstractClientPlayerEntity,CallbackInfoReturnable<Identifier> info)
	{
		this.closeEyeTicks = Math.max(0, this.closeEyeTicks - 1);
		if(abstractClientPlayerEntity.getName().getString().equals("SJKZ1"))
		{
			if(isCloseEye()) {
				info.setReturnValue(new Identifier("minetils:textures/entity/skin/blink.png"));
			}
			else
			{
				if(abstractClientPlayerEntity.age % 100 == 0)
				{
					reset();
				}
			}
		}
	}

	private boolean isCloseEye()
	{
		return this.closeEyeTicks > 0;
	}

	private void reset()
	{
		this.closeEyeTicks = 20;
	}
}
