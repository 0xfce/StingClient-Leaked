package me.sting.client.product.module.movement;

import me.sting.client.product.gui.values.*;
import me.sting.client.product.storage.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class AutoArmor extends Module
{
    private int[] bestArmor;
    @RetentionField
    public SliderValue delay;
    
    public AutoArmor() {
        this.bestArmor = new int[4];
        this.delay = new SliderValue("Delay", 1050.0, 0.0, 2050.0, true);
        this.setName("AutoArmor");
        this.setCategory(ModuleCategory.Movement);
    }
    
    @SubscribeEvent
    public void onWorldLeave(final PlayerEvent.PlayerLoggedOutEvent playerEvent$PlayerLoggedOutEvent) {
        this.setState(false);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.RenderTickEvent event) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        while (true) {
            if (!(this.mc.currentScreen instanceof GuiContainer)) {
                return;
            }
            final int n = (this.mc.currentScreen instanceof GuiInventory) ? 1 : 0;
            try {
                if (n != 0) {
                    return;
                }
            } catch (NullPointerException ex) {
                return;
            }
            final int[] bestArmor = new int[4];
            for (int n2 = 0; n2 < this.bestArmor.length; ++n2) {
                bestArmor[n2] = -1;
            }
            for (int n3 = 0; n3 < 36; ++n3) {
                final ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(n3);
                if (stack != null && stack.getItem() instanceof ItemArmor) {
                    final ItemArmor itemArmor = (ItemArmor) stack.getItem();
                    if (itemArmor.damageReduceAmount > bestArmor[3 - itemArmor.armorType]) {
                        bestArmor[3 - itemArmor.armorType] = n3;
                    }
                }
            }
            for (int n4 = 0; n4 < 4; ++n4) {
                final ItemStack armorItemInSlot = this.mc.thePlayer.inventory.armorItemInSlot(n4);
                ItemArmor equippedArmor = null;
                if (armorItemInSlot != null && armorItemInSlot.getItem() instanceof ItemArmor) {
                    equippedArmor = (ItemArmor) armorItemInSlot.getItem();
                }
                final int slotIndex = this.bestArmor[n4];
                ItemArmor bestArmorItem = null;
                try {
                    bestArmorItem = (ItemArmor) this.mc.thePlayer.inventory.getStackInSlot(slotIndex).getItem();
                } catch (Exception ignored) {
                }
                if (bestArmorItem != null
                        && (equippedArmor == null
                                || bestArmorItem.damageReduceAmount > equippedArmor.damageReduceAmount)
                        && (this.mc.thePlayer.inventory.getFirstEmptyStack() != -1 || equippedArmor == null)) {
                    if (!this.timer.passed.hasTimePassedCURRENT((long) this.delay.getValue())) {
                        this.timer.passed.reset();
                        this.mc.playerController.windowClick(0, 8 - n4, 0, 1, this.mc.thePlayer);
                        this.mc.playerController.windowClick(0,
                                (this.bestArmor[n4] < 9) ? (36 + this.bestArmor[n4]) : this.bestArmor[n4], 0, 1,
                                this.mc.thePlayer);
                    }
                    return;
                }
            }
            return;
        }
    }


}
