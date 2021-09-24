package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindInit {
    public static void init()
    {
    Minetils.danceKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.sjkz1misc.start_dance",GLFW.GLFW_KEY_R, "sjkz1misc"));
        Minetils.showPost = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.sjkz1misc.showPost", GLFW.GLFW_KEY_G, "sjkz1misc"));
    }
}
