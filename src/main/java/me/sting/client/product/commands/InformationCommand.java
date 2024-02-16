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
import net.minecraft.util.IChatComponent;

public class InformationCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@information";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@information";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@i");
        list.add("@info");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (llIIlIlIlI(array.length)) {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + this.getCommandUsage(commandSender)));
            return;
        }
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "Name: " + EnumChatFormatting.GRAY + "Stink(Skidded From Dunk/Funk/FDP/AlSa7r clients)"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "Version: " + EnumChatFormatting.GRAY + "I" + "-" + "1.0.0"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "ReleaseDate: " + EnumChatFormatting.GRAY + "1445/2024"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "Creators: " + EnumChatFormatting.GRAY + "moha"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "Skidders: " + EnumChatFormatting.GRAY + "slutty witty, slut devilsvul, whore 3beLh | Developer: moha"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "URL: " + EnumChatFormatting.GRAY + "https://www.youtube.com/@moha153"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "Discord: " + EnumChatFormatting.GRAY + "https://www.youtube.com/@moha153"));
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "Channel: " + EnumChatFormatting.GRAY + "https://www.youtube.com/@moha153"));
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
    

    private static boolean llIIlIlIlI(final int n) {
        return n != 0;
    }
}
