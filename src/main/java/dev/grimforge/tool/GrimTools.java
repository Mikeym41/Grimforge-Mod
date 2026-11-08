package dev.grimforge.tool;

import dev.grimforge.GrimComponents;
import dev.grimforge.Grimforge;
import dev.grimforge.material.GrimMaterial;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * The tool items, and the stats of built tools.
 */
public final class GrimTools {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Grimforge.MODID);

    private static final Map<ToolType, DeferredItem<GrimToolItem>> TOOLS = new EnumMap<>(ToolType.class);

    static {
        for (ToolType type : ToolType.values()) {
            TOOLS.put(type, ITEMS.registerItem(type.getName(), properties ->
                    new GrimToolItem(type.getProperties().apply(properties).setNoCombineRepair(), type)));
        }
    }

    private GrimTools() {
    }

    public static DeferredItem<GrimToolItem> get(ToolType type) {
        return TOOLS.get(type);
    }

    /**
     * Builds a tool from part materials.
     *
     * @param type the tool type
     * @param materials the material of each part, in the order of {@link ToolType#getParts()}
     * @return the tool
     */
    public static ItemStack build(ToolType type, List<GrimMaterial> materials) {
        GrimMaterial head = materials.get(0);
        GrimMaterial handle = materials.get(1);
        GrimMaterial extra = materials.get(2);

        int durability = Math.max(1, Math.round(
                head.durability() * 0.8F + handle.durability() * 0.1F + extra.durability() * 0.1F));
        float miningSpeed = head.miningSpeed() * handle.handleSpeed();
        float attackDamage = type.getBaseDamage() + head.attackDamage() * type.getHeadDamageScale();

        ItemStack stack = new ItemStack(get(type).get());
        stack.set(GrimComponents.PARTS.get(), materials.stream().map(GrimMaterial::id).toList());
        stack.set(DataComponents.MAX_DAMAGE, durability);
        stack.set(DataComponents.ENCHANTABLE, new Enchantable(head.enchantability()));
        stack.remove(DataComponents.REPAIRABLE);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, type.getAttackSpeed(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build());

        TagKey<Block> mineable = type.getMineable();
        if (mineable != null) {
            stack.set(DataComponents.TOOL, new Tool(List.of(
                    Tool.Rule.deniesDrops(getBlocks(head.incorrectForDrops())),
                    Tool.Rule.minesAndDrops(getBlocks(mineable), miningSpeed)),
                    1.0F, 1, true));
        }
        return stack;
    }

    private static HolderSet<Block> getBlocks(TagKey<Block> tag) {
        return BuiltInRegistries.BLOCK.get(tag)
                .<HolderSet<Block>>map(set -> set)
                .orElseGet(HolderSet::empty);
    }
}
