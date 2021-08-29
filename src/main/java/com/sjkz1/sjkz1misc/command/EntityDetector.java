package com.sjkz1.sjkz1misc.command;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class EntityDetector {
public static boolean glow;
    public EntityDetector(CommandDispatcher<FabricClientCommandSource> dispatcher)
{
    var node = dispatcher.register(ClientCommandManager.literal("entity_detect").executes(requirement -> EntityDetector.setGlowing()));
}

    private static int setGlowing()
    {
        glow = !glow;
        return 1;
    }
}
