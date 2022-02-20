package com.sjkz1.minetils.gui.screen;

import java.io.IOException;
import java.util.Objects;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.widget.ColorSliderWidget;
import com.sjkz1.minetils.render.Player;
import com.sjkz1.minetils.utils.ColorMatching;

import com.sjkz1.minetils.utils.SJKZ1Helper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class SkinEditorScreen extends Screen {
    protected SkinEditorScreen(Text text) {
        super(text);
    }
    private float playerXRot = 0;

    @Override
    protected void init() {
        int j = this.height / 4 + 48;
        this.addDrawableChild(new ColorSliderWidget(this.width / 2 - 120, j + 52 + 12, 98, 20, Text.of("Delete Rate: " + Minetils.CONFIG.getConfig().palletsRate), Minetils.CONFIG.getConfig().palletsRate) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of("Delete Rate: " + Minetils.CONFIG.getConfig().palletsRate));
            }

            @Override
            protected void applyValue() {
                Minetils.CONFIG.getConfig().palletsRate = MathHelper.floor(MathHelper.clampedLerp(0.0D, 100.0D, this.value));
                try {
                    Minetils.CONFIG.saveConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.addDrawableChild(new ColorSliderWidget(this.width / 2 - 120, j + 72 + 12, 98, 20, Text.of("Delete Rate X: " + Minetils.CONFIG.getConfig().deleteRateX), Minetils.CONFIG.getConfig().palletsRate) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of("Delete Rate X: " + Minetils.CONFIG.getConfig().deleteRateX));
            }

            @Override
            protected void applyValue() {
                Minetils.CONFIG.getConfig().deleteRateX = MathHelper.floor(MathHelper.clampedLerp(2.0D, 4.0D, this.value));
                try {
                    Minetils.CONFIG.saveConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.addDrawableChild(new ColorSliderWidget(this.width / 2 - 120, j + 92 + 12, 98, 20, Text.of("Delete Rate Y: " + Minetils.CONFIG.getConfig().deleteRateY), Minetils.CONFIG.getConfig().palletsRate) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of("Delete Rate Y: " + Minetils.CONFIG.getConfig().deleteRateY));
            }

            @Override
            protected void applyValue() {
                Minetils.CONFIG.getConfig().deleteRateY = MathHelper.floor(MathHelper.clampedLerp(2.0D, 4.0D, this.value));
                try {
                    Minetils.CONFIG.saveConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
            this.addDrawableChild(new ButtonWidget(this.width / 2 + 20, j + 52 + 12, 98, 20, Text.of("Create Skin"), (buttonWidget) -> SJKZ1Helper.runAsync(ColorMatching::createGlowingSkinImage)));
    }


    @Override
    public void render(MatrixStack matrixStack, int i, int j, float f) {
        playerXRot -= 0.15 * f;
        if (playerXRot <= -179.85) {
            playerXRot = 180;
        }
        renderBackground(matrixStack);
        SpecialMemberScreen.renderEntityInInventory(this.width / 2 ,this.height / 2 + 20,  75,playerXRot,0,new Player(Objects.requireNonNull(this.client).world, Objects.requireNonNull(this.client.player).getGameProfile()));
        super.render(matrixStack, i, j, f);
    }
}
