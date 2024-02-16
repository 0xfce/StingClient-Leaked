package me.sting.client.product.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

@Cancelable
public class PacketReceivedSendEvent extends Event
{
    public Packet Packet;
    
    public PacketReceivedSendEvent(final Packet packet) {
        this.Packet = packet;
    }
    
    public Packet getPacket() {
        return this.Packet;
    }
}
