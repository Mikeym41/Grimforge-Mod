package dev.grimforge.recipe;

import com.mojang.serialization.MapCodec;
import dev.grimforge.material.GrimMaterial;
import dev.grimforge.part.ToolPartItem;
import dev.grimforge.tool.GrimTools;
import dev.grimforge.tool.ToolType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

/**
 * Builds a tool from its parts in a crafting grid. The parts can go in any slot.
 */
public class ToolAssemblyRecipe extends CustomRecipe {

    public static final ToolAssemblyRecipe INSTANCE = new ToolAssemblyRecipe();
    public static final RecipeSerializer<ToolAssemblyRecipe> SERIALIZER =
            new RecipeSerializer<>(MapCodec.unit(INSTANCE), StreamCodec.unit(INSTANCE));

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return findMatch(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        Match match = findMatch(input);
        return match != null ? GrimTools.build(match.type(), match.materials()) : ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<ToolAssemblyRecipe> getSerializer() {
        return SERIALIZER;
    }

    private static @Nullable Match findMatch(CraftingInput input) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : input.items()) {
            if (stack.isEmpty()) {
                continue;
            }
            if (!(stack.getItem() instanceof ToolPartItem)) {
                return null;
            }
            stacks.add(stack);
        }

        for (ToolType type : ToolType.values()) {
            if (stacks.size() != type.getParts().size()) {
                continue;
            }
            List<ItemStack> remaining = new ArrayList<>(stacks);
            List<GrimMaterial> materials = new ArrayList<>();
            for (var part : type.getParts()) {
                ItemStack found = remaining.stream().filter(stack -> stack.is(part.get())).findFirst().orElse(null);
                if (found == null) {
                    break;
                }
                remaining.remove(found);
                materials.add(ToolPartItem.getMaterial(found));
            }
            if (materials.size() == type.getParts().size()) {
                return new Match(type, materials);
            }
        }
        return null;
    }

    private record Match(ToolType type, List<GrimMaterial> materials) {
    }
}
