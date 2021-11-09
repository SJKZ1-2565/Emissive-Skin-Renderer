package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.sjkz1.minetils.Minetils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
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
     * @author SJKZ1
     */
    @Overwrite
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = playerEntity.getEquippedStack(equipmentSlot);
        if ((itemStack2.isEmpty() || (itemStack2.getItem() instanceof ArmorItem || itemStack2.getItem() instanceof ElytraItem) && Minetils.CONFIG.getConfig().SwapArmorAndElytra)) {
            playerEntity.equipStack(equipmentSlot, itemStack.copy());
            if (!world.isClient()) {
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }

            playerEntity.setStackInHand(hand,itemStack2);
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

}
