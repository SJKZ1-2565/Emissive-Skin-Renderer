package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.ModelHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;

@Mixin(PlayerEntityModel.class)
@Environment(EnvType.CLIENT)
public class PlayerModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {
    private final MinecraftClient client = MinecraftClient.getInstance();
    public PlayerModelMixin() {
        super(null);
    }
    @Final
    @Shadow
    public ModelPart leftSleeve;
    @Final
    @Shadow
    public ModelPart rightSleeve;
    @Shadow @Final public ModelPart jacket;

    @Inject(method = "setAngles",at = @At("TAIL"))
    public void injectAnim(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        if(livingEntity instanceof AbstractClientPlayerEntity && Minetils.CONFIG.getConfig().enableEatingAnim) {
            eatingAnimationRightHand(rightArm, rightSleeve, livingEntity.age);
            eatingAnimationLeftHand(leftArm, leftSleeve, livingEntity.age);
        }
        if(livingEntity instanceof AbstractClientPlayerEntity)
        {
            ModelHelper.playerDance(head,rightArm,leftArm,body,hat,rightSleeve,leftSleeve,jacket, livingEntity.age);
        }
    }

    private  void eatingAnimationRightHand(ModelPart rightArm, ModelPart rightSleeve, int ticks) {

       if(client.player != null) {
           ItemStack itemstack = client.player.getStackInHand(Hand.field_5808);
           float deltas = ticks + client.getTickDelta();
           boolean drinkingoreating = itemstack.getUseAction() == UseAction.field_8950 || itemstack.getUseAction() == UseAction.field_8946;
           if (client.player.getItemUseTimeLeft() > 0 && drinkingoreating && client.player.getActiveHand() == Hand.field_5808) {
               rightArm.pitch  = (0.25F * MathHelper.sin(deltas) + 5F);
               rightArm.yaw = -6.75F;
               rightSleeve.copyTransform(rightArm);
           }
       }
    }

    private  void eatingAnimationLeftHand(ModelPart lefttArm, ModelPart leftSleeve, int ticks) {
        if(client.player != null) {
            ItemStack itemstack = client.player.getStackInHand(Hand.field_5810);
            float deltas = ticks + client.getTickDelta();
            boolean drinkingoreating = itemstack.getUseAction() == UseAction.field_8950 || itemstack.getUseAction() == UseAction.field_8946;
            if (client.player.getItemUseTimeLeft() > 0 && drinkingoreating && client.player.getActiveHand() == Hand.field_5810) {
                lefttArm.pitch = (0.25F * MathHelper.sin(deltas) + 5F);
                lefttArm.yaw = 6.75F;
                leftSleeve.copyTransform(lefttArm);
            }
        }
    }
}
