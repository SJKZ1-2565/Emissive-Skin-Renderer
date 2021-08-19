package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.render.RainbowLayer;

import net.minecraft.client.render.entity.CatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.entity.passive.CatEntity;

@Mixin(CatEntityRenderer.class)
public abstract class CatRendererMixin extends MobEntityRenderer<CatEntity, CatEntityModel<CatEntity>>
{
   CatRendererMixin() {
        super(null, null, 0);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V",at = @At("RETURN"))
    public void init(EntityRendererFactory.Context context, CallbackInfo info)
    {
        this.addFeature(new RainbowLayer<>(this));
    }
}
