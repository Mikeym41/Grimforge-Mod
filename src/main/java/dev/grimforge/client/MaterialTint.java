package dev.grimforge.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.grimforge.GrimComponents;
import dev.grimforge.material.GrimMaterials;
import java.util.List;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * Tints an item model layer with a material's color.
 *
 * <p>Without a part index, the part's own material is used. With one, the material of that part of a tool is used.</p>
 *
 * @param part the part index, or -1 for a single part
 */
public record MaterialTint(int part) implements ItemTintSource {

    public static final MapCodec<MaterialTint> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.optionalFieldOf("part", -1).forGetter(MaterialTint::part)
    ).apply(instance, MaterialTint::new));

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        Identifier material;
        if (part >= 0) {
            List<Identifier> parts = stack.get(GrimComponents.PARTS.get());
            material = parts != null && part < parts.size() ? parts.get(part) : null;
        } else {
            material = stack.get(GrimComponents.MATERIAL.get());
        }
        return ARGB.opaque(GrimMaterials.getOrDefault(material).color());
    }

    @Override
    public MapCodec<MaterialTint> type() {
        return MAP_CODEC;
    }
}
