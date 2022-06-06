package com.sjkz1.minetils;


import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sjkz1.minetils.config.MinetilsConfig;
import com.sjkz1.minetils.utils.ClientInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.*;
import java.net.*;
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
            SPECIAL_MEMBER.addAll(rd.lines().toList());
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
        UseItemCallback.EVENT.register(new Identifier(Minetils.MOD_ID, "switching_item"), this::doSwitchingItem);
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
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("google").then(CommandManager.argument("message", MessageArgumentType.message()).executes(context -> {
                Text text = MessageArgumentType.getMessage(context, "message");
                String url = "https://www.google.com/search?q=" + text.getString().replace(" ", "+");
                System.out.println(url);
                MinecraftClient.getInstance().executeSync(() ->
                {
                    try {
                        Util.getOperatingSystem().open(new URL(url));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                });
                return 1;

            })));
        });
    }

    private TypedActionResult<ItemStack> doSwitchingItem(PlayerEntity playerEntity, World world, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        EquipmentSlot equipmentSlot = LivingEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = playerEntity.getEquippedStack(equipmentSlot);
        if (Minetils.CONFIG.main.switchingArmorElytra) {
            if (itemStack.isItemEqual(Items.ELYTRA.getDefaultStack())) {
                this.switching(itemStack, itemStack2, playerEntity, equipmentSlot, world, hand);
                return TypedActionResult.pass(itemStack);
            } else if (itemStack.getItem() instanceof ArmorItem) {
                this.switching(itemStack, itemStack2, playerEntity, equipmentSlot, world, hand);
                return TypedActionResult.pass(itemStack);
            }
        }
        return TypedActionResult.pass(itemStack);
    }


    private void switching(ItemStack itemStack, ItemStack itemStack2, PlayerEntity playerEntity, EquipmentSlot equipmentSlot, World world, Hand hand) {
        if (itemStack2.getItem() instanceof ArmorItem || itemStack2.getItem() instanceof ElytraItem || itemStack2.isEmpty()) { //Checking Current Player Equipped Item in Chest Slot.
            playerEntity.equipStack(equipmentSlot, itemStack.copy());//Equip an item.
            if (!world.isClient()) {
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));//Increase Stats (server-side)
            }
            playerEntity.setStackInHand(hand, itemStack2);//Take Item from Chest slot into player hand
            MinecraftClient.getInstance().player.swingHand(hand);//Swing hand player what are you looking for?
            TypedActionResult.success(itemStack);//Success interact
        }
    }
}