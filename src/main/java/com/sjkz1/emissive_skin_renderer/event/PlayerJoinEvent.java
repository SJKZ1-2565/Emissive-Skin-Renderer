package com.sjkz1.emissive_skin_renderer.event;


import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class PlayerJoinEvent {

    public static void join(ServerGamePacketListenerImpl serverGamePacketListener, PacketSender packetSender, MinecraftServer minecraftServer) {
        ClientPacketListener clientPacketListener = Minecraft.getInstance().player.connection;
        clientPacketListener.getOnlinePlayers().forEach(playerInfo -> {
            System.out.println(playerInfo.getProfile().getName());
        });
    }
}
