package com.sjkz1.minetils.mixin;

import com.mojang.authlib.GameProfile;
import com.sjkz1.minetils.Minetils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractPlayerMixin extends PlayerEntity {


    public AbstractPlayerMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(world, blockPos, f, gameProfile);
    }

    @Redirect(method = "getCapeTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/PlayerListEntry;getCapeTexture()Lnet/minecraft/util/Identifier;"))
    public Identifier getCapeTexture(PlayerListEntry playerListEntry) {
        Identifier identifier = null;
        if (Minetils.CONFIG.main.SpecialCape) {
            Iterator memberListIterator = Minetils.SPECIAL_MEMBER.iterator();
            if (memberListIterator.hasNext()) {
                String name = (String)memberListIterator.next();
                if (this.getName().getString().contains(name)) {
                    identifier = new Identifier("minetils:textures/entity/cape_" + Minetils.CONFIG.main.IdentifierOrdinal + ".png");
                } else {
                    identifier = playerListEntry.getCapeTexture();
                }
            }
        } else {
            identifier = playerListEntry.getCapeTexture();
        }
        return identifier;
    }
}
