package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundInits {
    public static final Identifier DRAGONBALL_ID = new Identifier(Minetils.MOD_ID + ":dragonball");
    public static SoundEvent DRAGONBALL_SOUND_EVENT = new SoundEvent(DRAGONBALL_ID);
    public static void init()
    {		Registry.register(Registry.SOUND_EVENT, DRAGONBALL_ID, DRAGONBALL_SOUND_EVENT);
    }
}
