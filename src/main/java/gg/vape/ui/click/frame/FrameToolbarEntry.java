package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;

public class FrameToolbarEntry {
    private final FlowLayoutComponent layout;
    private boolean hideInBackMode;
    private final GuiComponent component;

    public FlowLayoutComponent getLayout() {
        return this.layout;
    }

    public void setHideInBackMode(boolean hideInBackMode) {
        this.hideInBackMode = hideInBackMode;
    }

    public GuiComponent getComponent() {
        return this.component;
    }

    public FrameToolbarEntry(GuiComponent component, boolean hideInBackMode) {
        this.component = component;
        this.hideInBackMode = hideInBackMode;
        this.layout = new FlowLayoutComponent(component.double_A());
        this.layout.h(component, new Object[0]);
        this.layout.setShowDisabledOverlay(false);
    }

    public FrameToolbarEntry(GuiComponent guiComponent) {
        this(guiComponent, true);
    }

    public boolean isHiddenInBackMode() {
        return this.hideInBackMode;
    }
}
