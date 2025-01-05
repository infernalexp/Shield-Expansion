package org.infernalstudios.shieldexp.network;

import com.google.gson.JsonElement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.infernalstudios.shieldexp.init.ShieldDataLoader;

import java.util.function.Supplier;

public class SyncShields {
    private static final int MAX = 32767 * 2;
    JsonElement data;
    ResourceLocation shield;

    public SyncShields(FriendlyByteBuf buf) {
        this.shield = buf.readResourceLocation();
        this.data = GsonHelper.fromJson(ShieldDataLoader.GSON, buf.readUtf(MAX), JsonElement.class);
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeResourceLocation(shield);
        buf.writeUtf(this.data.toString());
    }

    public SyncShields(ResourceLocation shield, JsonElement data){
        this.shield = shield;
        this.data = data;
    }

    public void handle(CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(this::handle);
        ctx.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private void handle() {
        // trust that the client can handle this even if the player isn't ready
        // this data isn't stored to the local player so even if the player is null, it's fine
        ShieldDataLoader.parse(shield, data.getAsJsonObject());
    }
}
