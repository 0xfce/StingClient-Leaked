package me.sting.client.product.module.render;

import java.util.TimerTask;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class XRayTask extends TimerTask {

        private XRay xray;

        public XRayTask(XRay xray) {
            this.xray = xray;
        }

        @Override
        public void run() {
            xray.blockPos.clear();
            
            Minecraft mc = Minecraft.getMinecraft();
            
            int radius = (int) xray.range.getValue();
            
            for (int y = radius; y >= -radius; y--) {
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (mc.thePlayer != null && mc.theWorld != null) {
                            BlockPos pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                            Block block = mc.theWorld.getBlockState(pos).getBlock();
                            
                            if (!isHighlightedBlock(block)) {
                                xray.blockPos.add(pos);
                            }
                        }
                    }
                }
            }
        }

        private boolean isHighlightedBlock(Block block) {
        return block.equals(Blocks.iron_ore) || // Examples of highlighted blocks
                block.equals(Blocks.gold_ore) ||
                block.equals(Blocks.diamond_ore) ||
                block.equals(Blocks.emerald_ore) ||
                block.equals(Blocks.lapis_ore) ||
                block.equals(Blocks.redstone_ore) ||
                block.equals(Blocks.coal_ore) ||
                block.equals(Blocks.mob_spawner);
    }
}