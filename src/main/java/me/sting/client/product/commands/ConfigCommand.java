package me.sting.client.product.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.ComboValue;
import me.sting.client.product.gui.values.OptionValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ConfigCommand implements ICommand
{
    private File[] config;
    
    public ConfigCommand() {
        this.config = new File[2];
        (this.config[0] = new File(Sting.CLIENT_FILE_VERSION + "\\Configs")).mkdir();
    }
    
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "@config";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/@config <save, remove, load> <NAME>";
    }
    
    public List getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("@c");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (llIlIIIllI(array.length, 2)) {
            if (!llIlIIIlll(array[0].equalsIgnoreCase("save") ? 1 : 0) || !llIlIIIlll(array[0].equalsIgnoreCase("s") ? 1 : 0) || !llIlIIIlll(array[0].equalsIgnoreCase("+") ? 1 : 0) || llIlIIlIII(array[0].equalsIgnoreCase("+s") ? 1 : 0)) {
                this.config[1] = new File(this.config[0].getAbsolutePath() + "\\" + array[1] + ".cfg");
                final File file = this.config[1];
                try {
                    this.saveCfg(file, commandSender);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if (!llIlIIIlll(array[0].equalsIgnoreCase("load") ? 1 : 0) || !llIlIIIlll(array[0].equalsIgnoreCase("l") ? 1 : 0) || !llIlIIIlll(array[0].equalsIgnoreCase("=") ? 1 : 0) || llIlIIlIII(array[0].equalsIgnoreCase("=l") ? 1 : 0)) {
                this.config[1] = new File(this.config[0].getAbsolutePath() + "\\" + array[1] + ".cfg");
                final File file2 = this.config[1];
                try {
                    this.loadCfg(file2, commandSender);
                }
                catch (IOException ex2) {
                    ex2.printStackTrace();
                }
            }
            else if (!llIlIIIlll(array[0].equalsIgnoreCase("remove") ? 1 : 0) || !llIlIIIlll(array[0].equalsIgnoreCase("r") ? 1 : 0) || !llIlIIIlll(array[0].equalsIgnoreCase("-") ? 1 : 0) || llIlIIlIII(array[0].equalsIgnoreCase("-r") ? 1 : 0)) {
                this.removeCfg(this.config[1] = new File(this.config[0].getAbsolutePath() + "\\" + array[1] + ".cfg"), commandSender);
            }
            else {
                commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
            }
        }
        else {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.DARK_RED + this.getCommandUsage(commandSender)));
        }
    }
    
    private void saveCfg(final File file, final ICommandSender commandSender) throws IOException {
        if (llIlIIIlll(file.exists() ? 1 : 0)) {
            file.createNewFile();
            final FileWriter fileWriter = new FileWriter(file);
            for (int n = 0; llIlIIlIIl(n, Sting.getModuleManager().modules.size()); ++n) {
                final Module module = (Module) Sting.getModuleManager().modules.get(n);
                for (int n2 = 0; llIlIIlIIl(n2, module.sliders.size()); ++n2) {
                    final SliderValue sliderValue = (SliderValue) module.sliders.get(n2);
                    fileWriter.write(module.name + ":" + module.key + ":" + module.state + ":" + sliderValue.name + ":" + sliderValue.getValue() + "\n");
                }
                for (int n3 = 0; llIlIIlIIl(n3, module.combos.size()); ++n3) {
                    final ComboValue comboValue = (ComboValue) module.combos.get(n3);
                    for (int n4 = 0; llIlIIlIIl(n4, comboValue.options.size()); ++n4) {
                        final OptionValue optionValue = (OptionValue) comboValue.options.get(n4);
                        fileWriter.write(module.name + ":" + module.key + ":" + module.state + ":" + comboValue.name + ":" + optionValue.name + ":" + optionValue.state + "\n");
                    }
                }
                for (int n5 = 0; llIlIIlIIl(n5, module.booleans.size()); ++n5) {
                    final BooleanValue booleanValue = (BooleanValue) module.booleans.get(n5);
                    fileWriter.write(module.name + ":" + module.key + ":" + module.state + ":" + booleanValue.name + ":" + booleanValue.state + "\n");
                }
            }
            fileWriter.close();
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + file.getName().replaceAll(".cfg", "") + EnumChatFormatting.GREEN + " config successfully saved."));
            return;
        }
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + file.getName().replaceAll(".cfg", "") + EnumChatFormatting.RED + " this config already exists, choose another config."));
    }
    
    private void loadCfg(final File file, final ICommandSender commandSender) throws IOException {
        if (llIlIIIlll(file.exists() ? 1 : 0)) {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + file.getName().replaceAll(".cfg", "") + EnumChatFormatting.RED + " this config doesn't exists, choose another config."));
            return;
        }
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while (llIlIIlIlI(line = bufferedReader.readLine())) {
            final String[] split = line.split(":");
            for (int n = 0; llIlIIlIIl(n, Sting.getModuleManager().modules.size()); ++n) {
                final Module module = (Module) Sting.getModuleManager().modules.get(n);
                if (!llIlIIIlll(split[0].equalsIgnoreCase(module.name) ? 1 : 0)) {
                    module.setKey(Integer.parseInt(split[1]));
                    module.setState(Boolean.parseBoolean(split[2]));
                    for (int n2 = 0; llIlIIlIIl(n2, module.sliders.size()); ++n2) {
                        final SliderValue sliderValue = (SliderValue) module.sliders.get(n2);
                        if (llIlIIlIII(split[3].equalsIgnoreCase(sliderValue.name) ? 1 : 0)) {
                            sliderValue.setValue(Double.parseDouble(split[4]));
                        }
                    }
                    for (int n3 = 0; llIlIIlIIl(n3, module.combos.size()); ++n3) {
                        final ComboValue comboValue = (ComboValue) module.combos.get(n3);
                        for (int n4 = 0; llIlIIlIIl(n4, comboValue.options.size()); ++n4) {
                            final OptionValue optionValue = (OptionValue) comboValue.options.get(n4);
                            if (llIlIIlIII(split[3].equalsIgnoreCase(comboValue.name) ? 1 : 0) && llIlIIlIII(split[4].equalsIgnoreCase(optionValue.name) ? 1 : 0)) {
                                optionValue.setState(Boolean.parseBoolean(split[5]));
                            }
                        }
                    }
                    for (int n5 = 0; llIlIIlIIl(n5, module.booleans.size()); ++n5) {
                        final BooleanValue booleanValue = (BooleanValue) module.booleans.get(n5);
                        if (llIlIIlIII(split[3].equalsIgnoreCase(booleanValue.name) ? 1 : 0)) {
                            booleanValue.setState(Boolean.parseBoolean(split[4]));
                        }
                    }
                }
            }
        }
        bufferedReader.close();
        commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + file.getName().replaceAll(".cfg", "") + " config successfully loaded."));
    }
    
    private void removeCfg(final File file, final ICommandSender commandSender) {
        if (llIlIIlIII(file.exists() ? 1 : 0)) {
            file.delete();
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + file.getName().replaceAll(".cfg", "") + EnumChatFormatting.GREEN + " config successfully removed."));
        }
        else {
            commandSender.addChatMessage((IChatComponent)new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GRAY + file.getName().replaceAll(".cfg", "") + EnumChatFormatting.RED + " this config doesn't exists, choose another config."));
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
//    
//    public int compareTo(final Object o) {
//        return this.compareTo((ICommand)o);
//    }
//    
    private static boolean llIlIIIllI(final int n, final int n2) {
        return n == n2;
    }
    
    private static boolean llIlIIlIIl(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean llIlIIlIlI(final Object o) {
        return o != null;
    }
    
    private static boolean llIlIIlIII(final int n) {
        return n != 0;
    }
    
    private static boolean llIlIIIlll(final int n) {
        return n == 0;
    }
}
