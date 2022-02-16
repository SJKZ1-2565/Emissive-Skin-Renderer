package com.sjkz1.minetils.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.sjkz1.minetils.Minetils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

@Mixin(ArmorItem.class)
public class ArmorItemMixin extends Item implements Wearable {
     ArmorItemMixin( ) {
        super(null);
    }

	/**
     * @author
     */
    @Overwrite
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        EquipmentSlot equipmentSlot = LivingEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = playerEntity.getEquippedStack(equipmentSlot);
        if ((itemStack2.isEmpty() || (itemStack2.getItem() instanceof ArmorItem || itemStack2.getItem() instanceof ElytraItem) && Minetils.CONFIG.getConfig().SwapArmorAndElytra)) {
            playerEntity.equipStack(equipmentSlot, itemStack.copy());
            if (!world.isClient()) {
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }

            playerEntity.setStackInHand(hand,itemStack2);
            if(!world.isClient && !itemStack2.getItem().equals(Items.AIR)) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Switching from " + itemStack.getItem().getName().getString() + " to " + itemStack2.getItem().getName().getString()));
            }
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

}
