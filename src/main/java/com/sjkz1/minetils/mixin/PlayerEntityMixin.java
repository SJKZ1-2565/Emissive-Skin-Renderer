package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{

	@Shadow public abstract Text getName();

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
}
