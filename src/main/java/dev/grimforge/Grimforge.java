package dev.grimforge;

import com.mojang.logging.LogUtils;
import dev.grimforge.material.GrimMaterial;
import dev.grimforge.material.GrimMaterials;
import dev.grimforge.part.GrimParts;
import dev.grimforge.recipe.GrimRecipes;
import dev.grimforge.tool.GrimTools;
import dev.grimforge.tool.ToolType;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

/**
 * Main class for Grimforge. NeoForge creates this when the mod loads.
 */
@Mod(Grimforge.MODID)
public class Grimforge {
    public static final String MODID = "grimforge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Placeholder item to prove the mod loads. Replaced by real runes in 0.5.
    public static final DeferredItem<Item> TEST_RUNE = ITEMS.registerSimpleItem("test_rune", p -> p);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GRIMFORGE_TAB =
            CREATIVE_MODE_TABS.register("grimforge", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.grimforge"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> new ItemStack(GrimTools.get(ToolType.PICKAXE).get()))
                    .displayItems((parameters, output) -> {
                        // A finished tool of every type in every material...
                        for (GrimMaterial material : GrimMaterials.all()) {
                            for (ToolType type : ToolType.values()) {
                                output.accept(GrimTools.build(type, List.of(material, material, material)));
                            }
                        }
                        // ...then every part in every material
                        for (var part : GrimParts.ALL) {
                            for (GrimMaterial material : GrimMaterials.all()) {
                                output.accept(part.get().withMaterial(material));
                            }
                        }
                        output.accept(TEST_RUNE.get());
                    })
                    .build());

    public Grimforge(IEventBus modEventBus, ModContainer modContainer) {
        GrimParts.init();
        GrimTools.init();

        ITEMS.register(modEventBus);
        GrimComponents.COMPONENTS.register(modEventBus);
        GrimRecipes.SERIALIZERS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Grimforge is heating up the forge");
    }
}
