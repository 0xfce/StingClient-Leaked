package me.sting.client.product.utils;

import net.minecraft.client.*;
import net.minecraft.item.*;

public class CombatUtil
{
    protected Minecraft mc;
    
    public CombatUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public boolean onWeaponOnly(final boolean b) {
        return (!b || (hasEquippedItem() && (isSword() || isAxe())));
    }

    private boolean hasEquippedItem() {
        return this.mc.thePlayer.getCurrentEquippedItem() != null;
    }

    private boolean isSword() {
        return this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }

    private boolean isAxe() {
        return this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe;
    }

}
