package me.sting.client.product.module.category;

import me.sting.client.product.module.*;

public class MODERATOR extends Module
{
    public MODERATOR() {
        this.setName("MODERATOR");
        this.isPrivate();
        this.setCategory(ModuleCategory.Sting);
    }
    
    @Override
    public void onEnable() {
        this.isPrivate();
    }
}
