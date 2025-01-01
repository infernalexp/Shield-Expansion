package org.infernalstudios.shieldexp.compat;

import net.bettercombat.api.AttackHand;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.infernalstudios.shieldexp.access.LivingEntityAccess;

public class BetterCombatAttackListener {
    @SubscribeEvent
    public static void register() {
        BetterCombatClientEvents.ATTACK_START.register((LocalPlayer player, AttackHand hand) -> {
            System.out.println("Player Attack Started!");
            if (player != null) {
                System.out.println("Player: " + player.getName().getString());
            }
            System.out.println("Hand: " + hand);
            player.sendSystemMessage(Component.nullToEmpty("yo"));
            if (LivingEntityAccess.get(player).getBlocking()) {
                LivingEntityAccess.get(player).setBlocking(false);
                LivingEntityAccess.get(player).setBlockedCooldown(20);
                player.stopUsingItem();
            }
        });
    }
}
