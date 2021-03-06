package com.sjkz1.emissive_skin_renderer;


import com.google.common.collect.Lists;
import com.sjkz1.emissive_skin_renderer.config.EmissiveSkinRendererConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class EmissiveSkinRenderer implements ModInitializer {
    public static final String MOD_ID = "emissive-skin-renderer";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
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
    }
}