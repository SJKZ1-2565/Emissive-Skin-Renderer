package com.sjkz1.sjkz1misc.mixin;

import com.google.common.collect.Maps;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Mixin(BossBarHud.class)
public class BossBarHudMixin {
    @Shadow
    private MinecraftClient client;
    @Shadow
     Map<UUID, ClientBossBar> bossBars = Maps.newLinkedHashMap();

    public BossBarHudMixin(MinecraftClient client) {
        this.client = client;

    }

    @Inject(method = "render",at = @At("TAIL"))
    public void render(MatrixStack matrixStack, CallbackInfo ci)
    {
        int i = this.client.getWindow().getScaledWidth();
        int j = 12;

        Iterator var4 = this.bossBars.values().iterator();
        ClientBossBar clientBossBar = (ClientBossBar)var4.next();
        int percent  = (int) (clientBossBar.getPercent() * 100);
        String text = "(" +  percent + "%)";
        int m = this.client.textRenderer.getWidth(text);
        int width = i / 2 - m / 2;
        int height = j;
        this.client.textRenderer.drawWithShadow(matrixStack, text, (float)width, (float)height, getColor());
    }

    public int getColor()
    {
        float ticks = ((float)(client.player.age % 20) + client.getTickDelta()) / 20.0F;
        Color color = Color.getHSBColor(ticks,0.9f,1);
        return SJKZ1Misc.CONFIG.getConfig().rainbowColor ? color.getRGB() : SJKZ1Misc.CONFIG.getConfig().color;
    }
}
