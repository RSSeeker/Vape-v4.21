package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import org.jetbrains.annotations.Nullable;

public enum PotionEffectFilterMode
implements INamed,
DescribedOption {
    HAS("Has", "Whether the item has the potion effect."),
    LEVEL("Level", "The level of the potion effect."),
    DURATION("Duration", "How long the potion effect lasts for.");

    public static final List<PotionEffectFilterMode> VALUES;
    private final String description;
    private final String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Nullable
    public static PotionEffectFilterMode findByName(String name) {
        for (PotionEffectFilterMode mode : VALUES) {
            if (!mode.getName().equalsIgnoreCase(name)) continue;
            return mode;
        }
        return null;
    }


    @Override
    public String getDescription() {
        return this.description;
    }

    private PotionEffectFilterMode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    static {
        VALUES = Arrays.asList(PotionEffectFilterMode.values());
    }

    public static PotionEffectFilterMode fromNameOrDefault(String name, PotionEffectFilterMode fallback) {
        PotionEffectFilterMode mode = PotionEffectFilterMode.findByName(name);
        return mode == null ? fallback : mode;
    }

    public static PotionEffectFilterMode fromName(String name) {
        return PotionEffectFilterMode.fromNameOrDefault(name, HAS);
    }
}

