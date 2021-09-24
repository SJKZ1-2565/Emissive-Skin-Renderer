package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundInits {
    public static final ResourceLocation DRAGONBALL_ID = new ResourceLocation(Minetils.MOD_ID + ":dragonball");
    public static SoundEvent DRAGONBALL_SOUND_EVENT = new SoundEvent(DRAGONBALL_ID);
    public static void init()
    {		Registry.register(Registry.SOUND_EVENT, DRAGONBALL_ID, DRAGONBALL_SOUND_EVENT);
    }
}
