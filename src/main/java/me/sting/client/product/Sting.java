package me.sting.client.product;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.sting.client.product.commands.ConfigCommand;
import me.sting.client.product.commands.EnemyCommand;
import me.sting.client.product.commands.FriendCommand;
import me.sting.client.product.commands.HelpCommand;
import me.sting.client.product.commands.InformationCommand;
import me.sting.client.product.commands.LoginerCommand;
import me.sting.client.product.commands.SpammerCommand;
import me.sting.client.product.gui.GraphicalUserInterface;
import me.sting.client.product.gui.elements.FrameElement;
import me.sting.client.product.managers.ModuleManager;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.movement.Sprint;
import me.sting.client.product.module.utilities.AntiStaffs;
import me.sting.client.product.module.utilities.Loginer;
import me.sting.client.product.utils.APIUtil;
import me.sting.client.product.utils.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = "Stink", version = "1.0.0", acceptedMinecraftVersions = "[1.8.9]")
public class Sting
{
    public static final String CLIENT_NAME = "Stink(Skidded From Dunk/Funk/FDP/AlSa7r clients)";
    public static final String CLIENT_VERSION_ROMATIC = "I";
    public static final String CLIENT_VERSION_NUMBER = "1.0.0";
    public static final String CLIENT_RELEASE_DATE = "1445/2024";
    public static final String CLIENT_CREATORS = "moha";
    public static final String CLIENT_DEVELOPERS = "@moha153";
    public static final String CLIENT_URL = "https://www.youtube.com/@moha153";
    public static final String CLIENT_DISCORD_URL = "https://discord.gg/";
    public static final String CLIENT_YOUTUBE_URL = "https://www.youtube.com/@moha153";
    public static final String CLIENT_PREFIX = EnumChatFormatting.DARK_AQUA + "[" + "Stink(Skidded From Dunk/Funk/FDP/AlSa7r clients)" + "-" + "I" + "] ";
    public static String SERVER = null;
    public static final File CLIENT_FILE = new File(Minecraft.getMinecraft().mcDataDir + "\\" + "Stink" + "Client");
    public static final File CLIENT_FILE_VERSION = new File(Sting.CLIENT_FILE + "\\" + "I");
    public static Color color = null;
    public static Color securityColor = null;
    public static ModuleManager moduleManager = new ModuleManager();
    public static GraphicalUserInterface gui = new GraphicalUserInterface();
    public static APIUtil api = new APIUtil();
    public static FontUtil frame = FontUtil.renderFont("Consolas", 17, true, true, true);
    public static FontUtil modules = FontUtil.renderFont("Consolas", 14, true, true, true);
    public static FontUtil others = FontUtil.renderFont("Consolas", 22, true, true, true);
    public static AntiStaffs antiStaffs = new AntiStaffs();

    
    public Sting() {
        Sting.CLIENT_FILE.mkdir();
        Sting.CLIENT_FILE_VERSION.mkdir();
    }
    
    @Mod.EventHandler
    public void onFML(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        Module.getModule(Sprint.class).setState(true);
        ClientCommandHandler.instance.registerCommand(new ConfigCommand());
        ClientCommandHandler.instance.registerCommand(new InformationCommand());
        ClientCommandHandler.instance.registerCommand(new HelpCommand());
        ClientCommandHandler.instance.registerCommand(new FriendCommand());
        ClientCommandHandler.instance.registerCommand(new EnemyCommand());
        ClientCommandHandler.instance.registerCommand(new SpammerCommand());
        ClientCommandHandler.instance.registerCommand(new LoginerCommand());
        if (LoginerCommand.pass.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(LoginerCommand.pass));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Loginer.password = line;
                }
                bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onConnectServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Sting.SERVER = event.manager.getRemoteAddress().toString();
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
//        System.out.println("HELLLLLL");
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        for (Module module : getModuleManager().modules) {
            if (module.key == Keyboard.getEventKey()) {
                 if (module.key == 0 && !(Minecraft.getMinecraft().currentScreen instanceof GraphicalUserInterface)) {
                     return;
                 }
                 for (FrameElement frameElement : GraphicalUserInterface.frames) {
                     if (module.key == 0 && frameElement.open) {
                         return;
                     }
                 }
                module.setState(!module.state);
                System.out.println(module.name + " is being activatedd!");
            }
        }
    }

    public static ModuleManager getModuleManager() {
        if (Sting.moduleManager == null) {
            Sting.moduleManager = new ModuleManager();
        }
        return Sting.moduleManager;
    }

    public static GraphicalUserInterface getGUI() {
        if (Sting.gui == null) {
            Sting.gui = new GraphicalUserInterface();
        }
        return Sting.gui;
    }

    public static APIUtil getAPI() {
        if (Sting.api == null) {
            Sting.api = new APIUtil();
        }
        return Sting.api;
    }

    static {
        
    }

}
