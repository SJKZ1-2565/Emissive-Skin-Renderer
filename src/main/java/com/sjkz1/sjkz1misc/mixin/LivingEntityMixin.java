package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.command.EntityDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class LivingEntityMixin extends Entity
{

	Minecraft mc = Minecraft.getInstance();

	LivingEntityMixin() {
		super(null, null);
	}

	@Inject(method = "tick",at = @At("HEAD"))
	public void tick(CallbackInfo info)
	{
		if(mc.player != null && mc.level != null) {
			this.setGlowingTag(EntityDetector.glow);
		}
	}
}
