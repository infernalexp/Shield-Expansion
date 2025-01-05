package org.infernalstudios.shieldexp.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

// TODO Delete - This is only kept here so the network isn't angry
public class ClearShields {
    public ClearShields(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf){
    }

    public ClearShields() {
    }

    public void handle(CustomPayloadEvent.Context ctx){
        ctx.setPacketHandled(true);
    }
}
