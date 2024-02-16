package me.sting.client.product.module.combat;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;
import me.sting.client.product.commands.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class Criticals extends Module
{
    @RetentionField
    private SliderValue delay;
    @RetentionField
    private SliderValue hurtime;
    @RetentionField
    private SliderValue ticks;
    @RetentionField
    public static BooleanValue detectFlag;
    @RetentionField
    private ComboValue crit;
    @RetentionField
    private BooleanValue weapon;
    private int attacks;
    
    public Criticals() {
        this.delay = new SliderValue("Delay", 10.0, 0.0, 700.0, true);
        this.hurtime = new SliderValue("HurtTime", 5.0, 0.0, 10.0, true);
        this.ticks = new SliderValue("Ticks", 0.0, 0.0, 10.0, false);
        this.crit = new ComboValue("Critical Mode", true, "option", new String[] { "state", "OnGround", "OffGround" });
        this.weapon = new BooleanValue("onWeapon Only", false);
        this.attacks = 0;
        this.setName("Criticals");
        this.setCategory(ModuleCategory.Combat);
        this.isPrivate();
        this.crit.combos[1].setState(true);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
    
    @Override
    public void onDisable() {
        this.attacks = 0;
        this.timer.passed.reset();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent packetReceivedSendEvent) {
        final Packet packet = packetReceivedSendEvent.getPacket();
        if (!this.state || !Criticals.detectFlag.state || packet == null || this.mc.thePlayer == null
                || this.mc.theWorld == null) {
            return;
        }
        if ((packet instanceof S08PacketPlayerPosLook) && this.mc.thePlayer.moveForward != 0.0f
                && this.mc.thePlayer.moveStrafing != 0.0f) {
            this.setState(false);
        }
    }

    @SubscribeEvent
    public void onAttack(final AttackEntityEvent attackEntityEvent) {
        if (FriendCommand.friends.contains(attackEntityEvent.target.getName().toLowerCase())) {
            return;
        }
        if (this.mc.thePlayer == null || this.mc.theWorld == null || !(attackEntityEvent.target instanceof EntityPlayer)
                || this.mc.thePlayer.isOnLadder() || this.mc.thePlayer.isInWater() || this.mc.thePlayer.isInLava()
                || this.mc.thePlayer.ridingEntity != null) {
            return;
        }
        if (this.combat.onWeaponOnly(this.weapon.state)) {
            return;
        }
        if (attackEntityEvent.entityPlayer.hurtTime >= this.hurtime.getValue()) {
            if (this.crit.combos[1].state && this.mc.thePlayer.onGround) {
                return;
            }
            if (this.crit.combos[2].state && this.mc.thePlayer.onGround) {
                return;
            }
            if (!this.timer.passed.hasTimePassedCURRENT((long) this.delay.getValue())) {
                return;
            }
            this.timer.passed.reset();
            this.attacks++;
            if (this.attacks >= this.ticks.getValue()) {
                return;
            }
            this.sendCrit(0.001, true);
            this.sendCrit(0.0, false);
            this.attacks = 0;
            this.mc.thePlayer.onCriticalHit(attackEntityEvent.target);
        }
    }

    private void sendCrit(final double n, final boolean b) {
        this.mc.thePlayer.sendQueue
                .addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX,
                        this.mc.thePlayer.posY + n, this.mc.thePlayer.posZ, b));
    }

    static {
        Criticals.detectFlag = new BooleanValue("Disable onFlag", true);
    }

}
