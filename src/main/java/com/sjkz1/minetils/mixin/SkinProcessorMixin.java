package com.sjkz1.minetils.mixin;

import net.minecraft.client.realms.util.SkinProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Mixin(SkinProcessor.class)
public class SkinProcessorMixin {

    @Inject(method = "process",at = @At(value = "INVOKE",target = "Ljava/awt/image/BufferedImage;getGraphics()Ljava/awt/Graphics;",shift = At.Shift.AFTER))
    public void createImage(BufferedImage bufferedImage, CallbackInfoReturnable<BufferedImage> cir)
    {
        try {
            ImageIO.write(bufferedImage, "png", new File("C:\\Users\\USER\\Desktop\\Modding\\SJKZ1misc\\SJKZ1Misc 1.17.1\\src\\main\\resources\\assets\\minetils\\textures\\entity\\downloaded_skin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
