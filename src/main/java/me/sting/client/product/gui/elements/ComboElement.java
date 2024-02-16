package me.sting.client.product.gui.elements;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.Element;
import me.sting.client.product.gui.values.OptionValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ComboElement extends Element
{
    public RenderUtil render;
    public OptionValue option;
    public OptionElement options;
    public int x;
    public int y;
    public int height;
    
    public ComboElement(final OptionValue option, final OptionElement options) {
        this.render = new RenderUtil();
        this.option = null;
        this.options = null;
        this.x = 0;
        this.y = 0;
        this.height = 0;
        this.option = option;
        this.options = options;
        this.x = options.button.frame.x + options.button.frame.width;
        this.y = options.button.frame.y + options.button.height;
        this.height = options.height;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (lIllllIlII(this.options.comboElement.isEmpty() ? 1 : 0)) {
            for (int n4 = 0; lIllllIlIl(n4, this.options.comboElement.size()); ++n4) {
                final ComboElement comboElement = (ComboElement) this.options.comboElement.get(n4);
                if (lIllllIllI(comboElement.isMouseOnButton(n, n2) ? 1 : 0) && lIllllIllI(this.options.open ? 1 : 0)) {
                    if (lIllllIllI(comboElement.option.open ? 1 : 0)) {
                        for (int n5 = 0; lIllllIlIl(n5, this.options.combo.options.size()); ++n5) {
                            ((OptionValue)this.options.combo.options.get(n5)).setState(false);
                        }
                        comboElement.option.setState(lIllllIlII((int)(comboElement.option.state ? 1 : 0)));
                    }
                    else if (lIllllIlII(comboElement.option.state ? 1 : 0)) {
                        comboElement.option.setState(true);
                        comboElement.option.combo.list.add(this.options.combo.description.toLowerCase() + comboElement.option.name.toLowerCase());
                    }
                    else {
                        comboElement.option.setState(false);
                        comboElement.option.combo.list.remove(this.options.combo.description.toLowerCase() + comboElement.option.name.toLowerCase());
                    }
                }
            }
        }
    }
    
    public void render(final int height) {
        if (lIllllIllI(this.options.open ? 1 : 0)) {
            this.height = height;
            Gui.drawRect(this.options.button.frame.x - 1, this.options.button.frame.y + this.height, this.options.button.frame.x + this.options.button.frame.width + 1, this.options.button.frame.y + 9 + this.height, new Color(0, 0, 18).getRGB());
            if (lIllllIlll(Module.getModule(this.options.button.module.getClass()).combos.get(Module.getModule(this.options.button.module.getClass()).combos.size() - 1), this.options.combo)) {
                this.render.drawCircle(this.options.button.frame.x + 3, this.options.button.frame.y + this.height + 8, 4, 360, new Color(0, 0, 18).getRGB());
                this.render.drawCircle(this.options.button.frame.x + this.options.button.frame.width - 3, this.options.button.frame.y + this.height + 8, 4, 360, new Color(0, 0, 18).getRGB());
                Gui.drawRect(this.options.button.frame.x + 4, this.options.button.frame.y + 9 + this.height, this.options.button.frame.x + this.options.button.frame.width - 4, this.options.button.frame.y + 12 + this.height, new Color(0, 0, 18).getRGB());
            }
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
//            Sting.others.drawString(this.option.name, this.options.button.frame.x * 2 + this.options.button.frame.width - 16, (this.options.button.frame.y + this.height) * 2 + 2, lIllllIllI(this.option.state ? 1 : 0) ? Sting.color.getRGB() : new Color(190, 190, 190).getRGB(), true, true);
            Minecraft.getMinecraft().fontRendererObj.drawString(this.option.name, this.options.button.frame.x * 2 + this.options.button.frame.width - 16, (this.options.button.frame.y + this.height) * 2 + 2, lIllllIllI(this.option.state ? 1 : 0) ? Sting.color.getRGB() : new Color(190, 190, 190).getRGB());
            GL11.glPopMatrix();
        }
    }
    
    public boolean isMouseOnButton(final int n, final int n2) {
        return lIllllIllI(this.options.open ? 1 : 0) && (lIlllllIII(n, this.options.button.frame.x - 3) && lIllllIlIl(n, this.options.button.frame.x + this.options.button.frame.width + 1) && lIlllllIII(n2, this.options.button.frame.y + this.height + 2) && lIllllIlIl(n2, this.options.button.frame.y + this.height + 2 + 9));
    }
    
    private static boolean lIllllIlIl(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean lIlllllIII(final int n, final int n2) {
        return n > n2;
    }
    
    private static boolean lIllllIlll(final Object o, final Object o2) {
        return o == o2;
    }
    
    private static boolean lIllllIllI(final int n) {
        return n != 0;
    }
    
    private static boolean lIllllIlII(final int n) {
        return n == 0;
    }
}
