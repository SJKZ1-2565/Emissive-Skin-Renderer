package com.sjkz1.minetils.utils;

import net.minecraft.util.math.BlockPos;

//This is for getting value of BlockPos inside Mixin Class
@SuppressWarnings("unused")
public class BlockPosGetter {

    public static BlockPos jobBlockPos;

    public static BlockPos getJobBlocksPos() {
        return jobBlockPos;
    }
}
