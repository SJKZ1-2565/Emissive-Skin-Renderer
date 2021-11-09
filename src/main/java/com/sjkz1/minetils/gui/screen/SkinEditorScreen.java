package com.sjkz1.minetils.gui.screen;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.widget.ColorSliderWidget;
import com.sjkz1.minetils.utils.ColorMatching;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;

public class SkinEditorScreen extends Screen {
    protected SkinEditorScreen(Text text) {
        super(text);
    }

    @Override
    protected void init() {
        int j = this.height / 4 + 48;
        this.addDrawableChild(new ColorSliderWidget(this.width / 2 - 100, j + 52 + 12, 98, 20, Text.of("Delete Rate: " + Minetils.CONFIG.getConfig().palletsRate), Minetils.CONFIG.getConfig().palletsRate) {
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
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 20, j + 52 + 12, 98, 20, Text.of("Create skin"), (buttonWidget) -> ColorMatching.createGlowingSkinImage()));

    }
}
