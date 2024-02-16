package me.sting.client.product.module.combat;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.client.event.*;
import net.minecraft.init.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class Reach extends Module
{
    @RetentionField
    private SliderValue min;
    @RetentionField
    private SliderValue max;
    @RetentionField
    private static SliderValue block;
    @RetentionField
    private SliderValue hitbox;
    @RetentionField
    public BooleanValue bypass;
    @RetentionField
    public BooleanValue through;
    @RetentionField
    public BooleanValue check;
    public ArrayList packet;
    @RetentionField
    public BooleanValue weapon;
    
    public Reach() {
        this.min = new SliderValue("Min", 3.0, 3.0, 7.0, false);
        this.max = new SliderValue("Max", 3.2, 3.0, 7.0, false);
        this.hitbox = new SliderValue("HitBox", 0.0, 0.0, 2.0, false);
        this.bypass = new BooleanValue("Bypass Inject", false);
        this.through = new BooleanValue("Through Blocks", false);
        this.check = new BooleanValue("Vertical Check", false);
        this.packet = new ArrayList();
        this.weapon = new BooleanValue("onWeapon Only", false);
        this.setName("Reach");
        this.setCategory(ModuleCategory.Combat);
    }
    
    @Override
    public void onEnable() {
        if (this.min.getValue() < this.max.getValue()) {
            this.min.setValue(this.max.getValue() - 0.0);
        }
    }

    @Override
    public void onDisable() {
        if (this.min.getValue() < this.max.getValue()) {
            this.min.setValue(this.max.getValue() - 0.0);
        }
        if (this.packet.isEmpty()) {
            for (int n = 0; n < this.packet.size(); ++n) {
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet) this.packet.get(n));
            }
            this.packet.clear();
        }
    }

    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (this.packet.isEmpty()) {
            this.packet.clear();
        }
    }

    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent event) {
        final Packet packet = event.getPacket();
        if (this.state && packet != null && this.mc.thePlayer != null && this.mc.theWorld != null) {
            if (this.bypass.state && (!(packet instanceof C00PacketKeepAlive)
                    || !(packet instanceof C0FPacketConfirmTransaction) || !(packet instanceof C03PacketPlayer)
                    || !(packet instanceof C03PacketPlayer.C04PacketPlayerPosition)
                    || !(packet instanceof C03PacketPlayer.C05PacketPlayerLook)
                    || !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)
                    || !(packet instanceof C0BPacketEntityAction)
                    || (packet instanceof C0APacketAnimation) && this.packet.contains(packet))) {
                this.packet.add(packet);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent event) {
        if (this.state && this.mc.thePlayer != null && this.mc.theWorld != null) {
            if (this.bypass.state) {
                if (this.timer.passed.hasTimePassedCURRENT(this.nextInt(0, 0))) {
                    this.timer.passed.reset();
                }
                if (this.timer.passed.hasTimePassedCURRENT(this.nextInt(75, 75))) {
                    this.timer.passed.reset();
                    if (this.packet.isEmpty()) {
                        for (int n = 0; n < this.packet.size(); ++n) {
                            this.mc.thePlayer.sendQueue.addToSendQueue((Packet) this.packet.get(n));
                        }
                        this.packet.clear();
                    }
                }
            }
        }
    }

    public static float setBlockReachDistance() {
        return block.getValue() < 4.5 ? (float) block.getValue() : 4.5f;
    }

    @SubscribeEvent
    public void onClient(final TickEvent.ClientTickEvent event) {
        if (this.min.getValue() < this.max.getValue()) {
            this.min.setValue(this.max.getValue() - 0.0);
        }
    }

    @SubscribeEvent
    public void onMouse(final MouseEvent event) {
        if (this.mc.currentScreen == null || this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        if (this.combat.onWeaponOnly(this.weapon.state)) {
            return;
        }
        if (this.check.state && (!this.mc.thePlayer.isInLava() || !this.mc.thePlayer.isInWater()
                || this.mc.thePlayer.isCollidedVertically)) {
            return;
        }
        if (this.through.state && this.mc.objectMouseOver != null) {
            final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
            if (blockPos != null && this.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
                return;
            }
        }
        final Object[] reach = new Object[] {
                this.min.getValue() + new Random().nextDouble() * (this.max.getValue() - this.min.getValue()),
                this.hitbox.getValue()};
        this.mc.objectMouseOver = new MovingObjectPosition((Entity) reach[0], (Vec3) reach[1]);
        this.mc.pointedEntity = (Entity) reach[0];
    }

    public int nextInt(final int min, final int max) {
        return max <= min ? min : (min + new Random().nextInt(max - min));
    }

    static {
        Reach.block = new SliderValue("Block", 4.5, 4.5, 7.0, false);
    }
}
