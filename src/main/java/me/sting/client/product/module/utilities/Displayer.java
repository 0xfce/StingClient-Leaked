package me.sting.client.product.module.utilities;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import java.text.*;
import me.sting.client.product.module.*;
import net.minecraft.client.gui.*;
import me.sting.client.product.gui.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import me.sting.client.product.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Displayer extends Module
{
    @RetentionField
    public BooleanValue spoofer;
    @RetentionField
    private ComboValue font;
    public DecimalFormat decimalFormat;
    public double lastRange;
    public long lastAttack;
    
    public Displayer() {
        this.spoofer = new BooleanValue("Spoofer", false);
        this.font = new ComboValue("Font", true, "option", new String[] { "state", "StingFont", "MinecraftFont" });
        this.decimalFormat = new DecimalFormat("#.##");
        this.lastRange = 0.0;
        this.lastAttack = 0L;
        this.setName("Displayer");
        this.setCategory(ModuleCategory.Utilities);
        this.font.combos[2].setState(true);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post renderGameOverlayEvent$Post) {
        if (this.mc.currentScreen instanceof GuiMainMenu) {
            return;
        }
        if (this.mc.currentScreen instanceof GraphicalUserInterface) {
            this.lastAttack = System.currentTimeMillis();
        }
        if (renderGameOverlayEvent$Post.type == RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        GL11.glPushMatrix();
        if (System.currentTimeMillis() - this.lastAttack + 3000L > 0) {
            if (this.spoofer.state && Double.valueOf(this.decimalFormat.format(this.lastRange)) < 3.0) {
                this.lastRange = 2.98;
            }
            if (this.spoofer.state && Double.valueOf(this.decimalFormat.format(this.lastRange)) > 0.0) {
                this.lastRange = this.mc.objectMouseOver.hitVec
                        .distanceTo(this.mc.getRenderViewEntity().getPositionEyes(1.0f));
            }
            if (this.font.combos[1].state) {
                Sting.frame.drawString(
                        EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + "Range" + EnumChatFormatting.GRAY
                                + "] " + EnumChatFormatting.WHITE + this.decimalFormat.format(this.lastRange)
                                + " blocks",
                        GraphicalUserInterface.x[2], GraphicalUserInterface.y[2] + 12, -1, true, false);
            }
            if (this.font.combos[2].state) {
                this.mc.fontRendererObj.drawStringWithShadow(
                        EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + "Range" + EnumChatFormatting.GRAY
                                + "] " + EnumChatFormatting.WHITE + this.decimalFormat.format(this.lastRange)
                                + " blocks",
                        (float) GraphicalUserInterface.x[2], (float) (GraphicalUserInterface.y[2] + 12), -1);
            }
        }
        GL11.glPopMatrix();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAttack(final AttackEntityEvent attackEntityEvent) {
        if (this.mc.objectMouseOver != null
                && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
                && this.mc.objectMouseOver.entityHit.getEntityId() == attackEntityEvent.target.getEntityId()) {
            this.lastRange = this.mc.objectMouseOver.hitVec
                    .distanceTo(this.mc.getRenderViewEntity().getPositionEyes(1.0f));
        }
        this.lastAttack = System.currentTimeMillis();
    }

}
