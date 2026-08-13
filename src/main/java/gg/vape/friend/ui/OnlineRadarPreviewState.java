package gg.vape.friend.ui;

import org.jetbrains.annotations.Nullable;

public class OnlineRadarPreviewState<K, V> {
    private final K key;
    private static int obfuscationSeed;
    @Nullable
    private final V value;

    private OnlineRadarPreviewState(K key, @Nullable V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> OnlineRadarPreviewState<K, V> create(K key, V value) {
        return new OnlineRadarPreviewState<K, V>(key, value);
    }

    public static int getReservedZero() {
        int seed = OnlineRadarPreviewState.getObfuscationSeed();
        return 0;
    }

    public String toString() {
        return "Pair(key=" + this.getKey() + ", value=" + this.getValue() + ")";
    }


    public static void setObfuscationSeed(int seed) {
        obfuscationSeed = seed;
    }

    public K getKey() {
        return this.key;
    }

    static {
        OnlineRadarPreviewState.setObfuscationSeed(10);
    }

    public static int getObfuscationSeed() {
        return obfuscationSeed;
    }

    @Nullable
    public V getValue() {
        return this.value;
    }
}

