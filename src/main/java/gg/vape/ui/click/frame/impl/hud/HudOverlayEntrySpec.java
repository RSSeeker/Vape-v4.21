package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.frame.Frame;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.Nullable;

public final class HudOverlayEntrySpec {
    private final String iconName;
    @Nullable
    private final Runnable action;
    @Nullable
    private final BooleanSupplier selectedSupplier;
    private final String label;
    @Nullable
    private final Class<? extends Frame> frameClass;

    public String getIconName() {
        return this.iconName;
    }

    @Nullable
    public Runnable getAction() {
        return this.action;
    }

    public static HudOverlayEntrySpec forFrame(String label, String iconName,
            Class<? extends Frame> frameClass) {
        return new HudOverlayEntrySpec(label, iconName, frameClass, null, null);
    }

    public String getLabel() {
        return this.label;
    }

    public static HudOverlayEntrySpec forAction(String label, String iconName,
            @Nullable Runnable action, @Nullable BooleanSupplier selectedSupplier) {
        return new HudOverlayEntrySpec(label, iconName, null, selectedSupplier, action);
    }

    public HudOverlayEntrySpec withSelectedSupplier(BooleanSupplier selectedSupplier) {
        return new HudOverlayEntrySpec(this.label, this.iconName, this.frameClass,
                selectedSupplier, this.action);
    }

    public HudOverlayEntrySpec withAction(Runnable action) {
        return new HudOverlayEntrySpec(this.label, this.iconName, this.frameClass,
                this.selectedSupplier, action);
    }

    private HudOverlayEntrySpec(String label, String iconName,
            @Nullable Class<? extends Frame> frameClass,
            @Nullable BooleanSupplier selectedSupplier, @Nullable Runnable action) {
        this.label = Objects.requireNonNull(label, "label");
        this.iconName = Objects.requireNonNull(iconName, "iconName");
        this.frameClass = frameClass;
        this.selectedSupplier = selectedSupplier;
        this.action = action;
    }

    @Nullable
    public BooleanSupplier getSelectedSupplier() {
        return this.selectedSupplier;
    }

    @Nullable
    public Class<? extends Frame> getFrameClass() {
        return this.frameClass;
    }
}
