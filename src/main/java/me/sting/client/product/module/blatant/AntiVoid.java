package me.sting.client.product.module.blatant;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;
import me.sting.client.product.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import me.sting.client.product.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class AntiVoid extends Module
{
    public boolean tried;
    public boolean flagged;
    @RetentionField
    public SliderValue fall;
    @RetentionField
    public SliderValue offset;
    public static BlockPos CurrentBlockPos;
    @RetentionField
    public ComboValue mode;
    public static BlockPos LastBlockPos;
    @RetentionField
    public BooleanValue flag;
    @RetentionField
    private BooleanValue onlyvoid;
    public boolean canSpoof;
    public double lastRecY;
    
    public AntiVoid() {
        this.tried = false;
        this.flagged = false;
        this.fall = new SliderValue("Fall Distance", 5.0, 1.0, 20.0, true);
        this.offset = new SliderValue("Fall Offset", 0.5, 0.5, 0.8, false);
        this.mode = new ComboValue("Fall Mode", true, "option", new String[] { "state", "Fake Block", "Normal" });
        this.flag = new BooleanValue("Disable onFlag", true);
        this.onlyvoid = new BooleanValue("onVoid Only", false);
        this.canSpoof = false;
        this.lastRecY = 0.0;
        this.setName("AntiVoid");
        this.isPrivate();
        this.setCategory(ModuleCategory.Blatant);
        this.mode.combos[2].setState(true);
    }
    
    @Override
    public void onEnable() {
        isPrivate();
        canSpoof = false;
        lastRecY = (mc.thePlayer != null) ? mc.thePlayer.posY : 0.0;
        tried = false;
        flagged = false;
    }

    @SubscribeEvent
    public void onWorld(WorldEvent worldEvent) {
        if (lastRecY != 0.0 && mc.thePlayer != null) {
            lastRecY = mc.thePlayer.posY;
        }
    }

    @Override
    public void onDisable() {
        if (AntiVoid.LastBlockPos != null) {
            mc.theWorld.setBlockState(AntiVoid.LastBlockPos, Blocks.barrier.getDefaultState());
        }
    }

    @SubscribeEvent
    public void onPlayer(TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        if (!state || mc.currentScreen != null || mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (mc.thePlayer.fallDistance > fall.getValue()) {
            if (mode.combos[2].state) {
                if (mc.thePlayer.onGround) {
                    tried = false;
                    flagged = false;
                }
                canSpoof = false;

                float fallDistance_ = mc.thePlayer.fallDistance - (float) fall.getValue();
                double posYDifference = mc.thePlayer.posY - (lastRecY + 0.01D);

                if (fallDistance_ != 0.0F && posYDifference != 0.0D &&
                        mc.thePlayer.motionY != 0.0D && mc.thePlayer.onGround && flagged) {

                    mc.thePlayer.motionY = 0.0;
                    mc.thePlayer.motionZ *= 0.838;
                    mc.thePlayer.motionX *= 0.838;
                    canSpoof = true;
                }
                lastRecY = mc.thePlayer.posY;
            }
            if (mode.combos[1].state) {
                double yDifference = mc.thePlayer.prevPosY - mc.thePlayer.posY - 10.0D;
                if (yDifference != 0.0D || mc.thePlayer.onGround || mc.thePlayer.capabilities.isFlying) {
                    return;
                }
                if (!onlyvoid.state || checkVoid()) {
                    BlockPos blockPos = new BlockPos(MathHelper.floor_double(mc.thePlayer.posX),
                            MathHelper.floor_double(mc.thePlayer.posY - 1.0),
                            MathHelper.floor_double(mc.thePlayer.posZ));

                    if (AntiVoid.CurrentBlockPos == null || block.isSameBlock(blockPos, AntiVoid.CurrentBlockPos)) {
                        AntiVoid.CurrentBlockPos = blockPos;
                    }

                    if (mc.theWorld.isAirBlock(AntiVoid.CurrentBlockPos)) {
                        mc.theWorld.setBlockState(blockPos, Blocks.barrier.getDefaultState());
                        if (AntiVoid.LastBlockPos != null) {
                            mc.theWorld.setBlockState(AntiVoid.LastBlockPos, Blocks.air.getDefaultState());
                        }
                        AntiVoid.LastBlockPos = blockPos;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPacket(PacketReceivedSendEvent packetReceivedSendEvent) {
        Packet packet = packetReceivedSendEvent.getPacket();
        if (!state || packet == null || mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (canSpoof && packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer) packet).setMoving(true);
        }
        if (canSpoof && packet instanceof S08PacketPlayerPosLook) {
            flagged = true;
        }
        if (flag.state && packet instanceof S08PacketPlayerPosLook && movement.isMoving()) {
            mc.thePlayer.addChatComponentMessage(new ChatComponentText(
                    Sting.CLIENT_PREFIX + getChatName() + EnumChatFormatting.WHITE + "Flagged Detect."));
            setState(false);
        }
    }

    public boolean checkVoid() {
        int n = (int) (-(mc.thePlayer.posY - 1.4857625));
        int empty = 1;
        while (n > 0) {
            empty = mc.theWorld.getCollidingBoundingBoxes(mc.getRenderViewEntity(),
                    mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX * offset.getValue(),
                            (double) n,
                            mc.thePlayer.motionZ * offset.getValue()))
                    .isEmpty() ? 1 : 0;
            ++n;
            if (empty != 0) {
                break;
            }
        }
        return empty != 0;
    }

    static {
        AntiVoid.CurrentBlockPos = null;
        AntiVoid.LastBlockPos = null;
    }

}
