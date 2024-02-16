package me.sting.client.product.transform;

import net.minecraftforge.fml.relauncher.*;
import java.util.*;

@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.SortingIndex(4)
public class PluginLoader implements IFMLLoadingPlugin
{
    public static boolean obfuscated;
    
    public String[] getASMTransformerClass() {
        return new String[] { Transformer.class.getName() };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map map) {
        PluginLoader.obfuscated = (boolean) map.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        PluginLoader.obfuscated = false;
    }
}
