package me.sting.client.product.module.movement;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.server.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;

public class Tiimer extends Module
{
    @RetentionField
    public SliderValue speed;
    @RetentionField
    public BooleanValue flag;
    
    public Tiimer() {
        this.speed = new SliderValue("Speed", 1.0, 1.0, 2.0, false);
        this.flag = new BooleanValue("Disable onFlag", true);
        this.setName("Timer");
        this.setCategory(ModuleCategory.Movement);
        this.setKey(0);
    }
    
    @Override
    public void onDisable() {
        this.timer.resetTimer();
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent fmlNetworkEvent$ClientDisconnectionFromServerEvent) {
        this.setState(false);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent packetReceivedSendEvent) {
        final Packet packet = packetReceivedSendEvent.getPacket();
        if (!(state && flag.state) || packet == null || mc.thePlayer == null || mc.theWorld != null) {
            return;
        }
        if ((packet instanceof S08PacketPlayerPosLook) && movement.isMoving()) {
            mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                    Sting.CLIENT_PREFIX + getChatName() + EnumChatFormatting.WHITE + "Flagged Detect."));
            setState(false);
        }
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        if (!(state && mc.currentScreen == null) || mc.thePlayer == null || mc.theWorld != null) {
            return;
        }
        timer.getTimer().timerSpeed = (float) speed.getValue();
    }

}
