package com.sjkz1.minetils.mixin;

import java.util.Objects;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.sjkz1.minetils.Minetils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{

	@Override
	@Shadow public abstract Text getName();

	@Shadow public abstract GameProfile getGameProfile();

	MinecraftClient mc = MinecraftClient.getInstance();

	PlayerEntityMixin() {
		super(null, null);
	}

	@Inject(method = "attack",at = @At("HEAD"), cancellable = true)
	public void attack(Entity target, CallbackInfo ci) {
		if (target instanceof VillagerEntity && !Objects.requireNonNull(mc.player).getAbilities().flying) {
			if (Minetils.CONFIG.getConfig().IgnoreHittingVillager) {
				ci.cancel();
			}
		} else if (target instanceof ItemFrameEntity itemFrameEntity) {
			if (itemFrameEntity.fixed) {
				ci.cancel();
			}
		}
	}
	@Inject(method = "tick",at = @At("HEAD"), cancellable = true)
	public void attack(CallbackInfo ci) {
//		if(this != null)
//		{
//			this.sendSystemMessage(Text.of(String.valueOf(AbstractClientPlayerEntity.getSkinId(this.getGameProfile().getName()))), UUID.randomUUID());
//		}
	}
}
