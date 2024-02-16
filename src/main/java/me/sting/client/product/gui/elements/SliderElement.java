package me.sting.client.product.gui.elements;

import me.sting.client.product.gui.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.utils.*;
import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import me.sting.client.product.module.*;
import me.sting.client.product.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import com.ibm.icu.math.*;

public class SliderElement extends Element
{
    public SliderValue slider;
    public RenderUtil render;
    public ButtonElement button;
    public boolean hovered;
    public int x;
    public int y;
    public int height;
    
    public SliderElement(final SliderValue slider, final ButtonElement button, final int height) {
        this.slider = null;
        this.render = new RenderUtil();
        this.button = null;
        this.hovered = false;
        this.x = 0;
        this.y = 0;
        this.height = 0;
        this.slider = slider;
        this.button = button;
        this.height = height;
    }
    
    @Override
    public void render() {
        final int n = (int)(this.slider.getValue() / this.slider.max * 70.0);
        Gui.drawRect(this.button.frame.x - 1, this.button.frame.y + this.height, this.button.frame.x + this.button.frame.width + 1, this.button.frame.y + 16 + this.height, new Color(0, 0, 20, 255).getRGB());
        if (llIIIIlIll(Module.getModule(this.button.module.getClass()).sliders.get(Module.getModule(this.button.module.getClass()).sliders.size() - 1), this.slider)) {
            this.render.drawCircle(this.button.frame.x + 3, this.button.frame.y + this.height + 15, 4, 360, new Color(0, 0, 20).getRGB());
            this.render.drawCircle(this.button.frame.x + this.button.frame.width - 3, this.button.frame.y + this.height + 15, 4, 360, new Color(0, 0, 20).getRGB());
            Gui.drawRect(this.button.frame.x + 4, this.button.frame.y + this.height + 16, this.button.frame.x + this.button.frame.width - 4, this.button.frame.y + 19 + this.height, new Color(0, 0, 20).getRGB());
        }
        Gui.drawRect(this.button.frame.x - 1, this.button.frame.y + 13 + this.height, this.button.frame.x + this.button.frame.width + 1, this.button.frame.y + 11 + this.height, new Color(50, 50, 50).getRGB());
        Gui.drawRect(this.button.frame.x - 1, this.button.frame.y + 13 + this.height, this.button.frame.x + 14 + n + 8, this.button.frame.y + 11 + this.height, Sting.color.getRGB());
        this.render.drawCircle(this.button.frame.x + 17 + n + 8, this.button.frame.y + 12 + this.height, 3, 10, new Color(0, 0, 20, 255).getRGB());
        this.render.drawCircle(this.button.frame.x + 19 + n + 8, this.button.frame.y + 12 + this.height, 3, 10, new Color(0, 0, 20, 255).getRGB());
        this.render.drawCircle(this.button.frame.x + 18 + n + 8, this.button.frame.y + 12 + this.height, 3, 10, new Color(255, 255, 255).getRGB());
        if (llIIIIllII(this.hovered ? 1 : 0)) {
            this.render.drawCircle(this.button.frame.x + 18 + n + 8, this.button.frame.y + 12 + this.height, 4, 20, new Color(200, 200, 200, 100).getRGB());
        }
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
//        Sting.others.drawString(this.slider.name, this.button.frame.x * 2 + 1.0f, (this.button.frame.y + this.height - 1) * 2 + 5, -1, true, false);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.slider.name, this.button.frame.x * 2 + 1.0f, (this.button.frame.y + this.height - 1) * 2 + 5, -1);
//        Sting.others.drawString(llIIIIllII(this.slider.name.equalsIgnoreCase("percentage") ? 1 : 0) ? (" %" + (llIIIIllII(this.slider.big ? 1 : 0) ? String.valueOf(this.slider.getValue()).substring(0, String.valueOf(this.slider.getValue()).indexOf(".")) : this.slider.getValue())) : (" " + (llIIIIllII(this.slider.big ? 1 : 0) ? String.valueOf(this.slider.getValue()).substring(0, String.valueOf(this.slider.getValue()).indexOf(".")) : this.slider.getValue())), this.button.frame.x * 2 + this.button.frame.width + 70, (this.button.frame.y + this.height - 1) * 2 + 5, -1, true, true);
        Minecraft.getMinecraft().fontRendererObj.drawString(llIIIIllII(this.slider.name.equalsIgnoreCase("percentage") ? 1 : 0) ? (" %" + (llIIIIllII(this.slider.big ? 1 : 0) ? String.valueOf(this.slider.getValue()).substring(0, String.valueOf(this.slider.getValue()).indexOf(".")) : this.slider.getValue())) : (" " + (llIIIIllII(this.slider.big ? 1 : 0) ? String.valueOf(this.slider.getValue()).substring(0, String.valueOf(this.slider.getValue()).indexOf(".")) : this.slider.getValue())), this.button.frame.x * 2 + this.button.frame.width + 70, (this.button.frame.y + this.height - 1) * 2 + 5, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void setHeight(final int height) {
        this.height = height;
    }
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.hovered = (!llIIIIllIl(this.isMouseOnButtonI(n, n2) ? 1 : 0) || llIIIIllII(this.isMouseOnButtonD(n, n2) ? 1 : 0));
        this.y = this.button.frame.y + this.height;
        this.x = this.button.frame.x;
        if (llIIIIllII(this.hovered ? 1 : 0) && llIIIIllII(Mouse.isButtonDown(0) ? 1 : 0)) {
            this.slider.setValue(this.round((n - this.button.frame.x) / (this.button.frame.width - 1) * this.slider.max, 1));
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (llIIIIllII(this.isMouseOnButtonD(n, n2) ? 1 : 0) && llIIIIllIl(n3)) {
            final SliderValue slider = this.slider;
            slider.setValue(Math.round((slider.getValue() - 0.1) * 10.0) / 10.0);
        }
        if (llIIIIllII(this.isMouseOnButtonI(n, n2) ? 1 : 0) && llIIIIllIl(n3)) {
            final SliderValue slider2 = this.slider;
            slider2.setValue(Math.round((slider2.getValue() + 0.1) * 10.0) / 10.0);
        }
    }
    
    public boolean isMouseOnButtonI(final int n, final int n2) {
        return llIIIIllII(this.button.frame.open ? 1 : 0) && llIIIIllII(this.button.open ? 1 : 0) && (llIIIIlllI(n, this.x + this.button.frame.width / 2) && llIIIIllll(n, this.x + this.button.frame.width) && llIIIIlllI(n2, this.y + 4) && llIIIIllll(n2, this.y + 14));
    }
    
    public boolean isMouseOnButtonD(final int n, final int n2) {
        return llIIIIllII(this.button.frame.open ? 1 : 0) && llIIIIllII(this.button.open ? 1 : 0) && (llIIIIlllI(n, this.x - 2) && llIIIIllll(n, this.x + (this.button.frame.width / 2 + 1)) && llIIIIlllI(n2, this.y + 2) && llIIIIllll(n2, this.y + 12));
    }
    
    public double round(final double n, final int n2) {
        return new BigDecimal(n).setScale(n2, 4).doubleValue();
    }
    
    private static boolean llIIIIllll(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean llIIIIlllI(final int n, final int n2) {
        return n > n2;
    }
    
    private static boolean llIIIIlIll(final Object o, final Object o2) {
        return o == o2;
    }
    
    private static boolean llIIIIllII(final int n) {
        return n != 0;
    }
    
    private static boolean llIIIIllIl(final int n) {
        return n == 0;
    }
}
