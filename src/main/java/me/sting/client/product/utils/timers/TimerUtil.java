package me.sting.client.product.utils.timers;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.*;

public class TimerUtil
{
    public PassedTimer passed;
    public ReachedTimer reached;
    
    public TimerUtil() {
        this.passed = new PassedTimer();
        this.reached = new ReachedTimer();
    }
    
    public Timer getTimer() {
        return (Timer)ObfuscationReflectionHelper.getPrivateValue((Class)Minecraft.class, (Object)Minecraft.getMinecraft(), new String[] { "timer", "timer" });
    }
    
    public void resetTimer() {
        this.getTimer().timerSpeed = 1.0f;
    }

    public void purge() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purge'");
    }

    public void cancel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancel'");
    }
}
