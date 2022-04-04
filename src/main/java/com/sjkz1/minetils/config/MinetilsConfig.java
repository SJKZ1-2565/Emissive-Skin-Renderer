package com.sjkz1.minetils.config;


import com.sjkz1.minetils.Minetils;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Minetils.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/redstone_block.png")
public class MinetilsConfig implements ConfigData {



    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public Main main;


    public MinetilsConfig() {
        this.main = new Main();
    }

    public static class Main {
        public boolean showHealthStatus = false;
        public boolean glowingSkin = true;
        public boolean IgnoreHittingVillager = true;
        public boolean switchingArmorElytra = true;
        public boolean healthStatusRainbowColor = false;
        public boolean showTamedHorse = false;
        public boolean disableBlockParticle = false;
        public boolean enableEatingAnim = true;
        public boolean SpecialCape = false;
        public boolean disableHurtCamera = false;
        public boolean manualSkinEditor = true;
        public boolean enableMobSpawnerDelay = true;
        public int healthStatusColor = 0x00ff20;
        public int IdentifierOrdinal = 1;
        public int yPositionHorseDisplay = 2;
        public int yPositionHealthStatus = 3;
        public double palletsRate = 100D;
    }
}
