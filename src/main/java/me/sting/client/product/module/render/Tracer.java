package me.sting.client.product.module.render;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import net.minecraft.entity.*;
import me.sting.client.product.commands.*;
import net.minecraftforge.client.event.*;
import me.sting.client.product.utils.*;
import me.sting.client.product.module.utilities.*;
import me.sting.client.product.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Tracer extends Module
{
    @RetentionField
    public SliderValue size;
    @RetentionField
    public ComboValue detection;
    @RetentionField
    public BooleanValue both;
    public boolean disable;
    public boolean originalViewBobbing;

    public Tracer() {
        this.size = new SliderValue("Line Size", 1.0, 1.0, 3.0, false);
        this.detection = new ComboValue("Detect", false, "option", new String[] { "state", "Friends", "Enemys" });
        this.both = new BooleanValue("Both", false);
        this.disable = false;
        this.setName("Tracer");
        this.setCategory(ModuleCategory.Render);
    }
    
    @Override
    public void onEnable() {
        this.originalViewBobbing = this.mc.gameSettings.viewBobbing;
        if (isViewBobbingEnabled()) {
            this.mc.gameSettings.viewBobbing = false;
        }
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.viewBobbing = this.originalViewBobbing;
    }

    public void detectFriend(final EntityPlayer player) {
        if (player == null) {
            return;
        }
        if (FriendCommand.friends.contains(player.getName().toLowerCase())) {
            renderPlayerWithColor(player, new Color(0, 5, 0).getRGB());
        }
    }

    public void detectEnemy(final EntityPlayer player) {
        if (player == null) {
            return;
        }
        if (EnemyCommand.enms.contains(player.getName().toLowerCase())) {
            renderPlayerWithColor(player, new Color(128, 0, 0).getRGB());
        }
    }

    @SubscribeEvent
    public void renderWorldLast(final RenderWorldLastEvent event) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        for (EntityPlayer player : this.mc.theWorld.playerEntities) {
            if (this.detection.combos[1].state) {
                detectFriend(player);
            }
            if (this.detection.combos[2].state) {
                detectEnemy(player);
            }
            if (!APIUtil.STAFFS_COLLECTED.isEmpty()) {
                for (String staff : APIUtil.STAFFS_COLLECTED) {
                    if (AntiStaffs.modes.combos[5].state && player.getName().equalsIgnoreCase(staff)) {
                        renderPlayerWithColor(player, new Color(255, 0, 0).getRGB());
                    }
                }
            }
            if (this.both.state) {
                if (player != this.mc.thePlayer && player.deathTime == 0
                        && (this.detection.combos[1].state || this.detection.combos[2].state)) {
                    renderPlayerWithColor(player, Sting.color.getRGB());
                }
            } else if (this.detection.combos[1].state && this.detection.combos[2].state && player != this.mc.thePlayer
                    && player.deathTime == 0) {
                renderPlayerWithColor(player, Sting.color.getRGB());
            }
        }
    }

    private void renderPlayerWithColor(EntityPlayer player, int colorRGB) {
        if (this.state || player.isInvisible()) {
            this.render.renderPlayer(player, colorRGB, (float) this.size.getValue());
        }
    }

    private boolean isViewBobbingEnabled() {
        return this.originalViewBobbing;
    }

}
