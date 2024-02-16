package me.sting.client.product.gui.values;

public class BooleanValue
{
    public boolean state;
    public String name;
    
    public BooleanValue(final String name, final boolean state) {
        this.name = name;
        this.state = state;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setState(final boolean state) {
        if (lllIlIllIl(state ? 1 : 0, this.state ? 1 : 0)) {
            return;
        }
        this.state = state;
    }
    
    public void toggle() {
        this.state = lllIlIllll(this.state ? 1 : 0);
    }
    
    private static boolean lllIlIllIl(final int n, final int n2) {
        return n == n2;
    }
    
    private static boolean lllIlIllll(final int n) {
        return n == 0;
    }
}
