package dev.grimforge.part;

import dev.grimforge.Grimforge;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * The tool part items.
 */
public final class GrimParts {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Grimforge.MODID);

    public static final DeferredItem<ToolPartItem> PICKAXE_HEAD = register("pickaxe_head", PartRole.HEAD);
    public static final DeferredItem<ToolPartItem> SHOVEL_HEAD = register("shovel_head", PartRole.HEAD);
    public static final DeferredItem<ToolPartItem> AXE_HEAD = register("axe_head", PartRole.HEAD);
    public static final DeferredItem<ToolPartItem> HOE_BLADE = register("hoe_blade", PartRole.HEAD);
    public static final DeferredItem<ToolPartItem> SWORD_BLADE = register("sword_blade", PartRole.HEAD);
    public static final DeferredItem<ToolPartItem> HANDLE = register("handle", PartRole.HANDLE);
    public static final DeferredItem<ToolPartItem> BINDING = register("binding", PartRole.EXTRA);
    public static final DeferredItem<ToolPartItem> CROSSGUARD = register("crossguard", PartRole.EXTRA);

    public static final List<DeferredItem<ToolPartItem>> ALL =
            List.of(PICKAXE_HEAD, SHOVEL_HEAD, AXE_HEAD, HOE_BLADE, SWORD_BLADE, HANDLE, BINDING, CROSSGUARD);

    private GrimParts() {
    }

    private static DeferredItem<ToolPartItem> register(String name, PartRole role) {
        return ITEMS.registerItem(name, properties -> new ToolPartItem(properties, role));
    }
}
