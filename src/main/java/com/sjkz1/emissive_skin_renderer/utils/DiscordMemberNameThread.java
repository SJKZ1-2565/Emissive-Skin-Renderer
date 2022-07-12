package com.sjkz1.emissive_skin_renderer.utils;

import com.google.gson.*;
import com.sjkz1.emissive_skin_renderer.gui.screen.SpecialMemberScreen;
import net.minecraft.ChatFormatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class DiscordMemberNameThread extends Thread {

    @Override
    public void run() {
        try {
            URL url = new URL("https://discord.com/api/guilds/675288690658115588/widget.json");
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            JsonArray jsonArray = JsonParser.parseReader(in).getAsJsonObject().get("members").getAsJsonArray();
            for (JsonElement jsonArray1 : jsonArray) {
                JsonObject jsonObject = (JsonObject) jsonArray1;
                String username = jsonObject.get("username").getAsString();
                String game = "";
                if (jsonObject.has("game")) {
                    game = jsonObject.get("game").getAsJsonObject().get("name").getAsString();
                }
                String isPlayingString = jsonObject.has("game") ? " is playing " : "";
                String finalString = username + isPlayingString + game;
                if (!finalString.contains("bot") && !finalString.equals("nSys 2") && !finalString.equals("MEE6") && !finalString.equals("Linkie")) {
                    SpecialMemberScreen.USERNAME.add(finalString);
                }
                String status = jsonObject.get("status").getAsString();
            }
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}
