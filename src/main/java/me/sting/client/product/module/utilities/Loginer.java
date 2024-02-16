package me.sting.client.product.module.utilities;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Loginer extends Module
{
    @RetentionField
    private SliderValue delay;
    public static String password;
    public static String prefix;
    
    public Loginer() {
        this.delay = new SliderValue("Delay", 30.0, 0.0, 1050.0, true);
        this.setName("Loginer");
        Loginer.prefix = this.getChatName();
        this.setCategory(ModuleCategory.Utilities);
    }
    
    @Override
    public void onEnable() {
        if (Loginer.password == null) {
            this.setState(false);
            this.mc.thePlayer.addChatComponentMessage((IChatComponent) new ChatComponentText(
                    Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + "please set password."));
        }
    }

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent clientChatReceivedEvent) {
        if (clientChatReceivedEvent.message == null || this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        final String getTextWithoutFormattingCodes = EnumChatFormatting
                .getTextWithoutFormattingCodes(clientChatReceivedEvent.message.getUnformattedText());
        if (getTextWithoutFormattingCodes.toLowerCase().contains("/login <password>") || getTextWithoutFormattingCodes
                .toLowerCase().contains("if you lost the password type /resetpassword")) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.thePlayer.sendChatMessage("/l " + password);              
                }
            }, (long) this.delay.getValue());
        }
    }

    static {
        Loginer.password = null;
        Loginer.prefix = null;
    }

}
