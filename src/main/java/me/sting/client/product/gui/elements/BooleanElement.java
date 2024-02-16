package me.sting.client.product.gui.elements;

import me.sting.client.product.gui.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.utils.*;
import me.sting.client.product.utils.timers.*;
import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import me.sting.client.product.module.*;
import me.sting.client.product.*;
import org.lwjgl.opengl.*;

public class BooleanElement extends Element
{
    public ButtonElement button;
    public BooleanValue booleans;
    public RenderUtil render;
    public TimerUtil timer;
    public boolean hovered;
    public double delay;
    public int height;
    public int x;
    public int y;
    
    public BooleanElement(final BooleanValue booleans, final ButtonElement button, final int height) {
        this.button = null;
        this.booleans = null;
        this.render = new RenderUtil();
        this.timer = new TimerUtil();
        this.hovered = false;
        this.delay = 69.5;
        this.height = 0;
        this.x = 0;
        this.y = 0;
        this.booleans = booleans;
        this.button = button;
        this.x = button.frame.x + button.frame.width;
        this.y = button.frame.y + button.height;
        this.height = height;
    }
    
    @Override
    public void render() {
        double var1;
        if(lIllllllII(this.booleans.state ? 1 : 0) && lIllllllIl((var1 = this.delay - 75.5D) == 0.0D?0:(var1 < 0.0D?-1:1)) && lIllllllII(this.timer.reached.hasTimeReachedNANO(1000L) ? 1 : 0)) {
           this.delay += 0.5D;
           this.timer.reached.reset();
        } else {
           double var2;
           if(lIlllllllI((var2 = this.delay - 69.5D) == 0.0D?0:(var2 < 0.0D?-1:1)) && lIllllllII(this.timer.reached.hasTimeReachedNANO(1000L) ? 1 : 0)) {
              this.delay -= 0.5D;
              this.timer.reached.reset();
           }
        }

        Gui.drawRect(this.button.frame.x - 1, this.button.frame.y + this.height, this.button.frame.x + 1 + this.button.frame.width * 1, this.button.frame.y + 16 + this.height, lIllllllII(this.hovered ? 1 : 0) ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB());
        if (llIIIIIIIl(Module.getModule(this.button.module.getClass()).booleans.get(Module.getModule(this.button.module.getClass()).booleans.size() - 1), this.booleans)) {
            this.render.drawCircle(this.button.frame.x + 3, this.button.frame.y + this.height + 15, 4, 360, lIllllllII(this.hovered ? 1 : 0) ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB());
            this.render.drawCircle(this.button.frame.x + this.button.frame.width - 3, this.button.frame.y + this.height + 15, 4, 360, lIllllllII(this.hovered ? 1 : 0) ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB());
            Gui.drawRect(this.button.frame.x + 4, this.button.frame.y + this.height + 16, this.button.frame.x + this.button.frame.width - 4, this.button.frame.y + 19 + this.height, lIllllllII(this.hovered ? 1 : 0) ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB());
        }
        Gui.drawRect(this.button.frame.x + this.button.frame.width - 12, this.button.frame.y + this.height + 5, this.button.frame.x + this.button.frame.width - 6, this.button.frame.y + 11 + this.height, lIllllllII(this.booleans.state ? 1 : 0) ? Sting.color.getRGB() : new Color(50, 50, 50).getRGB());
        this.render.drawCircle(this.button.frame.x + this.button.frame.width - 6, this.button.frame.y + this.height + 8, 3, 360, lIllllllII(this.booleans.state ? 1 : 0) ? Sting.color.getRGB() : new Color(50, 50, 50).getRGB());
        this.render.drawCircle(this.button.frame.x + this.button.frame.width - 12, this.button.frame.y + this.height + 8, 3, 360, lIllllllII(this.booleans.state ? 1 : 0) ? Sting.color.getRGB() : new Color(50, 50, 50).getRGB());
        if (llIIIIIIll(this.booleans.state ? 1 : 0)) {
            this.render.drawCircle(this.button.frame.x + this.delay + this.button.frame.width - 83.0, this.button.frame.y + this.height + 8, 3, 360, new Color(255, 255, 255).getRGB());
        }
        if (lIllllllII(this.booleans.state ? 1 : 0)) {
            this.render.drawCircle(this.button.frame.x + (int)this.delay + this.button.frame.width - 81, this.button.frame.y + this.height + 8, 3, 360, new Color(255, 255, 255).getRGB());
        }
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
//        Sting.others.drawString(this.booleans.name, (this.button.frame.x + 2) * 2, (this.button.frame.y + this.height + 3) * 2 + 3, -1, true, false);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.booleans.name, (this.button.frame.x + 2) * 2, (this.button.frame.y + this.height + 3) * 2 + 3, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void setHeight(final int height) {
        this.height = height;
    }
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.hovered = this.isMouseOnButton(n, n2);
        this.y = this.button.frame.y + this.height;
        this.x = this.button.frame.x;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (lIllllllII(this.isMouseOnButton(n, n2) ? 1 : 0) && llIIIIIIll(n3) && lIllllllII(this.button.open ? 1 : 0) && lIllllllII(this.button.frame.open ? 1 : 0)) {
            this.booleans.toggle();
        }
    }
    
    public boolean isMouseOnButton(final int n, final int n2) {
        return lIllllllII(this.button.frame.open ? 1 : 0) && lIllllllII(this.button.open ? 1 : 0) && (llIIIIIlIl(n, this.x - 2) && llIIIIIlll(n, this.x + this.button.frame.width) && llIIIIIlIl(n2, this.y) && llIIIIIlll(n2, this.y + 12));
    }
    
    private static boolean llIIIIIlll(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean llIIIIIlIl(final int n, final int n2) {
        return n > n2;
    }
    
    private static boolean llIIIIIIIl(final Object o, final Object o2) {
        return o == o2;
    }
    
    private static boolean lIllllllII(final int n) {
        return n != 0;
    }
    
    private static boolean llIIIIIIll(final int n) {
        return n == 0;
    }
    
    private static boolean lIllllllIl(final int n) {
        return n < 0;
    }
    
    private static boolean lIlllllllI(final int n) {
        return n > 0;
    }
}
