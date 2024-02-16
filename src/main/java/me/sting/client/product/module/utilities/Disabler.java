package me.sting.client.product.module.utilities;

import java.util.ArrayList;

import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Disabler extends Module
{
    @RetentionField
    public static BooleanValue notifc;
    private EntityLivingBase target;
    public static boolean inCombat;
    public static ArrayList packets;
    
    public Disabler() {
        this.target = null;
        this.setName("Disabler");
        this.isPrivate();
        this.setCategory(ModuleCategory.Utilities);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
    
    @SubscribeEvent
    public final void onWorld(final WorldEvent worldEvent) {
        this.target = null;
        Disabler.inCombat = false;
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEntityEvent attackEntityEvent) {
        final Entity target = attackEntityEvent.target;
        if (target instanceof EntityLivingBase) {
            this.target = (EntityLivingBase) target;
        }
        this.timer.passed.reset();
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent tickEvent$ClientTickEvent) {
        if (this.mc.thePlayer == null) {
            return;
        }
        Disabler.inCombat = false;
        if (this.timer.passed.hasTimePassedCURRENT(250L)) {
            Disabler.inCombat = true;
            return;
        }
        if (this.target != null) {
            if (!(this.mc.thePlayer.getDistanceToEntity(this.target) > 7.0f) || Disabler.inCombat) {
                this.target = null;
            } else {
                Disabler.inCombat = true;
            }
        }
    }

    public static void sendPacketNoEvent(final Packet packet) {
        Disabler.packets.add(packet);
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }

    static {
        Disabler.notifc = new BooleanValue("Notifications", false);
        Disabler.inCombat = false;
        Disabler.packets = new ArrayList();
    }

}
