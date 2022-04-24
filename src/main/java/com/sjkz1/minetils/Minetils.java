package com.sjkz1.minetils;


import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sjkz1.minetils.config.MinetilsConfig;
import com.sjkz1.minetils.utils.ClientInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;


public class Minetils implements ModInitializer {
    public static final String MOD_ID = "minetils";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static MinetilsConfig CONFIG;
    public static final List<String> SPECIAL_MEMBER = Lists.newCopyOnWriteArrayList();

    public static KeyBinding showPost;

    static {
        try {
            InputStream is = (new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/donate.txt")).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            for (String id : rd.lines().toList()) {
                SPECIAL_MEMBER.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(MinetilsConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(MinetilsConfig.class).getConfig();

        showPost = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minetils.showPost", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, Minetils.MOD_ID));
        ClientTickEvents.END_CLIENT_TICK.register(ClientInit::tick);
        ClientPlayConnectionEvents.JOIN.register(ClientInit::login);

        //Command
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal("server-folder").executes(context -> {
            if (!dedicated) {
                String path = MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().toFile() + "\\" + MinecraftClient.getInstance().getServer().getSaveProperties().getLevelName();
                Path paths = Path.of(path);
                Util.getOperatingSystem().open(paths.toFile());
                return 1;
            }
            return 0;
        })));
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal("covid-th").executes(context -> {
            URL url;
            try {
                url = new URL("https://covid19.ddc.moph.go.th/api/Cases/today-cases-by-provinces");
                InputStreamReader reader1 = new InputStreamReader(url.openStream());
                JsonArray obj = JsonParser.parseReader(reader1).getAsJsonArray();
                for (int k = 0; k < obj.size(); k++) {
                    int finalK = k;
                    Stream.of(obj).filter(covid -> covid.get(finalK).getAsJsonObject().get("new_case").getAsInt() != 0).forEach(covid -> MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(Formatting.RED + String.valueOf(finalK + 1) + ". " + covid.getAsJsonArray().get(finalK).getAsJsonObject().get("province").getAsString() + ": " + covid.getAsJsonArray().get(finalK).getAsJsonObject().get("new_case").getAsInt())));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        })));
    }
}