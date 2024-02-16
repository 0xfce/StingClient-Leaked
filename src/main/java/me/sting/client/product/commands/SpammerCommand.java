package me.sting.client.product.commands;

import java.util.ArrayList;
import java.util.List;

import me.sting.client.product.Sting;
import me.sting.client.product.module.utilities.Spammer;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class SpammerCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@spammer";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@spammer <message> <SPAMMER>";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@s");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (lIIIIIlIIIl(array.length, 2)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 3)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 4)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 5)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 6)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4] + " " + array[5];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 7)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4] + " " + array[5] + " " + array[6];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 8)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4] + " " + array[5] + " " + array[6] + " " + array[7];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 9)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4] + " " + array[5] + " " + array[6] + " " + array[7] + " " + array[8];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 10)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4] + " " + array[5] + " " + array[6] + " " + array[7] + " " + array[8] + " " + array[9];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        if (lIIIIIlIIIl(array.length, 11)) {
            if (!lIIIIIlIIlI(array[0].equalsIgnoreCase("message") ? 1 : 0) || !lIIIIIlIIlI(array[0].equalsIgnoreCase("msg") ? 1 : 0) || lIIIIIlIlII(array[0].equalsIgnoreCase("m") ? 1 : 0)) {
                Spammer.msg = array[1] + " " + array[2] + " " + array[3] + " " + array[4] + " " + array[5] + " " + array[6] + " " + array[7] + " " + array[8] + " " + array[9] + " " + array[10];
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        else {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
        }
        if (lIIIIIllIIl(Spammer.msg)) {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + Spammer.prefix + EnumChatFormatting.GREEN + "message set to " + EnumChatFormatting.DARK_AQUA + Spammer.msg));
        }
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
    
    
    private static boolean lIIIIIlIIIl(final int n, final int n2) {
        return n == n2;
    }
    
    private static boolean lIIIIIllIIl(final Object o) {
        return o != null;
    }
    
    private static boolean lIIIIIlIlII(final int n) {
        return n != 0;
    }
    
    private static boolean lIIIIIlIIlI(final int n) {
        return n == 0;
    }
}
