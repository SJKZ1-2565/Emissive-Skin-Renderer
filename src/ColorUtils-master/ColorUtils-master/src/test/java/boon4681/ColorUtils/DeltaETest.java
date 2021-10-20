package boon4681.ColorUtils;

import org.junit.jupiter.api.Test;

import java.awt.*;

public class DeltaETest {
    @Test
    void test1() {
        System.out.println(DeltaE.getDelta(Color.MAGENTA,Color.GREEN));
    }
    @Test
    void test2(){
        System.out.println(ColorMixer.mix(Color.MAGENTA,Color.GREEN));
    }
}
