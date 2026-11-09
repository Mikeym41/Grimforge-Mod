package dev.grimforge.compat.jei;

import dev.grimforge.GrimComponents;
import dev.grimforge.Grimforge;
import dev.grimforge.material.GrimMaterial;
import dev.grimforge.material.GrimMaterials;
import dev.grimforge.part.GrimParts;
import dev.grimforge.tool.GrimTools;
import dev.grimforge.tool.ToolType;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * The JEI plugin for Grimforge.
 */
@JeiPlugin
public class GrimforgeJeiPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(Grimforge.MODID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        for (var part : GrimParts.ALL) {
            registration.registerFromDataComponentTypes(part.get(), GrimComponents.MATERIAL.get());
        }
        for (ToolType type : ToolType.values()) {
            registration.registerFromDataComponentTypes(GrimTools.get(type).get(), GrimComponents.PARTS.get());
        }
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
        List<ItemStack> stacks = new ArrayList<>();
        for (GrimMaterial material : GrimMaterials.all()) {
            for (ToolType type : ToolType.values()) {
                stacks.add(GrimTools.build(type, List.of(material, material, material)));
            }
        }
        for (var part : GrimParts.ALL) {
            for (GrimMaterial material : GrimMaterials.all()) {
                stacks.add(part.get().create(material));
            }
        }
        registration.addExtraItemStacks(stacks);
    }
}