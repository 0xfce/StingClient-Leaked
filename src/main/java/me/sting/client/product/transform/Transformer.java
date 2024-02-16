package me.sting.client.product.transform;

import net.minecraft.launchwrapper.*;
import me.sting.client.product.transform.transformers.*;
import org.objectweb.asm.tree.*;
import java.util.function.*;
import org.objectweb.asm.*;
import java.util.*;
import com.google.common.collect.*;

public class Transformer implements IClassTransformer
{
    public static Multimap multimap;
    
    public Transformer() {
        registerTransformer(new TransformingNetworkManager());
        registerTransformer(new TransformingPlayerControllerMP());
    }
    
    public byte[] transform(final String className, final String transformedName, final byte[] bytecode) {
        if (bytecode == null) {
            return null;
        }
        final Collection<GroupTransforming> transformers = Transformer.multimap.get(transformedName);
        if (transformers.isEmpty()) {
            return bytecode;
        }
        final ClassReader classReader = new ClassReader(bytecode);
        final ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
        transformers.forEach(transformer -> transformer.transform(classNode, transformedName));
        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public static void registerTransformer(final GroupTransforming transformer) {
        if (transformer == null) {
            return;
        }
        for (String className : transformer.className()) {
            Transformer.multimap.put(className, transformer);
        }
    }

    static {
        Transformer.multimap = (Multimap) ArrayListMultimap.create();
    }

}
