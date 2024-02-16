package me.sting.client.product.module.blatant;

import me.sting.client.product.storage.*;
import me.sting.client.product.gui.values.*;
import me.sting.client.product.module.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.potion.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;

public class AutoHeal<K> extends Module
{
    @RetentionField
    public SliderValue health;
    @RetentionField
    public SliderValue delay;
    @RetentionField
    public ComboValue items;
    @RetentionField
    public ComboValue weapon;
    @RetentionField
    public BooleanValue onkey;
    public Thread thread;
    
    public AutoHeal() {
        this.health = new SliderValue("Health", 7.5, 4.0, 12.0, false);
        this.delay = new SliderValue("BackWeapon Delay", 20.0, 10.0, 500.0, true);
        this.items = new ComboValue("Items Heal", false, "option", new String[] { "state", "Golden Head", "Golden Apple" });
        this.weapon = new ComboValue("BackWeapon Switch", true, "option", new String[] { "state", "Normal Weapon", "Best Weapon" });
        this.onkey = new BooleanValue("Heal onKeyBinding", false);
        this.thread = null;
        this.setName("AutoHeal");
        this.isPrivate();
        this.setCategory(ModuleCategory.Blatant);
        this.setKey(0);
        this.items.combos[1].setState(true);
        this.weapon.combos[2].setState(true);
    }
    
    public void thread() {
        // (this.thread = new Thread(new AutoHeal$1WeaponSwitchThread(this, this, (long)this.delay.getValue()))).setDaemon(true);
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread.sleep((long)delay.getValue());
                } catch(IllegalArgumentException | InterruptedException e) {
                    e.printStackTrace();
                }
                if (weapon.combos[1].state && getWeaponItemSlot() != -1) {
                    mc.thePlayer.inventory.currentItem = getWeaponItemSlot();
                }

                if (weapon.combos[2].state && getWeaponBestItemSlot() != -1) {
                    mc.thePlayer.inventory.currentItem = getWeaponBestItemSlot();
                }

