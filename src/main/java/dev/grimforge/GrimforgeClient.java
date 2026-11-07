package dev.grimforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only setup. This class never loads on a dedicated server,
 * so rendering and screen code goes here.
 */
@Mod(value = Grimforge.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Grimforge.MODID, value = Dist.CLIENT)
public class GrimforgeClient {
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        Grimforge.LOGGER.info("Grimforge client ready");
    }
}
