package com.sjkz1.minetils;


import com.google.common.collect.Lists;
import com.sjkz1.minetils.config.MinetilsConfig;
import com.sjkz1.minetils.utils.DiscordMemberThread;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Minetils implements ModInitializer {
    public static final String MOD_ID = "minetils";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static MinetilsConfig CONFIG;
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
        AutoConfig.register(MinetilsConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(MinetilsConfig.class).getConfig();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                SJKZ1Helper.runAsync(() -> {
                    DiscordMemberThread discordMemberThread = new DiscordMemberThread();
                    discordMemberThread.start();
                });
            }
        });
    }
}