package me.sting.client.product.module.blatant;

import org.lwjgl.input.Mouse;

import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoPlace extends Module
{
    @RetentionField
    public static SliderValue Delay;
    @RetentionField
    public static BooleanValue Right;
    public static MovingObjectPosition MovingObjectPosition;
    public static BlockPos BlockPos;
    public static long TimeMillis;
    public static int Ticks;
    
    public AutoPlace() {
        this.setName("AutoPlace");
        this.setCategory(ModuleCategory.Blatant);
    }
    
    @Override
    public void onEnable() {
        if (AutoPlace.Right.state && Mouse.isButtonDown(1) && this.mc.thePlayer.capabilities.isFlying
                && Module.getModule(AutoPlace.class) != null && Module.getModule(AutoPlace.class).state) {
            final ItemStack getHeldItem = this.mc.thePlayer.getHeldItem();
            if (getHeldItem == null || (getHeldItem.getItem() instanceof ItemBlock)) {
                return;
            }
            if (AutoPlace.Right.state) {
                if (this.field.rightClickDelayTimer != null) {
                    try {
                        this.field.rightClickDelayTimer.set(this.mc, (this.mc.thePlayer.motionY != 0.0) ? 1 : 1000);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            AutoPlace.MovingObjectPosition = null;
            AutoPlace.BlockPos = null;
            AutoPlace.Ticks = 0;
        }
    }

    @Override
    public void onDisable() {
        if (AutoPlace.Right.state) {
            if (this.field.rightClickDelayTimer != null) {
                try {
                    this.field.rightClickDelayTimer.set(this.mc, 4);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        AutoPlace.MovingObjectPosition = null;
        AutoPlace.BlockPos = null;
        AutoPlace.Ticks = 0;
    }

    @SubscribeEvent
    public void DrawBlockHighLight(final DrawBlockHighlightEvent drawBlockHighlightEvent) {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (this.mc.thePlayer.capabilities.isFlying && this.mc.thePlayer.getHeldItem() == null
                && (this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            final MovingObjectPosition objectMouseOver = this.mc.objectMouseOver;
            if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectType.BLOCK
                    && objectMouseOver.sideHit != EnumFacing.UP && objectMouseOver.sideHit != EnumFacing.DOWN) {
                if (AutoPlace.MovingObjectPosition == null && (double) AutoPlace.Ticks > AutoPlace.Delay.getValue()) {
                    ++AutoPlace.Ticks;
                } else {
                    AutoPlace.MovingObjectPosition = objectMouseOver;
                    final BlockPos getBlockPos = objectMouseOver.getBlockPos();
                    if ((AutoPlace.BlockPos == null || getBlockPos.getX() != AutoPlace.BlockPos.getX()
                            || getBlockPos.getY() != AutoPlace.BlockPos.getY()
                            || getBlockPos.getZ() != AutoPlace.BlockPos.getZ())
                            && this.mc.theWorld.getBlockState(getBlockPos).getBlock() != Blocks.air
                            && (this.mc.theWorld.getBlockState(getBlockPos).getBlock() instanceof BlockLiquid)
                            && (!AutoPlace.Right.state || Mouse.isButtonDown(1))
                            && System.currentTimeMillis() - AutoPlace.TimeMillis > 25L) {
                        AutoPlace.TimeMillis = System.currentTimeMillis();
                        if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld,
                                this.mc.thePlayer.getHeldItem(), getBlockPos, objectMouseOver.sideHit,
                                objectMouseOver.hitVec)) {
                            this.field.sendClick(1, true);
                            this.mc.thePlayer.swingItem();
                            this.field.sendClick(1, false);
                            AutoPlace.BlockPos = getBlockPos;
                            AutoPlace.Ticks = 0;
                        }
                    }
                }
            }
        }
    }

}
