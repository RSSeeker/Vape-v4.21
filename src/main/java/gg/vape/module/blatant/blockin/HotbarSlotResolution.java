package gg.vape.module.blatant.blockin;

import org.jetbrains.annotations.Nullable;

public class HotbarSlotResolution<T extends HotbarSlotResolution<T>> {
    @Nullable
    private String message;
    private HotbarSlotResolutionStatus status;
    private boolean forced;

    HotbarSlotResolution(HotbarSlotResolutionStatus status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static HotbarSlotResolution pending(@Nullable String message) {
        return new HotbarSlotResolution(HotbarSlotResolutionStatus.PENDING, message);
    }

    public static HotbarSlotResolution failure(@Nullable String message) {
        return new HotbarSlotResolution(HotbarSlotResolutionStatus.FAILURE, message);
    }

    public static HotbarSlotResolution success(@Nullable String message) {
        return new HotbarSlotResolution(HotbarSlotResolutionStatus.SUCCESS, message);
    }

    public T setForced(boolean forced) {
        this.forced = forced;
        return (T)this;
    }

    public T force() {
        return this.setForced(true);
    }

    public boolean canContinue() {
        return this.status == HotbarSlotResolutionStatus.PENDING
                || this.status == HotbarSlotResolutionStatus.SUCCESS
                || this.status == HotbarSlotResolutionStatus.FAILURE && this.forced;
    }

    public T markSuccess(@Nullable String message) {
        this.status = HotbarSlotResolutionStatus.SUCCESS;
        this.message = message;
        return (T)this;
    }

    public boolean isFailure() {
        return this.status == HotbarSlotResolutionStatus.FAILURE;
    }

    public String getMessage() {
        return this.message == null ? "" : this.message;
    }

    public boolean isPending() {
        return this.status == HotbarSlotResolutionStatus.PENDING;
    }

    public T markFailure(@Nullable String message) {
        this.status = HotbarSlotResolutionStatus.FAILURE;
        this.message = message;
        return (T)this;
    }

    public boolean isSuccess() {
        return this.status == HotbarSlotResolutionStatus.SUCCESS;
    }

    public T markPending(@Nullable String message) {
        this.status = HotbarSlotResolutionStatus.PENDING;
        this.message = message;
        return (T)this;
    }

    HotbarSlotResolutionStatus getStatus() {
        return this.status;
    }

}
