package boon4681.ColorUtils;

import java.awt.*;

public class LabColor {
    private final Color RGB;
    private double L = 0;
    private double A = 0;
    private double B = 0;

    LabColor(int r, int g, int b) {
        this(new Color(r, g, b));
    }

    LabColor(Color color) {
        this.RGB = color;
        this.convertor();
    }

    private double leap(double t) {
        if (t > 0.008856) {
            return Math.pow(t, 1 / 3.0);
        } else {
            return 7.787 * t + 16 / 116.0;
        }
    }

    private void convertor() {
        double[] aRGB = new double[]{RGB.getRed(), RGB.getGreen(), RGB.getBlue()};
        for (int i = 0; i < aRGB.length; i++) {
            aRGB[i] = aRGB[i] / 255;
        }
        double a = aRGB[0] * 0.412453 + aRGB[1] * 0.357580 + aRGB[2] * 0.180423;
        double b = aRGB[0] * 0.212671 + aRGB[1] * 0.715160 + aRGB[2] * 0.072169;
        double c = aRGB[0] * 0.019334 + aRGB[1] * 0.119193 + aRGB[2] * 0.950227;
        a = a / 0.950456;
        c = c / 1.088754;
        a = leap(a);
        b = leap(b);
        c = leap(c);
        this.L = 116 * b - 16;
        this.A = 500 * (a - b);
        this.B = 200 * (b - c);
    }

    public double[] getLab() {
        return new double[]{this.L, this.A, this.B};
    }

    public double getL() {
        return L;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }
}