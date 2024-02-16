package me.sting.client.product.managers;

import java.lang.reflect.Field;
import java.util.ArrayList;

import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.ComboValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.blatant.AntiVoid;
import me.sting.client.product.module.blatant.AutoHeal;
import me.sting.client.product.module.blatant.AutoPlace;
import me.sting.client.product.module.blatant.Cracking;
import me.sting.client.product.module.blatant.RodMethod;
import me.sting.client.product.module.blatant.Stealer;
import me.sting.client.product.module.category.BLATANT;
import me.sting.client.product.module.category.COMBAT;
import me.sting.client.product.module.category.GUI;
import me.sting.client.product.module.category.MODERATOR;
import me.sting.client.product.module.category.MOVEMENT;
import me.sting.client.product.module.category.QuickCheck;
import me.sting.client.product.module.category.RENDER;
import me.sting.client.product.module.category.UTILITIES;
import me.sting.client.product.module.combat.AutoClicker;
import me.sting.client.product.module.combat.Criticals;
import me.sting.client.product.module.combat.KillAura;
import me.sting.client.product.module.combat.Reach;
import me.sting.client.product.module.combat.Velocity;
import me.sting.client.product.module.disablers.FakeLag;
import me.sting.client.product.module.disablers.LatestVerus;
import me.sting.client.product.module.disablers.VulcanHop;
import me.sting.client.product.module.moderator.Muter;
import me.sting.client.product.module.moderator.Reporter;
import me.sting.client.product.module.movement.AutoArmor;
import me.sting.client.product.module.movement.Fly;
import me.sting.client.product.module.movement.Inventory;
import me.sting.client.product.module.movement.KeepSprint;
import me.sting.client.product.module.movement.Speed;
import me.sting.client.product.module.movement.SpeedBridge;
import me.sting.client.product.module.movement.Sprint;
import me.sting.client.product.module.movement.Tiimer;
import me.sting.client.product.module.render.Arraylist;
import me.sting.client.product.module.render.Chams;
import me.sting.client.product.module.render.Tracer;
import me.sting.client.product.module.render.XRay;
import me.sting.client.product.module.utilities.AntiStaffs;
import me.sting.client.product.module.utilities.Disabler;
import me.sting.client.product.module.utilities.Displayer;
import me.sting.client.product.module.utilities.Loginer;
import me.sting.client.product.module.utilities.Spammer;
import me.sting.client.product.storage.RetentionField;

public class ModuleManager
{
    public ArrayList<Module> modules = new ArrayList<Module>();
    
    public ModuleManager() {
        this.modules.add(new COMBAT());
        this.modules.add(new MOVEMENT());
        this.modules.add(new BLATANT());
        this.modules.add(new RENDER());
        this.modules.add(new UTILITIES());
        this.modules.add(new MODERATOR());
        this.modules.add(new GUI());
        this.modules.add(new Reach());
        this.modules.add(new AutoClicker());
        this.modules.add(new KillAura());
        this.modules.add(new Criticals());
        this.modules.add(new Velocity());
        this.modules.add(new AutoArmor());
        this.modules.add(new SpeedBridge());
        this.modules.add(new Tiimer());
        this.modules.add(new Fly());
        this.modules.add(new Speed());
        this.modules.add(new KeepSprint());
        this.modules.add(new Sprint());
        this.modules.add(new Inventory());
        this.modules.add(new AutoHeal());
        this.modules.add(new AutoPlace());
        this.modules.add(new AntiVoid());
        this.modules.add(new RodMethod());
        this.modules.add(new Cracking());
        this.modules.add(new Stealer());
        this.modules.add(new Arraylist());
        this.modules.add(new Chams());
        this.modules.add(new Tracer());
        this.modules.add(new XRay());
        this.modules.add(new AntiStaffs());
        this.modules.add(new Loginer());
        this.modules.add(new Disabler());
        this.modules.add(new Displayer());
        this.modules.add(new Spammer());
        this.modules.add(new Reporter());
        this.modules.add(new Muter());
        this.modules.add(new QuickCheck());
        this.modules.add(new FakeLag());
        this.modules.add(new LatestVerus());
        this.modules.add(new VulcanHop());
        this.registerSettings();
    }
    
    public void registerSettings() {
        for (Module module : this.modules) {
            for (Field field : module.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(RetentionField.class)) {
                    field.setAccessible(true);
                    try {
                        if (field.getType().isAssignableFrom(SliderValue.class)) {
                            SliderValue sliderValue = (SliderValue) field.get(module);
                            if (!module.sliders.contains(sliderValue)) {
                                module.addSlider(sliderValue);
                                System.out.println("Slider added in " + module.name);
                            }
                        } else if (field.getType().isAssignableFrom(ComboValue.class)) {
                            ComboValue comboValue = (ComboValue) field.get(module);
                            if (!module.combos.contains(comboValue)) {
                                module.addCombo(comboValue);
                                System.out.println("Combo added in " + module.name);
                            }
                        } else if (field.getType().isAssignableFrom(BooleanValue.class)) {
                            BooleanValue booleanValue = (BooleanValue) field.get(module);
                            if (!module.booleans.contains(booleanValue)) {
                                module.addBoolean(booleanValue);
                                System.out.println("Boolean added in " + module.name);
                            }
                        }
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

}
