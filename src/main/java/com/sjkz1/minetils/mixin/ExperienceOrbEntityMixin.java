package com.sjkz1.minetils.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sjkz1.minetils.Minetils;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Shadow
    private int amount;

    @Shadow protected abstract int getMendingRepairAmount(int i);

    @Inject(method = "repairPlayerGears",at = @At("HEAD"))
    public void renderExp(PlayerEntity playerEntity, int i, CallbackInfoReturnable<Integer> cir)
    {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING, playerEntity, ItemStack::isDamaged);
        if (entry != null && Minetils.CONFIG.getConfig().displayMendingRepairAmount)
        {
            ItemStack itemStack = entry.getValue();
            int j = Math.min(this.getMendingRepairAmount(this.amount), itemStack.getDamage());
            String amount = Formatting.GREEN + String.valueOf(j);
            playerEntity.sendMessage(Text.of(Formatting.BOLD.toString() + Formatting.YELLOW + "Mending Repair amount "  + amount),true);
        }

    }


}
