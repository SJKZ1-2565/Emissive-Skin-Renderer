package com.sjkz1.emissive_skin_renderer;


import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.sjkz1.emissive_skin_renderer.command.DownloadNPCSkinCommand;
import com.sjkz1.emissive_skin_renderer.config.EmissiveSkinRendererConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class EmissiveSkinRenderer implements ModInitializer {
    public static final String MOD_ID = "emissive-skin-renderer";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static EmissiveSkinRendererConfig CONFIG;
    public static final List<String> SPECIAL_MEMBER = Lists.newCopyOnWriteArrayList();


    static {
        try {
            InputStream is = (new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/donate.txt")).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            SPECIAL_MEMBER.addAll(rd.lines().toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(EmissiveSkinRendererConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(EmissiveSkinRendererConfig.class).getConfig();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                new DownloadNPCSkinCommand(dispatcher));
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.getCurrentServer() != null) {
                var list = client.level.getEntitiesOfClass(Player.class, client.player.getBoundingBox().inflate(1000));
                for (Player player : list) {
                    System.out.println(player.getGameProfile().getId());
                }
            }
            if (client.player != null) {
                client.player.sendSystemMessage(Component.literal("[WARN] <Emissive Skin Renderer> is now better than old 20% :)").withStyle(ChatFormatting.GOLD));
            }
        });
    }

}

