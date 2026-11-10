package dev.grimforge.data;

import dev.grimforge.Grimforge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

/**
 * Registers Grimforges data generators.
 */
@EventBusSubscriber(modid = Grimforge.MODID)
public final class GrimDataGenerators {

    private GrimDataGenerators() {
    }

    @SubscribeEvent
    static void onGatherDataRegistryEntries(GatherDataRegistryEntriesEvent event) {
        event.recipe(GrimRecipeProvider::new);
    }
}
