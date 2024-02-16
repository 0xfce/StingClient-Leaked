package me.sting.client.product.module.disablers;

import me.sting.client.product.storage.*;
import java.util.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import me.sting.client.product.module.utilities.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.network.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;

public class LatestVerus extends Module
{
    @RetentionField
    public SliderValue verusBufferSizeValue;
    private boolean modified;
    @RetentionField
    public SliderValue verusRepeatTimesValue;
    private LinkedList packetBuffer;
    @RetentionField
    public SliderValue verusRepeatTimesFightingValue;
    @RetentionField
    public SliderValue verusFlagDelayValue;
    @RetentionField
    public BooleanValue verusSlientFlagApplyValue;
    private boolean verus2Stat;
    
    public LatestVerus() {
        this.verusBufferSizeValue = new SliderValue("VerusBufferSize", 300.0, 0.0, 1000.0, true);
        this.modified = false;
        this.verusRepeatTimesValue = new SliderValue("Verus-RepeatTimes", 1.0, 0.0, 5.0, true);
        this.packetBuffer = new LinkedList();
        this.verusRepeatTimesFightingValue = new SliderValue("Verus-RepeatTimesFighting", 1.0, 0.0, 5.0, true);
        this.verusFlagDelayValue = new SliderValue("Verus-FlagDelay", 900.0, 35.0, 99999.0, true);
        this.verusSlientFlagApplyValue = new BooleanValue("VerusSlientFlagApply", false);
        this.verus2Stat = false;
        this.setName("LatestVerus");
        this.isPrivate();
        this.setCategory(ModuleCategory.Disablers);
    }
    
    
    
    @Override
    public void onEnable() {
        this.isPrivate();
        this.verus2Stat = false;
        this.timer.reached.reset();
        this.modified = false;
        this.packetBuffer.clear();
    }
    
    @SubscribeEvent
    public final void onWorld(final WorldEvent worldEvent) {
        this.verus2Stat = false;
        this.packetBuffer.clear();
        this.timer.reached.reset();
    }
    

    private int getRepeatTimes() {
        return Disabler.inCombat ? (int)this.verusRepeatTimesFightingValue.getValue()
                : (int)this.verusRepeatTimesValue.getValue();
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent event) {
        this.modified = false;
        if (this.timer.reached.hasTimeReachedNANO1000000(490L)) {
            this.timer.reached.reset();
            if (this.packetBuffer.isEmpty()) {
                final Packet packet = (Packet) this.packetBuffer.poll();
                final int repeatTimes = this.getRepeatTimes();
                for (int n = 0; n < repeatTimes; ++n) {
                    Disabler.sendPacketNoEvent(packet);
                }
                if (Disabler.notifc.state) {
                    this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                            Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.GRAY + "Send packet Buffer"));
                }
            } else if (Disabler.notifc.state) {
                this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                        Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.GRAY + "Empty Packet Buffer"));
            }
        }
    }

    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent packetReceivedSendEvent) {
        final Packet packet = packetReceivedSendEvent.getPacket();
        if (packet instanceof C0FPacketConfirmTransaction) {
            this.packetBuffer.add(packet);
            packetReceivedSendEvent.setCanceled(true);
            if (this.packetBuffer.size() > this.verusBufferSizeValue.getValue()) {
                if (this.verus2Stat) {
                    this.verus2Stat = true;
                    this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(Sting.CLIENT_PREFIX
                            + this.getChatName() + EnumChatFormatting.GREEN + "AntiCheat is disabled."));
                }
                final Packet packet2 = (Packet) this.packetBuffer.poll();
                final int repeatTimes = this.getRepeatTimes();
                for (int n = 0; n < repeatTimes; ++n) {
                    Disabler.sendPacketNoEvent(packet2);
                }
            }
            if (Disabler.notifc.state) {
                this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName()
                        + EnumChatFormatting.GRAY + "Packet C0F IN " + this.packetBuffer.size()));
            }
        } else if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) packet;
            if (this.mc.thePlayer.ticksExisted % this.verusFlagDelayValue.getValue() == 0.0
                    && this.mc.thePlayer.ticksExisted > this.verusFlagDelayValue.getValue() + 1.0 && !this.modified) {
                if (Disabler.notifc.state) {
                    this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                            Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.GRAY + "Packet C03"));
                }
                this.modified = true;
                c03PacketPlayer.setMoving(false);
            }
        } else if (packet instanceof S08PacketPlayerPosLook && this.verusSlientFlagApplyValue.state) {
            final double dx = ((S08PacketPlayerPosLook) packet).getX() - this.mc.thePlayer.posX;
            final double dy = ((S08PacketPlayerPosLook) packet).getY() - this.mc.thePlayer.posY;
            final double dz = ((S08PacketPlayerPosLook) packet).getZ() - this.mc.thePlayer.posZ;
            if (Math.sqrt(dx * dx + dy * dy + dz * dz) > 8.0) {
                packetReceivedSendEvent.setCanceled(true);
                if (Disabler.notifc.state) {
                    this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                            Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.GRAY + "Silent Flag"));
                }
                Disabler.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(
                        ((S08PacketPlayerPosLook) packet).getX(), ((S08PacketPlayerPosLook) packet).getY(),
                        ((S08PacketPlayerPosLook) packet).getZ(), ((S08PacketPlayerPosLook) packet).getYaw(),
                        ((S08PacketPlayerPosLook) packet).getPitch(), true));
            }
        }
        if (this.mc.thePlayer != null && this.mc.thePlayer.ticksExisted > 7) {
            this.timer.reached.reset();
            this.packetBuffer.clear();
        }
    }

}
