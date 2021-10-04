package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class IdentifierUtils {

    public static Identifier getSpeCapeTexture()
    {
        return new Identifier(Minetils.MOD_ID+":textures/entity/cape_" +  Minetils.CONFIG.getConfig().IdentifierOrdinal + ".png");
    }
}
