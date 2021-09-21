package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.SJKZ1Misc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{

	@Override
	@Shadow protected abstract void takeShieldHit(LivingEntity livingEntity);

	MinecraftClient mc = MinecraftClient.getInstance();

	PlayerEntityMixin() {
		super(null, null);
	}

	@Inject(method = "attack",at = @At("HEAD"), cancellable = true)
	public void attack(Entity target, CallbackInfo ci)
	{
		if(target instanceof VillagerEntity && !Objects.requireNonNull(mc.player).getAbilities().creativeMode)
		{
			if(SJKZ1Misc.CONFIG.getConfig().IgnoreHittingVillager)
			{
				ci.cancel();
			}
		}
		else if(target instanceof ItemFrameEntity itemFrameEntity)
		{
			if(itemFrameEntity.fixed)
			{
				ci.cancel();
			}
		}
	}
}
