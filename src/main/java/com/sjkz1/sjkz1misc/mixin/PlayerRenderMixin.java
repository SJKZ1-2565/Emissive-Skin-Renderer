package com.sjkz1.sjkz1misc.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.render.GlowingLayer;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
    PlayerRenderMixin()
    {
        super(null, null, 0);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
   public void init(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci)
   {
         this.addLayer(new GlowingLayer((PlayerRenderer) (Object) this));
   }


    @Inject(method = "renderHand", at = @At("TAIL"))
    private void renderArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer abstractClientPlayer, ModelPart modelPart, ModelPart modelPart2, CallbackInfo ci) {

        float time = abstractClientPlayer.tickCount;
        modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.entitySolid(abstractClientPlayer.getSkinTextureLocation())), i, OverlayTexture.NO_OVERLAY);
        for(SpecialMember values : SpecialMember.VALUES)
        {
            RenderType GLOWING_LAYER = RenderType.eyes(GlowingLayer.getPath(values.getName().toLowerCase()));
            VertexConsumer inveterate = multiBufferSource.getBuffer(GLOWING_LAYER);

            if (!abstractClientPlayer.isInvisible() && abstractClientPlayer.getName().getString().equals(values.getName()) && SJKZ1Misc.CONFIG.getConfig().glowingSkin) {
                modelPart2.render(poseStack, inveterate, 15728640, OverlayTexture.NO_OVERLAY,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
                modelPart.render(poseStack, inveterate, 15728640, OverlayTexture.NO_OVERLAY,GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time),GlowingLayer.makeFade(time));
            }
        }

     }
}
