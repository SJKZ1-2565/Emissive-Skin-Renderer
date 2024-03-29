package com.sjkz1.emissive_skin_renderer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import com.sjkz1.emissive_skin_renderer.render.GlowingLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    PlayerRenderMixin() {
        super(null, null, 0);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        this.addLayer(new GlowingLayer<>((PlayerRenderer) (Object) this));
    }


    @Inject(method = "renderHand", at = @At("TAIL"))
    private void renderArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer abstractClientPlayer, ModelPart modelPart, ModelPart modelPart2, CallbackInfo ci) {

        float time = (float) abstractClientPlayer.tickCount;
        if (!abstractClientPlayer.isInvisible() && GlowingLayer.specialBoolean(abstractClientPlayer) && EmissiveSkinRenderer.CONFIG.main.glowingSkin) {
            modelPart.xRot = 0.0f;
            modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
            modelPart2.xRot = 0.0f;
            modelPart2.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
        } else if (!ResourceLocation.isValidResourceLocation(EmissiveSkinRenderer.MOD_ID + ":textures/skin/" + abstractClientPlayer.getUUID() + ".png") && !GlowingLayer.specialBoolean(abstractClientPlayer) && EmissiveSkinRenderer.CONFIG.main.glowingSkin && !abstractClientPlayer.isInvisible() && EmissiveSkinRenderer.CONFIG.main.manualSkin) {
            modelPart.xRot = 0.0f;
            modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
            modelPart2.xRot = 0.0f;
            modelPart2.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
        }
        else if (!ResourceLocation.isValidResourceLocation(EmissiveSkinRenderer.MOD_ID + ":textures/skin/glow.png") && !GlowingLayer.specialBoolean(abstractClientPlayer) && EmissiveSkinRenderer.CONFIG.main.glowingSkin && !abstractClientPlayer.isInvisible() && !EmissiveSkinRenderer.CONFIG.main.manualSkin) {
            modelPart.xRot = 0.0f;
            modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/skin/glow.png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
            modelPart2.xRot = 0.0f;
            modelPart2.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/skin/glow.png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
        }
    }
}
