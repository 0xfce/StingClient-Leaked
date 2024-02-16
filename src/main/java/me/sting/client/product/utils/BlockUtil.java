package me.sting.client.product.utils;

import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockUtil
{
    protected Minecraft mc;
    
    public BlockUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public boolean isFullBlock(final BlockPos blockPos) {
        Block block = getBlock(blockPos);
        AxisAlignedBB collisionBoundingBox = (block == null) ? null
                : block.getCollisionBoundingBox((World) mc.theWorld, blockPos, getState(blockPos));
        return (collisionBoundingBox != null) &&
                (collisionBoundingBox.maxX - collisionBoundingBox.minX > 1.0) &&
                (collisionBoundingBox.maxY - collisionBoundingBox.minY > 1.0) &&
                (collisionBoundingBox.maxZ - collisionBoundingBox.minZ > 1.0);
    }

    public IBlockState getState(final BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos);
    }

    public Block getBlock(final BlockPos blockPos) {
        return (mc.theWorld == null) ? null : mc.theWorld.getBlockState(blockPos).getBlock();
    }

    public double getCenterDistance(final BlockPos blockPos) {
        return mc.thePlayer.getDistance(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
    }

    public boolean isSameBlock(final BlockPos blockPos, final BlockPos blockPos2) {
        return (blockPos.getX() == blockPos2.getX()) &&
                (blockPos.getY() == blockPos2.getY()) &&
                (blockPos.getZ() == blockPos2.getZ());
    }

}
