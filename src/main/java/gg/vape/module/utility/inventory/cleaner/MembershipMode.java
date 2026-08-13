package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public enum MembershipMode
implements INamed {
    IS_IN("is in"),
    IS_NOT_IN("is not in");

    private final String label;
    public static final @UnmodifiableView List<MembershipMode> VALUES;

    public boolean matchesAny(String value, List<String> candidates) {
        for (String candidate : candidates) {
            if (!this.matches(value, candidate)) continue;
            return true;
        }
        return false;
    }

    public static MembershipMode fromName(String name) {
        return MembershipMode.fromNameOrDefault(name, IS_IN);
    }

    @Override
    public String getName() {
        return this.label;
    }

    private MembershipMode(String label) {
        this.label = label;
    }


    public boolean matches(String value, String candidate) {
        value = value.toLowerCase();
        candidate = candidate.toLowerCase();
        switch (this) {
            case IS_IN: {
                return value.contains(candidate);
            }
            case IS_NOT_IN: {
                return !value.contains(candidate);
            }
        }
        return false;
    }

    public static MembershipMode fromNameOrDefault(String name, MembershipMode fallback) {
        MembershipMode mode = MembershipMode.findByName(name);
        return mode == null ? fallback : mode;
    }

    static {
        VALUES = Arrays.asList(MembershipMode.values());
    }

    @Nullable
    public static MembershipMode findByName(String name) {
        for (MembershipMode mode : VALUES) {
            if (!mode.getName().equalsIgnoreCase(name)) continue;
            return mode;
        }
        return null;
    }
}

