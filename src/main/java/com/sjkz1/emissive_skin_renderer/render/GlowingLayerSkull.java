package com.sjkz1.emissive_skin_renderer.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GlowingLayerSkull {
    public static void renderSkull(@Nullable Direction direction, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SkullModelBase skullModelBase, GameProfile gameProfile) {
        if (EmissiveSkinRenderer.CONFIG.main.glowingSkin) {
            float time = Minecraft.getInstance().getDeltaFrameTime();
            poseStack.pushPose();
            if (direction == null) {
                poseStack.translate(0.5, 0.0, 0.5);
            } else {
                poseStack.translate(0.5f - (float) direction.getStepX() * 0.25f, 0.25, 0.5f - (float) direction.getStepZ() * 0.25f);
            }
            poseStack.scale(-1.0f, -1.0f, 1.0f);
            if (gameProfile.getName().equals("lastberries")) {
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/lastberries.png")));
                skullModelBase.setupAnim(g, f, 0.0f);
                skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0f);
            } else if (gameProfile.getName().equals("SJKZ1")) {
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/glow.png")));
                skullModelBase.setupAnim(g, f, 0.0f);
                skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0f);
            }
            poseStack.popPose();
        }
    }
}
