package com.sjkz1.sjkz1misc.block;

import java.awt.*;
import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.sjkz1misc.SJKZ1Misc;

import com.sjkz1.sjkz1misc.utils.SJKZ1Helper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class AbstractAncientDebrisBlock extends Block {

    public AbstractAncientDebrisBlock(Settings settings) {
        super(settings);
    }

    @Override
	public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        if (SJKZ1Misc.CONFIG.getConfig().ShowDebrisUnderFire) {

            PlayerEntity playerEntity = world.getClosestPlayer((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, 3.0D, false);
            if (playerEntity != null) {
                float ticks = (playerEntity.age % 20 + MinecraftClient.getInstance().getTickDelta()) / 20.0F;
                Color color = Color.getHSBColor(ticks,0.9f,1);

                if(SJKZ1Misc.CONFIG.getConfig().showFps)
                {
                    RenderSystem.sca;
                    matrixStack.scale(4F,4F,4F);
                    matrixStack.translate((float)(width / 2), (float)(height / 2), 0.0F);
                    TextRenderer textRenderer =  mc.textRenderer;
                    textRenderer.draw(matrixStack,text,width,height,formatting.getColorIndex());
                    matrixStack.pop();

                }
            }


            int x;
            double posX;
            double posY;
            double posZ;
            if(world.getBlockState(blockPos.up()).isOf(Blocks.FIRE) || world.getBlockState(blockPos.up()).isOf(Blocks.LAVA)) {
                for (x = 0; x < 6; ++x) {
                    posX = blockPos.getX() + random.nextDouble();
                    posY = blockPos.getY() + 2.5D;
                    posZ = blockPos.getZ() + random.nextDouble();
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX +2.5, posY, posZ, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX - 2.5, posY, posZ, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY -3.5, posZ, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY -2.5, posZ+2.5, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY -2.5, posZ-2.5, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