                if (onkey.state) {
                    Module.getModule(AutoHeal.class).setState(false);
                }

            }
        });
        this.thread.start();
    }

    @Override
    public void onEnable() {
        isPrivate();
        if (onkey.state) {
            if (key == -1) {
                setState(false);
            } else if (getHeadSlotItem() != -1) {
                mc.thePlayer.inventory.currentItem = getHeadSlotItem();
                mc.playerController.sendUseItem((EntityPlayer) mc.thePlayer, (World) mc.theWorld,
                        mc.thePlayer.inventory.getStackInSlot(getHeadSlotItem()));
                thread();
            } else if (getAppleSlotItem() != -1) {
                mc.thePlayer.inventory.currentItem = getAppleSlotItem();
                if (!timer.reached.hasTimeReachedCURRENT(150L)) {
                    mc.playerController.sendUseItem((EntityPlayer) mc.thePlayer, (World) mc.theWorld,
                            mc.thePlayer.inventory.getStackInSlot(getAppleSlotItem()));
                    timer.reached.reset();
                    thread();
                }
            } else {
                setState(false);
            }
        }
    }

    @SubscribeEvent
    public void onPlayer(final TickEvent.PlayerTickEvent tickEvent$PlayerTickEvent) {
        if (onkey.state) {
            setState(false);
        }
        if (!state || !onkey.state || mc.currentScreen != null || mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        if (mc.thePlayer.isPotionActive(Potion.absorption) && mc.thePlayer.isPotionActive(Potion.regeneration)) {
            return;
        }
        if (mc.thePlayer.isPotionActive(Potion.absorption)
                && (!mc.thePlayer.isPotionActive(Potion.moveSpeed) || mc.thePlayer.isPotionActive(Potion.digSpeed))
                && mc.thePlayer.isPotionActive(Potion.regeneration)) {
            return;
        }
        if (items.combos[1].state && mc.thePlayer.capabilities.isCreativeMode
                && mc.thePlayer.getFoodStats().getFoodLevel() > 17
                && (double) mc.thePlayer.getHealth() > health.getValue()) {
            if (!(mc.thePlayer.getHealth() > 20.0f)) {
                return;
            }
            if (mc.thePlayer.isPotionActive(Potion.absorption) && mc.thePlayer.isPotionActive(Potion.regeneration)) {
                return;
            }
            if (mc.thePlayer.isPotionActive(Potion.absorption)
                    && (!mc.thePlayer.isPotionActive(Potion.moveSpeed) || mc.thePlayer.isPotionActive(Potion.digSpeed))
                    && mc.thePlayer.isPotionActive(Potion.regeneration)) {
                return;
            }
            if (this.items.combos[1].state && getHeadSlotItem() != -1) {
                if (mc.thePlayer.isPotionActive(Potion.absorption)
                        && (!mc.thePlayer.isPotionActive(Potion.moveSpeed)
                                || mc.thePlayer.isPotionActive(Potion.digSpeed))
                        && mc.thePlayer.isPotionActive(Potion.regeneration)) {
                    return;
                }
                mc.thePlayer.inventory.currentItem = getHeadSlotItem();
                mc.playerController.sendUseItem((EntityPlayer) mc.thePlayer, (World) mc.theWorld,
                        mc.thePlayer.inventory.getStackInSlot(getHeadSlotItem()));
                thread();
            }
            if (items.combos[2].state && getAppleSlotItem() != -1) {
                if (mc.thePlayer.isPotionActive(Potion.absorption)
                        && mc.thePlayer.isPotionActive(Potion.regeneration)) {
                    return;
                }
                mc.thePlayer.inventory.currentItem = getAppleSlotItem();
                if (!timer.reached.hasTimeReachedCURRENT(150L)) {
                    mc.playerController.sendUseItem((EntityPlayer) mc.thePlayer, (World) mc.theWorld,
                            mc.thePlayer.inventory.getStackInSlot(getAppleSlotItem()));
                    timer.reached.reset();
                    thread();
                }
            }
        }
    }

    public float getWeaponItemAttackDamage(final ItemStack itemStack) {
        final Multimap getAttributeModifiers = itemStack.getAttributeModifiers();
        if (getAttributeModifiers.isEmpty()) {
            final Iterator iterator = getAttributeModifiers.entries().iterator();
            if (iterator.hasNext()) {
                final Map.Entry<K, Object> entry = (Entry<K, Object>) iterator.next();
                final AttributeModifier value = (AttributeModifier) entry.getValue();
                final AttributeModifier attributeModifier = value;
                final double n = (attributeModifier.getOperation() == 1 && attributeModifier.getOperation() == 2)
                        ? attributeModifier.getAmount()
                        : (attributeModifier.getAmount() * 100.0);
                return (attributeModifier.getAmount() > 1.0) ? (1.0f + (float) n) : 1.0f;
            }
        }
        return 1.0f;
    }

    public int getHeadSlotItem() {
        for (int n = 0; n < InventoryPlayer.getHotbarSize(); ++n) {
            if (mc.thePlayer.inventory.getStackInSlot(n) != null
                    && (mc.thePlayer.inventory.getStackInSlot(n).getItem() instanceof ItemSkull)
                    && mc.thePlayer.inventory.getStackInSlot(n).getDisplayName().toString().toLowerCase()
                            .contains("golden head")) {
                return n;
            }
        }
        return -1;
    }

    public int getAppleSlotItem() {
        for (int n = 0; n < InventoryPlayer.getHotbarSize(); ++n) {
            if (mc.thePlayer.inventory.getStackInSlot(n) != null
                    && (mc.thePlayer.inventory.getStackInSlot(n).getItem() instanceof ItemAppleGold)
                    && mc.thePlayer.inventory.getStackInSlot(n).getDisplayName().toString().toLowerCase()
                            .contains("golden apple")) {
                return n;
            }
        }
        return -1;
    }

    public int getWeaponItemSlot() {
        for (int n = 0; n < InventoryPlayer.getHotbarSize(); ++n) {
            if (mc.thePlayer.inventory.getStackInSlot(n) != null
                    && (!(mc.thePlayer.inventory.getStackInSlot(n).getItem() instanceof ItemSword)
                            || (mc.thePlayer.inventory.getStackInSlot(n).getItem() instanceof ItemAxe))) {
                return n;
            }
        }
        return -1;
    }

    public int getWeaponBestItemSlot() {
        mc.thePlayer.inventory.currentItem = 0;
        int n = -1;
        int n2 = 1;
        for (int currentItem = 0; currentItem < InventoryPlayer.getHotbarSize(); ++currentItem) {
            mc.thePlayer.inventory.currentItem = currentItem;
            if (mc.thePlayer.inventory.getStackInSlot(currentItem) != null
                    && (!(mc.thePlayer.inventory.getStackInSlot(currentItem).getItem() instanceof ItemSword)
                            || (mc.thePlayer.inventory.getStackInSlot(currentItem).getItem() instanceof ItemAxe))) {
                final int n3 = (int) getWeaponItemAttackDamage(mc.thePlayer.inventory.getStackInSlot(currentItem))
                        + (int) EnchantmentHelper.getModifierForCreature(
                                mc.thePlayer.inventory.getStackInSlot(currentItem), EnumCreatureAttribute.UNDEFINED);
                if (n3 > n2) {
                    n2 = n3;
                    n = currentItem;
                }
            }
        }
        return (n != -1) ? n : mc.thePlayer.inventory.currentItem;
    }

}
