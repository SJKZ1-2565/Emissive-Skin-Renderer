package com.sjkz1.sjkz1misc.block;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractAncientDebrisBlock extends Block {

    public AbstractAncientDebrisBlock(Settings settings) {
        super(settings);
    }


    @Override
	public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, Random random) {

        if (SJKZ1Misc.CONFIG.getConfig().ShowDebrisUnderFire) {

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
