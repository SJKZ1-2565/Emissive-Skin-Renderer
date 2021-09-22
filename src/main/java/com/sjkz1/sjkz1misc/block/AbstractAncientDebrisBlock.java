package com.sjkz1.sjkz1misc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public abstract class AbstractAncientDebrisBlock extends Block {

    public AbstractAncientDebrisBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        super.animateTick(blockState, level, blockPos, random);
        int x;
        double posX;
        double posY;
        double posZ;
        if(level.getBlockState(blockPos.above()).is(Blocks.FIRE) || level.getBlockState(blockPos.above()).is(Blocks.LAVA)) {
            for (x = 0; x < 6; ++x) {
                posX = blockPos.getX() + random.nextDouble();
                posY = blockPos.getY() + 2.5D;
                posZ = blockPos.getZ() + random.nextDouble();
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX +2.5, posY, posZ, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX - 2.5, posY, posZ, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY -3.5, posZ, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY -2.5, posZ+2.5, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, posX, posY -2.5, posZ-2.5, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
