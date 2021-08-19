package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.render.GlowingLayer;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
    PlayerRenderMixin()
    {
        super(null, null, 0);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
   public void init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo info)
   {
         this.addFeature(new GlowingLayer<>((PlayerEntityRenderer) (Object) this));
   }


    @Inject(method = "renderArm", at = @At("TAIL"))
    private void renderArm(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, ModelPart mainHand, ModelPart sleeve, CallbackInfo info) {

        float time = abstractClientPlayerEntity.age;

        GlowingLayer.makeFade(time);
        mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture())), i, OverlayTexture.DEFAULT_UV);
        for(SpecialMember values : SpecialMember.VALUES)
        {
            RenderLayer GLOWING_LAYER = RenderLayer.getEyes(GlowingLayer.getPath(values.getName().toLowerCase()));
            VertexConsumer inveterate = vertexConsumerProvider.getBuffer(GLOWING_LAYER);

            if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.getName().getString().equals(values.getName()) && SJKZ1Misc.CONFIG.getConfig().glowingSkin) {
                sleeve.render(matrixStack, inveterate, 15728640, OverlayTexture.DEFAULT_UV,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
                mainHand.render(matrixStack, vertexConsumerProvider.getBuffer(GLOWING_LAYER), 15728640, OverlayTexture.DEFAULT_UV,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
            }
        }

     }
}
