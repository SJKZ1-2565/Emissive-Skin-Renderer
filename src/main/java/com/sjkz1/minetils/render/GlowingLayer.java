package com.sjkz1.minetils.render;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.ColorMatching;
import com.sjkz1.minetils.utils.SpecialMember;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class GlowingLayer<T extends AbstractClientPlayerEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {
    public GlowingLayer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }


    public static Identifier getPath() {

        return Minetils.CONFIG.getConfig().manualSkinEditor ? new Identifier(Minetils.MOD_ID + ":textures/entity/skin/glow.png") : ColorMatching.identifier;
    }


    public static float makeFade(float alpha) {
        return Math.min(0.7F, (MathHelper.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float time = MinecraftClient.getInstance().getTickDelta() + tickDelta;
        Color color = Color.getHSBColor(tickDelta % 360, 0.9f, 1);
        for(SpecialMember values : SpecialMember.VALUES)
        {
            RenderLayer GLOWING_LAYER = RenderLayer.getEyes(getPath());
            if (!entity.isInvisible() && entity.getName().getString().equals(values.getName()) && Minetils.CONFIG.getConfig().glowingSkin) {
                VertexConsumer inveterate = vertexConsumers.getBuffer(GLOWING_LAYER);
                this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, makeFade(time), makeFade(time), makeFade(time),  makeFade(time));
            }
            else if (!entity.isInvisible() && entity.getName().getString().equals("SJKZ1") && Minetils.CONFIG.getConfig().glowingSkin) {
                VertexConsumer inveterate = vertexConsumers.getBuffer(GLOWING_LAYER);
                this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, makeFade(color.getRed()), makeFade(color.getGreen()), makeFade(color.getBlue()),  makeFade(time));
            }
        }
    }
}
