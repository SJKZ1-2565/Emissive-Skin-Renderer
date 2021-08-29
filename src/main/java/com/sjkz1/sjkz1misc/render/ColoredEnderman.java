package com.sjkz1.sjkz1misc.render;

import com.sjkz1.sjkz1misc.SJKZ1Misc;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

public class ColoredEnderman<T extends EndermanEntity, M extends EndermanEntityModel<T>> extends FeatureRenderer<T, M> {

    public ColoredEnderman(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }
    private static final Identifier TEXTURE = GlowingLayer.getPath("enderman");

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
       if(SJKZ1Misc.CONFIG.getConfig().coloredEnderman) {
           VertexConsumer inveterate = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
           this.getContextModel().render(matrixStack, inveterate, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
       }
    }
}
