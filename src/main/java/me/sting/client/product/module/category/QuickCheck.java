package me.sting.client.product.module.category;

import me.sting.client.product.gui.values.*;
import me.sting.client.product.storage.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.client.event.*;
import me.sting.client.product.module.utilities.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;

public class QuickCheck extends Module
{
    @RetentionField
    public SliderValue delay;
    public boolean ISINSPECTATOR;
    
    public QuickCheck() {
        this.delay = new SliderValue("Delay", 1500.0, 300.0, 10000.0, true);
        this.ISINSPECTATOR = false;
        this.setName("QuickCheck");
        this.isPrivate();
        this.setCategory(ModuleCategory.Specs);
    }
    
    @SubscribeEvent
    public void disconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent fmlNetworkEvent$ClientDisconnectionFromServerEvent) {
        this.ISINSPECTATOR = false;
    }
    
    @SubscribeEvent
    public void world(final WorldEvent.Load event) {
        this.ISINSPECTATOR = false;
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent clientChatReceivedEvent) {
        if (lIllIIlIIl(clientChatReceivedEvent.message)) {
            final String getTextWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(clientChatReceivedEvent.message.getUnformattedText());
            if (lIllIIlllI(getTextWithoutFormattingCodes)) {
                return;
            }
            if (lIllIlIIII(getTextWithoutFormattingCodes.contains("Spectate mode has been enabled.") ? 1 : 0)) {
                this.ISINSPECTATOR = true;
            }
            if (lIllIlIIII(getTextWithoutFormattingCodes.contains("Spectate mode has been disabled.") ? 1 : 0)) {
                this.ISINSPECTATOR = false;
            }
            if (lIllIlIIII(getTextWithoutFormattingCodes.contains("You need to be in spectate mode to teleport.") ? 1 : 0)) {
                this.ISINSPECTATOR = false;
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
        if (lIllIlIIII(AntiStaffs.SPECS_LINKEDLIST.isEmpty() ? 1 : 0)) {
            if (lIllIlIIII(this.ISINSPECTATOR ? 1 : 0)) {
                if (lIllIlIIII(AntiStaffs.noti.state ? 1 : 0)) {
                    this.mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + "CHECKS list empty right now."));
                    this.setState(false);
                }
            }
            else if (lIllIlIIII(AntiStaffs.noti.state ? 1 : 0)) {
                this.mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + "You need to be spectate mode!"));
                this.setState(false);
            }
        }
    }
    
    @SubscribeEvent
    public void onClient(final TickEvent.ClientTickEvent tickEvent$ClientTickEvent) {
        if (lIllIlIIll(AntiStaffs.SPECS_LINKEDLIST.isEmpty() ? 1 : 0) && lIllIlIIII(this.timer.reached.hasTimeReachedCURRENT((long)this.delay.getValue()) ? 1 : 0)) {
            this.timer.reached.reset();
            if (lIllIlIIII(this.ISINSPECTATOR ? 1 : 0)) {
                this.mc.thePlayer.sendChatMessage("/tp " + AntiStaffs.SPECS_LINKEDLIST.poll());
                this.mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + AntiStaffs.SPECS_LINKEDLIST.poll()));
            }
            else {
                this.setState(false);
                if (lIllIlIIII(AntiStaffs.noti.state ? 1 : 0)) {
                    this.mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + "You need to be spectate mode!"));
                }
            }
        }
        if (lIllIlIIII(AntiStaffs.SPECS_LINKEDLIST.isEmpty() ? 1 : 0)) {
            if (lIllIlIIII(AntiStaffs.noti.state ? 1 : 0)) {
                this.mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.GREEN + "successfully teleport to staffs."));
            }
            this.setState(false);
        }
    }
    
    private static boolean lIllIIlIIl(final Object o) {
        return o != null;
    }
    
    private static boolean lIllIIlllI(final Object o) {
        return o == null;
    }
    
    private static boolean lIllIlIIII(final int n) {
        return n != 0;
    }
    
    private static boolean lIllIlIIll(final int n) {
        return n == 0;
    }
}
