package com.sjkz1.minetils.config;


import com.sjkz1.minetils.Minetils;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Minetils.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/sculk.png")
public class MinetilsConfig implements ConfigData {


    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public Main main;


    public MinetilsConfig() {
        this.main = new Main();
    }

    public static class Main {
        public boolean glowingSkin = true;
        public double palletsRate = 100D;
    }
}
