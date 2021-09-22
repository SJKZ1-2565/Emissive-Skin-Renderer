package com.sjkz1.sjkz1misc.command;

import java.io.File;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;


public class OpenFolderCommand {
    public OpenFolderCommand(CommandDispatcher<FabricClientCommandSource> dispatcher)
{
    var node = dispatcher.register(ClientCommandManager.literal("open-folder").executes(requirement -> OpenFolderCommand.openSFolder()));
}

    private static int openSFolder()
    {
        Util.OS.UNKNOWN.openFile(new File(FabricLoader.getInstance().getGameDirectory(), "saves"));
        return 1;
    }
}
