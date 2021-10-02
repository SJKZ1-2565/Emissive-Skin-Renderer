package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{

	@Shadow @Final private PlayerInventory inventory;
	MinecraftClient mc = MinecraftClient.getInstance();

	PlayerEntityMixin() {
		super(null, null);
	}

	@Inject(method = "tick",at= @At("HEAD"))
	public void tick(CallbackInfo ci)
	{
		System.out.println(this.inventory.getStack(this.inventory.selectedSlot));
	}

	@Inject(method = "attack",at = @At("HEAD"), cancellable = true)
	public void attack(Entity target, CallbackInfo ci)
	{
		if(target instanceof VillagerEntity && !Objects.requireNonNull(mc.player).getAbilities().flying)
		{
			if(Minetils.CONFIG.getConfig().IgnoreHittingVillager)
			{
				ci.cancel();
			}
		}
		else if(target instanceof ItemFrameEntity itemFrameEntity)
		{
//			if(itemFrameEntity.fixed)
//			{
//				ci.cancel();
//			}
		}
	}
}
