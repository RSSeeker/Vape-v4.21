package gg.vape.module.blatant.blockin;

import org.jetbrains.annotations.Nullable;

public class HotbarSlotResolutionWithValue<T>
extends HotbarSlotResolution<HotbarSlotResolutionWithValue<T>> {
    @Nullable
    private T value;

    HotbarSlotResolutionWithValue(HotbarSlotResolutionStatus status, @Nullable String message,
                                  @Nullable T value) {
        super(status, message);
        this.value = value;
    }

    public HotbarSlotResolutionWithValue() {
        this(HotbarSlotResolutionStatus.PENDING, null, null);
    }

    @Nullable
    public T getValue() {
        return this.value;
    }

    public HotbarSlotResolutionWithValue<T> setValue(@Nullable T value) {
        this.value = value;
        return this;
    }
}
