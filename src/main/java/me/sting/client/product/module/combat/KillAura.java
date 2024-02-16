package me.sting.client.product.module.combat;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.lwjgl.input.Mouse;

import me.sting.client.product.commands.FriendCommand;
import me.sting.client.product.events.PacketReceivedSendEvent;
import me.sting.client.product.gui.values.BooleanValue;
import me.sting.client.product.gui.values.ComboValue;
import me.sting.client.product.gui.values.SliderValue;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.ModuleCategory;
import me.sting.client.product.storage.RetentionField;
import me.sting.client.product.utils.timers.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KillAura extends Module
{
    @RetentionField
    private SliderValue min;
    @RetentionField
    private SliderValue max;
    @RetentionField
    private SliderValue distance;
    @RetentionField
    private SliderValue swtch;
    @RetentionField
    private SliderValue limited;
    @RetentionField
    private SliderValue fov;
    @RetentionField
    private ComboValue target;
    @RetentionField
    private ComboValue priority;
    @RetentionField
    public BooleanValue autoblock;
    @RetentionField
    public BooleanValue raytrace;
    @RetentionField
    public BooleanValue rots;
    @RetentionField
    public BooleanValue click;
    @RetentionField
    public BooleanValue swng;
    @RetentionField
    public BooleanValue weapon;
    private EntityPlayer entityPlayer;
    public TimerUtil singleTimer;
    public TimerUtil switchTimer;
    private int count;
    
    public KillAura() {
        this.min = new SliderValue("Min", 10.0, 5.0, 20.0, true);
        this.max = new SliderValue("Max", 12.0, 5.0, 20.0, true);
        this.distance = new SliderValue("Distance", 5.0, 3.0, 7.0, false);
        this.swtch = new SliderValue("Switch Delay", 100.0, 10.0, 2000.0, true);
        this.limited = new SliderValue("Limited Multi", 3.0, 1.0, 10.0, true);
        this.fov = new SliderValue("Fov", 180.0, 30.0, 360.0, true);
        this.target = new ComboValue("Aura Target", true, "option", new String[] { "state", "Single", "Switch", "Multi" });
        this.priority = new ComboValue("Priority", true, "option", new String[] { "state", "Health", "Distance", "Armor" });
        this.autoblock = new BooleanValue("AutoBlock", false);
        this.raytrace = new BooleanValue("Raytrace", false);
        this.rots = new BooleanValue("Rotation", false);
        this.click = new BooleanValue("While Click", false);
        this.swng = new BooleanValue("Swing Item", false);
        this.weapon = new BooleanValue("onWeapon Only", false);
        this.entityPlayer = null;
        this.singleTimer = new TimerUtil();
        this.switchTimer = new TimerUtil();
        this.count = 0;
        this.setName("KillAura");
        this.isPrivate();
        this.setCategory(ModuleCategory.Combat);
        this.target.combos[2].setState(true);
        this.priority.combos[1].setState(true);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedSendEvent packetReceivedSendEvent) {
        final Packet packet = packetReceivedSendEvent.getPacket();
        if (!this.state || packet == null || this.mc.currentScreen == null || this.mc.thePlayer == null
                || this.mc.theWorld == null) {
            return;
        }
        if (this.autoblock.state && (packet instanceof C07PacketPlayerDigging)
                && ((C07PacketPlayerDigging) packet).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
            packetReceivedSendEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        if (!this.state || this.mc.currentScreen == null || this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        if (this.combat.onWeaponOnly(this.weapon.state)) {
            return;
        }
        if (this.click.state && Mouse.isButtonDown(0)) {
            return;
        }
        final long n = (long) (this.min.getValue()
                + new Random().nextDouble() * (this.max.getValue() - this.min.getValue()));
        if (this.target.combos[1].state) {
            if (this.singleTimer.reached.hasTimeReachedCURRENT(1000L / n)) {
                return;
            }
            this.singleTimer.reached.reset();
            if (this.raytrace.state) {
                this.mc.playerController.attackEntity((EntityPlayer) this.mc.thePlayer, (Entity) this.getPlayer());
                this.rotation();
                if (this.swng.state) {
                    this.mc.thePlayer.swingItem();
                }
            } else {
                if (this.raytracing(this.getPlayer())) {
                    return;
                }
                this.mc.thePlayer.sendQueue.addToSendQueue(
                        (Packet) new C02PacketUseEntity((Entity) this.getPlayer(), C02PacketUseEntity.Action.ATTACK));
                this.rotation();
                if (this.swng.state) {
                    this.mc.thePlayer.swingItem();
                }
            }
        } else if (this.target.combos[2].state) {
            if (this.singleTimer.reached.hasTimeReachedCURRENT(1000L / n)) {
                return;
            }
            this.singleTimer.reached.reset();
            if (this.raytrace.state) {
                this.mc.playerController.attackEntity((EntityPlayer) this.mc.thePlayer, (Entity) this.getPlayers());
                this.rotation();
                if (this.swng.state) {
                    this.mc.thePlayer.swingItem();
                }
            } else {
                if (this.raytracing(this.getPlayers())) {
                    return;
                }
                this.mc.thePlayer.sendQueue.addToSendQueue(
                        (Packet) new C02PacketUseEntity((Entity) this.getPlayers(), C02PacketUseEntity.Action.ATTACK));
                this.rotation();
                if (this.swng.state) {
                    this.mc.thePlayer.swingItem();
                }
            }
        } else if (this.target.combos[3].state) {
            if (this.singleTimer.reached.hasTimeReachedCURRENT(1000L / n)) {
                return;
            }
            this.singleTimer.reached.reset();
            for (int n2 = 0; n2 < this.limited.getValue(); ++n2) {
                if (this.getPlayerMulti().size() > n2 && this.getPlayerMulti().get(n2) != null) {
                    final EntityPlayer entityPlayer = (EntityPlayer) this.getPlayerMulti().get(n2);
                    if (entityPlayer != null) {
                        if (this.raytrace.state) {
                            this.mc.playerController.attackEntity((EntityPlayer) this.mc.thePlayer,
                                    (Entity) entityPlayer);
                            this.rotation();
                            if (this.swng.state) {
                                this.mc.thePlayer.swingItem();
                            }
                        } else {
                            if (this.raytracing(entityPlayer)) {
                                return;
                            }
                            this.mc.thePlayer.sendQueue
                                    .addToSendQueue((Packet) new C02PacketUseEntity((Entity) entityPlayer,
                                            C02PacketUseEntity.Action.ATTACK));
                            this.rotation();
                            if (this.swng.state) {
                                this.mc.thePlayer.swingItem();
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean raytracing(final EntityPlayer entityPlayer) {
        final Vec3 getPositionEyes = this.mc.thePlayer.getPositionEyes(1.6f);
        final Vec3 getPositionEyes2 = this.mc.getRenderViewEntity().getPositionEyes(1.6f);
        final Vec3 addVector = getPositionEyes.addVector(getPositionEyes2.xCoord * this.distance.getValue(),
                getPositionEyes2.yCoord * this.distance.getValue(), getPositionEyes2.zCoord * this.distance.getValue());
        return this.mc.theWorld.rayTraceBlocks(getPositionEyes, addVector, false, false, true) == null
                && !this.raytrace.state && entityPlayer.getEntityBoundingBox().expand(1.0, 1.0, 1.0)
                        .calculateIntercept(getPositionEyes, addVector) == null;
    }

    private void rotation() {
        if (this.rots.state) {
            if (this.target.combos[1].state) {
                this.rotation.playerSoftFacing((Entity) this.getPlayer());
            } else if (this.target.combos[2].state) {
                this.rotation.playerSoftFacing((Entity) this.getPlayers());
            } else if (this.target.combos[3].state) {
                this.rotation.playerSoftFacing((Entity) this.getPlayers());
            }
        }
    }

    private EntityPlayer getPlayer() {
        final List<EntityPlayer> list = (List<EntityPlayer>) this.mc.theWorld.playerEntities.stream().filter((pl) -> isPlayerValid(pl))
                .collect(Collectors.toList());
        if (this.priority.combos[1].state) {
            list.sort(
                    Comparator.comparingDouble((pl)-> getPlayerMulti(pl)));
        }
        if (this.priority.combos[2].state) {
            list.sort(Comparator.comparingDouble((pl)-> getPlayerDistance(pl)));
        }
        if (this.priority.combos[3].state) {
            list.sort(
                    Comparator.comparingDouble((pl)-> getPlayerArmor(pl)));
        }
        return (list.size() > 0 && list.get(0) != null) ? (EntityPlayer) list.get(0) : null;
    }

    private EntityPlayer getPlayers() {
        final List<EntityPlayer> list = (List<EntityPlayer>) this.mc.theWorld.playerEntities.stream().filter((pl) -> isPlayerValid(pl))
                .collect(Collectors.toList());
        if (this.priority.combos[1].state) {
            list.sort(
                    Comparator.comparingDouble((pl) -> getPlayerMulti(pl)));
        }
        if (this.priority.combos[2].state) {
            list.sort(Comparator.comparingDouble((pl) -> getPlayerDistance(pl)));
        }
        if (this.priority.combos[3].state) {
            list.sort(
                    Comparator.comparingDouble((pl) -> getPlayerArmor(pl)));
        }
        if (this.target.combos[2].state) {
            if (list.size() > this.count) {
                if (list.get(this.count) != null) {
                    this.entityPlayer = (EntityPlayer) list.get(this.count);
                    if (this.entityPlayer == null) {
                        return null;
                    }
                    if (this.switchTimer.reached.hasTimeReachedCURRENT((long) this.swtch.getValue())) {
                        this.switchTimer.reached.reset();
                        ++this.count;
                    }
                }
            } else {
                this.count = 0;
            }
        }
        return this.entityPlayer;
    }

    private List getPlayerMulti() {
        final List<EntityPlayer> list = (List<EntityPlayer>) this.mc.theWorld.playerEntities.stream().filter((pl) -> isPlayerValid(pl))
                .collect(Collectors.toList());
        if (this.priority.combos[1].state) {
            list.sort(Comparator.comparingDouble((pl) -> getPlayerMulti(pl)));
        }
        if (this.priority.combos[2].state) {
            list.sort(Comparator.comparingDouble((pl) -> getPlayerDistance(pl)));
        }
        if (this.priority.combos[3].state) {
            list.sort(Comparator.comparingDouble((pl) -> getPlayerMulti(pl)));
        }
        return list;
    }

    // Player statistics lambdas
    private static double getPlayerMulti(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount();
    }

    private double getPlayerDistance(EntityPlayer entityPlayer) {
        return entityPlayer.getDistanceToEntity((Entity) this.mc.thePlayer);
    }

    private static double getPlayerArmor(EntityPlayer entityPlayer) {
        return entityPlayer.getTotalArmorValue();
    }

    // Player check lambda
    private boolean isPlayerValid(EntityPlayer entityPlayer) {
        return entityPlayer != this.mc.thePlayer &&
                !entityPlayer.isDead &&
                (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 1.0E-4f) &&
                this.rotation.isAngleLargeEnough((Entity) entityPlayer, (int) this.fov.getValue()) &&
                this.mc.thePlayer.canEntityBeSeen((Entity) entityPlayer) &&
                (this.mc.thePlayer.getDistanceToEntity((Entity) entityPlayer) <= this.distance.getValue()) &&
                !FriendCommand.friends.contains(entityPlayer.getName().toLowerCase());
    }


}
