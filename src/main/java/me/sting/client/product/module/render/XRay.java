package me.sting.client.product.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.sting.client.product.Sting;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class XRay extends Module
{
    @RetentionField
    public SliderValue range;
    private Timer tiimer;
    public List<BlockPos> blockPos;
    
    public XRay() {
        this.range = new SliderValue("Range", 20.0, 5.0, 50.0, false);
        this.tiimer = new Timer();
        this.blockPos = new ArrayList<BlockPos>();
        this.setName("XRay");
        this.setCategory(ModuleCategory.Render);
    }
    
    @Override
    public void onEnable() {
        // Check if tiimer is null, and create a new instance if it is
        if (this.tiimer == null) {
            this.tiimer = new Timer();
        }

        // Check if tiimer is not null before scheduling the task
        if (this.tiimer != null) {
            try {
                // Schedule the timer task to run at fixed intervals
                this.tiimer.scheduleAtFixedRate(createTimerTask(), 0L, 700L);
            } catch (NullPointerException ex) {
                // Handle NullPointerException
                if (this.mc != null && this.mc.thePlayer != null) {
                    this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(Sting.CLIENT_PREFIX
                            + this.getChatName() + EnumChatFormatting.RED + "null pointer, try again."));
                }
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (isTimerNotNull()) {
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
        }
    }

    private TimerTask createTimerTask() {
        return new XRayTask(this);
    }



    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (isPlayerAndWorldNotNull() && this.blockPos.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<BlockPos> blockPositions = new ArrayList<BlockPos>(blockPos);
            for (BlockPos pos : blockPositions) {
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                int[] color = getBlockColor(block);
                if (isColorValid(color)) {
                    this.render.renderBlock(pos, new Color(color[0], color[1], color[2]).getRGB(), true);
                }
            }
        }
    }

    private int[] getBlockColor(final Block block) {
        int red = 0, green = 0, blue = 0;
        if (block.equals(Blocks.iron_ore)) {
            red = green = blue = 255;
        } else if (block.equals(Blocks.gold_ore)) {
            red = green = 255;
        } else if (block.equals(Blocks.diamond_ore)) {
            green = 220;
            blue = 255;
        } else if (block.equals(Blocks.emerald_ore)) {
            red = 35;
            green = 255;
        } else if (block.equals(Blocks.lapis_ore)) {
            green = 50;
            blue = 255;
        } else if (block.equals(Blocks.redstone_ore)) {
            red = 255;
        } else if (block.equals(Blocks.coal_ore)) {
            red = green = blue = 10;
        } else if (block.equals(Blocks.mob_spawner)) {
            red = 30;
            blue = 135;
        }
        return new int[] { red, green, blue };
    }

    static List<BlockPos> accessBlockPositions(final XRay xRay) {
        return xRay.blockPos;
    }

    static SliderValue accessRange(final XRay xRay) {
        return xRay.range;
    }

    private boolean isTimerNotNull() {
        return this.timer != null;
    }

    private boolean isPlayerAndWorldNotNull() {
        return this.mc.thePlayer != null && this.mc.theWorld != null;
    }

    private boolean isColorValid(int[] color) {
        return color[0] + color[1] + color[2] > 0;
    }

}
