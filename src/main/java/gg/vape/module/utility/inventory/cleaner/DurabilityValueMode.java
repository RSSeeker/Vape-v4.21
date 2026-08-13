package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public enum DurabilityValueMode
implements INamed,
DescribedOption {
    PERCENTAGE("Percentage", "The durability of the item as a percentage"),
    VALUE("Value", "The durability of the item as the direct value");

    private final String description;
    public static final @UnmodifiableView List<DurabilityValueMode> VALUES;
    private final String name;

    static {
        VALUES = Arrays.asList(DurabilityValueMode.values());
    }


    @Override
    public String getName() {
        return this.name;
    }

    private DurabilityValueMode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public static DurabilityValueMode fromNameOrDefault(String name, DurabilityValueMode fallback) {
        DurabilityValueMode mode = DurabilityValueMode.findByName(name);
        return mode == null ? fallback : mode;
    }

    @Nullable
    public static DurabilityValueMode findByName(String name) {
        for (DurabilityValueMode mode : VALUES) {
            if (!mode.getName().equalsIgnoreCase(name)) continue;
            return mode;
        }
        return null;
    }

    public static DurabilityValueMode fromName(String name) {
        return DurabilityValueMode.fromNameOrDefault(name, PERCENTAGE);
    }
}

