package com.sjkz1.sjkz1misc.mixin;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.AxolotlEntityModel;
import net.minecraft.entity.AngledModelEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AxolotlEntityModel.class)
public abstract class AxolotlDanceMixin<T extends AxolotlEntity & AngledModelEntity> extends AnimalModel<T>
{


    @Shadow @Final private ModelPart head;

    @Shadow @Final private ModelPart body;

    AxolotlDanceMixin() {
        super();
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/passive/AxolotlEntity;FFFFF)V",at = @At("TAIL"))
    public void dance(T axolotlEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        if(SJKZ1Misc.dance) {
            if(axolotlEntity.getName().getString().equals("Mooky")) {
                SJKZ1Helper.axolotlDance(this.head, this.body, axolotlEntity.age, h);
            }
        }
    }
}
