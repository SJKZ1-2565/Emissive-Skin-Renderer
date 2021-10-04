package com.sjkz1.minetils.mixin;

import com.mojang.authlib.GameProfile;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.IdentifierUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractPlayerMixin  extends PlayerEntity {


    public AbstractPlayerMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(world, blockPos, f, gameProfile);
    }

    @Redirect(method = "getCapeTexture",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/network/PlayerListEntry;getCapeTexture()Lnet/minecraft/util/Identifier;"))
    public Identifier getCapeTexture(PlayerListEntry playerListEntry)
    {
        Identifier identifier = null;
        if(Minetils.CONFIG.getConfig().SpecialCape)
        {
            for(String name : Minetils.SPECIAL_MEMBER)
            {
                if(this.getName().getString().contains(name))
                {
                    identifier = IdentifierUtils.getSpeCapeTexture();
                }
                else {
                    identifier = playerListEntry.getCapeTexture();
                }
                break;
            }
        }
        else
        {
            identifier = playerListEntry.getCapeTexture();
        }
        return identifier;
    }
}
