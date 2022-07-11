package com.sjkz1.emissive_skin_renderer.render;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GlowingLayerSkull {

    private static final Map<SkullBlock.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(SkullBlock.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
        hashMap.put(SkullBlock.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
        hashMap.put(SkullBlock.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
        hashMap.put(SkullBlock.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
        hashMap.put(SkullBlock.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
        hashMap.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultSkin());
    });

    public static void renderSkull(@Nullable Direction direction, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SkullModelBase skullModelBase, GameProfile gameProfile, RenderType renderType, int ticks) {
        if (EmissiveSkinRenderer.CONFIG.main.glowingSkin) {
            float time = Minecraft.getInstance().getDeltaFrameTime() + (float) ticks;
            poseStack.pushPose();
            if (direction == null) {
                poseStack.translate(0.5, 0.0, 0.5);
            } else {
                poseStack.translate(0.5f - (float) direction.getStepX() * 0.25f, 0.25, 0.5f - (float) direction.getStepZ() * 0.25f);
            }
            poseStack.scale(-1.0f, -1.0f, 1.0f);
            if (!StringUtil.isNullOrEmpty(gameProfile.getName())) {
                for (String s : EmissiveSkinRenderer.SPECIAL_MEMBER) {
                    if (gameProfile.getName().equals(s)) {
                        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
                        skullModelBase.setupAnim(g, f, 0.0f);
                        skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time));
                    }
                }
                if (gameProfile.getName().equals("Technoblade")) {
                    VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
                    skullModelBase.setupAnim(g, f, 0.0f);
                    skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time));
                }
            }
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
            skullModelBase.setupAnim(g, f, 0.0f);
            skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time));
            poseStack.popPose();

        }
    }

    public static RenderType getRenderType(SkullBlock.Type type, @Nullable GameProfile gameProfile) {
        ResourceLocation resourceLocation = SKIN_BY_TYPE.get(type);
        if (type != SkullBlock.Types.PLAYER || gameProfile == null) {
            return RenderType.entityCutoutNoCullZOffset(resourceLocation);
        }
        Minecraft minecraft = Minecraft.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfile);
        if (map.containsKey((Object) MinecraftProfileTexture.Type.SKIN)) {
            if (StringUtil.isNullOrEmpty(gameProfile.getName())) {
                return RenderType.entityTranslucent(minecraft.getSkinManager().registerTexture(map.get((Object) MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
            }
            for (String s : EmissiveSkinRenderer.SPECIAL_MEMBER) {
                if (gameProfile.getName().equals(s)) {
                    return RenderType.eyes(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + s.toLowerCase() + ".png"));
                }
            }
            if (gameProfile.getName().equals("Technoblade")) {
                return RenderType.eyes(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/technoblade.png"));
            }
            return RenderType.entityTranslucent(minecraft.getSkinManager().registerTexture(map.get((Object) MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
        }
        return RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(gameProfile)));
    }
}

