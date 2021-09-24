package com.sjkz1.sjkz1misc.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobRenderer.class)
public abstract class LivingRendererMixin <T extends Mob, M extends EntityModel<T>> extends LivingEntityRenderer<T, M>
{

	public LivingRendererMixin(EntityRendererProvider.Context context, M entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(method = "render",at = @At("HEAD"))
	public void render(T mob, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci)
	{
		SJKZ1Helper.renderLabel(mob,poseStack,multiBufferSource,i);
		if(mob instanceof AbstractHorse abstractHorse)
		{
			SJKZ1Helper.renderLabelTamed(abstractHorse,poseStack,multiBufferSource,i);
		}
	}
}
