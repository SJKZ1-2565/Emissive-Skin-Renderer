package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

@Mixin(PlayerEntityModel.class)
public class PlayerDanceMixin<T extends LivingEntity> extends BipedEntityModel<T>
{

    @Shadow @Final public ModelPart rightSleeve;

    @Shadow @Final public ModelPart leftSleeve;

    @Shadow @Final public ModelPart jacket;


    PlayerDanceMixin() {
        super(null);
    }

    @Inject(method = "setAngles",at = @At("TAIL"))
    public void PlayDance(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
            SJKZ1Helper.PlayerDance(head, hat, leftArm, rightArm, rightSleeve, leftSleeve, body, jacket, livingEntity.age, h);
    }
}
