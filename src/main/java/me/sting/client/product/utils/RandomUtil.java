package me.sting.client.product.utils;

import java.util.*;
import org.apache.commons.lang3.*;

public class RandomUtil
{
    public Random random;
    
    public RandomUtil() {
        this.random = new Random();
    }
    
    public String getRandomChat(final int n) {
        return RandomStringUtils.random(n, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890~!@#$%^&*()_+=-][';/.,<>\\\\|*");
    }
}
