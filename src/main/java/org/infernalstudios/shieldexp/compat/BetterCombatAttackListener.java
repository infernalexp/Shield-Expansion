package org.infernalstudios.shieldexp.compat;

import net.bettercombat.api.AttackHand;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.infernalstudios.shieldexp.access.LivingEntityAccess;
import org.infernalstudios.shieldexp.init.Config;

import static org.infernalstudios.shieldexp.events.ShieldExpansionEvents.getShieldValue;

public class BetterCombatAttackListener {
    @SubscribeEvent
    public static void register() {
        BetterCombatClientEvents.ATTACK_START.register((LocalPlayer player, AttackHand hand) -> {
            Item item = player.getOffhandItem().getItem();
            if (Boolean.TRUE.equals(Config.isShield(item)) && LivingEntityAccess.get(player).getBlocking()) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(player.getUUID());
                if (LivingEntityAccess.get(player).getBlocking())
                    LivingEntityAccess.get(player).setBlocking(false);
                LivingEntityAccess.get(player).setParryWindow(0);
                if (!player.getCooldowns().isOnCooldown(item))
                    player.getCooldowns().addCooldown(item, getShieldValue(item, "cooldownTicks").intValue());
                player.stopUsingItem();
            }
        });
    }
}
