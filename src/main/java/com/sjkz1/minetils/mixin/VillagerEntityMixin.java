package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.VillagerDataContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity
        implements InteractionObserver,
        VillagerDataContainer {

    VillagerEntityMixin() {
        super(null, null);
    }

    @Override
    public boolean damage(DamageSource damageSource, float f) {
        Entity entity = damageSource.getAttacker();
        if (entity instanceof PlayerEntity && Minetils.CONFIG.main.IgnoreHittingVillager) {
            return false;
        }
        return super.damage(damageSource, f);
    }
}
