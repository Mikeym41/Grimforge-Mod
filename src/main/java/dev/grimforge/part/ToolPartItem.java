package dev.grimforge.part;

import dev.grimforge.GrimComponents;
import dev.grimforge.material.GrimMaterial;
import dev.grimforge.material.GrimMaterials;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

/**
 * A tool part. The material is stored on the stack.
 */
public class ToolPartItem extends Item {

    private final PartRole role;

    public ToolPartItem(Properties properties, PartRole role) {
        super(properties);
        this.role = role;
    }

    public PartRole getRole() {
        return role;
    }

    /**
     * Creates a stack of this part.
     *
     * @param material the material
     * @return the stack
     */
    public ItemStack create(GrimMaterial material) {
        ItemStack stack = new ItemStack(this);
        stack.set(GrimComponents.MATERIAL.get(), material.id());
        return stack;
    }

    /**
     * Gets the material of a part stack.
     *
     * @param stack the stack
     * @return the material
     */
    public static GrimMaterial getMaterial(ItemStack stack) {
        return GrimMaterials.getOrDefault(stack.get(GrimComponents.MATERIAL.get()));
    }

    @Override
    public Component getName(ItemStack stack) {
        GrimMaterial material = GrimMaterials.get(stack.get(GrimComponents.MATERIAL.get()));
        if (material == null) {
            return super.getName(stack);
        }
        return Component.translatable(getDescriptionId() + ".named", material.displayName());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        GrimMaterial material = getMaterial(stack);
        switch (role) {
            case HEAD -> {
                tooltip.accept(stat("tier", material.tier()));
                tooltip.accept(stat("durability", material.durability()));
                tooltip.accept(stat("mining_speed", material.miningSpeed()));
                tooltip.accept(stat("attack_damage", material.attackDamage()));
            }
            case HANDLE -> {
                tooltip.accept(stat("speed_multiplier", material.handleSpeed()));
                tooltip.accept(stat("durability", Math.round(material.durability() * 0.1F)));
            }
            case EXTRA -> tooltip.accept(stat("durability", Math.round(material.durability() * 0.1F)));
        }
    }

    private static Component stat(String key, Object value) {
        return Component.translatable("tooltip.grimforge.stat." + key, value).withStyle(ChatFormatting.GRAY);
    }
}
