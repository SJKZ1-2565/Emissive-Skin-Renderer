package com.sjkz1.emissive_skin_renderer.mixin;

import net.minecraft.client.resources.SplashManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Mixin(SplashManager.class)
public abstract class SplashManagerMixin extends SimplePreparableReloadListener<List<String>> {

    @Shadow
    @Final
    private static RandomSource RANDOM;

    @Inject(method = "getSplash", at = @At("TAIL"), cancellable = true)
    public void injectMyBirthDay(CallbackInfoReturnable<String> cir) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.MONTH) + 1 == 6 && calendar.get(Calendar.DATE) == 3) {
            cir.setReturnValue("§dHappy birthday SJKZ1!");
        }
        if (RANDOM.nextInt(1000) == 0) {
            cir.setReturnValue("Thanks to boon4681");
        }
        if (RANDOM.nextInt(1000) == 1) {
            cir.setReturnValue("Visit boon4681 GitHub!");
        }
    }
}
