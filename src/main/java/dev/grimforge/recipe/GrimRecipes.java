package dev.grimforge.recipe;

import dev.grimforge.Grimforge;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * The recipe serializers.
 */
public final class GrimRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Grimforge.MODID);

    public static final Supplier<RecipeSerializer<ToolAssemblyRecipe>> TOOL_ASSEMBLY =
            SERIALIZERS.register("tool_assembly", () -> ToolAssemblyRecipe.SERIALIZER);

    private GrimRecipes() {
    }
}
