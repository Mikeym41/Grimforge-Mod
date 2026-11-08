package dev.grimforge.material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.Nullable;

/**
 * The built-in materials.
 */
public final class GrimMaterials {

    private static final List<GrimMaterial> ALL = new ArrayList<>();
    private static final Map<Identifier, GrimMaterial> BY_ID = new HashMap<>();

    public static final GrimMaterial WOOD = register(new GrimMaterial(
            "wood", 0x9C7448, 1, 59, 2.0F, 0.0F, 1.0F, 15, BlockTags.INCORRECT_FOR_WOODEN_TOOL));
    public static final GrimMaterial STONE = register(new GrimMaterial(
            "stone", 0x9A9A9A, 2, 131, 4.0F, 1.0F, 0.95F, 5, BlockTags.INCORRECT_FOR_STONE_TOOL));
    public static final GrimMaterial IRON = register(new GrimMaterial(
            "iron", 0xE0E0E0, 3, 250, 6.0F, 2.0F, 1.0F, 14, BlockTags.INCORRECT_FOR_IRON_TOOL));
    public static final GrimMaterial GOLD = register(new GrimMaterial(
            "gold", 0xF5D43A, 1, 32, 12.0F, 0.0F, 1.1F, 22, BlockTags.INCORRECT_FOR_GOLD_TOOL));
    public static final GrimMaterial DIAMOND = register(new GrimMaterial(
            "diamond", 0x4AEDD9, 4, 1561, 8.0F, 3.0F, 1.0F, 10, BlockTags.INCORRECT_FOR_DIAMOND_TOOL));

    private GrimMaterials() {
    }

    private static GrimMaterial register(GrimMaterial material) {
        ALL.add(material);
        BY_ID.put(material.id(), material);
        return material;
    }

    /**
     * Gets all materials, in registration order.
     *
     * @return the materials
     */
    public static List<GrimMaterial> all() {
        return Collections.unmodifiableList(ALL);
    }

    /**
     * Gets a material by its ID.
     *
     * @param id the ID
     * @return the material, or null if there is none
     */
    public static @Nullable GrimMaterial get(@Nullable Identifier id) {
        return id == null ? null : BY_ID.get(id);
    }

    /**
     * Gets a material by its ID, falling back to wood.
     *
     * @param id the ID
     * @return the material
     */
    public static GrimMaterial getOrDefault(@Nullable Identifier id) {
        GrimMaterial material = get(id);
        return material != null ? material : WOOD;
    }
}
