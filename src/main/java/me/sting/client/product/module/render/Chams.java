package me.sting.client.product.module.render;

import me.sting.client.product.gui.values.*;
import me.sting.client.product.storage.*;
import me.sting.client.product.module.*;
import org.lwjgl.opengl.*;
import me.sting.client.product.module.utilities.*;
import me.sting.client.product.utils.*;
import me.sting.client.product.utils.APIUtil;
import me.sting.client.product.commands.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;

public class Chams extends Module
{
    @RetentionField
    private BooleanValue perrank;
    
    public Chams() {
        this.perrank = new BooleanValue("Per Rank Color", false);
        this.setName("Chams");
        this.setCategory(ModuleCategory.Render);
    }
    
    @SubscribeEvent
public void onRenderPlayerPre(final RenderPlayerEvent.Pre event) {
    if (event.entityPlayer == this.mc.thePlayer) {
        return;
    }
    GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
    if (this.perrank.state && event.entityPlayer != null && event.entityPlayer.getDisplayName() != null) {
        String displayName = event.entityPlayer.getDisplayName().getUnformattedText().toLowerCase();
        if (displayName.startsWith("\ufffd5")) {
            GL11.glColor3d(128.0, 0.0, 128.0);
        } else if (displayName.startsWith("\ufffd8")) {
            GL11.glColor3d(0.0, 128.0, 128.0);
        } else if (displayName.startsWith("\ufffda")) {
            GL11.glColor3d(0.0, 255.0, 0.0);
        } else if (displayName.startsWith("\ufffdb")) {
            GL11.glColor3d(0.0, 255.0, 255.0);
        } else if (displayName.startsWith("\ufffd6")) {
            GL11.glColor3d(255.0, 165.0, 0.0);
        } else if (displayName.startsWith("\ufffd9")) {
            GL11.glColor3d(0.0, 0.0, 255.0);
        }
    }
    if (AntiStaffs.modes.combos[4].state) {
        for (String staff : APIUtil.STAFFS_COLLECTED) {
            if (event.entityPlayer.getName().equalsIgnoreCase(staff)) {
                GL11.glColor3d(255.0, 0.0, 0.0);
            }
        }
    }
    if (FriendCommand.friends.contains(event.entityPlayer.getName().toLowerCase())) {
        GL11.glColor3d(0.0, 5.0, 0.0);
    }
    if (EnemyCommand.enms.contains(event.entityPlayer.getName().toLowerCase())) {
        GL11.glColor3d(128.0, 0.0, 0.0);
    }
    GL11.glPolygonOffset(1.0f, -1100000.0f);
}

@SubscribeEvent
public void onRenderPlayerPost(final RenderPlayerEvent.Post event) {
    if (event.entityPlayer == this.mc.thePlayer) {
        return;
    }
    GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
    // onRenderPlayerPre(event);
}

}
