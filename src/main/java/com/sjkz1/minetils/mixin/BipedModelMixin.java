package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
@Environment(EnvType.CLIENT)
public abstract class BipedModelMixin<T extends LivingEntity>
        extends AnimalModel<T>
        implements ModelWithArms,
        ModelWithHead {

    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftArm;
    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();


    @Inject(method = "positionRightArm", at = @At("TAIL"))
    public void injectAnim(T livingEntity, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getStackInHand(livingEntity.getActiveHand());
        switch (itemstack.getUseAction()) {
            case EAT: {
                if (livingEntity instanceof PlayerEntity && Minetils.CONFIG.main.enableEatingAnim) {
                    animateRightArmEatingAnimation((PlayerEntity) livingEntity, livingEntity.age % 20);
                }
            }
            case DRINK: {
                if (livingEntity instanceof PlayerEntity && Minetils.CONFIG.main.enableEatingAnim) {
                    animateRightArmEatingAnimation((PlayerEntity) livingEntity, livingEntity.age % 20);
                }
            }
        }
    }

    @Inject(method = "positionLeftArm", at = @At("TAIL"))
    public void injectAnim2(T livingEntity, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getStackInHand(livingEntity.getActiveHand());
        switch (itemstack.getUseAction()) {
            case EAT: {
                if (livingEntity instanceof PlayerEntity && Minetils.CONFIG.main.enableEatingAnim) {
                    animateLeftArmEatingAnimation((PlayerEntity) livingEntity, livingEntity.age % 20);
                }
            }
            case DRINK: {
                if (livingEntity instanceof PlayerEntity && Minetils.CONFIG.main.enableEatingAnim) {
                    animateLeftArmEatingAnimation((PlayerEntity) livingEntity, livingEntity.age % 20);
                }
            }
        }
    }

    private void animateRightArmEatingAnimation(PlayerEntity player, int ticks) {
        if (player != null) {
            ItemStack itemstack = player.getStackInHand(player.getActiveHand());
            float deltas = ticks + client.getTickDelta();
            boolean drinkingOrEating = itemstack.getUseAction() == UseAction.EAT || itemstack.getUseAction() == UseAction.DRINK;
            if (player.getItemUseTimeLeft() > 0 && drinkingOrEating) {
                this.rightArm.pitch = (0.25F * MathHelper.sin(deltas) + 5F);
                this.rightArm.yaw = -6.75F;
            }
        }
    }

    private void animateLeftArmEatingAnimation(PlayerEntity player, int ticks) {
        if (player != null) {
            ItemStack itemstack = player.getStackInHand(player.getActiveHand());
            float deltas = ticks + client.getTickDelta();
            boolean drinkingOrEating = itemstack.getUseAction() == UseAction.EAT || itemstack.getUseAction() == UseAction.DRINK;
            if (player.getItemUseTimeLeft() > 0 && drinkingOrEating) {
                this.leftArm.pitch = (0.25F * MathHelper.sin(deltas) + 5F);
                this.leftArm.yaw = 6.75F;
            }
        }
    }

}

