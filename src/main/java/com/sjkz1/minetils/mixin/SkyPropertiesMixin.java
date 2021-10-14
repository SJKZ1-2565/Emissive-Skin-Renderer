package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.utils.console;
import net.minecraft.client.render.SkyProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//https://github.com/KingCyrus20/Prima-Materia/blob/7d963277da71ff2ea0072d2a3e06952116ab3e64/src/main/java/io/meltec/prima/mixin/SkyPropertiesMixin.java
@Mixin(SkyProperties.class)
public abstract class SkyPropertiesMixin{

    @Accessor("skyType")
    abstract SkyProperties.SkyType getSkyType();

    @SuppressWarnings("SameParameterValue")
    @Mutable
    @Accessor("cloudsHeight")
    abstract void setCloudsHeight(float cloudsHeight);

    @Inject(method = "<init>(FZLnet/minecraft/client/render/SkyProperties$SkyType;ZZ)V", at = @At("TAIL"))
    private void SkyProperties(float cloudsHeight, boolean alternateSkyColor, SkyProperties.SkyType skyType, boolean brightenLighting, boolean darkened, CallbackInfo ci) {
        if (getSkyType() == SkyProperties.SkyType.NORMAL)
        {
            setCloudsHeight(192f);
            console.log("YEET");
        }
    }
}
