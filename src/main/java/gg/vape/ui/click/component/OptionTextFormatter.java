package gg.vape.ui.click.component;

import gg.vape.unmap.INamed;
import org.jetbrains.annotations.Nullable;

public interface OptionTextFormatter<T> {
    public String format(T value);

    public static OptionTextFormatter<String> strings() {
        return OptionTextFormatter.strings("");
    }

    public static OptionTextFormatter<String> strings(@Nullable String fallbackText) {
        return value -> OptionTextFormatter.formatStringOrFallback(fallbackText, value);
    }

    public static <N extends INamed> OptionTextFormatter<N> namedValues(@Nullable String fallbackText) {
        return value -> OptionTextFormatter.formatNamedOrFallback(fallbackText, value);
    }

    static String formatStringOrFallback(String fallbackText, String value) {
        return value != null ? value : fallbackText;
    }

    public static <N extends INamed> OptionTextFormatter<N> namedValues() {
        return OptionTextFormatter.namedValues("");
    }

    static String formatNamedOrFallback(String fallbackText, INamed value) {
        return value != null ? value.getName() : fallbackText;
    }

}

