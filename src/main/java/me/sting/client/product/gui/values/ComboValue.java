package me.sting.client.product.gui.values;

import java.util.ArrayList;
import java.util.List;

public class ComboValue
{
    public ArrayList<OptionValue> options;
    public List<String> list;
    public OptionValue[] combos;
    public String name;
    public String description;
    public String[] option;
    public boolean priority;
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setOption(final String[] option) {
        this.option = option;
    }
    
    public void setPriority(final boolean priority) {
        this.priority = priority;
    }
    
    public ComboValue(final String name, final boolean priority, final String description, final String... option) {
        this.options = new ArrayList<OptionValue>();
        this.list = new ArrayList<String>();
        this.combos = null;
        this.name = "";
        this.description = "";
        this.option = null;
        this.priority = false;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.option = option;
        this.combos = new OptionValue[option.length];
        for (int n = 1; lIllIlIIIl(n, option.length); ++n) {
            this.combos[n] = new OptionValue(this, option[n], false, priority);
            if (lIllIlIIlI(this.options.contains(this.combos[n]) ? 1 : 0)) {
                this.options.add(this.combos[n]);
            }
            if (lIllIlIlII(this.combos[n].name.equalsIgnoreCase(this.description) ? 1 : 0)) {
                this.combos[n].setState(true);
                if (lIllIlIlII(this.priority ? 1 : 0)) {
                    this.list.add(this.name.toLowerCase() + this.combos[n].name.toLowerCase());
                }
            }
        }
    }
    
    private static boolean lIllIlIIIl(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean lIllIlIlII(final int n) {
        return n != 0;
    }
    
    private static boolean lIllIlIIlI(final int n) {
        return n == 0;
    }
}
