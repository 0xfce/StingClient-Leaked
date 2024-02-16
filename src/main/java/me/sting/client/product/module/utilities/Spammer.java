package me.sting.client.product.module.utilities;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Spammer extends Module
{
    @RetentionField
    public SliderValue min;
    @RetentionField
    public SliderValue max;
    @RetentionField
    public SliderValue randoms;
    @RetentionField
    private BooleanValue onChat;
    public static String prefix;
    private boolean isDisconnect;
    public static String msg;
    
    public Spammer() {
        this.min = new SliderValue("Min", 988.0, 0.0, 1000.0, true);
        this.max = new SliderValue("Max", 988.0, 0.0, 1000.0, true);
        this.randoms = new SliderValue("EndingRandomChars", 5.0, 2.0, 8.0, true);
        this.onChat = new BooleanValue("onChat Only", true);
        this.isDisconnect = false;
        this.setName("Spammer");
        this.setCategory(ModuleCategory.Utilities);
        Spammer.prefix = this.getChatName();
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent fmlNetworkEvent$ClientDisconnectionFromServerEvent) {
        this.setState(false);
        this.isDisconnect = true;
    }
    
    @SubscribeEvent
    public void onJoin(final EntityJoinWorldEvent entityJoinWorldEvent) {
        if (this.isDisconnect) {
            this.mc.thePlayer.addChatComponentMessage((IChatComponent) new ChatComponentText(Sting.CLIENT_PREFIX
                    + this.getChatName() + EnumChatFormatting.RED + "is disabled because lost connection to server."));
            this.isDisconnect = false;
        }
    }

    @SubscribeEvent
    public void onClient(final TickEvent.ClientTickEvent tickEvent$ClientTickEvent) {
        if (this.onChat.state && !(this.mc.currentScreen instanceof GuiChat)) {
            return;
        }
        if (this.timer.passed
                .hasTimePassedCURRENT(this.nextInt((int) this.min.getValue(), (int) this.max.getValue()))) {
            return;
        }
        this.timer.passed.reset();
        final String randomChat = this.random.getRandomChat((int) this.randoms.getValue());
        final String randomChat2 = this.random.getRandomChat((int) this.randoms.getValue());
        if (Spammer.msg == null) {
            this.mc.thePlayer.sendChatMessage(randomChat + " Bye bye chat, XD " + randomChat2);
        } else {
            this.mc.thePlayer.sendChatMessage(randomChat + " " + Spammer.msg + " " + randomChat2);
        }
    }

    public int nextInt(final int n, final int n2) {
        return n + new Random().nextInt(n2 - n);
    }

    static {
        Spammer.prefix = null;
        Spammer.msg = null;
    }

}
