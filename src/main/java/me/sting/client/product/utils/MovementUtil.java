package me.sting.client.product.utils;

import net.minecraft.client.*;

public class MovementUtil
{
    protected Minecraft mc;
    
    public MovementUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public boolean isMoving() {
        return !(this.mc.thePlayer.moveForward == 0.0f) || (this.mc.thePlayer.moveStrafing != 0.0f);
    }

    public void setSpeed(double n) {
        double direction = this.getDirection();
        this.mc.thePlayer.motionX = -(Math.sin(direction) * n);
        this.mc.thePlayer.motionZ = Math.cos(direction) * n;
    }

    public void setStrafe(double n) {
        if (this.isMoving()) {
            return;
        }
        double direction = this.getDirection();
        this.mc.thePlayer.motionX = -Math.sin(direction) * n;
        this.mc.thePlayer.motionZ = Math.cos(direction) * n;
    }

    public float getSpeed() {
        return (float) Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX
                + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
    }

    public float getDirection() {
        float rotationYaw = this.mc.thePlayer.rotationYaw;
        if (this.mc.thePlayer.moveForward > 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = (this.mc.thePlayer.moveForward > 0.0f) ? -0.5f : (this.mc.thePlayer.moveForward < 0.0f ? 0.5f : 1.0f);
        if (this.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (this.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }

}
