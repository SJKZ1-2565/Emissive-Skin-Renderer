package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.config.SJKZ1MiscConfig;
import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.Packet;
import net.minecraft.server.command.TitleCommand;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{

	@Shadow protected abstract void takeShieldHit(LivingEntity livingEntity);

	MinecraftClient mc = MinecraftClient.getInstance();

	PlayerEntityMixin() {
		super(null, null);
	}

	@Inject(method = "attack",at = @At("HEAD"), cancellable = true)
	public void attack(Entity target, CallbackInfo ci)
	{
		if(target instanceof VillagerEntity && !mc.player.getAbilities().creativeMode)
		{
			if(SJKZ1Misc.CONFIG.getConfig().IgnoreHittingVillager)
			{
				ci.cancel();
			}
			else if(target instanceof ItemFrameEntity)
			{
				ItemFrameEntity itemFrameEntity = (ItemFrameEntity) target;
				if(itemFrameEntity.fixed)
				{
					ci.cancel();
				}
			}
		}
	}
	@Inject(method = "tick",at = @At("HEAD"))
	public void tick(CallbackInfo info)
	{
		if(mc.player != null && mc.world != null) {
//			int arrow = SJKZ1Helper.getArrowItem(mc.player.getInventory());
//			for (int i = arrow;i == 0;)
//			{
//				System.out.println("No arrow left");
//				break;
//			}
		}
	}

//	@Override
//	public void tick()
//	{
//		super.tick();
//		if(SJKZ1Helper.isBambooClose())
//	}
}
