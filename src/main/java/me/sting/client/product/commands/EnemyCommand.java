package me.sting.client.product.commands;

import java.util.ArrayList;
import java.util.List;

import me.sting.client.product.Sting;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class EnemyCommand implements ICommand
{
    public static List<String> enms;
    
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@enemy";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@enemy <add, remove> <IGN> <list>";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@e");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] args) throws CommandException {
        if (args.length >= 2) {
            if (!isAddCommand(args[0]) && !isRemoveCommand(args[0])) {
                addEnemy(args[1], commandSender);
            } else if (isRemoveCommand(args[0])) {
                removeEnemy(args[1], commandSender);
            } else {
                sendCommandUsageMessage(commandSender);
            }
        } else if (args.length == 1) {
            if (!isListCommand(args[0])) {
                listEnemies(commandSender);
            } else {
                sendCommandUsageMessage(commandSender);
            }
        } else {
            sendCommandUsageMessage(commandSender);
        }
    }

    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return true;
    }

    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] args,
            final BlockPos blockPos) {
        return null;
    }

    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }

    public static void addEnemy(final String enemyName, final ICommandSender commandSender) {
        if (!enms.contains(enemyName.toLowerCase())) {
            enms.add(enemyName.toLowerCase());
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + enemyName
                    + EnumChatFormatting.GREEN + " added into your enemy list."));
        } else {
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + enemyName
                    + EnumChatFormatting.YELLOW + " already in your enemy list."));
        }
    }

    public static void removeEnemy(final String enemyName, final ICommandSender commandSender) {
        if (enms.contains(enemyName.toLowerCase())) {
            enms.remove(enemyName.toLowerCase());
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + enemyName
                    + EnumChatFormatting.RED + " was removed from your enemy list."));
        } else {
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + enemyName
                    + EnumChatFormatting.DARK_RED + " isn't in your enemy list."));
        }
    }

    public static void listEnemies(final ICommandSender commandSender) {
        if (enms.isEmpty()) {
            commandSender.addChatMessage(new ChatComponentText(
                    Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + "you don't have any enms."));
        } else {
            enms.forEach(enemy -> commandSender
                    .addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + enemy)));
        }
    }

    private static boolean isAddCommand(final String command) {
        return command.equalsIgnoreCase("add") || command.equalsIgnoreCase("a") || command.equalsIgnoreCase("+");
    }

    private static boolean isRemoveCommand(final String command) {
        return command.equalsIgnoreCase("remove") || command.equalsIgnoreCase("r") || command.equalsIgnoreCase("-");
    }

    private static boolean isListCommand(final String command) {
        return command.equalsIgnoreCase("list") || command.equalsIgnoreCase("l") || command.equalsIgnoreCase("=");
    }

    private void sendCommandUsageMessage(final ICommandSender commandSender) {
        commandSender.addChatMessage(new ChatComponentText(
                Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
    }

    static {
        enms = new ArrayList<>();
    }

}
