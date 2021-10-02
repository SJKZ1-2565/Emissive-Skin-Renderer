package com.sjkz1.minetils.command;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.lwjgl.system.CallbackI;

import java.io.File;


public class OpenFolderCommand {


    public OpenFolderCommand(CommandDispatcher<FabricClientCommandSource> dispatcher)
{
    var node = dispatcher.register(ClientCommandManager.literal("open-folder").executes(requirement -> OpenFolderCommand.openSFolder()));
}

    private static int openSFolder()
    {
       Util.getOperatingSystem().open(new File(WorldSavePath.DATAPACKS.getRelativePath()));
        return 1;
    }
}
