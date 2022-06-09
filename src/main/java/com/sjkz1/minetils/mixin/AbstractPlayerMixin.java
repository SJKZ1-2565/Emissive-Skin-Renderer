package com.sjkz1.minetils.mixin;

import com.mojang.authlib.GameProfile;
import com.sjkz1.minetils.Minetils;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractPlayerMixin extends Player {


    public AbstractPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(level, blockPos, f, gameProfile, profilePublicKey);
    }

    @Redirect(method = "getCloakTextureLocation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerInfo;getCapeLocation()Lnet/minecraft/resources/ResourceLocation;"))
    public ResourceLocation getCapeTexture(PlayerInfo instance) {
        ResourceLocation identifier = null;
        if (Minetils.CONFIG.main.SpecialCape) {
            Iterator memberListIterator = Minetils.SPECIAL_MEMBER.iterator();
            if (memberListIterator.hasNext()) {
                String name = (String) memberListIterator.next();
                if (this.getName().getString().contains(name)) {
                    identifier = new ResourceLocation("minetils:textures/entity/cape_" + Minetils.CONFIG.main.IdentifierOrdinal + ".png");
                } else {
                    identifier = instance.getCapeLocation();
                }
            }
        } else {
            identifier = instance.getCapeLocation();
        }
        return identifier;
    }
}
