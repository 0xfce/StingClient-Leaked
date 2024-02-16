package me.sting.client.product.commands;

import java.util.*;
import me.sting.client.product.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class HelpCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@help";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@help";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@h");
        list.add("@hlp");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (lllIlIlllI(array.length)) {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getCommandUsage(commandSender)));
            return;
        }
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "/@a + /@auth " + EnumChatFormatting.GRAY + "for users crack."));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "/@i + /@info " + EnumChatFormatting.GRAY + "shows info for client."));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "/@f + /@friend " + EnumChatFormatting.GRAY + "protect your friend."));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "/@e + /@enemy " + EnumChatFormatting.GRAY + "for weaks people."));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "/@s + /@spammer " + EnumChatFormatting.GRAY + "set spammer msg."));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "/@l + /@loginer " + EnumChatFormatting.GRAY + "set loginer password."));
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return true;
    }
    
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] array, final int n) {
        return false;
    }
    
//    public int compareTo(final Object o) {
//        return this.compareTo((ICommand)o);
//    }
    
    private static boolean lllIlIlllI(final int n) {
        return n != 0;
    }
}
