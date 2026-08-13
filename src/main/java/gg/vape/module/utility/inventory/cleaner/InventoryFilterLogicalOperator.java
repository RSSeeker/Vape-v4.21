package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public enum InventoryFilterLogicalOperator
implements INamed {
    AND("And"),
    OR("Or");

    private final String displayName;
    public static final List<InventoryFilterLogicalOperator> VALUES;

    static {
        VALUES = Arrays.asList(InventoryFilterLogicalOperator.values());
    }

    private InventoryFilterLogicalOperator(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    @Nullable
    public static InventoryFilterLogicalOperator findByName(String name) {
        for (InventoryFilterLogicalOperator operator : VALUES) {
            if (!operator.getName().equalsIgnoreCase(name)) continue;
            return operator;
        }
        return null;
    }


}

