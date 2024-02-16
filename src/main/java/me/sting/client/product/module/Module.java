package me.sting.client.product.module;

import java.util.*;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.GraphicalUserInterface;
import me.sting.client.product.gui.elements.FrameElement;
import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.ComboValue;
import me.sting.client.product.gui.values.OptionValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.utils.BlockUtil;
import me.sting.client.product.utils.CombatUtil;
import me.sting.client.product.utils.FieldUtil;
import me.sting.client.product.utils.MovementUtil;
import me.sting.client.product.utils.RandomUtil;
import me.sting.client.product.utils.RenderUtil;
import me.sting.client.product.utils.RotationUtil;
import me.sting.client.product.utils.timers.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
// import scala.tools.nsc.doc.model.Class;

public class Module
{
    protected TimerUtil timer;
    protected FieldUtil field;
    protected BlockUtil block;
    protected CombatUtil combat;
    protected MovementUtil movement;
    protected RenderUtil render;
    protected RotationUtil rotation;
    protected RandomUtil random;
    public Minecraft mc;
    public ArrayList<SliderValue> sliders;
    public ArrayList<ComboValue> combos;
    public ArrayList<BooleanValue> booleans;
    public String name;
    public ModuleCategory category;
    public boolean state;
    public boolean security = false;
    public int key;
    
    public Module() {
        this.timer = new TimerUtil();
        this.field = new FieldUtil();
        this.block = new BlockUtil();
        this.combat = new CombatUtil();
        this.movement = new MovementUtil();
        this.render = new RenderUtil();
        this.rotation = new RotationUtil();
        this.random = new RandomUtil();
        this.mc = Minecraft.getMinecraft();
        this.sliders = new ArrayList();
        this.combos = new ArrayList();
        this.booleans = new ArrayList();
        this.name = "Stink Skidded";
        this.category = null;
        this.state = false;
//        this.security = false;
        this.key = 0;
    }
    
    public String getChatName() {
        return EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + this.name + ": ";
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void isPrivate() {
        // if (APIUtil.getUSERS()) {
//        this.security = true;
        // } else {
        //     this.security = true;
        //     this.setState(false);
        //     this.setKey(0);
        // }
    }

    public void addSlider(final SliderValue sliderValue) {
        this.sliders.add(sliderValue);
    }

    public void addCombo(final ComboValue comboValue) {
        this.combos.add(comboValue);
    }

    public ArrayList<OptionValue> addSetting(final String s) {
        final ArrayList<OptionValue> list = new ArrayList<>();
        for (ComboValue comboValue : this.combos) {
            if (comboValue.name.equalsIgnoreCase(s)) {
                for (OptionValue optionValue : comboValue.options) {
                    if (!list.contains(optionValue)) {
                        list.add(optionValue);
                    }
                }
            }
        }
        return list;
    }

    public void addBoolean(final BooleanValue booleanValue) {
        this.booleans.add(booleanValue);
    }

    public void setState(final boolean b) {
        if (this.state == b) {
            return;
        }
        for (FrameElement frameElement : GraphicalUserInterface.frames) {
            if (frameElement.open && this.security) {
                this.state = b;
            }
        }
        this.state = b;
        if (b) {
            MinecraftForge.EVENT_BUS.register(this);
            FMLCommonHandler.instance().bus().register(this);
            this.onEnable();
            System.out.println(this.name + " activated");
        } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            FMLCommonHandler.instance().bus().unregister(this);
            this.onDisable();
            System.out.println(this.name + " disactivated");
        }
    }

    public static ArrayList<Module> getCategoryModules(final ModuleCategory moduleCategory) {
        final ArrayList<Module> list = new ArrayList<>();
        for (Module module : Sting.getModuleManager().modules) {
            if (module.category == moduleCategory) {
                list.add(module);
            }
        }
        return list;
    }

    public static Module getModule(final Class clazz) {
        for (Module module : Sting.getModuleManager().modules) {
            if (module.getClass() == clazz) {
                return module;
            }
        }
        return null;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCategory(final ModuleCategory category) {
        this.category = category;
    }

    public void setSecurity(final boolean security) {
        this.security = security;
    }

    public void setKey(final int key) {
        this.key = key;
    }

}
