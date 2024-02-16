package me.sting.client.product.module.movement;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.event.entity.player.*;
import me.sting.client.product.commands.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class KeepSprint extends Module
{
    @RetentionField
    public SliderValue xz;
    @RetentionField
    public SliderValue percentage;
    @RetentionField
    public BooleanValue weapon;
    
    public KeepSprint() {
        this.xz = new SliderValue("Motion X/Z", 0.6, 0.6, 0.9, false);
        this.percentage = new SliderValue("Percentage", 100.0, 0.0, 100.0, true);
        this.weapon = new BooleanValue("onWeapon Only", false);
        this.setName("KeepSprint");
        this.setCategory(ModuleCategory.Movement);
        this.setKey(0);
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEntityEvent event) {
        if (!state || FriendCommand.friends.contains(event.target.getName().toLowerCase()) ||
                mc.currentScreen != null || mc.thePlayer == null || mc.theWorld == null ||
                event.target instanceof EntityPlayer) {
            return;
        }
        if (combat.onWeaponOnly(weapon.state)) {
            return;
        }
        if (Math.random() < percentage.getValue() / 100.0) {
            mc.thePlayer.motionX /= xz.getValue();
            mc.thePlayer.motionZ /= xz.getValue();
            mc.thePlayer.setSprinting(true);
        }
    }

}
