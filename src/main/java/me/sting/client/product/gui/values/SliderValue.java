package me.sting.client.product.gui.values;

import java.math.*;

public class SliderValue
{
    public String name;
    public boolean big;
    public double min;
    public double max;
    public double value;
    
    public SliderValue(final String name, final double value, final double min, final double max, final boolean big) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.big = big;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setBig(final boolean big) {
        this.big = big;
    }
    
    public void setMin(final double min) {
        this.min = min;
    }
    
    public void setMax(final double max) {
        this.max = max;
    }
    
    public void setValue(final double value) {
        this.value = value;
        double var3;
        if(lllIllIIII((var3 = value - this.min) == 0.0D?0:(var3 < 0.0D?-1:1))) {
           this.value = this.min;
        }

    }
    
    public double getValue() {
        return this.round(this.value, 2);
    }
    
    public double round(final double n, final int n2) {
        return new BigDecimal(n).setScale(n2, 4).doubleValue();
    }
    
    private static boolean lllIllIIII(final int n) {
        return n < 0;
    }
}
