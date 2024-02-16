package me.sting.client.product.commands;

import java.util.*;
import me.sting.client.product.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.function.*;

public class FriendCommand implements ICommand
{
    public static List friends;
    
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@friend";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@friend <add, remove> <IGN> <list>";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@f");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] args) throws CommandException {
        if (args.length == 2) {
            if (!isAddCommand(args[0]) || !isRemoveCommand(args[0])) {
                addFriend(args[1], commandSender);
            } else if (isRemoveCommand(args[0])) {
                removeFriend(args[1], commandSender);
            } else {
                sendCommandUsageMessage(commandSender);
            }
        } else if (args.length == 1) {
            if (!isListCommand(args[0])) {
                listFriends(commandSender);
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

    public static void addFriend(final String friendName, final ICommandSender commandSender) {
        if (!FriendCommand.friends.contains(friendName.toLowerCase())) {
            FriendCommand.friends.add(friendName.toLowerCase());
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY
                    + friendName + EnumChatFormatting.GREEN + " added into your friend list."));
        } else {
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY
                    + friendName + EnumChatFormatting.YELLOW + " already in your friend list."));
        }
    }

    public static void removeFriend(final String friendName, final ICommandSender commandSender) {
        if (FriendCommand.friends.contains(friendName.toLowerCase())) {
            FriendCommand.friends.remove(friendName.toLowerCase());
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY
                    + friendName + EnumChatFormatting.RED + " was removed from your friend list."));
        } else {
            commandSender.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY
                    + friendName + EnumChatFormatting.DARK_RED + " isn't in your friend list."));
        }
    }

    public static void listFriends(final ICommandSender commandSender) {
        if (FriendCommand.friends.isEmpty()) {
            commandSender.addChatMessage(new ChatComponentText(
                    Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + "you don't have any friends, don't be sad :("));
        } else {
            FriendCommand.friends.forEach(friend -> commandSender
                    .addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + friend)));
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
        FriendCommand.friends = new ArrayList<>();
    }

}
