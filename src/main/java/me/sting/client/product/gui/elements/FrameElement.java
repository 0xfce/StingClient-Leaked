package me.sting.client.product.gui.elements;

import java.awt.Color;
import java.util.ArrayList;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.Element;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class FrameElement
{
    public ArrayList<Element> elements;
    public ModuleCategory category;
    public RenderUtil render;
    public boolean dragging;
    public boolean open;
    public int x;
    public int y;
    public int height;
    public int width;
    public int dragX;
    public int dragY;
    
    public FrameElement(final ModuleCategory category) {
        this.elements = new ArrayList<Element>();
        this.category = null;
        this.render = new RenderUtil();
        this.dragging = false;
        this.open = false;
        this.x = 2;
        this.y = 25;
        this.height = 15;
        this.width = 100;
        this.dragX = 0;
        this.dragY = 0;
        this.category = category;
//        int height = this.height;
        for (int n = 0; n < Module.getCategoryModules(this.category).size(); ++n) {
            this.elements.add(new ButtonElement(Module.getCategoryModules(this.category).get(n), this, height));
            height += 0;
        }
    }
    
    public void renderFrame() {
        Gui.drawRect(this.x - 1, this.y - 3, this.x + this.width + 1, this.y + this.height - 2, new Color(0, 0, 25).getRGB());
        this.render.drawCircle(this.x + 3, this.y - 2, 4, 360, new Color(0, 0, 25).getRGB());
        this.render.drawCircle(this.x + this.width - 3, this.y - 2, 4, 360, new Color(0, 0, 25).getRGB());
        Gui.drawRect(this.x + 4, this.y - 6, this.x + this.width - 4, this.y + this.height - 16, new Color(0, 0, 25).getRGB());
        if (this.open|| !this.elements.isEmpty()) {
            this.render.drawCircle(this.x + 3, this.y + 12, 4, 360, new Color(0, 0, 25).getRGB());
            this.render.drawCircle(this.x + this.width - 3, this.y + 12, 4, 360, new Color(0, 0, 25).getRGB());
            Gui.drawRect(this.x + 4, this.y + 11, this.x + this.width - 4, this.y + this.height + 1, new Color(0, 0, 25).getRGB());
        }
        Minecraft.getMinecraft().fontRendererObj.drawString(this.category.name.toUpperCase(), this.x + this.width - 64, this.y, new Color(200, 200, 200).getRGB());
//        Sting.frame.drawString(this.category.name.toUpperCase(), this.x + this.width - 64, this.y, new Color(200, 200, 200).getRGB(), true, true);
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("sting") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "sting.png"), this.x, this.y - 7, 35.0f, 23.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("combat") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "combat.png"), this.x + 4, this.y - 2, 18.0f, 14.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("movement") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "alhilal.png"), this.x + 7, this.y - 2, 11.0f, 13.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("blatant") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "blatant.png"), this.x + 4, this.y - 2, 13.0f, 13.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("render") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "render.png"), this.x + 4, this.y - 2, 18.0f, 14.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("utilities") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "utilities.png"), this.x + 4, this.y - 2, 14.0f, 14.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("moderator") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "moderator.png"), this.x + 4, this.y, 10.0f, 10.0f, new Color(255, 255, 255).getRGB());
//        }
//        if (llIlIllIII(this.category.name.equalsIgnoreCase("hud") ? 1 : 0)) {
//            this.render.drawTexturedRectangle(new ResourceLocation("sting", "sting.png"), this.x, this.y - 7, 35.0f, 23.0f, new Color(255, 255, 255).getRGB());
//        }
        if (!this.elements.isEmpty()) {
            this.render.drawPolygon(this.x + this.width - 12, this.y + 5, 3.0, 3, -1, this.open);
        }
        if (this.open && !this.elements.isEmpty()) {
            for (int n = 0; n < this.elements.size(); ++n) {
                ((Element)this.elements.get(n)).render();
            }
            Gui.drawRect(this.x - 1, this.y + 11, this.x + this.width + 1, this.y + this.height, new Color(0, 0, 25).getRGB());
        }
    }
    
    public void refresh() {
        int height = this.height;
        for (int n = 0; n < this.elements.size(); ++n) {
            final Element element = (Element) this.elements.get(n);
            if (element instanceof ButtonElement) {
                final ButtonElement buttonElement = (ButtonElement)element;
                int height2 = buttonElement.getHeight();
                for (int n2 = 0; n2 < buttonElement.elements.size(); ++n2) {
                    final Element element2 = (Element) buttonElement.elements.get(n2);
                    if (element2 instanceof OptionElement) {
                        final OptionElement optionElement = (OptionElement)element2;
                        if (buttonElement.open && this.elements.indexOf(buttonElement) < this.elements.size() - 1) {
                            ((Element)this.elements.get(this.elements.indexOf(buttonElement) + 1)).setHeight(height);
                            height2 += optionElement.getHeight() - 16;
                        }
                    }
                }
                buttonElement.setHeight(height);
                height += height2;
            }
        }
    }
    
    public void isWithinHeader(final int n, final int n2) {
        if (llIlIllIII(this.dragging ? 1 : 0)) {
            this.setX(n - this.dragX);
            this.setY(n2 - this.dragY);
        }
    }
    
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public boolean isMouseOnButton(final int n, final int n2) {
        return llIlIllIll(n, this.x - 3) && llIlIlllIl(n, this.x + this.width) && llIlIllIll(n2, this.y - 6) && llIlIlllIl(n2, this.y + this.height);
    }
    
    private static boolean llIlIllIll(final int n, final int n2) {
        return n >= n2;
    }
    
    private static boolean llIlIlIlll(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean llIlIlllIl(final int n, final int n2) {
        return n <= n2;
    }
    
    private static boolean llIlIllIII(final int n) {
        return n != 0;
    }
    
    private static boolean llIlIllIIl(final int n) {
        return n == 0;
    }
}
