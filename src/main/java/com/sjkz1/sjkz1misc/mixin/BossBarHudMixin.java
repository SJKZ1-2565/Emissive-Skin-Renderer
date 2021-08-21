package com.sjkz1.sjkz1misc.mixin;

import com.google.common.collect.Maps;
import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
    private static final Identifier UNICODE = new Identifier("uniform");

    public BossBarHudMixin(MinecraftClient client) {
        this.client = client;

    }

    @Inject(method = "render",at = @At("TAIL"))
    public void render(MatrixStack matrixStack, CallbackInfo ci)
    {
        int i = this.client.getWindow().getScaledWidth();


        Iterator var4 = this.bossBars.values().iterator();
        ClientBossBar clientBossBar = (ClientBossBar)var4.next();
        int percent  = (int) (clientBossBar.getPercent() * 100);
        String text = "(" +  percent + "%)";
        Text orderedText = Text.of(text);
        orderedText.getWithStyle(Style.EMPTY.withFont(UNICODE));
        int m = this.client.textRenderer.getWidth(text);
        int width = i / 4 - m / 4;
        int height = 12;
        matrixStack.push();
        matrixStack.scale(0.5F,0.5F,0.5F);
        TextRenderer textRenderer =  this.client.textRenderer;
        Screen.drawCenteredText(matrixStack,textRenderer,text,width,height,getColor());
        matrixStack.pop();
    }

    public int getColor()
    {
        float ticks = ((float)(client.player.age % 20) + client.getTickDelta()) / 20.0F;
        Color color = Color.getHSBColor(ticks,0.9f,1);
        return SJKZ1Misc.CONFIG.getConfig().rainbowColor ? color.getRGB() : SJKZ1Misc.CONFIG.getConfig().color;
    }
}
