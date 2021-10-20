package com.sjkz1.minetils.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public abstract class ColorSliderWidget extends SliderWidget {
    public ColorSliderWidget(int i, int j, int k, int l, Text text, double d) {
        super(i, j, k, l, text, d);
    }
}
