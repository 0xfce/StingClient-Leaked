package me.sting.client.product.transform.transformers;

import me.sting.client.product.events.*;
import me.sting.client.product.transform.*;
import org.objectweb.asm.tree.*;

public class TransformingNetworkManager implements GroupTransforming
{
    @Override
    public String[] className() {
        return new String[] { "net.minecraft.network.NetworkManager" };
    }
    
    @Override
    public void transform(final ClassNode classNode, final String s) {
        for (int n = 0; llllIIIIII(n, classNode.methods.size()); ++n) {
            final MethodNode methodNode = classNode.methods.get(n);
            if (!llllIIIIIl(methodNode.name.equals("sendPacket") ? 1 : 0) || !llllIIIIIl(methodNode.name.equals("sendPacket") ? 1 : 0) || (llllIIIIlI(methodNode.name.equals("a") ? 1 : 0) && !llllIIIIIl(methodNode.desc.equals("(Lnet/minecraft/network/Packet;)V") ? 1 : 0)) || llllIIIIlI(methodNode.desc.equals("(Lff;)V") ? 1 : 0)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.getSendPacket());
                break;
            }
            if (!llllIIIIIl(methodNode.name.equals("channelRead0") ? 1 : 0) || (llllIIIIlI(methodNode.name.equals("a") ? 1 : 0) && !llllIIIIIl(methodNode.desc.equals("(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/IPacket;)V") ? 1 : 0)) || llllIIIIlI(methodNode.desc.equals("(Lio/netty/channel/ChannelHandlerContext;Lff;)V") ? 1 : 0)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.getChannelRead());
            }
        }
    }
    
    public InsnList getSendPacket() {
        final InsnList list = new InsnList();
        list.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
        list.add((AbstractInsnNode)new TypeInsnNode(187, PacketReceivedSendEvent.class.getName().replace(".", "/")));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new MethodInsnNode(183, PacketReceivedSendEvent.class.getName().replace(".", "/"), "<init>", llllIIIIlI(PluginLoader.obfuscated ? 1 : 0) ? "(Lff;)V" : "(Lnet/minecraft/network/Packet;)V", false));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
        final LabelNode labelNode = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, labelNode));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)labelNode);
        return list;
    }
    
    public InsnList getChannelRead() {
        final InsnList list = new InsnList();
        list.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
        list.add((AbstractInsnNode)new TypeInsnNode(187, PacketReceivedSendEvent.class.getName().replace(".", "/")));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(183, PacketReceivedSendEvent.class.getName().replace(".", "/"), "<init>", llllIIIIlI(PluginLoader.obfuscated ? 1 : 0) ? "(Lff;)V" : "(Lnet/minecraft/network/Packet;)V", false));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
        final LabelNode labelNode = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, labelNode));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)labelNode);
        return list;
    }
    
    private static boolean llllIIIIII(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean llllIIIIlI(final int n) {
        return n != 0;
    }
    
    private static boolean llllIIIIIl(final int n) {
        return n == 0;
    }
}
