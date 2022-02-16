package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.HTTPSCodeThread;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    @Inject(method = "getLeftText", at = @At("RETURN"))
    public void render(CallbackInfoReturnable<List<String>> cir) throws IOException {
        SJKZ1Helper.runSlowAsync(() ->
                new HTTPSCodeThread().start());
         Minetils.getRightText(cir.getReturnValue());
    }

}
