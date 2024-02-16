package me.sting.client.product.utils;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RotationUtil
{
    protected Minecraft mc;
    
    public RotationUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public float rotationUntilTarget(final Entity entity) {
        if (entity != null) {
            double deltaX = entity.posX - this.mc.thePlayer.posX;
            double deltaY = entity.posY - this.mc.thePlayer.posY;
            double deltaZ = entity.posZ - this.mc.thePlayer.posZ;
            double yaw = -(Math.atan2(deltaX, deltaZ) * 57.2957795);
            double pitch = -(Math.asin(deltaY / Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ))
                    * 57.2957795);
            return (float) yaw;
        }
        return -1.0f;
    }

    public boolean isAngleLargeEnough(final Entity entity, float angle) {
        angle *= 0.5;
        double angleDiff = ((this.mc.thePlayer.rotationYaw - this.rotationUntilTarget(entity)) % 360.0 + 540.0) % 360.0
                - 180.0;
        return (angleDiff < 0.0 && angleDiff > -angle) || (angleDiff > -180.0 && angleDiff < angle);
    }

    public float[] setRotations(final Entity entity) {
        if (entity == null) {
            return null;
        }
        double deltaX = entity.posX - this.mc.thePlayer.posX;
        double deltaY = (entity instanceof EntityLivingBase)
                ? ((EntityLivingBase) entity).posY + ((EntityLivingBase) entity).getEyeHeight() * 0.9
                        - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight())
                : (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0
                        - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        double deltaZ = entity.posZ - this.mc.thePlayer.posZ;
        return new float[] {
                this.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(
                        (float) (Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0f - this.mc.thePlayer.rotationYaw),
                this.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(
                        (float) (-(Math.atan2(deltaY, MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ)) * 180.0
                                / Math.PI)) - this.mc.thePlayer.rotationPitch)
        };
    }

    public void playerSoftFacing(final Entity entity) {
        float[] rotations = this.setRotations(entity);
        if (rotations != null) {
            this.mc.thePlayer.rotationYawHead = rotations[0];
            this.mc.thePlayer.renderYawOffset = rotations[0];
        }
    }

}
