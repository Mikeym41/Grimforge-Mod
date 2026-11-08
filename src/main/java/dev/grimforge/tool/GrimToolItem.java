package dev.grimforge.tool;

import dev.grimforge.GrimComponents;
import dev.grimforge.material.GrimMaterial;
import dev.grimforge.material.GrimMaterials;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

/**
 * A tool built from parts.
 */
public class GrimToolItem extends Item {

    private final ToolType type;

    public GrimToolItem(Properties properties, ToolType type) {
        super(properties);
        this.type = type;
    }

    public ToolType getType() {
        return type;
    }

    @Override
    public Component getName(ItemStack stack) {
        List<Identifier> parts = stack.get(GrimComponents.PARTS.get());
        GrimMaterial head = parts == null || parts.isEmpty() ? null : GrimMaterials.get(parts.getFirst());
        if (head == null) {
            return super.getName(stack);
        }
        return Component.translatable(getDescriptionId() + ".named", head.displayName());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        List<Identifier> parts = stack.get(GrimComponents.PARTS.get());
        if (parts == null) {
            return;
        }
        for (int i = 0; i < parts.size() && i < type.getParts().size(); i++) {
            Component part = Component.translatable(type.getParts().get(i).get().getDescriptionId());
            Component material = GrimMaterials.getOrDefault(parts.get(i)).displayName();
            tooltip.accept(Component.translatable("tooltip.grimforge.part", part, material)
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
