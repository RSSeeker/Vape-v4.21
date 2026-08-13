package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;

public class FixedProgressPanel
extends PanelComponent {
    double preferredHeight = 0.0;
    private static final String WRAP_LAYOUT_CONSTRAINT = "wrap";

    @Override
    public void v() {
    }

    @Override
    public double x() {
        return 100.0;
    }

    @Override
    public double C() {
        return this.preferredHeight;
    }

    public FixedProgressPanel() {
        super(100.0, 0.0);
        this.setShowDisabledOverlay(false);
        this.t(24.0);
        this.F(FrameScrollbarPlacement.OUTSIDE);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WRAP_LAYOUT_CONSTRAINT);
        this.o(100.0);
        this.N(false);
    }

    @Override
    public void Y() {
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    @Override
    public void V() {
    }
}
