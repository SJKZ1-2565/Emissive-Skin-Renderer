package com.sjkz1.minetils;


import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.InputConstants;
import com.sjkz1.minetils.config.MinetilsConfig;
import com.sjkz1.minetils.utils.ClientInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
    public static KeyMapping showPost;

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

        showPost = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.minetils.showPost", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, Minetils.MOD_ID));
        ClientTickEvents.END_CLIENT_TICK.register(ClientInit::tick);
        UseItemCallback.EVENT.register(new ResourceLocation(Minetils.MOD_ID, "switching_item"), this::doSwitchingItem);
        //Command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("server-folder").executes(context -> {
            String path = Minecraft.getInstance().getLevelSource().getBaseDir().toFile() + "\\" + Minecraft.getInstance().getCurrentServer().getResourcePackStatus().getName();
            Path paths = Path.of(path);
            Util.getPlatform().openFile(paths.toFile());
            return 1;
        })));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(ClientCommandManager.literal("covid-th").executes(context -> {
            URL url;
            try {
                url = new URL("https://covid19.ddc.moph.go.th/api/Cases/today-cases-by-provinces");
                InputStreamReader reader1 = new InputStreamReader(url.openStream());
                JsonArray obj = JsonParser.parseReader(reader1).getAsJsonArray();
                for (int k = 0; k < obj.size(); k++) {
                    int finalK = k;
                    Stream.of(obj).filter(covid -> covid.get(finalK).getAsJsonObject().get("new_case").getAsInt() != 0).forEach(covid -> Minecraft.getInstance().gui.getChat().addMessage(Component.literal(String.valueOf(finalK + 1) + ". " + covid.getAsJsonArray().get(finalK).getAsJsonObject().get("province").getAsString() + ": " + covid.getAsJsonArray().get(finalK).getAsJsonObject().get("new_case").getAsInt()).withStyle(ChatFormatting.RED)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        })));
    }

    private InteractionResultHolder<ItemStack> doSwitchingItem(Player player, Level level, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        EquipmentSlot equipmentSlot = LivingEntity.getEquipmentSlotForItem(itemStack);
        ItemStack itemStack2 = player.getItemBySlot(equipmentSlot);
        if (Minetils.CONFIG.main.switchingArmorElytra) {
            if (itemStack.is(Items.ELYTRA)) {
                this.switching(itemStack, itemStack2, player, equipmentSlot, level, interactionHand);
                return InteractionResultHolder.pass(itemStack);
            } else if (itemStack.getItem() instanceof ArmorItem) {
                this.switching(itemStack, itemStack2, player, equipmentSlot, level, interactionHand);
                return InteractionResultHolder.pass(itemStack);
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private void switching(ItemStack itemStack, ItemStack itemStack2, Player playerEntity, EquipmentSlot equipmentSlot, Level world, InteractionHand hand) {
        if (itemStack2.getItem() instanceof ArmorItem || itemStack2.getItem() instanceof ElytraItem || itemStack2.isEmpty()) { //Checking Current Player Equipped Item in Chest Slot.
            playerEntity.setItemSlot(equipmentSlot, itemStack.copy());//Equip an item.
            if (!world.isClientSide()) {
                playerEntity.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));//Increase Stats (server-side)
            }
            playerEntity.setItemInHand(hand, itemStack2);//Take Item from Chest slot into player hand
            playerEntity.swing(hand);//Swing hand player what are you looking for?
            playerEntity.level.playSound(playerEntity, playerEntity.getOnPos(), itemStack2.getEquipSound(), SoundSource.PLAYERS, 1.0f, 1.0f);
            InteractionResultHolder.success(itemStack);//Success interact
        }
    }
}