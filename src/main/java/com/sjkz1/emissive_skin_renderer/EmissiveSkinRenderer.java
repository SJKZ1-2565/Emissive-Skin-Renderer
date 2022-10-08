package com.sjkz1.emissive_skin_renderer;


import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.sjkz1.emissive_skin_renderer.config.EmissiveSkinRendererConfig;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import com.sjkz1.emissive_skin_renderer.utils.EmissiveUtils;
import com.sjkz1.emissive_skin_renderer.utils.SJKZ1Helper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
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

    public static boolean hasTypedMsg = false;

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
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (handler.player != null) {
                if (!EmissiveUtils.GLOW_ORE_DIR.exists()) {
                    EmissiveUtils.GLOW_ORE_DIR.mkdirs();
                }
                SJKZ1Helper.runAsync(ColorMatching::MoveToResourceLoc);
                handler.player.sendSystemMessage(Component.literal("[WARN] <Emissive Skin Renderer> is now better than old 20% :)").withStyle(ChatFormatting.GOLD));
                handler.player.sendSystemMessage(Component.literal("<Emissive Skin Renderer> DO NOT type `img import`").withStyle(ChatFormatting.RED));
                EmissiveUtils.getImageFromBlock(Blocks.DIAMOND_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.COAL_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.REDSTONE_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.LAPIS_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.EMERALD_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.GOLD_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.COPPER_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.DEEPSLATE_DIAMOND_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.DEEPSLATE_REDSTONE_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.DEEPSLATE_LAPIS_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.DEEPSLATE_EMERALD_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.DEEPSLATE_GOLD_ORE);
                EmissiveUtils.getImageFromBlock(Blocks.DEEPSLATE_COPPER_ORE);
                EmissiveUtils.getColorWithGreaterThan();
                EmissiveUtils.getColorWithLessThan();
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (handler.player != null) {
                hasTypedMsg = false;
            }
        });
        ServerTickEvents.END_WORLD_TICK.register(client -> {
            if (client != null) {
                Minecraft.getInstance().gui.getChat().getRecentChat().forEach(s -> {
                    if (s.contains("img import") && !hasTypedMsg) {
                        SJKZ1Helper.runAsync(EmissiveUtils::MoveOreImageResourceLocation);
                        hasTypedMsg = true;
                    }
                });
            }
        });
    }
}

