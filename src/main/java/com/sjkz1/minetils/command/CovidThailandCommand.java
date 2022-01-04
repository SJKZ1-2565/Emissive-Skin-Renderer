package com.sjkz1.minetils.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.sjkz1.minetils.Minetils;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

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
            Formatting new_case_color = obj.get(k).getAsJsonObject().get("new_case").getAsInt() >= 100 ? Formatting.RED : Formatting.GOLD;
            Formatting province_color = obj.get(k).getAsJsonObject().get("province").getAsString().contains(Minetils.CONFIG.getConfig().provinceName) ? Formatting.GREEN : new_case_color;
            Formatting color = Minetils.CONFIG.getConfig().hightlightProvince ? province_color : new_case_color;
            if (!obj.isEmpty()) {
                MinecraftClient.getInstance().player.sendSystemMessage(Text.of(color + obj.get(k).getAsJsonObject().get("province").getAsString() + ": " + obj.get(k).getAsJsonObject().get("new_case").getAsInt()), UUID.randomUUID());
            }
            else
            {
                MinecraftClient.getInstance().player.sendSystemMessage(Text.of(Formatting.RED+"Object empty "+obj.isEmpty()),UUID.randomUUID());

            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        MinecraftClient.getInstance().player.sendSystemMessage(Text.of(Formatting.RED+"Object empty"),UUID.randomUUID());
        MinecraftClient.getInstance().player.sendSystemMessage(Text.of(Formatting.RED+e.getStackTrace().toString()),UUID.randomUUID());
    }
    return 1;
}

}
