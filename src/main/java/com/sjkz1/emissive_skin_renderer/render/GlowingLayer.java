package com.sjkz1.emissive_skin_renderer.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class GlowingLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {


    public GlowingLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    public static float makeFade(float alpha) {
        return Math.min(0.7F, (Mth.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }


    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        float time = h + entity.tickCount;
        if (!entity.isInvisible() && EmissiveSkinRenderer.CONFIG.main.glowingSkin) {
            for (String s : EmissiveSkinRenderer.SPECIAL_MEMBER) {
                if (entity.getName().getString().equals(s)) {
                    VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + s.toLowerCase() + ".png")));
                    this.getParentModel().renderToBuffer(poseStack, inveterate, 0xF00000, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
                }
            }
            if (entity.getName().getString().equals(entity.getName().getString()) && ColorMatching.identifier != null) {
                VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(ColorMatching.identifier));
                this.getParentModel().renderToBuffer(poseStack, inveterate, 0xF00000, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
        }
    }
}
