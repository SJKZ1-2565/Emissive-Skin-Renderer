package com.sjkz1.minetils.utils;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class DiscordMemberThread extends Thread {

    @Override
    public void run() {
        try {
            URL url = new URL("https://discord.com/api/guilds/675288690658115588/widget.json");
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            SpecialMemberScreen.ONLINE_USER = JsonParser.parseReader(in).getAsJsonObject().get("presence_count").getAsInt();
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}
