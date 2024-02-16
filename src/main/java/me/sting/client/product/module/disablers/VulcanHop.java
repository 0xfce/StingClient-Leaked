package me.sting.client.product.module.disablers;

import java.util.*;
import me.sting.client.product.module.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.*;
import me.sting.client.product.module.utilities.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.client.*;

public class VulcanHop extends Module
{
    private final LinkedList packetBuffer;
    private int currentTrans;
    private int vulTickCounterUID;
    
    public VulcanHop() {
        this.packetBuffer = new LinkedList();
        this.setName("VulcanHop");
        this.isPrivate();
        this.setCategory(ModuleCategory.Disablers);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
        this.vulTickCounterUID = -25767;
        this.mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + "Disabler Only work when you rejoined the server!"));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent event) {
        if (this.timer.passed.hasTimePassedCURRENT(5000L) && this.packetBuffer.size() > 4) {
            this.timer.passed.reset();
            while (this.packetBuffer.size() > 4) {
                Disabler.sendPacketNoEvent((Packet) this.packetBuffer.poll());
            }
        }
    }

    @SubscribeEvent
    public void onWorld(final WorldEvent event) {
        this.currentTrans = 0;
        this.packetBuffer.clear();
        this.timer.passed.reset();
        this.vulTickCounterUID = -25767;
    }

    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent event) {
        final Packet packet = event.getPacket();
        if (packet instanceof C0FPacketConfirmTransaction) {
            final C0FPacketConfirmTransaction c0FPacketConfirmTransaction = (C0FPacketConfirmTransaction) packet;
            final int windowIdDiff = Math
                    .abs(Math.abs(c0FPacketConfirmTransaction.getWindowId()) - Math.abs(this.vulTickCounterUID));
            if (Math.abs(windowIdDiff) <= 4) {
                this.vulTickCounterUID = c0FPacketConfirmTransaction.getWindowId();
                this.packetBuffer.add(packet);
                event.setCanceled(true);
                if (Disabler.notifc.state) {
                    this.mc.thePlayer
                            .addChatComponentMessage(new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName()
                                    + EnumChatFormatting.GRAY + "C0F-PingTickCounter IN " + this.packetBuffer.size()));
                }
            } else if (Math.abs(Math.abs(c0FPacketConfirmTransaction.getWindowId()) - 25767) <= 4) {
                this.vulTickCounterUID = c0FPacketConfirmTransaction.getWindowId();
                if (Disabler.notifc.state) {
                    this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(Sting.CLIENT_PREFIX
                            + this.getChatName() + EnumChatFormatting.GRAY + "C0F-PingTickCounter RESETED"));
                }
            }
        }
    }

}
