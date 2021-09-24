package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ArmorItem.class)
public class ArmorItemMixin extends Item implements Wearable {
     ArmorItemMixin( ) {
        super(null);
    }

    /**
     * @author SJKZ1
     */
    @Overwrite
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
        ItemStack itemStack2 = playerEntity.getItemBySlot(equipmentSlot);
        if (itemStack2.isEmpty() || (itemStack2.getItem() instanceof ArmorItem || itemStack2.getItem() instanceof ElytraItem && Minetils.CONFIG.getConfig().SwapArmorAndElytra)){
            playerEntity.setItemSlot(equipmentSlot, itemStack.copy());
            if (!world.isClientSide()) {
                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }


            playerEntity.setItemInHand(hand,itemStack2);
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

}
