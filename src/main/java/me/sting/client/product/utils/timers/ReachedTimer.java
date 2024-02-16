package me.sting.client.product.utils.timers;

public class ReachedTimer
{
    public long nanoTime;
    public long currentTime;
    public long previousMS;
    
    public ReachedTimer() {
        this.nanoTime = System.nanoTime();
        this.currentTime = System.currentTimeMillis();
        this.previousMS = System.nanoTime() / 1000000L;
    }
    
    public boolean hasTimeReachedNANO1000000(final long n) {
        return System.nanoTime() / 1000000L - this.previousMS >= n;
    }

    public boolean hasTimeReachedNANO(final long n) {
        return System.nanoTime() - this.nanoTime >= n;
    }

    public boolean hasTimeReachedCURRENT(final long n) {
        return System.currentTimeMillis() - this.currentTime >= n;
    }
    public void reset() {
        this.previousMS = System.nanoTime() / 1000000L;
        this.nanoTime = System.nanoTime();
        this.currentTime = System.currentTimeMillis();
    }
    
}
