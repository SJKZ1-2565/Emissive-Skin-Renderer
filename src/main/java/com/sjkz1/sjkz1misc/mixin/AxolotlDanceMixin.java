package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.AxolotlEntityModel;
import net.minecraft.entity.AngledModelEntity;
import net.minecraft.entity.passive.AxolotlEntity;

@Mixin(AxolotlEntityModel.class)
public abstract class AxolotlDanceMixin<T extends AxolotlEntity & AngledModelEntity> extends AnimalModel<T>
{

    @Shadow @Final private ModelPart body;

    AxolotlDanceMixin() {
        super();
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/passive/AxolotlEntity;FFFFF)V",at = @At("TAIL"))
    public void dance(T axolotlEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        if(SJKZ1Misc.dance) {
                SJKZ1Helper.axolotlDance(this.body, axolotlEntity.age, h);
        }
    }
}
