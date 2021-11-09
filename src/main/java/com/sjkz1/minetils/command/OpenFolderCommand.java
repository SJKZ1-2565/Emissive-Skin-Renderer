package com.sjkz1.minetils.command;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;


public class OpenFolderCommand {


    public OpenFolderCommand(CommandDispatcher<FabricClientCommandSource> dispatcher)
{
    var node = dispatcher.register(ClientCommandManager.literal("datapacks-folder").executes(requirement -> OpenFolderCommand.openSFolder()));
}

    private static int openSFolder()
    {
//       Util.getOperatingSystem().open(new File(WorldSavePath.DATAPACKS.getRelativePath()));
        return 1;
    }
}
