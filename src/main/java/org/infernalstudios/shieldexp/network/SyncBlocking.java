package org.infernalstudios.shieldexp.network;

import com.google.gson.JsonElement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.infernalstudios.shieldexp.access.LivingEntityAccess;
import org.infernalstudios.shieldexp.init.ShieldDataLoader;

import java.util.UUID;
import java.util.function.Supplier;

import static org.infernalstudios.shieldexp.events.ShieldExpansionEvents.getShieldValue;

public class SyncBlocking {
    UUID id;
    boolean blocking;

    public SyncBlocking(FriendlyByteBuf buf) {
        this.id = buf.readUUID();
        this.blocking = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
        buf.writeBoolean(blocking);
    }

    public SyncBlocking(UUID id, boolean blocking){
        this.id = id;
        this.blocking = blocking;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(this::handle);
        ctx.get().setPacketHandled(true);
    }

    private void handle() {
        Player player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(id);
        Item item = player.getOffhandItem().getItem();
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(player.getUUID());
        LivingEntityAccess.get(player).setBlocking(false);
        LivingEntityAccess.get(player).setParryWindow(0);
        if (player.isUsingItem()) {
            if (!player.getCooldowns().isOnCooldown(item))
                player.getCooldowns().addCooldown(item, getShieldValue(item, "cooldownTicks").intValue());
            player.stopUsingItem();
        }
    }
}
