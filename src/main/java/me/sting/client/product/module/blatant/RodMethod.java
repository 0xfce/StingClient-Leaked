package me.sting.client.product.module.blatant;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.utils.timers.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;

public class RodMethod extends Module
{
    @RetentionField
    public SliderValue rod;
    @RetentionField
    public SliderValue sword;
    @RetentionField
    public BooleanValue fastrod;
    @RetentionField
    public BooleanValue swtch;
    public TimerUtil swordDelay;
    public TimerUtil timer;
    
    public RodMethod() {
        this.rod = new SliderValue("Rod Switch Delay", 400.0, 250.0, 1000.0, true);
        this.sword = new SliderValue("Sword Switch Delay", 800.0, 500.0, 2000.0, true);
        this.fastrod = new BooleanValue("Fast Rod", false);
        this.swtch = new BooleanValue("Switch Method", false);
        this.swordDelay = new TimerUtil();
        this.timer = new TimerUtil();
        this.setName("RodMethod");
        this.isPrivate();
        this.setCategory(ModuleCategory.Blatant);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        if (!this.state || this.mc.currentScreen == null || this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        if (this.getFishingRodSlotItem() != -1) {
            final ItemStack getCurrentEquippedItem = this.mc.thePlayer.getCurrentEquippedItem();
            if (getCurrentEquippedItem != null && this.swtch.state) {
                if (Mouse.isButtonDown(1) && (getCurrentEquippedItem.getItem() instanceof ItemSword)
                        && this.mc.thePlayer.isBlocking()) {
                    if (this.timer.reached.hasTimeReachedCURRENT((long) this.rod.getValue())) {
                        return;
                    }
                    this.timer.reached.reset();
                    this.mc.thePlayer.inventory.currentItem = this.getFishingRodSlotItem();
                    this.mc.playerController.sendUseItem((EntityPlayer) this.mc.thePlayer, (World) this.mc.theWorld,
                            this.mc.thePlayer.inventory.getStackInSlot(this.getFishingRodSlotItem()));
                } else if (Mouse.isButtonDown(0) && (getCurrentEquippedItem.getItem() instanceof ItemFishingRod)
                        && this.getSwordItemSlot() != -1) {
                    if (this.swordDelay.reached.hasTimeReachedCURRENT((long) this.sword.getValue())) {
                        return;
                    }
                    this.swordDelay.reached.reset();
                    this.mc.thePlayer.inventory.currentItem = this.getSwordItemSlot();
                }
            }
        }
        if (this.fastrod.state) {
            if (this.mc.thePlayer.inventory.currentItem != this.getFishingRodSlotItem() || Mouse.isButtonDown(1)) {
                return;
            }
            this.mc.getItemRenderer().resetEquippedProgress();
        }
    }

    public int getFishingRodSlotItem() {
        for (int n = 0; n < InventoryPlayer.getHotbarSize(); ++n) {
            if (this.mc.thePlayer.inventory.getStackInSlot(n) != null
                    && (this.mc.thePlayer.inventory.getStackInSlot(n).getItem() instanceof ItemFishingRod)) {
                return n;
            }
        }
        return -1;
    }

    public int getSwordItemSlot() {
        for (int n = 0; n < InventoryPlayer.getHotbarSize(); ++n) {
            if (this.mc.thePlayer.inventory.getStackInSlot(n) != null
                    && (this.mc.thePlayer.inventory.getStackInSlot(n).getItem() instanceof ItemSword)) {
                return n;
            }
        }
        return -1;
    }

}
