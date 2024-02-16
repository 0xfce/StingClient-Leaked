package me.sting.client.product.transform;

import org.objectweb.asm.tree.*;

public interface GroupTransforming
{
    String[] className();
    
    void transform(final ClassNode p0, final String p1);
}
