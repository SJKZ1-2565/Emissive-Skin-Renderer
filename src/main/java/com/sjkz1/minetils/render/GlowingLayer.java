package com.sjkz1.minetils.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.ColorMatching;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class GlowingLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {


    public GlowingLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    public static float makeFade(float alpha) {
        return Math.min(0.7F, (Mth.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }


    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        float time = Minecraft.getInstance().getDeltaFrameTime() + entity.tickCount;
        if (!entity.isInvisible() && Minetils.CONFIG.main.glowingSkin) {
            if (entity.getName().getString().equals("UnZygote")) {
                VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(Minetils.MOD_ID, "textures/entity/skin/unzygote.png")));
                this.getParentModel().renderToBuffer(poseStack, inveterate, i, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
            else if (entity.getName().getString().equals("lastberries")) {
                VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(Minetils.MOD_ID, "textures/entity/skin/lastberries.png")));
                this.getParentModel().renderToBuffer(poseStack, inveterate, i, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
            else if (entity.getName().getString().equals("SJKZ1")) {
                VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(Minetils.MOD_ID, "textures/entity/skin/glow.png")));
                this.getParentModel().renderToBuffer(poseStack, inveterate, i, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
            else if (entity.getName().getString().equals("AnodizeX_Youen")) {
                VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(Minetils.MOD_ID, "textures/entity/skin/anodizex_youen.png")));
                this.getParentModel().renderToBuffer(poseStack, inveterate, i, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
            else if (entity.getName().getString().equals(entity.getName()) && ColorMatching.identifier != null)
            {
                VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(ColorMatching.identifier));
                this.getParentModel().renderToBuffer(poseStack, inveterate, i, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
        }
    }
}
