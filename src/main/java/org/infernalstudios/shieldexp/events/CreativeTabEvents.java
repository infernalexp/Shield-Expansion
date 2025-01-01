package org.infernalstudios.shieldexp.events;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.shieldexp.init.ItemsInit;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CreativeTabEvents {
    @SubscribeEvent
    public static void addShields(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(ItemsInit.WOODEN_SHIELD);
            event.accept(ItemsInit.IRON_SHIELD);
            event.accept(ItemsInit.GOLDEN_SHIELD);
            event.accept(ItemsInit.DIAMOND_SHIELD);
            event.accept(ItemsInit.NETHERITE_SHIELD);
            if (ModList.get().isLoaded("miningmaster"))
                event.accept(ItemsInit.PARAGON_SHIELD);
            if (ModList.get().isLoaded("savage_and_ravage"))
                event.accept(ItemsInit.GRIEFER_SHIELD);
        }
    }
}
