package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {
    private MinecraftClient client = MinecraftClient.getInstance();
    public PlayerModelMixin() {
        super(null);
    }
    @Shadow
    public ModelPart leftSleeve;
    @Shadow
    public ModelPart rightSleeve;
    @Inject(method = "setAngles",at = @At("TAIL"))
    public void injectAnim(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        eatingAnimationRightHand(Hand.MAIN_HAND,rightArm,rightSleeve,livingEntity.age);
        eatingAnimationLeftHand(Hand.OFF_HAND,leftArm,leftSleeve,livingEntity.age);
    }

    private  void eatingAnimationRightHand(Hand hand, ModelPart rightArm, ModelPart rightSleeve, int ticks) {
        ItemStack itemstack = client.player.getStackInHand(hand);
        float deltas = client.getTickDelta();
        boolean drinkingoreating = itemstack.getUseAction() == UseAction.EAT || itemstack.getUseAction() == UseAction.DRINK;
        if ( client.player.getItemUseTimeLeft() > 0 && drinkingoreating &&  client.player.getActiveHand() == hand) {
            rightArm.pitch =  (0.25F * MathHelper.sin(ticks + deltas) + 5F);
            rightArm.yaw = -6.75F;
            rightSleeve.copyTransform(rightArm);

        }
    }

    private  void eatingAnimationLeftHand(Hand hand, ModelPart lefttArm, ModelPart leftSleeve, int ticks) {
        ItemStack itemstack =  client.player.getStackInHand(hand);
        float deltas = client.getTickDelta();
        boolean drinkingoreating = itemstack.getUseAction() == UseAction.EAT || itemstack.getUseAction() == UseAction.DRINK;
        if ( client.player.getItemUseTimeLeft() > 0 && drinkingoreating &&  client.player.getActiveHand() == hand) {
            lefttArm.pitch =  (0.25F * MathHelper.sin(ticks + deltas) + 5F);
            lefttArm.yaw = 6.75F;
            leftSleeve.copyTransform(lefttArm);
        }
    }
}
