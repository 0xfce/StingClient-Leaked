package me.sting.client.product.module.blatant;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Cracking extends Module
{
    @RetentionField
    public SliderValue range;
    @RetentionField
    public SliderValue ticks;
    @RetentionField
    private BooleanValue fastbreak;
    private BlockPos blockPos;
    private int tick;
    
    public Cracking() {
        this.range = new SliderValue("Range", 5.0, 1.0, 7.0, false);
        this.ticks = new SliderValue("Ticks", 20.0, 1.0, 50.0, true);
        this.fastbreak = new BooleanValue("Stop Destroy", true);
        this.blockPos = null;
        this.tick = 0;
        this.setName("Cracking");
        this.isPrivate();
        this.setCategory(ModuleCategory.Blatant);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }

    @SubscribeEvent
    public void onClient(final TickEvent.ClientTickEvent tickEvent$ClientTickEvent) {
        ++this.tick;
        if ((this.tick % ((int) this.ticks.getValue())) == 0 && this.mc.thePlayer != null && this.mc.theWorld != null) {
            this.tick = 0;
            int n2;
            for (int n = n2 = (int) this.range.getValue(); n2 >= -n; --n2) {
                for (int n3 = -n; n3 <= n; ++n3) {
                    for (int n4 = -n; n4 <= n; ++n4) {
                        final BlockPos blockPos = new BlockPos(this.mc.thePlayer.posX + n3, this.mc.thePlayer.posY + n2,
                                this.mc.thePlayer.posZ + n4);
                        final boolean isBed = this.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.bed;
                        final boolean isDragonEgg = this.mc.theWorld.getBlockState(blockPos)
                                .getBlock() == Blocks.dragon_egg;
                        if (this.blockPos == blockPos) {
                            if (!isBed || isDragonEgg) {
                                this.blockPos = null;
                            }
                        } else if (!isBed || !isDragonEgg) {
                            this.breakBlock(blockPos, this.fastbreak.state);
                            this.blockPos = blockPos;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void breakBlock(final BlockPos blockPos, final boolean b) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
        if (b) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
        }
    }

}
