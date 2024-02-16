package me.sting.client.product.module.movement;

import me.sting.client.product.gui.values.*;
import me.sting.client.product.storage.*;
import me.sting.client.product.utils.timers.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Fly extends Module
{
    @RetentionField
    public SliderValue speed;
    private TimerUtil timer;
    
    public Fly() {
        this.speed = new SliderValue("Gamemode Speed", 1.0, 1.0, 3.0, false);
        this.timer = new TimerUtil();
        this.setName("Fly");
        this.isPrivate();
        this.setCategory(ModuleCategory.Movement);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
    
    @Override
    public void onDisable() {
        timer.resetTimer();
        if (mc.thePlayer != null) {
            mc.thePlayer.capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void gamemode(final TickEvent.PlayerTickEvent event) {
        timer.getTimer().timerSpeed = (float) speed.getValue();
        if (mc.thePlayer != null) {
            mc.thePlayer.capabilities.isFlying = true;
        }
    }

}
