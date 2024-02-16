package me.sting.client.product.module;

public enum ModuleCategory
{
    Sting("Sting", 0, "STING", 0), 
    Combat("Combat", 1, "COMBAT", 1), 
    Movement("Movement", 2, "MOVEMENT", 2), 
    Blatant("Blatant", 3, "BLATANT", 3), 
    Render("Render", 4, "RENDER", 4), 
    Utilities("Utilities", 5, "UTILITIES", 5), 
    SeniorMod("SeniorMod", 6, "MODERATOR", 6), 
    Disablers("Disablers", 7, "[X] DISABLERS", 7), 
    Modules("Modules", 8, "[X] MODULES", 8), 
    Displayer("Displayer", 9, "[X] DISPLAYER", 9), 
    Friends("Friends", 10, "[#] FRIENDS", 10), 
    Enemys("Enemys", 11, "[#] ENEMYS", 11), 
    Staffs("Staffs", 12, "[#] STAFFS", 12), 
    Specs("Specs", 13, "[!] CHECKS", 13), 
    Radar("Radar", 14, "[!] RADAR", 14);
    
    public String name;
    public int id;
    private static final ModuleCategory[] $VALUES;
    
    private ModuleCategory(final String s, final int n, final String name, final int id) {
        this.name = name;
        this.id = id;
    }
    
    static {
        $VALUES = new ModuleCategory[] { ModuleCategory.Sting, ModuleCategory.Combat, ModuleCategory.Movement, ModuleCategory.Blatant, ModuleCategory.Render, ModuleCategory.Utilities, ModuleCategory.SeniorMod, ModuleCategory.Disablers, ModuleCategory.Modules, ModuleCategory.Displayer, ModuleCategory.Friends, ModuleCategory.Enemys, ModuleCategory.Staffs, ModuleCategory.Specs, ModuleCategory.Radar };
    }
}
