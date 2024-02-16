package me.sting.client.product.gui.elements;

import me.sting.client.product.gui.*;
import java.util.*;
import me.sting.client.product.utils.*;
import me.sting.client.product.gui.values.*;
import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import me.sting.client.product.module.*;
import org.lwjgl.opengl.*;
import me.sting.client.product.*;

public class OptionElement extends Element
{
    public ArrayList<ComboElement> comboElement;
    public RenderUtil render;
    public ComboElement combos;
    public ComboValue combo;
    public ButtonElement button;
    public boolean hovered;
    public boolean open;
    public int x;
    public int y;
    public int height;
    public int width;

    public OptionElement(ComboValue combo, ButtonElement button, int n) {
        this.comboElement = new ArrayList<>();
        this.render = new RenderUtil();
        this.button = button;
        this.hovered = false;
        this.open = false;
        this.combo = combo;
        this.height = n * this.combo.option.length;
//        this.height = null;
        this.x = button.frame.x + this.button.frame.width;
        this.y = button.frame.y + button.height;
        this.width = this.height + 10;
        if (!button.module.combos.isEmpty() && !this.button.module.combos.isEmpty()) {
            for (Object setting : button.module.addSetting(this.combo.name)) {
                this.combos = new ComboElement((OptionValue) setting, this);
                if (!this.comboElement.contains(this.combos)) {
                    this.comboElement.add(this.combos);
                    this.width += 16;
                }
            }
        }
    }

    public void render() {
        this.width = this.height + 16;

        int backgroundColor = this.hovered ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB();
        Gui.drawRect(this.button.frame.x - 1, this.button.frame.y + this.height, this.button.frame.x + this.button.frame.width + 1, this.button.frame.y + 16 + this.height, backgroundColor);

        if (Module.getModule(this.button.module.getClass()).combos.get(Module.getModule(this.button.module.getClass()).combos.size() - 1) == this.combo) {
            int circleColor = this.hovered ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB();
            this.render.drawCircle(this.button.frame.x + 3, this.button.frame.y + 15 + this.height, 4, 360, circleColor);
            this.render.drawCircle(this.button.frame.x + this.button.frame.width - 3, this.button.frame.y + 15 + this.height, 4, 360, circleColor);
            Gui.drawRect(this.button.frame.x + 4, this.button.frame.y + 16 + this.height, this.button.frame.x + this.button.frame.width - 4, this.button.frame.y + 19 + this.height, backgroundColor);
        }


        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
//        Sting.others.drawString(this.combo.name, this.button.frame.x * 2 + this.button.frame.width - 16, (this.button.frame.y + this.height) * 2 + 9, new Color(150, 150, 150).getRGB(), true, true);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.combo.name, this.button.frame.x * 2 + this.button.frame.width - 16, (this.button.frame.y + this.height) * 2 + 9, new Color(150, 150, 150).getRGB());
        GL11.glPopMatrix();
   
        if (!this.comboElement.isEmpty()) {
            for (ComboElement element : this.comboElement) {
                element.render(this.width);
                this.width += 9;
            }
        }
    }

    public void mouseClicked(int n, int n2, int n3) {
        if (this.open && this.button.frame.open && this.button.open && n3 == 0) {
            this.combos.mouseClicked(n, n2, n3);
        }
        if (this.button.frame.open && this.isMouseOnButton(n, n2) && n3 == 1) {
            this.open = !this.open;
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void updateComponent(int n, int n2) {
        this.hovered = this.isMouseOnButton(n, n2);
        this.y = this.button.frame.y + this.height;
        this.x = this.button.frame.x;
    }

    public int getHeight() {
        return this.open ? this.width - this.height : 16;
    }

    public boolean isMouseOnButton(int n, int n2) {
        return this.button.frame.open && this.button.open && n > this.x - 4 && n < this.x + 80 && n2 > this.y && n2 < this.y + 10;
    }
}
