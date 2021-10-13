package com.sjkz1.minetils.mixin;

import net.minecraft.client.render.SkyProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {

    @Inject(method = "getCloudsHeight",at = @At(value = "HEAD"), cancellable = true)
    public void CloudsLevel(CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(192.00F);
    }
}
