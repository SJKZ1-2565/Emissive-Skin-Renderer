package com.sjkz1.minetils.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AxolotlEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;
import java.awt.*;

public class RainBowAxolotl<T extends AxolotlEntity, M extends AxolotlEntityModel<T>> extends FeatureRenderer<T, M>
{
    private final Identifier SKIN =  new Identifier("textures/entity/axolotl/axolotl_lucy.png");

    public RainBowAxolotl(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
        float x = ((float)((entity.age + entity.age) % 90) + g) / 90.0F;
        Color color = Color.getHSBColor(x,0.9f,1);
        RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCull(SKIN);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
        if(entity.getName().getString().equals("Nom Yen")) {
            if(entity.getVariant().equals(AxolotlEntity.Variant.LUCY)) {
                this.getContextModel().render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, color.getRed(), color.getBlue(), color.getGreen(), 1);
            }
        }
    }
}
