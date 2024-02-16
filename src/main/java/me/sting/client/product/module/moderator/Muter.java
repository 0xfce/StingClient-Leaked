package me.sting.client.product.module.moderator;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import java.util.*;
import java.util.Timer;

import net.minecraftforge.fml.common.eventhandler.*;

public class Muter extends Module
{
    @RetentionField
    private SliderValue delay;
    @RetentionField
    private static ComboValue reasons;
    @RetentionField
    private static BooleanValue disable;
    public TimerTask timerTask;
    
    public Muter() {
        this.delay = new SliderValue("Delay", 400.0, 10.0, 1050.0, true);
        this.timerTask = null;
        this.setName("Muter");
        this.isPrivate();
        this.setCategory(ModuleCategory.SeniorMod);
        this.setKey(0);
        // Muter.reasons.combos[1].setState(true);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
    
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (state && event.message != null && mc.thePlayer != null && mc.theWorld != null) {
            String[] splitMessage = event.message.getUnformattedText().split(" ");
            if (!(splitMessage.length > 1 && splitMessage[1].equalsIgnoreCase("blocksmc"))) {
                Timer timer = new Timer();
                timer.schedule(new MuterTask(splitMessage), (long)delay.getValue());
            }
        }
    }
}
