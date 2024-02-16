package me.sting.client.product.module.render;

import me.sting.client.product.gui.values.*;
import me.sting.client.product.storage.*;
import me.sting.client.product.module.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import me.sting.client.product.gui.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import me.sting.client.product.module.category.*;
import me.sting.client.product.module.disablers.*;
import me.sting.client.product.commands.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Arraylist extends Module
{
    @RetentionField
    public static ComboValue arrays;
    
    public Arraylist() {
        this.setName("Arraylist");
        this.setCategory(ModuleCategory.Render);
        Arraylist.arrays.combos[1].setState(true);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post renderGameOverlayEvent$Post) {
        if (isMainMenu()) {
            return;
        }
        if (renderGameOverlayEvent$Post.type == RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        GL11.glPushMatrix();
        int yOffset = GraphicalUserInterface.y[1] + 12;
        if (Arraylist.arrays.combos[1].state) {
            Sting.frame.drawString(EnumChatFormatting.GRAY + "Sting" + "-" + "I", GraphicalUserInterface.x[1], yOffset,
                    -1, true, false);
            for (Module module : Sting.getModuleManager().modules) {
                if (module != this && !isDefaultModule(module)) {
                    if (module.state) {
                        Sting.frame.drawString(EnumChatFormatting.BOLD + "-" + module.name, GraphicalUserInterface.x[1],
                                yOffset + 10, Sting.color.getRGB(), true, false);
                        yOffset += 9;
                    }
                }
            }
        }
        int friendOffset = GraphicalUserInterface.y[3] + 12;
        if (Arraylist.arrays.combos[2].state) {
            this.mc.fontRendererObj.drawStringWithShadow(
                    EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + "[#] FRIENDS",
                    (float) GraphicalUserInterface.x[3], (float) friendOffset, -1);
            for (Object friend : FriendCommand.friends) {
                this.mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.GREEN + String.valueOf(friend),
                        (float) GraphicalUserInterface.x[3], (float) (friendOffset + 10), Sting.color.getRGB());
                friendOffset += 9;
            }
        }
        int enemyOffset = GraphicalUserInterface.y[4] + 12;
        if (Arraylist.arrays.combos[3].state) {
            this.mc.fontRendererObj.drawStringWithShadow(
                    EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.BOLD + "[#] ENEMYS",
                    (float) GraphicalUserInterface.x[4], (float) enemyOffset, -1);
            for (Object enemy : EnemyCommand.enms) {
                this.mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.GRAY + String.valueOf(enemy),
                        (float) GraphicalUserInterface.x[4], (float) (enemyOffset + 10), Sting.color.getRGB());
                enemyOffset += 9;
            }
        }
        GL11.glPopMatrix();
    }

    private boolean isMainMenu() {
        return mc.currentScreen instanceof GuiMainMenu;
    }

    private boolean isDefaultModule(Module module) {
        return module == Module.getModule(GUI.class)
                || module == Module.getModule(COMBAT.class)
                || module == Module.getModule(MOVEMENT.class)
                || module == Module.getModule(BLATANT.class)
                || module == Module.getModule(RENDER.class)
                || module == Module.getModule(UTILITIES.class)
                || module == Module.getModule(MODERATOR.class)
                || module == Module.getModule(FakeLag.class)
                || module == Module.getModule(LatestVerus.class)
                || module == Module.getModule(VulcanHop.class);
    }
    
    static {
        Arraylist.arrays = new ComboValue("Arrays", false, "option", new String[] { "state", "Modules", "Friends", "Enemys" });
    }

}
