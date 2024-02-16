package me.sting.client.product.gui;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.elements.ButtonElement;
import me.sting.client.product.gui.elements.FrameElement;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.module.category.BLATANT;
import me.sting.client.product.module.category.COMBAT;
import me.sting.client.product.module.category.GUI;
import me.sting.client.product.module.category.MODERATOR;
import me.sting.client.product.module.category.MOVEMENT;
import me.sting.client.product.module.category.RENDER;
import me.sting.client.product.module.category.UTILITIES;
import me.sting.client.product.module.render.Arraylist;
import me.sting.client.product.module.utilities.AntiStaffs;
import me.sting.client.product.module.utilities.Disabler;
import me.sting.client.product.module.utilities.Displayer;
import me.sting.client.product.utils.FieldUtil;
import me.sting.client.product.utils.FontUtil;
import me.sting.client.product.utils.MethodUtil;
import me.sting.client.product.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GraphicalUserInterface extends GuiScreen
{
    public static ArrayList<FrameElement> frames;
    public RenderUtil render;
    public FieldUtil field;
    public MethodUtil method;
    public static boolean[] state;
    public static int[] x;
    public static int[] y;
    
    public GraphicalUserInterface() {
        this.render = new RenderUtil();
        this.field = new FieldUtil();
        this.method = new MethodUtil();
        GraphicalUserInterface.state[0] = true;
        GraphicalUserInterface.state[1] = true;
        GraphicalUserInterface.state[2] = true;
        GraphicalUserInterface.state[3] = true;
        GraphicalUserInterface.state[4] = true;
        GraphicalUserInterface.state[5] = true;
        GraphicalUserInterface.state[6] = true;
        GraphicalUserInterface.state[7] = true;
        int x = 5;
        ModuleCategory[] values;
        for (int length = (values = ModuleCategory.values()).length, n = 0; lllIIlIIIl(n, length); ++n) {
            final FrameElement frameElement = new FrameElement(values[n]);
            frameElement.setX(x);
            frameElement.setY(50);
            GraphicalUserInterface.frames.add(frameElement);
            x += frameElement.width + 3;
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
//        Sting.frame.drawString("StingClient(Skidded from AlSa7r/Dunk/Funk/FDP clients) ", 7.0f, 5.0f, Color.WHITE.getRGB(), true, false);
    	mc.fontRendererObj.drawStringWithShadow("StinkClient(Skidded from AlSa7r/Dunk/Funk/FDP clients) ", 7.0f, 5.0f, Color.WHITE.getRGB());

        String string = null;
		try {
			string = InetAddress.getLocalHost().getHostName().replace("DESKTOP-", "[") + "]";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        final float n4 = 14.0f;
        final float n5 = 18.0f;
        final int rgb = Color.BLUE.getRGB();
//      final FontUtil frame = Sting.frame;
        final boolean b = true;
        final boolean b2 = false;
//        frame.drawString(string, n4, n5, rgb, b, b2);
        Minecraft.getMinecraft().fontRendererObj.drawString(string, n4, n5, rgb, b);
        for (int n6 = 0; lllIIlIIIl(n6, GraphicalUserInterface.frames.size()); ++n6) {
            final FrameElement frameElement = (FrameElement) GraphicalUserInterface.frames.get(n6);
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("sting") ? 1 : 0) && lllIIlIIll(frameElement.category.id) && lllIIlIIlI(Module.getModule(GUI.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("combat") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 1) && lllIIlIIlI(Module.getModule(COMBAT.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("movement") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 2) && lllIIlIIlI(Module.getModule(MOVEMENT.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("blatant") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 3) && lllIIlIIlI(Module.getModule(BLATANT.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("render") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 4) && lllIIlIIlI(Module.getModule(RENDER.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("utilities") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 5) && lllIIlIIlI(Module.getModule(UTILITIES.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("moderator") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 6) && lllIIlIIlI(Module.getModule(MODERATOR.class).state ? 1 : 0)) {
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[x] disablers") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 7) && lllIIlIIlI(Module.getModule(Disabler.class).state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[0] ? 1 : 0) && lllIIlIIlI(Module.getModule(Disabler.class).state ? 1 : 0)) {
                    GraphicalUserInterface.state[0] = false;
                    frameElement.setX(10);
                    frameElement.setY(200);
                }
                GraphicalUserInterface.x[0] = frameElement.x;
                GraphicalUserInterface.y[0] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[x] modules") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 8) && lllIIlIIlI(Module.getModule(Arraylist.class).state ? 1 : 0) && lllIIlIIlI(Arraylist.arrays.combos[1].state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[1] ? 1 : 0) && lllIIlIIlI(Module.getModule(Arraylist.class).state ? 1 : 0) && lllIIlIIlI(Arraylist.arrays.combos[1].state ? 1 : 0)) {
                    GraphicalUserInterface.state[1] = false;
                    frameElement.setX(110);
                    frameElement.setY(200);
                }
                GraphicalUserInterface.x[1] = frameElement.x;
                GraphicalUserInterface.y[1] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[x] displayer") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 9) && lllIIlIIlI(Module.getModule(Displayer.class).state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[2] ? 1 : 0) && lllIIlIIlI(Module.getModule(Displayer.class).state ? 1 : 0)) {
                    GraphicalUserInterface.state[2] = false;
                    frameElement.setX(210);
                    frameElement.setY(200);
                }
                GraphicalUserInterface.x[2] = frameElement.x;
                GraphicalUserInterface.y[2] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[#] friends") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 10) && lllIIlIIlI(Module.getModule(Arraylist.class).state ? 1 : 0) && lllIIlIIlI(Arraylist.arrays.combos[2].state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[3] ? 1 : 0) && lllIIlIIlI(Module.getModule(Arraylist.class).state ? 1 : 0) && lllIIlIIlI(Arraylist.arrays.combos[2].state ? 1 : 0)) {
                    GraphicalUserInterface.state[3] = false;
                    frameElement.setX(310);
                    frameElement.setY(200);
                }
                GraphicalUserInterface.x[3] = frameElement.x;
                GraphicalUserInterface.y[3] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[#] enemys") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 11) && lllIIlIIlI(Module.getModule(Arraylist.class).state ? 1 : 0) && lllIIlIIlI(Arraylist.arrays.combos[3].state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[4] ? 1 : 0) && lllIIlIIlI(Module.getModule(Arraylist.class).state ? 1 : 0) && lllIIlIIlI(Arraylist.arrays.combos[3].state ? 1 : 0)) {
                    GraphicalUserInterface.state[4] = false;
                    frameElement.setX(410);
                    frameElement.setY(200);
                }
                GraphicalUserInterface.x[4] = frameElement.x;
                GraphicalUserInterface.y[4] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[#] staffs") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 12) && lllIIlIIlI(Module.getModule(AntiStaffs.class).state ? 1 : 0) && lllIIlIIlI(AntiStaffs.modes.combos[1].state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[5] ? 1 : 0) && lllIIlIIlI(Module.getModule(AntiStaffs.class).state ? 1 : 0) && lllIIlIIlI(AntiStaffs.modes.combos[1].state ? 1 : 0)) {
                    GraphicalUserInterface.state[5] = false;
                    frameElement.setX(510);
                    frameElement.setY(200);
                }
                GraphicalUserInterface.x[5] = frameElement.x;
                GraphicalUserInterface.y[5] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[!] checks") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 13) && lllIIlIIlI(Module.getModule(AntiStaffs.class).state ? 1 : 0) && lllIIlIIlI(AntiStaffs.modes.combos[2].state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[6] ? 1 : 0) && lllIIlIIlI(Module.getModule(AntiStaffs.class).state ? 1 : 0) && lllIIlIIlI(AntiStaffs.modes.combos[2].state ? 1 : 0)) {
                    GraphicalUserInterface.state[6] = false;
                    frameElement.setX(10);
                    frameElement.setY(350);
                }
                GraphicalUserInterface.x[6] = frameElement.x;
                GraphicalUserInterface.y[6] = frameElement.y;
                frameElement.renderFrame();
            }
            if (lllIIlIIlI(frameElement.category.name.equalsIgnoreCase("[!] radar") ? 1 : 0) && lllIIlIlII(frameElement.category.id, 14) && lllIIlIIlI(Module.getModule(AntiStaffs.class).state ? 1 : 0) && lllIIlIIlI(AntiStaffs.modes.combos[3].state ? 1 : 0)) {
                if (lllIIlIIlI(GraphicalUserInterface.state[7] ? 1 : 0) && lllIIlIIlI(Module.getModule(AntiStaffs.class).state ? 1 : 0) && lllIIlIIlI(AntiStaffs.modes.combos[3].state ? 1 : 0)) {
                    GraphicalUserInterface.state[7] = false;
                    frameElement.setX(110);
                    frameElement.setY(350);
                }
                GraphicalUserInterface.x[7] = frameElement.x;
                GraphicalUserInterface.y[7] = frameElement.y;
                frameElement.renderFrame();
            }
            frameElement.isWithinHeader(n, n2);
            for (int n7 = 0; lllIIlIIIl(n7, frameElement.elements.size()); ++n7) {
                ((Element)frameElement.elements.get(n7)).updateComponent(n, n2);
            }
        }
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
        for (int n4 = 0; lllIIlIIIl(n4, GraphicalUserInterface.frames.size()); ++n4) {
            ((FrameElement)GraphicalUserInterface.frames.get(n4)).setDragging(false);
        }
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        for (int n4 = 0; lllIIlIIIl(n4, GraphicalUserInterface.frames.size()); ++n4) {
            final FrameElement frameElement = (FrameElement) GraphicalUserInterface.frames.get(n4);
            if (lllIIlIIlI(frameElement.isMouseOnButton(n, n2) ? 1 : 0) && lllIIlIIll(n3)) {
                frameElement.setDragging(true);
                frameElement.dragX = n - frameElement.x;
                frameElement.dragY = n2 - frameElement.y;
            }
            if (lllIIlIIlI(frameElement.isMouseOnButton(n, n2) ? 1 : 0) && lllIIlIlII(n3, 1)) {
                frameElement.setOpen(lllIIlIIll((int)(frameElement.open ? 1 : 0)));
            }
            if (lllIIlIIlI(frameElement.open ? 1 : 0) && lllIIlIIll(frameElement.elements.isEmpty() ? 1 : 0)) {
                for (int n5 = 0; lllIIlIIIl(n5, frameElement.elements.size()); ++n5) {
                    ((Element)frameElement.elements.get(n5)).mouseClicked(n, n2, n3);
                }
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void initGui() {
        if (lllIIlIIlI(OpenGlHelper.shadersSupported ? 1 : 0) && lllIIlIIlI((this.mc.getRenderViewEntity() instanceof EntityPlayer) ? 1 : 0)) {
            if (lllIIlIlIl(this.field.shaderGroup(this.mc.entityRenderer))) {
                this.field.shaderGroup(this.mc.entityRenderer).deleteShaderGroup();
            }
            if (lllIIlIIlI(GUI.background.state ? 1 : 0)) {
                this.method.renderShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
    }
    
    public void keyTyped(final char c, final int n) {
        for (int n2 = 0; lllIIlIIIl(n2, GraphicalUserInterface.frames.size()); ++n2) {
            final FrameElement frameElement = (FrameElement) GraphicalUserInterface.frames.get(n2);
            if (lllIIlIIlI(frameElement.open ? 1 : 0) && lllIIlIlll(n, 1) && lllIIlIIll(frameElement.elements.isEmpty() ? 1 : 0)) {
                for (int n3 = 0; lllIIlIIIl(n3, frameElement.elements.size()); ++n3) {
                    ((Element)frameElement.elements.get(n3)).keyTyped(c, n);
                }
            }
        }
        if (lllIIlIlII(n, 1)) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public void onGuiClosed() {
        if (lllIIlIlIl(this.field.shaderGroup(this.mc.entityRenderer))) {
            this.field.shaderGroup(this.mc.entityRenderer).deleteShaderGroup();
        }
        for (int n = 0; lllIIlIIIl(n, GraphicalUserInterface.frames.size()); ++n) {
            final FrameElement frameElement = (FrameElement) GraphicalUserInterface.frames.get(n);
            for (int n2 = 0; lllIIlIIIl(n2, frameElement.elements.size()); ++n2) {
                final Element element = (Element) frameElement.elements.get(n2);
                if (lllIIlIIlI((element instanceof ButtonElement) ? 1 : 0)) {
                    final ButtonElement buttonElement = (ButtonElement)element;
                    if (lllIIlIIlI(buttonElement.binding ? 1 : 0)) {
                        buttonElement.binding = false;
                    }
                }
            }
        }
    }
    
    static {
        GraphicalUserInterface.frames = new ArrayList<FrameElement>();
        GraphicalUserInterface.state = new boolean[8];
        GraphicalUserInterface.x = new int[8];
        GraphicalUserInterface.y = new int[8];
    }
    
    private static boolean lllIIlIlII(final int n, final int n2) {
        return n == n2;
    }
    
    private static boolean lllIIlIIIl(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean lllIIlIlIl(final Object o) {
        return o != null;
    }
    
    private static boolean lllIIlIIlI(final int n) {
        return n != 0;
    }
    
    private static boolean lllIIlIIll(final int n) {
        return n == 0;
    }
    
    private static boolean lllIIlIlll(final int n, final int n2) {
        return n != n2;
    }
}
