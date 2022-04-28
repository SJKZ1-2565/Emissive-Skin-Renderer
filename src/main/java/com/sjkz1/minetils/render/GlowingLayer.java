package com.sjkz1.minetils.render;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.ColorMatching;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class GlowingLayer<T extends AbstractClientPlayerEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {
    public GlowingLayer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    public static float makeFade(float alpha) {
        return Math.min(0.7F, (MathHelper.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float time = MinecraftClient.getInstance().getTickDelta() + entity.age;
        if (!entity.isInvisible() && entity.getName().getString().equals("UnZygote") && Minetils.CONFIG.main.glowingSkin) {
            VertexConsumer inveterate = vertexConsumers.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/unzygote.png")));
            this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, makeFade(time), makeFade(time), makeFade(time), 1.0F);
        }
        if (!entity.isInvisible() && entity.getName().getString().equals("lastberries") && Minetils.CONFIG.main.glowingSkin) {
            VertexConsumer inveterate = vertexConsumers.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/lastberries.png")));
            this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, makeFade(time), makeFade(time), makeFade(time), 1.0F);
        }
        if (!entity.isInvisible() && entity.getName().getString().equals("SJKZ1") && Minetils.CONFIG.main.glowingSkin) {
            VertexConsumer inveterate = vertexConsumers.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/glow.png")));
            this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, makeFade(time), makeFade(time), makeFade(time), 1.0F);
        }
        if (!entity.isInvisible() && entity.getName().getString().equals("AnodizeX_Youen") && Minetils.CONFIG.main.glowingSkin) {
            VertexConsumer inveterate = vertexConsumers.getBuffer(RenderLayer.getEyes(new Identifier(Minetils.MOD_ID, "textures/entity/skin/anodizex_youen.png")));
            this.getContextModel().render(matrixStack, inveterate, light, OverlayTexture.DEFAULT_UV, makeFade(time), makeFade(time), makeFade(time), 1.0F);
        }
    }
}
