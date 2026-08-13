package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.layout.WrappingFlowLayout;

public class FlowLayoutComponent
extends FrameComponent {
    private final WrappingFlowLayout flowLayout;
    private static final String DEFAULT_LAYOUT_OPTIONS;
    private static String legacyState;

    @Override
    public void Y() {
    }

    @Override
    public void v() {
    }

    @Override
    public void V() {
    }

    static {
        FlowLayoutComponent.setLegacyState("cAcODb");
        DEFAULT_LAYOUT_OPTIONS = "wrap, widthwrap";
    }

    public static String getLegacyState() {
        return legacyState;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }


    public void unusedHook() {
    }

    public FlowLayoutComponent(double width) {
        this(width, 0.0);
    }

    public FlowLayoutComponent(double width, double height) {
        this.Y(height);
        this.flowLayout = new WrappingFlowLayout(this);
        this.flowLayout.o(width);
        this.flowLayout.M(DEFAULT_LAYOUT_OPTIONS);
        this.N(this.flowLayout);
    }

    @Override
    public double x() {
        return this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C();
    }

    public void setLayoutWidth(double width) {
        this.flowLayout.o(width);
    }

    public double getVisibleChildrenWidth() {
        double totalWidth = 0.0;
        if (!this.V$src$Z$1xhop3l()) {
            return totalWidth;
        }
        for (GuiComponent child : this.f()) {
            if (!child.V$src$Z$1xhop3l()) continue;
            totalWidth += child.A();
        }
        return totalWidth;
    }

    @Override
    public double C() {
        return this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y();
    }

    public static void setLegacyState(String legacyState) {
        FlowLayoutComponent.legacyState = legacyState;
    }
}

