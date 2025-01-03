package org.infernalstudios.shieldexp.compat;

import net.bettercombat.api.AttackHand;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import org.infernalstudios.shieldexp.init.Config;
import org.infernalstudios.shieldexp.init.NetworkInit;
import org.infernalstudios.shieldexp.network.SyncBlocking;

public class BetterCombatAttackListener {
    @SubscribeEvent
    public static void register() {
        BetterCombatClientEvents.ATTACK_START.register((LocalPlayer player, AttackHand hand) -> {
            Item item = player.getOffhandItem().getItem();
            if (Boolean.TRUE.equals(Config.isShield(item))) {
                NetworkInit.INSTANCE.send(PacketDistributor.SERVER.noArg(), new SyncBlocking(player.getUUID(), false));
            }
        });
    }
}
