package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.render.ColoredEnderman;
import com.sjkz1.sjkz1misc.render.GlowingLayer;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntityRenderer.class)
public abstract class EndermanRenderMixin extends MobEntityRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>>
{
    @Shadow public abstract Identifier getTexture(EndermanEntity endermanEntity);

    EndermanRenderMixin()
    {
        super(null, null, 0);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
   public void init(EntityRendererFactory.Context context, CallbackInfo ci)
   {
         this.addFeature(new ColoredEnderman<>(this));

   }

}
