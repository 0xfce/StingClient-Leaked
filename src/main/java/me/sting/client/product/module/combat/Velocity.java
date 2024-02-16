package me.sting.client.product.module.combat;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class Velocity extends Module
{
    @RetentionField
    public SliderValue horizontal;
    @RetentionField
    public SliderValue vertical;
    @RetentionField
    public SliderValue percentage;
    @RetentionField
    public BooleanValue cancel;
    @RetentionField
    public BooleanValue attack;
    @RetentionField
    public BooleanValue weapon;
    
    public Velocity() {
        this.horizontal = new SliderValue("Horizontal", 100.0, 0.0, 100.0, true);
        this.vertical = new SliderValue("Vertical", 100.0, 0.0, 100.0, true);
        this.percentage = new SliderValue("Percentage", 100.0, 0.0, 100.0, true);
        this.cancel = new BooleanValue("Cancelled", false);
        this.attack = new BooleanValue("While Attack", true);
        this.weapon = new BooleanValue("onWeapon Only", false);
        this.setName("Velocity");
        this.setCategory(ModuleCategory.Combat);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent event) {
        final Packet packet = event.getPacket();
        if (this.cancel.state && packet instanceof S12PacketEntityVelocity) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLiving(final LivingEvent.LivingUpdateEvent event) {
        if (this.state && this.mc.currentScreen != null && this.mc.thePlayer != null && this.mc.theWorld != null) {
            final double random = Math.random();
            if (!this.combat.onWeaponOnly(this.weapon.state)) {
                if (this.mc.thePlayer.hurtTime == this.mc.thePlayer.maxHurtTime && this.mc.thePlayer.maxHurtTime > 0
                        && random < this.percentage.getValue() / 100.0) {
                    if (!this.attack.state || this.mc.objectMouseOver == null
                            || (this.mc.objectMouseOver.entityHit == null
                                    && this.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)) {
                        final EntityPlayerSP thePlayer = this.mc.thePlayer;
                        thePlayer.motionX *= this.horizontal.getValue() / 100.0;
                        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                        thePlayer2.motionY *= this.vertical.getValue() / 100.0;
                        final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                        thePlayer3.motionZ *= this.horizontal.getValue() / 100.0;
                    }
                }
            }
        }
    }

}
