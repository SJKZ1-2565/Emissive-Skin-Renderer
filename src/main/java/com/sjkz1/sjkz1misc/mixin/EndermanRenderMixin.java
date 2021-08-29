package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.render.ColoredEnderman;

import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

@Mixin(EndermanEntityRenderer.class)
public abstract class EndermanRenderMixin extends MobEntityRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>>
{
    @Override
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
