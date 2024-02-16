package me.sting.client.product.module.utilities;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.sting.client.product.Sting;
import me.sting.client.product.events.PacketReceivedSendEvent;
import me.sting.client.product.gui.GraphicalUserInterface;
import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.ComboValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import me.sting.client.product.utils.APIUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class AntiStaffs extends Module
{
    @RetentionField
    private BooleanValue block;
    public static LinkedList SPECS_LINKEDLIST;
    private String randomPoint;
    @RetentionField
    public static ComboValue modes;
    @RetentionField
    public ComboValue watch;
    @RetentionField
    private BooleanValue spammer;
    private String endRandom;
    @RetentionField
    public static BooleanValue noti;
    
    public AntiStaffs() {
        this.block = new BooleanValue("Detect Distance", false);
        this.randomPoint = null;
        this.watch = new ComboValue("WatchChat Detected", true, "option", new String[] { "state", "Party", "Guild" });
        this.spammer = new BooleanValue("WatchChat Spammer", false);
        this.endRandom = null;
        this.setName("AntiStaffs");
        this.isPrivate();
        this.randomPoint = this.random.getRandomChat(2);
        this.endRandom = this.random.getRandomChat(2);
        this.setCategory(ModuleCategory.Utilities);
        AntiStaffs.modes.combos[1].setState(true);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
        if (Sting.SERVER != null && !Sting.SERVER.equalsIgnoreCase("localhost")
                && (!Sting.SERVER.contains("blocksmc.com") || Sting.SERVER.contains("BLOCKSMC.COM"))
                && !this.mc.isSingleplayer()) {
            // APIUtil.fetchStaffs();
        } else if (this.security) {
            this.setState(false);
            this.mc.thePlayer.addChatComponentMessage((IChatComponent) new ChatComponentText(
                    Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + "join blocksmc server."));
        }
    }

    @SubscribeEvent
    public void disconnect(
            final FMLNetworkEvent.ClientDisconnectionFromServerEvent fmlNetworkEventClientDisconnectionFromServerEvent) {
        if (AntiStaffs.SPECS_LINKEDLIST.isEmpty()) {
            AntiStaffs.SPECS_LINKEDLIST.clear();
        }
        this.timer.passed.reset();
    }

    @SubscribeEvent
    public void world(final WorldEvent.Load worldEventLoad) {
        if (AntiStaffs.SPECS_LINKEDLIST.isEmpty()) {
            AntiStaffs.SPECS_LINKEDLIST.clear();
        }
        this.timer.passed.reset();
    }

    @SubscribeEvent
    public void Packet(final PacketReceivedSendEvent packetReceivedSendEvent) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        final Packet packet = packetReceivedSendEvent.getPacket();
        if (packet == null) {
            return;
        }
        if (packet instanceof S38PacketPlayerListItem) {
            final S38PacketPlayerListItem s38PacketPlayerListItem = (S38PacketPlayerListItem) packet;
            if (s38PacketPlayerListItem.getAction() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
                for (final S38PacketPlayerListItem.AddPlayerData s38PacketPlayerListItemAddPlayerData : s38PacketPlayerListItem
                        .getEntries()) {
                    if (s38PacketPlayerListItemAddPlayerData != null) {
                        for (final String s : APIUtil.STAFFS_COLLECTED) {
                            if (s != null
                                    && s38PacketPlayerListItemAddPlayerData.getProfile().getName().equalsIgnoreCase(s)
                                    && AntiStaffs.noti.state) {
                                if (this.spammer.state
                                        && this.timer.passed.hasTimePassedCURRENT(this.nextInt(515, 515))) {
                                    if (this.watch.combos[1].state) {
                                        this.mc.thePlayer.sendChatMessage("/p chat " + this.randomPoint + " " + s
                                                + " detected. " + this.endRandom);
                                    } else if (this.watch.combos[2].state) {
                                        this.mc.thePlayer.sendChatMessage("/g chat " + this.randomPoint + " " + s
                                                + " detected. " + this.endRandom);
                                    }
                                    this.timer.passed.reset();
                                }
                                this.mc.thePlayer.addChatComponentMessage((IChatComponent) new ChatComponentText(
                                        Sting.CLIENT_PREFIX + this.getChatName() + EnumChatFormatting.RED + s
                                                + EnumChatFormatting.YELLOW + " detected."));
                            }
                        }
                    }
                }
            }
            if (!(packet instanceof S38PacketPlayerListItem)
                    || s38PacketPlayerListItem.getAction() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (final S38PacketPlayerListItem.AddPlayerData s38PacketPlayerListItemAddPlayerData2 : s38PacketPlayerListItem
                        .getEntries()) {
                    if (s38PacketPlayerListItemAddPlayerData2 != null) {
                        final EntityPlayer getPlayerEntityByUUID = this.mc.theWorld
                                .getPlayerEntityByUUID(s38PacketPlayerListItemAddPlayerData2.getProfile().getId());
                        if (getPlayerEntityByUUID != null
                                && APIUtil.STAFFS_COLLECTED.contains(getPlayerEntityByUUID.getName())) {
                            if (AntiStaffs.SPECS_LINKEDLIST.contains(getPlayerEntityByUUID.getName())) {
                                AntiStaffs.SPECS_LINKEDLIST.add(getPlayerEntityByUUID.getName());
                            }
                            if (this.watch.combos[1].state) {
                                this.mc.thePlayer.sendChatMessage(
                                        "/p chat " + this.randomPoint + " no staffs in spectator ;) " + this.endRandom);
                            } else if (this.watch.combos[2].state) {
                                this.mc.thePlayer.sendChatMessage(
                                        "/g chat " + this.randomPoint + " no staffs in spectator ;) " + this.endRandom);
                            }
                            if (AntiStaffs.noti.state) {
                                this.mc.thePlayer.addChatComponentMessage(
                                        (IChatComponent) new ChatComponentText(Sting.CLIENT_PREFIX + this.getChatName()
                                                + EnumChatFormatting.RED + getPlayerEntityByUUID.getName()
                                                + EnumChatFormatting.YELLOW + " might be spectating."));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent clientChatReceivedEvent) {
        if (clientChatReceivedEvent.message == null) {
            return;
        }
        final String[] split = EnumChatFormatting
                .getTextWithoutFormattingCodes(clientChatReceivedEvent.message.getUnformattedText()).split(" ");
        if (split == null) {
            return;
        }
        for (final String s : APIUtil.STAFFS_COLLECTED) {
            if ((split[3].equalsIgnoreCase(s) && split[4].contains("isn't") && !split[5].contains("online."))
                    || (split[4].equalsIgnoreCase(s) && split[5].contains("isn't") && split[6].contains("online."))) {
                AntiStaffs.SPECS_LINKEDLIST.remove(s);
            }
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayPost(final RenderGameOverlayEvent.Post renderGameOverlayEventPost)
            throws IllegalAccessException {
        if (!(this.mc.currentScreen instanceof GuiMainMenu)) {
            return;
        }
        if (renderGameOverlayEventPost.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        GL11.glPushMatrix();
        int n = GraphicalUserInterface.y[5] + 12;
        @SuppressWarnings("null")
        final List sortedCopy = this.field.getNetworkPlayerInfo(this.mc.ingameGUI.getTabList())
                .sortedCopy((Iterable) this.mc.getNetHandler().getPlayerInfoMap());
        if (AntiStaffs.modes.combos[1].state) {
            this.mc.fontRendererObj.drawStringWithShadow(
                    EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "[#] STAFFS",
                    (float) GraphicalUserInterface.x[5], (float) n, -1);
            if (APIUtil.STAFFS_COLLECTED.isEmpty()) {
                for (int n2 = 0; n2 < APIUtil.STAFFS_COLLECTED.size(); ++n2) {
                    final String s = (String) APIUtil.STAFFS_COLLECTED.get(n2);
                    if (s != null && sortedCopy.isEmpty()) {
                        for (int n3 = 0; n3 < sortedCopy.size(); ++n3) {
                            final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo) sortedCopy.get(n3);
                            if (networkPlayerInfo != null
                                    && networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(s)) {
                                if (AntiStaffs.SPECS_LINKEDLIST.contains(s)) {
                                    AntiStaffs.SPECS_LINKEDLIST.remove(s);
                                }
                                if (this.spammer.state
                                        && this.timer.passed.hasTimePassedCURRENT(this.nextInt(515, 515))) {
                                    if (this.watch.combos[1].state) {
                                        this.mc.thePlayer.sendChatMessage("/p chat " + this.randomPoint + " " + s
                                                + " detected. " + this.endRandom);
                                    } else if (this.watch.combos[2].state) {
                                        this.mc.thePlayer.sendChatMessage("/g chat " + this.randomPoint + " " + s
                                                + " detected. " + this.endRandom);
                                    }
                                    this.timer.passed.reset();
                                }
                                String string = "";
                                final EntityPlayer getPlayerEntityByUUID = this.mc.theWorld
                                        .getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId());
                                if (this.block.state && getPlayerEntityByUUID != null
                                        && getPlayerEntityByUUID != this.mc.thePlayer && (double) this.mc.thePlayer
                                                .getDistanceToEntity((Entity) getPlayerEntityByUUID) > 1.0) {
                                    string = EnumChatFormatting.GRAY + " [" + (int) this.mc.thePlayer
                                            .getDistanceToEntity((Entity) getPlayerEntityByUUID) + "]";
                                }
                                String s2;
                                if (getPlayerEntityByUUID != null && getPlayerEntityByUUID.getDisplayName() != null
                                        && getPlayerEntityByUUID.getDisplayName().getUnformattedText().toLowerCase()
                                                .startsWith("\ufffd9")) {
                                    s2 = EnumChatFormatting.BLUE + "" + s;
                                } else {
                                    s2 = EnumChatFormatting.RED + "" + s;
                                }
                                this.mc.fontRendererObj.drawStringWithShadow(s2 + string,
                                        (float) GraphicalUserInterface.x[5], (float) (n + 10), -1);
                                n += 9;
                            }
                        }
                    }
                }
            }
        }
        int n4 = GraphicalUserInterface.y[6] + 12;
        if (AntiStaffs.modes.combos[2].state) {
            this.mc.fontRendererObj.drawStringWithShadow(
                    EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "[!] CHECKS",
                    (float) GraphicalUserInterface.x[6], (float) n4, -1);
            for (int n5 = 0; n5 < AntiStaffs.SPECS_LINKEDLIST.size(); ++n5) {
                this.mc.fontRendererObj.drawStringWithShadow(
                        EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD
                                + (String) AntiStaffs.SPECS_LINKEDLIST.get(n5),
                        (float) GraphicalUserInterface.x[6], (float) (n4 + 10), -1);
                n4 += 9;
            }
        }
        GL11.glPopMatrix();
    }
    
    @SubscribeEvent
    public void RenderTick(final TickEvent.RenderTickEvent tickEvent) {
        if (tickEvent.phase != TickEvent.Phase.END) {
            return;
        }
        if (this.mc.theWorld == null || this.mc.thePlayer == null) {
            return;
        }
        if (AntiStaffs.modes.combos[3].state) {
            return;
        }
        final int n = GraphicalUserInterface.x[7];
        final int n2 = GraphicalUserInterface.y[7] + 16;
        final int n3 = n2 + 100;
        Gui.drawRect(n, n2, n + 100, n3, new Color(15, 15, 15).getRGB());
        Gui.drawRect(n - 1, n2 - 1, n + 100, n2, -1);
        Gui.drawRect(n - 1, n3, n + 100, n3 + 1, -1);
        Gui.drawRect(n - 1, n2, n, n3, -1);
        Gui.drawRect(n + 100, n2, n + 99, n3, -1);
        this.render.drawPolygon(n + 49, n2 + 50, 5.0, 3, Color.white.getRGB(), false);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        final int getScaleFactor = new ScaledResolution(this.mc).getScaleFactor();
        GL11.glScissor(5 * getScaleFactor, this.mc.displayHeight - getScaleFactor * 170,
                105 * getScaleFactor - getScaleFactor * 5, getScaleFactor * 100);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        for (int n4 = 0; n4 < this.mc.theWorld.loadedEntityList.size(); ++n4) {
            final Entity entity = this.mc.theWorld.loadedEntityList.get(n4);
            if (!APIUtil.STAFFS_COLLECTED.isEmpty()) {
                for (int n5 = 0; n5 < APIUtil.STAFFS_COLLECTED.size(); ++n5) {
                    final String s = (String) APIUtil.STAFFS_COLLECTED.get(n5);
                    if (s != null && (entity instanceof EntityPlayer) && entity != this.mc.thePlayer
                            && entity.getName().equalsIgnoreCase(s)) {
                        final double getDistanceSqToEntity = this.mc.thePlayer.getDistanceSqToEntity(entity);
                        if (getDistanceSqToEntity > 360.0) {
                            final double n6 = (this.mc.thePlayer.rotationYaw
                                    + Math.atan2(entity.posX - this.mc.thePlayer.posX,
                                            entity.posZ - this.mc.thePlayer.posZ) * 57.295780181884766)
                                    % 360.0 * 0.01745329238474369;
                            final double n7 = getDistanceSqToEntity / 7.0;
                            final double n8 = n7 * Math.sin(n6);
                            final double n9 = n7 * Math.cos(n6);
                            GL11.glPushMatrix();
                            GL11.glEnable(3042);
                            GL11.glEnable(2848);
                            GL11.glDisable(2929);
                            GL11.glDisable(3553);
                            GL11.glBlendFunc(770, 771);
                            GL11.glEnable(3042);
                            GL11.glLineWidth(0.5f);
                            GL11.glColor3d(255.0, 255.0, 255.0);
                            GL11.glBegin(2);
                            GL11.glVertex2d((double) (n + 49), (double) (n2 + 50));
                            GL11.glVertex2d(n + 49 - n8, n2 + 50 - n9);
                            GL11.glEnd();
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            GL11.glDisable(3042);
                            GL11.glEnable(3553);
                            GL11.glEnable(2929);
                            GL11.glDisable(2848);
                            GL11.glDisable(3042);
                            GL11.glPopMatrix();
                            this.render.drawPolygon(n + 49 - n8, n2 + 50 - n9, 2.0, 360, Color.red.getRGB(), false);
                        }
                    }
                }
            }
        }
    }
    
    public int nextInt(final int n, final int n2) {
        return n2 - n <= 0 ? n : (n + new Random().nextInt(n2 - n));
    }
    
    static {
        AntiStaffs.SPECS_LINKEDLIST = new LinkedList();
        AntiStaffs.modes = new ComboValue("Detected Methods", false, "option", new String[] { "state", "[#] STAFFS", "[!] CHECKS", "[!] RADAR", "[!] CHAMS", "[!] TRACER" });
        AntiStaffs.noti = new BooleanValue("Notifications", false);
    }
    
}
