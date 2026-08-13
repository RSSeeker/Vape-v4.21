package gg.vape.module.utility.inventory.cleaner.ui;

import org.jetbrains.annotations.Nullable;

public class ItemPickerSelection<L, R> {
    @Nullable
    private final L left;
    @Nullable
    private final R right;

    public static <L, R> ItemPickerSelection<L, R> empty() {
        return new ItemPickerSelection<L, R>(null, null);
    }

    public static <L, R> ItemPickerSelection<L, R> ofRight(@Nullable R r) {
        return new ItemPickerSelection<L, R>(null, r);
    }

    public static <L, R> ItemPickerSelection<L, R> ofLeft(@Nullable L l) {
        return new ItemPickerSelection<L, R>(l, null);
    }

    public String toString() {
        return "Either{left=" + this.left + ", right=" + this.right + '}';
    }

    ItemPickerSelection(@Nullable L l, @Nullable R r) {
        this.left = l;
        this.right = r;
    }

    @Nullable
    public L getLeft() {
        return this.left;
    }

    @Nullable
    public R getRight() {
        return this.right;
    }
}
