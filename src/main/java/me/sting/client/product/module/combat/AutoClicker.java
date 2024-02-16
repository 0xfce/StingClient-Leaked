package me.sting.client.product.module.combat;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import net.minecraftforge.event.entity.player.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.sting.client.product.module.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.init.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;

public class AutoClicker extends Module
{
    private long klashDown;
    private long klashrop;
    @RetentionField
    private BooleanValue acc;
    @RetentionField
    public static SliderValue mincps;
    public long delay;
    @RetentionField
    public static SliderValue maxcps;
    private long rightUP;
    @RetentionField
    public static BooleanValue breaks;
    private boolean skip;
    private double cps;
    @RetentionField
    public static SliderValue blockhit;
    public long keepalive;
    private long rightDOWN;
    @RetentionField
    public static SliderValue JitterValue;
    private long klashUp;
    @RetentionField
    public static BooleanValue weapon;
    private long nex;
    public long threshold;

    public AutoClicker() {
        this.acc = new BooleanValue("Acceleration", false);
        this.delay = 1L;
        this.keepalive = 0L;
        this.threshold = 0L;
        this.setName("AutoClicker");
        this.setCategory(ModuleCategory.Combat);
    }
    
    @SubscribeEvent
    public void blockHITING(final AttackEntityEvent attackEntityEvent) {
        if (this.mc.currentScreen != null) {
            return;
        }
        final float n = (int) AutoClicker.blockhit.getValue() * 100;
        this.keepalive = System.nanoTime() / 1000000L;
        if (state && ((float) (keepalive - threshold) <= n || threshold < -1L)) {
            this.threshold = System.nanoTime() / 1000000L;
            if (AutoClicker.blockhit.getValue() > 0.0) {
                try {
                    final Robot robot = new Robot();
                    robot.mousePress(4);
                    robot.mouseRelease(4);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    private void click() {
        if (AutoClicker.mincps.getValue() > AutoClicker.maxcps.getValue()) {
            return;
        }
        if (Mouse.isButtonDown(1)) {
            return;
        }
        long n = (int) Math.round(900.0 / AutoClicker.mincps.getValue()
                + new Random().nextDouble() * (AutoClicker.maxcps.getValue() - AutoClicker.mincps.getValue()) - 9.0);
        if (System.currentTimeMillis() > this.klashrop) {
            if (this.skip && new Random().nextInt(100) > 85) {
                this.skip = true;
                this.cps = 1.1 + new Random().nextDouble() * 0.15;
            } else {
                this.skip = false;
            }
            this.klashrop = System.currentTimeMillis() + 500L + new Random().nextInt(1500);
        }
        if (this.skip) {
            n *= (long) this.cps;
        }
        if (System.currentTimeMillis() > this.nex) {
            if (new Random().nextInt(100) > 80) {
                n += 50L + new Random().nextInt(150);
            }
            this.nex = System.currentTimeMillis() + 500L + new Random().nextInt(1500);
        }
        this.klashDown = System.currentTimeMillis() + n;
        this.klashUp = System.currentTimeMillis() + n / 2L - new Random().nextInt(10);
        if (this.acc.state) {
            this.mc.thePlayer.swingItem();
        }
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent tickEvent$ClientTickEvent) {
        if ((!this.state || !this.state) && AutoClicker.mincps.getValue() > AutoClicker.maxcps.getValue()) {
            AutoClicker.mincps.setValue(AutoClicker.maxcps.getValue() - 3.0);
        }
        if (AutoClicker.JitterValue.getValue() != 0.0 && new Random().nextDouble() > 0.65) {
            if (Mouse.isButtonDown(1)) {
                return;
            }
            if (this.mc.currentScreen != null) {
                return;
            }
            if (Mouse.isButtonDown(0)) {
                final float n = (float) (AutoClicker.JitterValue.getValue() * 0.3);
                if (new Random().nextBoolean()) {
                    this.mc.thePlayer.rotationYaw += new Random().nextFloat() * n;
                } else {
                    this.mc.thePlayer.rotationYaw -= new Random().nextFloat() * n;
                }
                if (new Random().nextBoolean()) {
                    this.mc.thePlayer.rotationYaw += (float) (new Random().nextFloat() * (n * 0.75));
                } else {
                    this.mc.thePlayer.rotationYaw -= (float) (new Random().nextFloat() * (n * 0.75));
                }
            }
        }
        if (this.mc.currentScreen == null && this.combat.onWeaponOnly(AutoClicker.weapon.state)) {
            Mouse.poll();
            if (Mouse.isButtonDown(0)) {
                if (AutoClicker.breaks.state && this.mc.objectMouseOver != null) {
                    final BlockPos getBlockPos = this.mc.objectMouseOver.getBlockPos();
                    if (getBlockPos != null) {
                        if (this.mc.theWorld.getBlockState(getBlockPos).getBlock() == Blocks.air) {
                            KeyBinding.setKeyBindState(this.key, true);
                            KeyBinding.onTick(this.key);
                            return;
                        }
                        KeyBinding.setKeyBindState(this.key, false);
                    }
                }
                if (this.klashDown == 0L && this.klashUp == 0L) {
                    if (System.currentTimeMillis() > this.klashDown) {
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), true);
                        KeyBinding.onTick(this.mc.gameSettings.keyBindAttack.getKeyCode());
                        this.field.sendClick(0, true);
                        this.click();
                    } else if (System.currentTimeMillis() > this.klashUp) {
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                        this.field.sendClick(0, false);
                    }
                } else {
                    this.click();
                }
                if (Mouse.isButtonDown(1)) {
                    this.rightUP = 0L;
                    this.rightDOWN = 0L;
                }
            } else {
                this.rightUP = 0L;
                this.rightDOWN = 0L;
                this.klashUp = 0L;
                this.klashDown = 0L;
            }
        }
    }

    static {
        AutoClicker.mincps = new SliderValue("Min", 14.0, 5.0, 20.0, true);
        AutoClicker.maxcps = new SliderValue("Max", 17.0, 5.0, 20.0, true);
        AutoClicker.breaks = new BooleanValue("Break Blocks", false);
        AutoClicker.blockhit = new SliderValue("Block Hit", 0.0, 0.0, 15.0, false);
        AutoClicker.JitterValue = new SliderValue("Jitter", 0.0, 0.0, 10.0, false);
        AutoClicker.weapon = new BooleanValue("onWeapon Only", false);
    }

}
