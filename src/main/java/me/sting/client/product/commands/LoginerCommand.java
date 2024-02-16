package me.sting.client.product.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.sting.client.product.Sting;
import me.sting.client.product.module.utilities.Loginer;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class LoginerCommand implements ICommand
{
    public static File pass;
    
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@loginer";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@loginer <setpassword> <PASSWORD>";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@l");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (lllllllIII(array.length, 2)) {
            if (!lllllllIlI(array[0].equalsIgnoreCase("setpassword") ? 1 : 0) || !lllllllIlI(array[0].equalsIgnoreCase("sp") ? 1 : 0) || !lllllllIlI(array[0].equalsIgnoreCase("s") ? 1 : 0) || llllllllII(array[0].equalsIgnoreCase("p") ? 1 : 0)) {
                Loginer.password = array[1];
                if (lllllllIlI(LoginerCommand.pass.exists() ? 1 : 0)) {
                    try {
						LoginerCommand.pass.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                Label_0124: {
                    if (!llllllllII(LoginerCommand.pass.exists() ? 1 : 0)) {
                        break Label_0124;
                    }
                    FileWriter fileWriter = null;
					try {
						fileWriter = new FileWriter(LoginerCommand.pass);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    try {
						fileWriter.write(array[1]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    final FileWriter fileWriter2 = fileWriter;
                    try {
                        fileWriter2.close();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (llllllllIl(Loginer.password)) {
                    commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + Loginer.prefix + EnumChatFormatting.GREEN + "password set to " + EnumChatFormatting.DARK_AQUA + Loginer.password));
                }
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        else {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
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
    
//    public int compareTo(final Object o) {
//        return this.compareTo((ICommand)o);
//    }
    
    static {
        LoginerCommand.pass = new File(Sting.CLIENT_FILE_VERSION + "\\Loginer Password.txt");
    }
    
    private static boolean lllllllIII(final int n, final int n2) {
        return n == n2;
    }
    
    private static boolean llllllllIl(final Object o) {
        return o != null;
    }
    
    private static boolean llllllllII(final int n) {
        return n != 0;
    }
    
    private static boolean lllllllIlI(final int n) {
        return n == 0;
    }
}
