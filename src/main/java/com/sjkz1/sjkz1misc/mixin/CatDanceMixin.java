package com.sjkz1.sjkz1misc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;

import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.entity.passive.CatEntity;

@Mixin(CatEntityModel.class)
public class CatDanceMixin<T extends CatEntity> extends OcelotEntityModel<T>
{
    CatDanceMixin()
    {
        super(null);
    }
    @Inject(at = @At("TAIL"), method = "setAngles(Lnet/minecraft/entity/passive/CatEntity;FFFFF)V")
    private void init(T catEntity, float f, float g, float h, float i, float j, CallbackInfo info)
    {
        if(animationState == 3) {
            SJKZ1Helper.CatDance(head, catEntity.age, h);

        }
    }
}
