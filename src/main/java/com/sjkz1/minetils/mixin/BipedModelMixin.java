package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
@Environment(EnvType.CLIENT)
public abstract class BipedModelMixin<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {

    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftArm;
    @Unique
    private final Minecraft client = Minecraft.getInstance();


    @Inject(method = "poseRightArm", at = @At("TAIL"))
    public void injectAnim(T livingEntity, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getItemInHand(livingEntity.getUsedItemHand());
        switch (itemstack.getUseAnimation()) {
            case EAT: {
                if (livingEntity instanceof Player && Minetils.CONFIG.main.enableEatingAnim) {
                    animateRightArmEatingAnimation((Player) livingEntity, livingEntity.tickCount % 20);
                }
            }
            case DRINK: {
                if (livingEntity instanceof Player && Minetils.CONFIG.main.enableEatingAnim) {
                    animateRightArmEatingAnimation((Player) livingEntity, livingEntity.tickCount % 20);
                }
            }
        }
    }

    @Inject(method = "positionLeftArm", at = @At("TAIL"))
    public void injectAnim2(T livingEntity, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getItemInHand(livingEntity.getUsedItemHand());
        switch (itemstack.getUseAnimation()) {
            case EAT: {
                if (livingEntity instanceof Player && Minetils.CONFIG.main.enableEatingAnim) {
                    animateLeftArmEatingAnimation((Player) livingEntity, livingEntity.tickCount % 20);
                }
            }
            case DRINK: {
                if (livingEntity instanceof PlayerEntity && Minetils.CONFIG.main.enableEatingAnim) {
                    animateLeftArmEatingAnimation((Player) livingEntity, livingEntity.tickCount % 20);
                }
            }
        }
    }

    private void animateRightArmEatingAnimation(Player player, int ticks) {
        if (player != null) {
            ItemStack itemstack = player.getItemInHand(player.getUsedItemHand());
            float deltas = ticks + client.getDeltaFrameTime();
            boolean drinkingOrEating = itemstack.getUseAnimation() == UseAnim.EAT || itemstack.getUseAnimation() == UseAnim.DRINK;
            if (player.getUseItemRemainingTicks() > 0 && drinkingOrEating) {
                this.rightArm.xRot = (0.25F * Mth.sin(deltas) + 5F);
                this.rightArm.yRot = -6.75F;
            }
        }
    }

    private void animateLeftArmEatingAnimation(Player player, int ticks) {
        if (player != null) {
            ItemStack itemstack = player.getItemInHand(player.getUsedItemHand());
            float deltas = ticks + client.getDeltaFrameTime();
            boolean drinkingOrEating = itemstack.getUseAnimation() == UseAnim.EAT || itemstack.getUseAnimation() == UseAnim.DRINK;
            if (player.getUseItemRemainingTicks() > 0 && drinkingOrEating) {
                this.leftArm.xRot = (0.25F * Mth.sin(deltas) + 5F);
                this.leftArm.yRot = 6.75F;
            }
        }
    }

}

