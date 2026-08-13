package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import org.jetbrains.annotations.Nullable;

public enum EnchantmentFilterMode
implements INamed,
DescribedOption {
    HAS("Has", "Whether the item has the enchantment."),
    LEVEL("Level", "The level of the enchantment.");

    private final String description;
    public static final List<EnchantmentFilterMode> VALUES;
    private final String name;

    public static EnchantmentFilterMode fromName(String name) {
        return EnchantmentFilterMode.fromNameOrDefault(name, HAS);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    static {
        VALUES = Arrays.asList(EnchantmentFilterMode.values());
    }

    private EnchantmentFilterMode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static EnchantmentFilterMode fromNameOrDefault(String name, EnchantmentFilterMode fallback) {
        EnchantmentFilterMode mode = EnchantmentFilterMode.findByName(name);
        return mode == null ? fallback : mode;
    }

    @Nullable
    public static EnchantmentFilterMode findByName(String name) {
        for (EnchantmentFilterMode mode : VALUES) {
            if (!mode.getName().equalsIgnoreCase(name)) continue;
            return mode;
        }
        return null;
    }

}

