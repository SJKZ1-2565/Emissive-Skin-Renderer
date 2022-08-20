package boon4681.ColorUtils;

import java.awt.*;

public class DeltaE {
    public static double getDelta(Color c1, Color c2) {
        double[] l1 = new LabColor(c1).getLab();
        double[] l2 = new LabColor(c2).getLab();
        double deltaE = 0;
        for (int i = 0; i < 3; i++) {
            deltaE += Math.pow(l1[i] - l2[i], 2);
        }
        return Math.sqrt(deltaE);
    }
}
