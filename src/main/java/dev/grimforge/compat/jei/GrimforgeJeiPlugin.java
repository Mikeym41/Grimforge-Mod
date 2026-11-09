package dev.grimforge.compat.jei;

import com.ibm.icu.lang.UCharacter;
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
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

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

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        for (ToolType type : ToolType.values()) {
            for (GrimMaterial material : GrimMaterials.all()) {
                recipes.add(createAssemblyRecipe(type, material));
            }
        }
        registration.addRecipes(RecipeTypes.CRAFTING, recipes);
    }

    /**
     * Creates an example recipe for JEI. As the recipe can't be seen here.
     *
     * @param type the tool type
     * @param material the material of every part
     * @return the recipe
     */

    private static RecipeHolder<CraftingRecipe> createAssemblyRecipe(ToolType type, GrimMaterial material) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (var part : type.getParts()) {
            ingredients.add(DataComponentIngredient.of(false, part.get().create(material)));
        }
        ItemStack result = GrimTools.build(type, List.of(material, material, material));
        CraftingRecipe recipe = new ShapelessRecipe(
                new Recipe.CommonInfo(false),
                new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.EQUIPMENT, ""),
                ItemStackTemplate.fromNonEmptyStack(result),
                ingredients);
        Identifier id = Identifier.fromNamespaceAndPath(Grimforge.MODID, "jei" + "/" + material.name());
        return new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, id), recipe);
    }
}