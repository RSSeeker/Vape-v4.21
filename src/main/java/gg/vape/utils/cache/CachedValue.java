package gg.vape.utils.cache;

public class CachedValue<T> {
    private boolean cached = false;
    private static String legacyState;
    private T value;

    public void setValue(T value) {
        this.value = value;
        this.cached = true;
    }

    public boolean isCached() {
        return this.cached;
    }

    public static String getLegacyState() {
        return legacyState;
    }

    public T getCachedValue() {
        return this.value;
    }

    public static void setLegacyState(String state) {
        legacyState = state;
    }

    public void clear() {
        this.cached = false;
    }

    static {
        if (CachedValue.getLegacyState() == null) {
            CachedValue.setLegacyState("zYblo");
        }
    }
}
