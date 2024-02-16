package me.sting.client.product.module.movement;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import java.util.*;
import me.sting.client.product.module.*;
import net.minecraft.client.settings.*;
import me.sting.client.product.events.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import java.util.function.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import me.sting.client.product.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;

public class Inventory extends Module
{
    @RetentionField
    private BooleanValue noDetectableValue;
    @RetentionField
    private BooleanValue rotateValue;
    @RetentionField
    public ComboValue bypass;
    @RetentionField
    public ComboValue sprint;
    public List blinkPacketList;
    private boolean lastInvOpen;
    private boolean invOpen;
    
    public Inventory() {
        this.noDetectableValue = new BooleanValue("NoDetectable", false);
        this.rotateValue = new BooleanValue("Rotate", true);
        this.bypass = new ComboValue("Bypass", false, "option", new String[] { "state", "NoOpenPacket", "Blink" });
        this.sprint = new ComboValue("NoSprint", false, "option", new String[] { "state", "Real", "PacketSpoof" });
        this.blinkPacketList = new ArrayList();
        this.lastInvOpen = false;
        this.invOpen = false;
        this.setName("Inventory");
        this.setCategory(ModuleCategory.Movement);
    }
   
    @Override
    public void onDisable() {
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindForward) || mc.currentScreen != null) {
            field.renderPressed(mc.gameSettings.keyBindForward, false);
        }
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindBack) || mc.currentScreen != null) {
            field.renderPressed(mc.gameSettings.keyBindBack, false);
        }
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight) || mc.currentScreen != null) {
            field.renderPressed(mc.gameSettings.keyBindRight, false);
        }
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft) || mc.currentScreen != null) {
            field.renderPressed(mc.gameSettings.keyBindLeft, false);
        }
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindJump) || mc.currentScreen != null) {
            field.renderPressed(mc.gameSettings.keyBindJump, false);
        }
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSprint) || mc.currentScreen != null) {
            field.renderPressed(mc.gameSettings.keyBindSprint, false);
        }
        blinkPacketList.clear();
        lastInvOpen = false;
        invOpen = false;
    }

    @SubscribeEvent
    public void onPacket(PacketReceivedSendEvent event) {
        Packet packet = event.getPacket();
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        lastInvOpen = invOpen;
        if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus) packet)
                .getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
            invOpen = true;
            if (sprint.combos[2].state) {
                if (mc.thePlayer.isSprinting()) {
                    mc.getNetHandler().addToSendQueue(
                            new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                if (mc.thePlayer.isSneaking()) {
                    mc.getNetHandler().addToSendQueue(
                            new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
            }
        }
        if ((packet instanceof S2EPacketCloseWindow && !(packet instanceof C0DPacketCloseWindow))
                || (packet instanceof C0DPacketCloseWindow)) {
            invOpen = false;
            if (sprint.combos[2].state) {
                if (mc.thePlayer.isSprinting()) {
                    mc.getNetHandler().addToSendQueue(
                            new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                if (mc.thePlayer.isSneaking()) {
                    mc.getNetHandler().addToSendQueue(
                            new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                }
            }
        }
        if (bypass.combos[1].state) {
            if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus) packet)
                    .getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                event.setCanceled(true);
            }
        } else if (bypass.combos[2].state && packet instanceof C03PacketPlayer) {
            if (lastInvOpen) {
                blinkPacketList.add(packet);
                event.setCanceled(true);
            } else if (blinkPacketList.isEmpty()) {
                blinkPacketList.add(packet);
                event.setCanceled(true);
                // blinkPacketList.forEach(mc.thePlayer.sendQueue::addToSendQueue);
                for(Object p : blinkPacketList) {
                    mc.thePlayer.sendQueue.addToSendQueue((Packet)p);
                }
                blinkPacketList.clear();
            }
        }
    }

    @SubscribeEvent
    public void onWorld(WorldEvent event) {
        blinkPacketList.clear();
        invOpen = false;
        lastInvOpen = false;
    }

    @SubscribeEvent
    public void onPlayer(TickEvent.PlayerTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)
                && (!noDetectableValue.state || !(mc.currentScreen instanceof GuiContainer))) {
            field.renderPressed(mc.gameSettings.keyBindForward, GameSettings.isKeyDown(mc.gameSettings.keyBindForward));
            field.renderPressed(mc.gameSettings.keyBindBack, GameSettings.isKeyDown(mc.gameSettings.keyBindBack));
            field.renderPressed(mc.gameSettings.keyBindRight, GameSettings.isKeyDown(mc.gameSettings.keyBindRight));
            field.renderPressed(mc.gameSettings.keyBindLeft, GameSettings.isKeyDown(mc.gameSettings.keyBindLeft));
            field.renderPressed(mc.gameSettings.keyBindJump, GameSettings.isKeyDown(mc.gameSettings.keyBindJump));
            field.renderPressed(mc.gameSettings.keyBindSprint, GameSettings.isKeyDown(mc.gameSettings.keyBindSprint));
            if (mc.currentScreen instanceof GraphicalUserInterface && mc.thePlayer.isSneaking()
                    && mc.thePlayer.isBlocking() && mc.thePlayer.isEating()
                    && Float.compare(mc.thePlayer.moveForward, 0.0f) != 0 && mc.thePlayer.isCollidedHorizontally
                    && !mc.thePlayer.isDead) {
                try {
                    mc.thePlayer.setSprinting(true);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
            }
            if (rotateValue.state) {
                if (Keyboard.isKeyDown(Keyboard.KEY_UP) && mc.thePlayer.rotationPitch > -90.0f) {
                    mc.thePlayer.rotationPitch -= 5.0f;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && mc.thePlayer.rotationPitch < 90.0f) {
                    mc.thePlayer.rotationPitch += 5.0f;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                    mc.thePlayer.rotationYaw -= 5.0f;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                    mc.thePlayer.rotationYaw += 5.0f;
                }
            }
        }
    }

    private void lambda$onPacket$0(C03PacketPlayer packet) {
        
    }

}
