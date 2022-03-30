package com.sjkz1.minetils.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;


public class OpenFolderCommand {


    public OpenFolderCommand(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("datapacks_folder").executes(requirement -> OpenFolderCommand.openSFolder()));
    }

    private static int openSFolder() {
        Util.getOperatingSystem().open(MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().toFile());
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(Formatting.BOLD.toString() + Formatting.YELLOW + "Open " + MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().toFile()));
        return 1;
    }
}
