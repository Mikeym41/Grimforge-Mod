package dev.grimforge.data;

import dev.grimforge.GrimComponents;
import dev.grimforge.Grimforge;
import dev.grimforge.material.GrimMaterial;
import dev.grimforge.material.GrimMaterials;
import dev.grimforge.part.GrimParts;
import dev.grimforge.part.ToolPartItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.registries.DeferredItem;

/**
 * Generates Grimforge recipes.
 */
public class GrimRecipeProvider extends RecipeProvider {

    public GrimRecipeProvider(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements, Grimforge.MODID);
    }

    @Override
    protected void buildRecipes() {
        for (GrimMaterial material : GrimMaterials.all()) {
            part(GrimParts.PICKAXE_HEAD, material, "###", " C ");
            part(GrimParts.AXE_HEAD, material, "##", "#C");
            part(GrimParts.HOE_BLADE, material, "##", " C");
            part(GrimParts.SWORD_BLADE, material, "#", "#", "C");
            part(GrimParts.SHOVEL_HEAD, material, "#", "C");
            part(GrimParts.HANDLE, material, "C", "#");
            part(GrimParts.BINDING, material, "#C");
            part(GrimParts.CROSSGUARD, material, "# ", " C");
        }

    }

    private void part(DeferredItem<ToolPartItem> part, GrimMaterial material, String... pattern) {
        DataComponentPatch components = DataComponentPatch.builder()
                .set(GrimComponents.MATERIAL.get(), material.id())
                .build();
        ShapedRecipeBuilder builder = shaped(RecipeCategory.TOOLS, new ItemStackTemplate(part, 1, components))
                .define('#', material.toolMaterials())
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_material", has(material.toolMaterials()));
        for (String row : pattern) {
            builder.pattern(row);
        }
        Identifier id = Identifier.fromNamespaceAndPath(Grimforge.MODID,part.getId().getPath() + "_" + material.name());
        builder.save(output, ResourceKey.create(Registries.RECIPE, id));
    }
}