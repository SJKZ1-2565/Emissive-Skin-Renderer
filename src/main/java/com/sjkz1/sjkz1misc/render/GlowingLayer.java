package com.sjkz1.sjkz1misc.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GlowingLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{


    public GlowingLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    public static ResourceLocation getPath(String name) {
        return new ResourceLocation("sjkz1misc:textures/entity/" + name + ".png");
    }


    public static float makeFade(float alpha)
    {
        return  Math.min(0.7F, (Mth.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer entity, float f, float g, float h, float j, float k, float l) {
        float time = entity.tickCount + h;
        for(SpecialMember values : SpecialMember.VALUES)
        {
            RenderType GLOWING_LAYER = RenderType.eyes(getPath(values.getName().toLowerCase()));
            if (!entity.isInvisible() && entity.getName().getString().equals(values.getName()) && SJKZ1Misc.CONFIG.getConfig().glowingSkin) {
                VertexConsumer inveterate = multiBufferSource.getBuffer(GLOWING_LAYER);
                this.getParentModel().renderToBuffer(poseStack, inveterate, i, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
            }
        }
    }
}
