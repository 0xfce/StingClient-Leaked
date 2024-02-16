package me.sting.client.product.module.moderator;

import java.util.TimerTask;

import me.sting.client.product.module.Module;
import net.minecraft.client.Minecraft;

public class MuterTask extends TimerTask {

    private String[] msg;

    public MuterTask(String[] msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String s = null;

        if ((msg[3].equalsIgnoreCase("watchdog") && Reporter.watchdog.state) &&
                ((msg[9].equalsIgnoreCase("pvp.") && Reporter.servers.combos[2].state) ||
                (msg[9].equalsIgnoreCase("skypvp.") && Reporter.servers.combos[3].state) ||
                (msg[9].equalsIgnoreCase("skypvp2.") && Reporter.servers.combos[4].state) ||
                (msg[9].equalsIgnoreCase("redstonepvp.") && Reporter.servers.combos[5].state) ||
                (msg[9].endsWith(".") && Reporter.servers.combos[1].state))) {
            s = msg[9].endsWith(".") ? msg[9].substring(0, msg[9].length() - 1) : msg[9];
        } else if ((!areAllServerCombosDisabled() || 
                    msg[9].equalsIgnoreCase("pvp.") && Reporter.servers.combos[2].state ||
                    msg[9].equalsIgnoreCase("skypvp.") && Reporter.servers.combos[3].state ||
                    msg[9].equalsIgnoreCase("skypvp2.") && Reporter.servers.combos[4].state ||
                    msg[9].equalsIgnoreCase("redstonepvp.") && Reporter.servers.combos[5].state ||
                    msg[9].endsWith(".") && Reporter.servers.combos[1].state)) {
            s = msg[9].endsWith(".") ? msg[9].substring(0, msg[9].length() - 1) : msg[9];
        }

        if (s != null) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/goto " + s);
            if (!Reporter.disable.state) {
                Module.getModule((Class) Reporter.class).setState(false);
            }
        }
    }

    private boolean areAllServerCombosDisabled() {
        return !Reporter.servers.combos[2].state &&
            !Reporter.servers.combos[3].state &&
            !Reporter.servers.combos[4].state &&
            !Reporter.servers.combos[5].state &&
            Reporter.servers.combos[1].state;
    }
    
}
