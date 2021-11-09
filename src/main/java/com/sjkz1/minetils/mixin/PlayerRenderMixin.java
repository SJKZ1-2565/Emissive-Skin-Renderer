package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.render.GlowingLayer;
import com.sjkz1.minetils.utils.SpecialMember;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
    PlayerRenderMixin()
    {
        super(null, null, 0);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
   public void init(EntityRendererFactory.Context context, boolean bl, CallbackInfo ci)
   {
       this.addFeature(new GlowingLayer<>((PlayerEntityRenderer) (Object) this));
   }


    @Inject(method = "renderArm", at = @At("TAIL"))
    private void re(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, ModelPart mainHand, ModelPart sleeve, CallbackInfo ci) {

        float time = abstractClientPlayerEntity.age;
        mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture())), i, OverlayTexture.DEFAULT_UV);
        for(SpecialMember values : SpecialMember.VALUES)
        {
            RenderLayer GLOWING_LAYER = RenderLayer.getEyes(GlowingLayer.getPath(values.getName().toLowerCase()));
            VertexConsumer inveterate = vertexConsumerProvider.getBuffer(GLOWING_LAYER);

            if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().equals(values.getName()) && Minetils.CONFIG.getConfig().glowingSkin) {
                sleeve.render(matrixStack, inveterate, 15728640, OverlayTexture.DEFAULT_UV,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
                mainHand.render(matrixStack, inveterate, 15728640, OverlayTexture.DEFAULT_UV,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
            }
        }

     }
}
