package dev.grimforge;

import dev.grimforge.client.MaterialTint;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

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

    // Lets item model files use "grimforge:material" to color parts
    @SubscribeEvent
    static void registerTints(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Identifier.fromNamespaceAndPath(Grimforge.MODID, "material"), MaterialTint.MAP_CODEC);
    }
}
