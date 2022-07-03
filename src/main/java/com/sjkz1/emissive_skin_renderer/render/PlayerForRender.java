package com.sjkz1.emissive_skin_renderer.render;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PlayerForRender extends AbstractClientPlayer {


    public PlayerForRender(ClientLevel clientLevel, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(clientLevel, gameProfile, profilePublicKey);
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return false;
    }


    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }


    @Override
    public ItemStack getItemInHand(InteractionHand interactionHand) {
        return ItemStack.EMPTY;
    }


    @Override
    public boolean shouldShowName() {
        return false;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }
}
