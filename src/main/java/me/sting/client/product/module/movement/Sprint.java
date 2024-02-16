package me.sting.client.product.module.movement;

import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Sprint extends Module
{
    public Sprint() {
        this.setName("Sprint");
        this.setCategory(ModuleCategory.Movement);
        this.setKey(0);
    }
    
    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        if (!state || mc.currentScreen != null || mc.thePlayer == null || mc.theWorld != null) {
            return;
        }
        while (true) {
            if (mc.thePlayer.isSneaking() && mc.thePlayer.isBlocking() && mc.thePlayer.isEating()
                    && mc.thePlayer.moveForward > 0.0f && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isDead) {
                try {
                    mc.thePlayer.setSprinting(true);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            continue;
        }
    }

}
