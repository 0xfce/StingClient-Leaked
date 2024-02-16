package me.sting.client.product.module.moderator;

import java.util.TimerTask;

import me.sting.client.product.module.Module;
import net.minecraft.client.Minecraft;

public class ReporterTask extends TimerTask {

    private String[] msg;
    
    public ReporterTask(String[] msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String server = null;

        if (isValidWatchdogMessage() && Reporter.watchdog.state) {
            server = getServerName();
        }

        if (isNonWatchdogMessage() && hasEnabledServer()) {
            server = getServerName();
        }

        if (server != null) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/goto " + server);
            if (Reporter.disable.state) {
                return;
            }
            Module.getModule((Class) Reporter.class).setState(false);
        }
    }

    private boolean isValidWatchdogMessage() {
        return msg[3].equalsIgnoreCase("watchdog") && Reporter.watchdog.state;
    }

    private boolean isNonWatchdogMessage() {
        return !msg[3].equalsIgnoreCase("watchdog") && hasEnabledServer();
    }

    private boolean hasEnabledServer() {
        return Reporter.servers.combos[1].state || Reporter.servers.combos[2].state ||
                Reporter.servers.combos[3].state || Reporter.servers.combos[4].state ||
                Reporter.servers.combos[5].state;
    }

    private String getServerName() {
        String serverName = null;

        if (msg[9].endsWith(".")) {
            serverName = msg[9].substring(0, msg[9].length() - 1);
        }

        return serverName;
    }

}
