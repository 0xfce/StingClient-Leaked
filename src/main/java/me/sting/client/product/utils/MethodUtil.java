package me.sting.client.product.utils;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.lang.reflect.*;

public class MethodUtil
{
    protected Minecraft mc;
    public Method loadShader;
    
    public MethodUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void renderShader(ResourceLocation resourceLocation) {
        try {
            Method loadShader = EntityRenderer.class.getDeclaredMethod("loadShader", ResourceLocation.class);
            loadShader.setAccessible(true);
            loadShader.invoke(mc.entityRenderer, resourceLocation);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public static String getMethodByReturnType(Class clazz, String returnType) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getReturnType().getName().equals(returnType)) {
                return method.getName();
            }
        }
        return "";
    }

}
