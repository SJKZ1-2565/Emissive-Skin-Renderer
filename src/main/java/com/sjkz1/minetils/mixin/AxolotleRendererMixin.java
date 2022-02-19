package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.minetils.render.RainBowAxolotl;

import net.minecraft.client.render.entity.AxolotlEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AxolotlEntityModel;
import net.minecraft.entity.passive.AxolotlEntity;

@Mixin(AxolotlEntityRenderer.class)
public abstract class AxolotleRendererMixin extends MobEntityRenderer<AxolotlEntity, AxolotlEntityModel<AxolotlEntity>> {

    public AxolotleRendererMixin(EntityRendererFactory.Context context, AxolotlEntityModel<AxolotlEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>" ,at = @At("RETURN"))
    public void render(EntityRendererFactory.Context context, CallbackInfo ci)
    {
        this.addFeature(new RainBowAxolotl<>( this));
    }
}
