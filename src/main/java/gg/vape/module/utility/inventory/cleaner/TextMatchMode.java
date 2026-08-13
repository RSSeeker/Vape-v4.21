package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public enum TextMatchMode
implements INamed {
    EQUALS("equals"),
    DOES_NOT_EQUAL("does not equal"),
    MATCH_REGEX("matches regex"),
    DOES_NOT_MATCH_REGEX("does not match regex"),
    CONTAINS("contains"),
    DOES_NOT_CONTAIN("does not contain"),
    IS_IN("is in", true),
    IS_NOT_IN("is not in", true);

    public static final @UnmodifiableView List<TextMatchMode> VALUES;
    private final String name;
    private final boolean multiValue;

    public boolean matchesAny(String value, List<String> candidates) {
        for (String candidate : candidates) {
            if (!this.matches(value, candidate)) continue;
            return true;
        }
        return false;
    }

    public boolean matches(String value, String candidate) {
        value = value.toLowerCase();
        candidate = candidate.toLowerCase();
        switch (this) {
            case EQUALS: {
                return value.equals(candidate);
            }
            case DOES_NOT_EQUAL: {
                return !value.equals(candidate);
            }
            case MATCH_REGEX: {
                return value.matches(candidate);
            }
            case DOES_NOT_MATCH_REGEX: {
                return !value.matches(candidate);
            }
            case CONTAINS: 
            case IS_IN: {
                return value.contains(candidate);
            }
            case DOES_NOT_CONTAIN: 
            case IS_NOT_IN: {
                return !value.contains(candidate);
            }
        }
        return false;
    }

    @Nullable
    public static TextMatchMode findByName(String name) {
        for (TextMatchMode mode : VALUES) {
            if (!mode.getName().equalsIgnoreCase(name)) continue;
            return mode;
        }
        return null;
    }


    static {
        VALUES = Arrays.asList(TextMatchMode.values());
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean supportsMultipleValues() {
        return this.multiValue;
    }

    private TextMatchMode(String name, boolean multiValue) {
        this.name = name;
        this.multiValue = multiValue;
    }

    private TextMatchMode(String name) {
        this(name, false);
    }

    public static TextMatchMode fromName(String name) {
        return TextMatchMode.fromNameOrDefault(name, EQUALS);
    }

    public static TextMatchMode fromNameOrDefault(String name, TextMatchMode fallback) {
        TextMatchMode mode = TextMatchMode.findByName(name);
        return mode == null ? fallback : mode;
    }
}

