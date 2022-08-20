package boon4681.ColorUtils;

import java.awt.*;

public class ColorMixer {
    private static int a(int c1, int c2) {
        double c = ((1 - (1 - c1 / 255) * (1 - c2 / 255)) * 255);
        return (int) c;
    }

    public static Color mix(Color c1, Color c2) {
        return new Color(a(c1.getRed(), c2.getRed()), a(c1.getGreen(), c2.getGreen()), a(c1.getBlue(), c2.getBlue()));
    }
}
