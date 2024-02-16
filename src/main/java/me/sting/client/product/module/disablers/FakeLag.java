package me.sting.client.product.module.disablers;

import me.sting.client.product.gui.values.*;
import me.sting.client.product.storage.*;
import java.util.*;
import me.sting.client.product.utils.timers.*;
import me.sting.client.product.module.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;
import me.sting.client.product.module.utilities.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.client.*;

public class FakeLag extends Module
{
    @RetentionField
    private SliderValue lagdelay;
    @RetentionField
    private SliderValue lagduration;
    public LinkedList packetBuffer;
    public TimerUtil delaytimer;
    private boolean isSent;
    public TimerUtil durationtimer;
    
    public FakeLag() {
        this.lagdelay = new SliderValue("LagDelay", 75.0, 0.0, 2000.0, true);
        this.lagduration = new SliderValue("LagDuration", 180.0, 100.0, 1000.0, true);
        this.packetBuffer = new LinkedList();
        this.delaytimer = new TimerUtil();
        this.durationtimer = new TimerUtil();
        this.setName("FakeLag");
        this.isPrivate();
        this.setCategory(ModuleCategory.Disablers);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
        this.isSent = false;
        this.packetBuffer.clear();
    }
    
    @SubscribeEvent
    public void onWorld(final WorldEvent worldEvent) {
        this.isSent = false;
        this.delaytimer.reached.reset();
        this.durationtimer.reached.reset();
        this.packetBuffer.clear();
    }
    
    @Override
    public void onDisable() {
        if (this.packetBuffer.isEmpty()) {
            for (int n = 0; n < this.packetBuffer.size(); ++n) {
                Disabler.sendPacketNoEvent((Packet) this.packetBuffer.get(n));
            }
            this.packetBuffer.clear();
        }
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent event) {
        if (this.delaytimer.reached.hasTimeReachedNANO1000000((long) this.lagdelay.getValue())) {
            this.durationtimer.reached.reset();
        }
        if (this.durationtimer.reached.hasTimeReachedNANO1000000((long) this.lagduration.getValue())
                && this.packetBuffer.isEmpty()) {
            this.delaytimer.reached.reset();
            this.durationtimer.reached.reset();
            for (int n = 0; n < this.packetBuffer.size(); ++n) {
                Disabler.sendPacketNoEvent((Packet) this.packetBuffer.get(n));
            }
            if (Disabler.notifc.state) {
                this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName()
                        + EnumChatFormatting.GRAY + "Release buffered(size=" + this.packetBuffer.size() + ")."));
            }
            this.isSent = true;
            this.packetBuffer.clear();
        }
    }

    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent event) {
        final Packet packet = event.getPacket();
        if (!((packet instanceof C03PacketPlayer) || (packet instanceof C00PacketKeepAlive)
                || (packet instanceof C0FPacketConfirmTransaction)
                || (packet instanceof C03PacketPlayer.C04PacketPlayerPosition)
                || (packet instanceof C03PacketPlayer.C05PacketPlayerLook)
                || (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)
                || !(packet instanceof C0BPacketEntityAction)) && this.packetBuffer.contains(packet)) {
            event.setCanceled(true);
            this.packetBuffer.add(packet);
        }
    }

}
