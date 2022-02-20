package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ClientInit {

    public static boolean dance = false;


    public static void tick(MinecraftClient client) {

        if (client.player != null) {
            SJKZ1Helper.runAsync(() ->
                    new DiscordMemberThread().start());
        }
        if (Minetils.danceKey.wasPressed()) {
            dance = !dance;
            client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
            if (dance) {
                client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
            }
        }
        if (Minetils.openModScreen.wasPressed()) {
            if (!Minetils.CONFIG.getConfig().manualSkinEditor) {
                client.setScreen(new SpecialMemberScreen(Text.of("")));
            } else {
                client.inGameHud.getChatHud().addMessage(Text.of(Formatting.RED + "You must have to disable manual skin editor!"));
            }

            if (Minetils.showPost.wasPressed()) {
                assert client.player != null;
                int i = (int) client.player.getX();
                int j = (int) client.player.getY();
                int k = (int) client.player.getZ();
                String pos = "X:" + i + " Y:" + j + " Z:" + k;
                String NetherPos = "Nether position X:" + i / 8 + " Y:" + j + " Z:" + k / 8;
                String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j + " Z:" + k * 8;


                client.inGameHud.getChatHud().addMessage(Text.of(pos));
                if (client.player.world.getDimension().isPiglinSafe()) {
                    client.inGameHud.getChatHud().addMessage(Text.of(OverWorldPose));
                } else {
                    client.inGameHud.getChatHud().addMessage(Text.of(NetherPos));
                }
            }
        }
    }

    public static TypedActionResult<ItemStack> test(PlayerEntity playerEntity, World world, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        EquipmentSlot equipmentSlot = LivingEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = playerEntity.getEquippedStack(equipmentSlot);
        if ((itemStack2.isEmpty() || (itemStack2.getItem() instanceof ArmorItem || itemStack2.getItem() instanceof ElytraItem) && Minetils.CONFIG.getConfig().SwapArmorAndElytra)) {
            playerEntity.equipStack(equipmentSlot, itemStack.copy());
            if (!world.isClient()) {
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            }

            playerEntity.setStackInHand(hand,itemStack2);
            if(!world.isClient() && !itemStack2.getItem().equals(Items.AIR)) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Switching from " + itemStack.getItem().getName().getString() + " to " + itemStack2.getItem().getName().getString()));
            }            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }
}

