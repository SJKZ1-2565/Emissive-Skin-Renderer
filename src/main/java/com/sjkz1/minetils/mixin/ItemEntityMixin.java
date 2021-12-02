package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.sjkz1.minetils.command.EntityDetector;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;


@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {


    public ItemEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
    	 this.setGlowing(EntityDetector.glow);
    	 super.tick();
    }

}
