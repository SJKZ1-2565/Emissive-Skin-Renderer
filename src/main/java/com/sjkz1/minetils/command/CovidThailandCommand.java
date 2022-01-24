package com.sjkz1.minetils.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Stream;

public class CovidThailandCommand {
    public CovidThailandCommand(CommandDispatcher<FabricClientCommandSource> dispatcher)
{
    var node = dispatcher.register(ClientCommandManager.literal("covid-th").executes(requirement -> CovidThailandCommand.print()));
}
    private static int print()
{
    URL url;
    try {
        url = new URL("https://covid19.ddc.moph.go.th/api/Cases/today-cases-by-provinces");
        InputStreamReader reader1 = new InputStreamReader(url.openStream());
        JsonArray obj = JsonParser.parseReader(reader1).getAsJsonArray();
        for (int k = 0; k < obj.size(); k++) {
            int finalK = k;
            Stream.of(obj).filter(covid -> covid.get(finalK).getAsJsonObject().get("new_case").getAsInt() != 0).forEach(covid -> MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(Formatting.RED + covid.getAsJsonArray().get(finalK).getAsJsonObject().get("province").getAsString() + ": " + covid.getAsJsonArray().get(finalK).getAsJsonObject().get("new_case").getAsInt())));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return 1;
}

}
