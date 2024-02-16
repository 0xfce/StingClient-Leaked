package me.sting.client.product.module.movement;

import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;

public class SpeedBridge extends Module
{
    public SpeedBridge() {
        this.setName("SpeedBridge");
        this.setCategory(ModuleCategory.Movement);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent tickEvent) {
        if (!mc.thePlayer.isSprinting() || mc.thePlayer.isInLava() || mc.thePlayer.isInWater()) {
            return;
        }
        if (mc.currentScreen != null && getItem() && mc.theWorld
                .isAirBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ))) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
        } else {
            try {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean getItem() {
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        return currentItem != null &&
                (currentItem.getItem() instanceof ItemSnow) &&
                Item.getIdFromItem(currentItem.getItem()) != 30 &&
                (currentItem.getItem() instanceof ItemBlock);
    }

}
