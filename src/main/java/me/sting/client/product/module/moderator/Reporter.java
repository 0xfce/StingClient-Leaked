package me.sting.client.product.module.moderator;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import java.util.*;
import java.util.Timer;

import net.minecraftforge.fml.common.eventhandler.*;

public class Reporter extends Module
{
    @RetentionField
    public SliderValue min;
    @RetentionField
    public SliderValue max;
    @RetentionField
    public static ComboValue servers;
    @RetentionField
    public static BooleanValue watchdog;
    @RetentionField
    public static BooleanValue disable;
    public TimerTask timerTask;
    
    public Reporter() {
        this.min = new SliderValue("Min Delay", 400.0, 10.0, 1050.0, true);
        this.max = new SliderValue("Max Delay", 800.0, 10.0, 1050.0, true);
        this.timerTask = null;
        this.setName("Reporter");
        this.isPrivate();
        this.setCategory(ModuleCategory.SeniorMod);
        this.setKey(0);
        Reporter.servers.combos[1].setState(true);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if (!isEnabled() || !isValidEvent(event)) {
            return;
        }

        String[] splitMessage = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText())
                .split(" ");

        if (splitMessage.length >= 10) {
            scheduleReportTask(splitMessage);
        }
    }

    private boolean isEnabled() {
        return this.state && this.mc.thePlayer != null && this.mc.theWorld != null;
    }

    private boolean isValidEvent(final ClientChatReceivedEvent event) {
        return event.message != null && event.message.getUnformattedText() != null;
    }

    private void scheduleReportTask(final String[] splitMessage) {
        long delay = (long) (this.min.getValue()
                + new Random().nextDouble() * (this.max.getValue() - this.min.getValue()));
        Timer timer = new Timer();

        timer.schedule(new ReporterTask(splitMessage), delay);
    }

    static {
        Reporter.servers = new ComboValue("Joining Servers", false, "option",
                new String[] { "state", "MiniGames Servers", "PvP Servers", "SkyPvP Servers", "SkyPvP Classic Servers",
                        "RedStonePvP Servers" });
        Reporter.watchdog = new BooleanValue("Watchdog", true);
        Reporter.disable = new BooleanValue("Disable onReport", true);
    }

}
