package dev.grimforge.material;

import dev.grimforge.Grimforge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * A material that tool parts can be made from.
 *
 * @param name the name, used in the ID and translation key
 * @param color the tint color, as 0xRRGGBB
 * @param tier the mining tier
 * @param durability the durability of a head
 * @param miningSpeed the mining speed of a head
 * @param attackDamage the attack damage bonus of a head
 * @param handleSpeed the mining speed multiplier of a handle
 * @param enchantability the enchantability
 * @param incorrectForDrops the blocks a head can't harvest
 * @param toolMaterials items parts are crafted from
 */
public record GrimMaterial(
        String name,
        int color,
        int tier,
        int durability,
        float miningSpeed,
        float attackDamage,
        float handleSpeed,
        int enchantability,
        TagKey<Block> incorrectForDrops,
        TagKey<Item> toolMaterials) {

    /**
     * Gets the ID of this material.
     *
     * @return the ID
     */
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Grimforge.MODID, name);
    }

    /**
     * Gets the display name of this material.
     *
     * @return the display name
     */
    public Component displayName() {
        return Component.translatable("material." + Grimforge.MODID + "." + name);
    }
}
