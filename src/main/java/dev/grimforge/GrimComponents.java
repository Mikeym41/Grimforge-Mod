package dev.grimforge;

import java.util.List;
import java.util.function.Supplier;

import dev.grimforge.tool.ToolType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Data components stored on parts and tools.
 */
public final class GrimComponents {

    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Grimforge.MODID);

    /**
     * The material of a part.
     */
    public static final Supplier<DataComponentType<Identifier>> MATERIAL = COMPONENTS.registerComponentType("material",
            builder -> builder
                    .persistent(Identifier.CODEC)
                    .networkSynchronized(Identifier.STREAM_CODEC));

    /**
     * The materials of a tool's parts, in the order given by {@link ToolType#getParts()}
     */
    public static final Supplier<DataComponentType<List<Identifier>>> PARTS = COMPONENTS.registerComponentType("parts",
            builder -> builder
                    .persistent(Identifier.CODEC.listOf())
                    .networkSynchronized(Identifier.STREAM_CODEC.apply(ByteBufCodecs.list())));

    private GrimComponents() {
    }
}
