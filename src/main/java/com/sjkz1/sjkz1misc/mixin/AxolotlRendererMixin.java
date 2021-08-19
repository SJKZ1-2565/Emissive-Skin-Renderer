package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.render.RainbowLayer;

import net.minecraft.client.render.entity.AxolotlEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AxolotlEntityModel;
import net.minecraft.entity.passive.AxolotlEntity;

@Mixin(AxolotlEntityRenderer.class)
public abstract class AxolotlRendererMixin extends MobEntityRenderer<AxolotlEntity, AxolotlEntityModel<AxolotlEntity>>
{
   AxolotlRendererMixin() {
        super(null, null, 0);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    public void init(EntityRendererFactory.Context context, CallbackInfo info)
    {
        this.addFeature(new RainbowLayer<>(this));
    }
}
