package me.sting.client.product.utils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.lwjgl.input.Mouse;

import com.google.common.collect.Ordering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class FieldUtil
{
    protected Minecraft mc;
    public Field button;
    public Field buttonstate;
    public Field buttons;
    public Field networkPlayerInfo;
    public Field rightClickDelayTimer;
    public Field shaderGroup;
    public Field pressed;
    
    public FieldUtil() {
        try {
            this.mc = Minecraft.getMinecraft();
            this.button = MouseEvent.class.getDeclaredField("button");
            final Field declaredField = MouseEvent.class.getDeclaredField("buttonstate");
            this.buttonstate = declaredField;
            this.buttons = Mouse.class.getDeclaredField("buttons");
        }
        catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        try {
            final Field declaredField2 = GuiPlayerTabOverlay.class.getDeclaredField("field_175252_a");
            (this.networkPlayerInfo = declaredField2).setAccessible(true);
        }
        catch (NoSuchFieldException ex2) {
            ex2.printStackTrace();
        }
        try {
            final Field declaredField3 = Minecraft.class.getDeclaredField("rightClickDelayTimer");
            this.rightClickDelayTimer = declaredField3;
        }
        catch (NoSuchFieldException ex5) {
            try {
                final Field declaredField4 = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                this.rightClickDelayTimer = declaredField4;
            }
            catch (NoSuchFieldException ex3) {
                ex3.printStackTrace();
            }
        }
        try {
            final Field declaredField5 = EntityRenderer.class.getDeclaredField("theShaderGroup");
            this.shaderGroup = declaredField5;
            final boolean accessible = true;
            declaredField5.setAccessible(accessible);
        }
        catch (NoSuchFieldException ex6) {
            try {
                final Field declaredField6 = EntityRenderer.class.getDeclaredField("theShaderGroup");
                this.shaderGroup = declaredField6;
                final boolean accessible2 = true;
                declaredField6.setAccessible(accessible2);
            }
            catch (NoSuchFieldException ex4) {
                ex4.printStackTrace();
            }
        }
        try {
            final Field declaredField7 = KeyBinding.class.getDeclaredField("pressed");
            (this.pressed = declaredField7).setAccessible(true);
        }
        catch (NoSuchFieldException | SecurityException ex7) {
            try {
                final Field declaredField8 = KeyBinding.class.getDeclaredField("pressed");
                this.pressed = declaredField8;
                final boolean accessible3 = true;
                declaredField8.setAccessible(accessible3);
            }
            catch (NoSuchFieldException | SecurityException ex8) {
                // final Throwable t;
                ex8.printStackTrace();
            }
        }
    }
    
    public void sendClick(final int n, final boolean b) {
        final MouseEvent mouseEvent = new MouseEvent();
        this.button.setAccessible(true);
        final Field button = this.button;
        final MouseEvent mouseEvent2 = mouseEvent;
        try {
            button.set(mouseEvent2, n);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        this.button.setAccessible(false);
        this.buttonstate.setAccessible(true);
        final Field buttonstate = this.buttonstate;
        final MouseEvent mouseEvent3 = mouseEvent;
        final Boolean value = b;
        try {
            buttonstate.set(mouseEvent3, value);
        }
        catch (IllegalAccessException ex2) {
            ex2.printStackTrace();
        }
        this.buttonstate.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
        this.buttons.setAccessible(true);
        try {
            final ByteBuffer byteBuffer = (ByteBuffer)this.buttons.get(null);
            final Field buttons = this.buttons;
            final boolean accessible = false;
            buttons.setAccessible(accessible);
            byteBuffer.put(n, (byte)(lIIIIlIllIl(b ? 1 : 0) ? 1 : 0));
        }
        catch (IllegalAccessException ex3) {
            ex3.printStackTrace();
        }
    }
    
    public Ordering getNetworkPlayerInfo(final Object o) {
        final Field networkPlayerInfo = this.networkPlayerInfo;
        try {
            return (Ordering)networkPlayerInfo.get(o);
        }
        catch (IllegalAccessException ex) {
            return null;
        }
    }
    
    public ShaderGroup shaderGroup(final Object o) {
        try {
            return (ShaderGroup)this.shaderGroup.get(o);
        }
        catch (IllegalArgumentException | IllegalAccessException ex) {
            // final Throwable t;
            ex.printStackTrace();
            return null;
        }
    }
    
    public void renderPressed(final Object o, final boolean b) {
        try {
            final Field declaredField = KeyBinding.class.getDeclaredField("pressed");
            this.pressed = declaredField;
            final boolean accessible = true;
            declaredField.setAccessible(accessible);
        }
        catch (NoSuchFieldException | SecurityException ex) {
            try {
                final Field declaredField2 = KeyBinding.class.getDeclaredField("pressed");
                (this.pressed = declaredField2).setAccessible(true);
            }
            catch (NoSuchFieldException | SecurityException ex2) {
                // final Throwable t;
                ex2.printStackTrace();
                return;
            }
        }
        final Field pressed = this.pressed;
        try {
            pressed.setBoolean(o, b);
        }
        catch (IllegalArgumentException | IllegalAccessException ex3) {
            // final Throwable t2;
            ex3.printStackTrace();
        }
    }
    
    private static boolean lIIIIlIllIl(final int n) {
        return n != 0;
    }
}
