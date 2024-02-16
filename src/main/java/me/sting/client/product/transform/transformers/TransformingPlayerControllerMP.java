package me.sting.client.product.transform.transformers;

import me.sting.client.product.module.combat.*;
import me.sting.client.product.utils.*;
import me.sting.client.product.transform.*;
import org.objectweb.asm.tree.*;

public class TransformingPlayerControllerMP implements GroupTransforming
{
    @Override
    public String[] className() {
        return new String[] { "net.minecraft.client.multiplayer.PlayerControllerMP" };
    }
    
    @Override
    public void transform(final ClassNode classNode, final String s) {
        for (int n = 0; lIIIIlIIIIl(n, classNode.methods.size()); ++n) {
            final MethodNode methodNode = classNode.methods.get(n);
            if (!lIIIIlIIIlI(methodNode.name.equals("getBlockReachDistance") ? 1 : 0) || !lIIIIlIIIlI(methodNode.name.equals("getBlockReachDistance") ? 1 : 0) || lIIIIlIIIll(methodNode.name.equals("d") ? 1 : 0)) {
                for (int n2 = 0; lIIIIlIIIIl(n2, methodNode.instructions.toArray().length); ++n2) {
                    final AbstractInsnNode abstractInsnNode = methodNode.instructions.toArray()[n2];
                    if (lIIIIlIIlIl(abstractInsnNode)) {
                        methodNode.instructions.remove(abstractInsnNode);
                    }
                }
                methodNode.instructions.insert(this.getBlockReachDistanceInsnList());
                return;
            }
        }
    }
    
    public InsnList getBlockReachDistanceInsnList() {
        final InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, Reach.class.getName().replace(".", "/"), MethodUtil.getMethodByReturnType(Reach.class, "float"), lIIIIlIIIll(PluginLoader.obfuscated ? 1 : 0) ? "()F" : "()F", false));
        list.add((AbstractInsnNode)new InsnNode(174));
        return list;
    }
    
    private static boolean lIIIIlIIIIl(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean lIIIIlIIlIl(final Object o) {
        return o != null;
    }
    
    private static boolean lIIIIlIIIll(final int n) {
        return n != 0;
    }
    
    private static boolean lIIIIlIIIlI(final int n) {
        return n == 0;
    }
}
