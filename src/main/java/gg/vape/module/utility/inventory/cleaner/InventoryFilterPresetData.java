package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.unmap.INamed;
import gg.vape.wrapper.impl.ItemStack;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface InventoryFilterPresetData
extends INamed {
    public @UnmodifiableView List<InventoryFilterConditionGroup> getConditionGroups();

    public boolean matches(ItemStack itemStack);

    @Override
    public String getName();
}
