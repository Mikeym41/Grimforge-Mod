package dev.grimforge.tool;

import dev.grimforge.part.GrimParts;
import dev.grimforge.part.ToolPartItem;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jspecify.annotations.Nullable;

/**
 * The kinds of tools that can be built. Parts are listed head first, then handle, then extra.
 */
public enum ToolType {
    PICKAXE("pickaxe", 1.0F, -2.8F, 1.0F, BlockTags.MINEABLE_WITH_PICKAXE,
            properties -> properties.pickaxe(ToolMaterial.WOOD, 1.0F, -2.8F),
            GrimParts.PICKAXE_HEAD, GrimParts.HANDLE, GrimParts.BINDING),
    SHOVEL("shovel", 1.5F, -3.0F, 1.0F, BlockTags.MINEABLE_WITH_SHOVEL,
            properties -> properties.shovel(ToolMaterial.WOOD, 1.5F, -3.0F),
            GrimParts.SHOVEL_HEAD, GrimParts.HANDLE, GrimParts.BINDING),
    AXE("axe", 6.0F, -3.1F, 1.0F, BlockTags.MINEABLE_WITH_AXE,
            properties -> properties.axe(ToolMaterial.WOOD, 6.0F, -3.1F),
            GrimParts.AXE_HEAD, GrimParts.HANDLE, GrimParts.BINDING),
    HOE("hoe", 0.0F, -1.0F, 0.0F, BlockTags.MINEABLE_WITH_HOE,
            properties -> properties.hoe(ToolMaterial.WOOD, 0.0F, -1.0F),
            GrimParts.HOE_BLADE, GrimParts.HANDLE, GrimParts.BINDING),
    SWORD("sword", 3.0F, -2.4F, 1.0F, null,
            properties -> properties.sword(ToolMaterial.WOOD, 3.0F, -2.4F),
            GrimParts.SWORD_BLADE, GrimParts.HANDLE, GrimParts.CROSSGUARD);

    private final String name;
    private final float baseDamage;
    private final float attackSpeed;
    private final float headDamageScale;
    private final @Nullable TagKey<Block> mineable;
    private final UnaryOperator<Item.Properties> properties;
    private final List<DeferredItem<ToolPartItem>> parts;

    @SafeVarargs
    ToolType(String name, float baseDamage, float attackSpeed, float headDamageScale,
             @Nullable TagKey<Block> mineable, UnaryOperator<Item.Properties> properties,
             DeferredItem<ToolPartItem>... parts) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.attackSpeed = attackSpeed;
        this.headDamageScale = headDamageScale;
        this.mineable = mineable;
        this.properties = properties;
        this.parts = List.of(parts);
    }

    public String getName() {
        return name;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * Gets how much of the head's attack damage the tool gets.
     *
     * @return the multiplier
     */
    public float getHeadDamageScale() {
        return headDamageScale;
    }

    /**
     * Gets the blocks this tool mines quickly.
     *
     * @return the block tag, or null if this tool isn't for mining
     */
    public @Nullable TagKey<Block> getMineable() {
        return mineable;
    }

    /**
     * Applies vanilla's properties for this kind of tool.
     *
     * @return the properties function
     */
    public UnaryOperator<Item.Properties> getProperties() {
        return properties;
    }

    public List<DeferredItem<ToolPartItem>> getParts() {
        return parts;
    }
}
