package me.sting.client.product.module.movement;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.server.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.settings.*;

public class Speed extends Module
{
    @RetentionField
    public SliderValue dispersion;
    @RetentionField
    public SliderValue timers;
    @RetentionField
    public BooleanValue flag;
    @RetentionField
    public BooleanValue hit;
    public boolean fakeTimer;
    
    public Speed() {
        this.dispersion = new SliderValue("Dispersion Speed", 1.0, 0.8, 2.0, false);
        this.timers = new SliderValue("Fake Timer", 1.1, 1.0, 5.0, false);
        this.flag = new BooleanValue("Disable onFlag", true);
        this.hit = new BooleanValue("Dispersion onHit", true);
        this.fakeTimer = false;
        this.setName("Speed");
        this.setCategory(ModuleCategory.Movement);
        this.setKey(0);
    }
    
    @Override
    public void onDisable() {
        this.timer.resetTimer();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent event) {
        final Packet packet = event.getPacket();
        if (!state || !flag.state || packet == null || mc.thePlayer == null || mc.theWorld == null
                || mc.theWorld.isRemote) {
            return;
        }
        if (packet instanceof S08PacketPlayerPosLook && movement.isMoving()) {
            mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                    Sting.CLIENT_PREFIX + getChatName() + EnumChatFormatting.WHITE + "Flagged Detect."));
            setState(false);
        }
    }

    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        setState(false);
    }

    @SubscribeEvent
    public void onLiving(final LivingEvent.LivingUpdateEvent event) {
        if (!state || !hit.state || mc.currentScreen != null || mc.thePlayer == null || mc.theWorld == null
                || mc.thePlayer.onGround) {
            return;
        }
        if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
            movement.setStrafe(movement.getSpeed() * dispersion.getValue());
        }
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent event) {
        if (!state || mc.currentScreen != null || mc.thePlayer == null || mc.theWorld == null
                || mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
            return;
        }
        if (fakeTimer) {
            timer.getTimer().timerSpeed = 1.0f;
            fakeTimer = false;
        }
        mc.thePlayer.setSprinting(false);
        field.renderPressed(mc.gameSettings.keyBindJump, GameSettings.isKeyDown(mc.gameSettings.keyBindJump));
        if (Math.abs(mc.thePlayer.movementInput.moveStrafe) > 0.1f) {
            mc.thePlayer.jumpMovementFactor = 0.026499f;
        } else {
            mc.thePlayer.jumpMovementFactor = 0.0244f;
        }
        if (movement.getSpeed() < 0.215 && mc.thePlayer.onGround) {
            movement.setStrafe(0.215);
        }
        if (mc.thePlayer.onGround && movement.isMoving()) {
            field.renderPressed(mc.gameSettings.keyBindJump, false);
            mc.thePlayer.jump();
            if (mc.thePlayer.isAirBorne) {
                return;
            }
            if (!fakeTimer) {
                timer.getTimer().timerSpeed = (float) timers.getValue();
                fakeTimer = true;
            }
            movement.setStrafe(movement.getSpeed());
            if (movement.getSpeed() >= 0.5) {
                return;
            }
            movement.setStrafe(0.48489999771118164);
        } else if (movement.isMoving()) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        }
    }

}
