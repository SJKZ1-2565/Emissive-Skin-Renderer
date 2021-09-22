package com.sjkz1.sjkz1misc.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {
    private final Minecraft client = Minecraft.getInstance();
    public PlayerModelMixin() {
        super(null);
    }
    @Final
    @Shadow
    public ModelPart leftSleeve;
    @Final
    @Shadow
    public ModelPart rightSleeve;
    @Inject(method = "setupAnim",at = @At("TAIL"))
    public void injectAnim(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        eatingAnimationRightHand(rightArm,rightSleeve,livingEntity.tickCount);
        eatingAnimationLeftHand(leftArm,leftSleeve,livingEntity.tickCount);
    }

    private  void eatingAnimationRightHand(ModelPart rightArm, ModelPart rightSleeve, int ticks) {

       if(client.player != null) {
           ItemStack itemstack = client.player.getItemInHand(InteractionHand.MAIN_HAND);
           float deltas = client.getDeltaFrameTime();
           boolean drinkingoreating = itemstack.getUseAnimation() == UseAnim.EAT || itemstack.getUseAnimation() == UseAnim.DRINK;
           if (client.player.getTicksUsingItem() > 0 && drinkingoreating && client.player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
               rightArm.x = (0.25F * Mth.sin(ticks + deltas) + 5F);
               rightArm.y = -6.75F;
               rightSleeve.copyFrom(rightArm);
           }
       }
    }

    private  void eatingAnimationLeftHand(ModelPart lefttArm, ModelPart leftSleeve, int ticks) {
        if(client.player != null) {
            ItemStack itemstack = client.player.getItemInHand(InteractionHand.OFF_HAND);
            float deltas = client.getDeltaFrameTime();
            boolean drinkingoreating = itemstack.getUseAnimation() == UseAnim.EAT || itemstack.getUseAnimation() == UseAnim.DRINK;
            if (client.player.getTicksUsingItem() > 0 && drinkingoreating && client.player.getUsedItemHand() == InteractionHand.OFF_HAND) {
                lefttArm.x = (0.25F * Mth.sin(ticks + deltas) + 5F);
                lefttArm.y = 6.75F;
                leftSleeve.copyFrom(lefttArm);
            }
        }
    }
}
