package com.sjkz1.sjkz1misc.mixin;

import ca.weblite.objc.Client;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import com.sjkz1.sjkz1misc.utils.ClientInit;
import com.sjkz1.sjkz1misc.utils.KeyBindInit;
import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AxolotlModel.class)
public abstract class AxolotlDanceMixin<T extends Axolotl & LerpingModel> extends AgeableListModel<T>
{
    @Shadow
    private ModelPart body;

    AxolotlDanceMixin() {
        super();
    }

    @Inject(method = "setupAnim",at = @At("TAIL"))
    public void dance(T axolotlEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        if(ClientInit.dance) {
            if(axolotlEntity.getName().getString().equals("Mooky")) {
                SJKZ1Helper.axolotlDance(body, axolotlEntity.tickCount, h);
            }
        }
    }
}
