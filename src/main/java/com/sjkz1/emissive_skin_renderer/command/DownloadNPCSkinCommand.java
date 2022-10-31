package com.sjkz1.emissive_skin_renderer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.Player;

public class DownloadNPCSkinCommand {
    public DownloadNPCSkinCommand(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("npc-skin").executes(context -> {
            assert Minecraft.getInstance().player != null;
            var remotePlayerList = Minecraft.getInstance().level.getEntitiesOfClass(RemotePlayer.class, Minecraft.getInstance().player.getBoundingBox().inflate(1000));
            for (Player player : remotePlayerList) {
                ColorMatching.downloadSkinNew(player);
            }
            return 1;
        }));
    }
}
