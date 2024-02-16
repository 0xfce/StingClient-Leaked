package me.sting.client.product.utils.timers;

public class PassedTimer
{
    public long millisTime;
    
    public PassedTimer() {
        this.millisTime = -1L;
    }
    
    public boolean hasTimePassedCURRENT(final long n) {
        return System.currentTimeMillis() - this.millisTime >= n;
    }
    
    public void reset() {
        this.millisTime = System.currentTimeMillis();
    }

}
