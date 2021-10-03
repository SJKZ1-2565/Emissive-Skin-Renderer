package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class IdentifierUtils {

    public static int IDENTIFIER_ORDINAL = 1;
    public static boolean SPECIAL_IDENTIFIER;

    public static Identifier getSpeCapeTexture()
    {
        return new Identifier(Minetils.MOD_ID+":textures/entity/cape_" + IDENTIFIER_ORDINAL + ".png");
    }
}
