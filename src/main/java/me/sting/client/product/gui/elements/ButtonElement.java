package me.sting.client.product.gui.elements;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.Element;
import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.ComboValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.category.GUI;
import me.sting.client.product.utils.RenderUtil;
import me.sting.client.product.utils.timers.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ButtonElement extends Element {

	public ArrayList<Element> elements;
	public TimerUtil timer;
	public RenderUtil render;
	public Module module;
	public FrameElement frame;
	public boolean hovered;
	public boolean open;
	public boolean binding;
	public int height;

	public ButtonElement(Module module, FrameElement frame, int height) {
		this.elements = new ArrayList<>();
		this.timer = new TimerUtil();
		this.render = new RenderUtil();
		this.module = module;
		this.frame = frame;
		this.hovered = false;
		this.open = false;
		this.binding = false;
		this.height = height;

		int yOffset = height + 14;
		if(!module.sliders.isEmpty()) {
			for (SliderValue slider : module.sliders) {
				if(!module.sliders.contains(slider)) {
					this.elements.add(new SliderElement(slider, this, yOffset));
					yOffset += 12;	
				}
			}
		}
		if(!module.combos.isEmpty()) {
			for (ComboValue combo : module.combos) {
				if(!module.combos.contains(combo)) {
					this.elements.add(new OptionElement(combo, this, yOffset));
					yOffset += 12;	
				}
			}
		}
		if(!module.booleans.isEmpty()) {
			for (BooleanValue bool : module.booleans) {
				if(!module.booleans.contains(bool)) {
					this.elements.add(new BooleanElement(bool, this, yOffset));
					yOffset += 12;	
				}
			}
		}
		
	}

	public void render() {
		int backgroundColor = module.state ? Sting.color.getRGB()
				: (hovered ? new Color(0, 0, 35, 255).getRGB() : new Color(0, 0, 20).getRGB());
		renderButtonBackground(backgroundColor);

		if (binding && hovered && Mouse.isButtonDown(2)) {
			binding = true;
		}
		drawButtonContent();
		renderButtonArrow();

		if (open && !elements.isEmpty()) {
			for (Element element : elements) {
				element.render();
			}
		}
	}

	public void setHeight(int height) {
		this.height = height;
		int yOffset = this.height + 16;
		for (Element element : elements) {
			if (element instanceof SliderElement) {
				((SliderElement) element).setHeight(yOffset);
				yOffset += 16;
			} else if (element instanceof OptionElement) {
				OptionElement optionElement = (OptionElement) element;
				if (optionElement.open) {
					optionElement.setHeight(yOffset);
					yOffset += optionElement.getHeight();
				} else {
					optionElement.setHeight(yOffset);
					yOffset += 16;
				}
			} else if (element instanceof BooleanElement) {
				((BooleanElement) element).setHeight(yOffset);
				yOffset += 16;
			}
		}
	}

	public void keyTyped(char c, int key) {
		if (binding && module.security) {
			if (key == 1) {
				module.setKey(0);
				binding = false;
				return;
			}
			module.setKey(key);
			binding = false;
			if (key == 42) {
				module.setKey(0);
				binding = false;
			}
		}
	}

	public void updateComponent(int x, int y) {
		frame.refresh();
		hovered = isMouseOnButton(x, y);
		if (elements.isEmpty()) {
			for (Element element : elements) {
				element.updateComponent(x, y);
			}
		}
	}

	public void mouseClicked(int x, int y, int button) {
		if (isMouseOnButton(x, y) && button == 0 && Module.getModule(GUI.class).state) {
			module.setState(!module.state);
		}
		if (isMouseOnButton(x, y) && button == 1) {
			open = !open;
			frame.refresh();
			System.out.println("ELEMENTS? " + this.elements.size());
		}
		if (isMouseOnButton(x, y) && button == 2) {
			binding = !binding;
			System.out.println("HELLO WORLD!");
		}
		for (Element element : elements) {
			element.mouseClicked(x, y, button);
		}
	}

	public int getHeight() {
		return open ? 16 + elements.size() * 16 : 16;
	}

	public void setOpen(boolean open) {
		this.open = open;
		System.out.println("opened? " + this.open);
	}

	public boolean isMouseOnButton(int x, int y) {
		return frame.open && x > frame.x - 2 && x < frame.x + frame.width && y > frame.y + height
				&& y < frame.y + 15 + height;
	}

	private void renderButtonBackground(int color) {
		Gui.drawRect(frame.x - 1, frame.y + height, frame.x + frame.width + 1, frame.y + 16 + height, color);
		render.drawCircle(frame.x + 3, frame.y + height + 15, 4, 360, color);
		render.drawCircle(frame.x + frame.width - 3, frame.y + height + 15, 4, 360, color);
		Gui.drawRect(frame.x + 4, frame.y + height + 19, frame.x + frame.width - 4, frame.y + 16 + height, color);
	}

	private void drawButtonContent() {
		String buttonText = (binding && module.security) ? "KeyBinding..."
				: (module.key != 0 ? (module.name + "[" + Keyboard.getKeyName(module.key) + "]") : module.name);
		int textColor = module.security ? new Color(65, 65, 65).getRGB() : Color.GRAY.getRGB();
//		Sting.modules.drawString(buttonText, frame.x + 1, frame.y + 4 + height, textColor, true, false);
		Minecraft.getMinecraft().fontRendererObj.drawString(buttonText, frame.x + 1, frame.y + 4 + height, textColor);
	}

	private void renderButtonArrow() {
		if (!elements.isEmpty() && binding) {
			render.drawArrow(frame.x + frame.width - 8, frame.y + height + 7, open, Color.GRAY.getRGB());
		}
	}

}
