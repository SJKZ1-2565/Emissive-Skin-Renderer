package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.render.GlowingLayer;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    PlayerRenderMixin() {
        super(null, null, 0);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityRendererFactory.Context context, boolean bl, CallbackInfo ci) {
        this.addFeature(new GlowingLayer<>((PlayerEntityRenderer) (Object) this));
    }


    @Inject(method = "renderArm", at = @At("TAIL"))
    private void renderArm(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, ModelPart mainHand, ModelPart sleeve, CallbackInfo ci) {

        float time = (float) abstractClientPlayerEntity.age;
        if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().equals("SJKZ1")) {
            sleeve.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/glow.png"))), i, OverlayTexture.DEFAULT_UV, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
            mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/glow.png"))), i, OverlayTexture.DEFAULT_UV, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
        } else if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().equals("UnZygote") && Minetils.CONFIG.main.glowingSkin) {
            sleeve.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/unzygote.png"))), i, OverlayTexture.DEFAULT_UV, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
            mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/unzygote.png"))), i, OverlayTexture.DEFAULT_UV, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
        } else if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().equals("lastberries") && Minetils.CONFIG.main.glowingSkin) {
            sleeve.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/unzygote.png"))), i, OverlayTexture.DEFAULT_UV, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
            mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/lastberries.png"))), i, OverlayTexture.DEFAULT_UV, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
        }
    }
}
