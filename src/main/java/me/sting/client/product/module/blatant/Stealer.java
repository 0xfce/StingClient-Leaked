package me.sting.client.product.module.blatant;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Stealer extends Module
{
    @RetentionField
    public SliderValue delay;
    @RetentionField
    public BooleanValue comp;
    
    public Stealer() {
        this.delay = new SliderValue("Delay", 50.0, 10.0, 800.0, true);
        this.comp = new BooleanValue("No Compass", false);
        this.setName("Stealer");
        this.setCategory(ModuleCategory.Blatant);
    }
    
    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        final Container openContainer = this.mc.thePlayer.openContainer;
        if (openContainer != null && (openContainer instanceof ContainerChest)) {
            final ContainerChest containerChest = (ContainerChest) openContainer;
            if (this.comp.state && containerChest.getLowerChestInventory().getDisplayName().toString().toLowerCase()
                    .contains("where would you like to go?")) {
                return;
            }
            for (int n = 0; n < containerChest.getLowerChestInventory().getSizeInventory(); ++n) {
                if (containerChest.getLowerChestInventory().getStackInSlot(n) != null
                        && this.timer.reached.hasTimeReachedCURRENT((long) this.delay.getValue())) {
                    this.mc.playerController.windowClick(containerChest.windowId, n, 0, 1,
                            (EntityPlayer) this.mc.thePlayer);
                    this.timer.reached.reset();
                }
            }
            if (this.isContainerEmpty(openContainer)) {
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    public boolean isContainerEmpty(final Container container) {
        boolean b = true;
        for (int n = 0; n < (container.inventorySlots.size() < 90 ? 54 : 27); ++n) {
            if (container.getSlot(n).getHasStack()) {
                b = false;
            }
        }
        return b;
    }

}
