package org.infernalstudios.shieldexp.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;
import org.infernalstudios.shieldexp.ShieldExpansion;
import org.infernalstudios.shieldexp.network.ClearShields;
import org.infernalstudios.shieldexp.network.SyncShields;

public class NetworkInit {
    public static SimpleChannel INSTANCE;

    public static void registerPackets() {
        INSTANCE = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(ShieldExpansion.MOD_ID, "packets"))
                .optional()
                .acceptedVersions((status, version) -> true)
                .networkProtocolVersion(1)
                .simpleChannel();

        INSTANCE.messageBuilder(SyncShields.class, NetworkDirection.PLAY_TO_CLIENT).encoder(SyncShields::encode).decoder(SyncShields::new).consumerNetworkThread(SyncShields::handle).add();

        // TODO Delete - This is only kept here so the network isn't angry
        INSTANCE.messageBuilder(ClearShields.class, NetworkDirection.PLAY_TO_CLIENT).encoder(ClearShields::encode).decoder(ClearShields::new).consumerNetworkThread(ClearShields::handle).add();

        MinecraftForge.EVENT_BUS.register(new NetworkInit());
    }
}
