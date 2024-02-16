package me.sting.client.product.module.category;


import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GUI extends Module
{
    @RetentionField
    public SliderValue red;
    @RetentionField
    public SliderValue green;
    @RetentionField
    public SliderValue blue;
    @RetentionField
    public static BooleanValue background;
    @RetentionField
    public BooleanValue rainbow;
    
    public GUI() {
        this.red = new SliderValue("Red", 15.0, 0.0, 255.0, true);
        this.green = new SliderValue("Green", 75.0, 0.0, 255.0, true);
        this.blue = new SliderValue("Blue", 175.0, 0.0, 255.0, true);
        this.rainbow = new BooleanValue("Rainbow", false);
        this.setName("GUI");
        this.setKey(Keyboard.KEY_RSHIFT);
        this.setCategory(ModuleCategory.Sting);
    }
    
    @Override
    public void onEnable() {
        this.mc.displayGuiScreen((GuiScreen)Sting.gui);
        this.setState(this.state);
    }
    
    @Override
    public void onDisable() {
        if (this.state) {
            this.setState(true);
        }
    }
    
    @SubscribeEvent
    public void Tick(final TickEvent tickEvent) {
        Sting.color = new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue());
        if (this.rainbow.state) {
            Sting.color = new Color(Color.HSBtoRGB((System.currentTimeMillis() + 1500L) % 5000L / 4750.0f, 0.8f, 0.8f));
        }
    }
    
    static {
        GUI.background = new BooleanValue("Background", true);
    }
    
}
