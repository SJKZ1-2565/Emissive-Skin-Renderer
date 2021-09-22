package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity
{



	Minecraft mc = Minecraft.getInstance();

	PlayerEntityMixin() {
		super(null, null);
	}

	@Inject(method = "attack",at = @At("HEAD"), cancellable = true)
	public void attack(Entity target, CallbackInfo ci)
	{
		if(target instanceof Villager && !Objects.requireNonNull(mc.player).getAbilities().flying)
		{
			if(SJKZ1Misc.CONFIG.getConfig().IgnoreHittingVillager)
			{
				ci.cancel();
			}
		}
		else if(target instanceof ItemFrame itemFrameEntity)
		{
//			if(itemFrameEntity.fixed)
//			{
//				ci.cancel();
//			}
		}
	}
}
