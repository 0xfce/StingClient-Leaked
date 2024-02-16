package me.sting.client.product.gui.values;

public class OptionValue
{
    public ComboValue combo;
    public String name;
    public boolean state;
    public boolean open;
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setState(final boolean state) {
        this.state = state;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public OptionValue(final ComboValue combo, final String name, final boolean state, final boolean open) {
        this.combo = combo;
        this.name = name;
        this.state = state;
        this.open = open;
    }
}
