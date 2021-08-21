package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.command.EntityDetector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{

	MinecraftClient mc = MinecraftClient.getInstance();

	LivingEntityMixin() {
		super(null, null);
	}

	@Inject(method = "tick",at = @At("HEAD"))
	public void tick(CallbackInfo info)
	{
		if(mc.player != null && mc.world != null) {
			this.setGlowing(EntityDetector.glow);
		}
	}

//	@Override
//	public void tick()
//	{
//		super.tick();
//		if(SJKZ1Helper.isBambooClose())
//	}
}
