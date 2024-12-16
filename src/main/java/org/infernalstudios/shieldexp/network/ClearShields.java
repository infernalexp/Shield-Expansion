package org.infernalstudios.shieldexp.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

// TODO Delete - This is only kept here so the network isn't angry
public class ClearShields {
    public ClearShields(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf){
    }

    public ClearShields() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().setPacketHandled(true);
    }
}
