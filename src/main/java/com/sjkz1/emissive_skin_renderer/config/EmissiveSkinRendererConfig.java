package com.sjkz1.emissive_skin_renderer.config;


import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = EmissiveSkinRenderer.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/sand.png")
public class EmissiveSkinRendererConfig implements ConfigData {


    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public Main main;


    public EmissiveSkinRendererConfig() {
        this.main = new Main();
    }

    public static class Main {
        public boolean glowingSkin = true;
        public double palletsRate = 100D;
        public boolean glowingHorseArmor = false;
        public boolean renderPlayerNameInThirdPerson = true;
    }
}
