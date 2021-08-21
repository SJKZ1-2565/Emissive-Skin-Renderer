package com.sjkz1.sjkz1misc.utils;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

public abstract class AbstractAncientDebrisBlock extends Block {

    public AbstractAncientDebrisBlock(Settings settings) {
        super(settings);
    }

    public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        if (world.getBlockState(blockPos.up()).isOf(Blocks.FIRE) && SJKZ1Misc.CONFIG.getConfig().ShowDebrisUnderFire) {
            int x;
            double posX;
            double posY;
            double posZ;
            for (x = 0; x < 6; ++x) {
                posX = (double) blockPos.getX() + random.nextDouble();
                posY = (double) blockPos.getY() + 2.5D;
                posZ = (double) blockPos.getZ() + random.nextDouble();
                world.addParticle(ParticleTypes.WITCH, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
