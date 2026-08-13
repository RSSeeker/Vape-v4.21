package gg.vape.friend.ui;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;

public class FriendRequestRowsPanel
extends PanelComponent {
    private static final String LAYOUT_MODE = "wrap";

    @Override
    public void v() {
    }

    @Override
    public double x() {
        return 100.0;
    }

    @Override
    public void Y() {
    }

    @Override
    public void c() {
        super.c();
    }

    public int getRowCount() {
        return this.f().size();
    }

    public FriendRequestRowsPanel() {
        super(100.0, 96.0);
        this.setShowDisabledOverlay(false);
        this.F(FrameScrollbarPlacement.OUTSIDE);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(LAYOUT_MODE);
        this.setVisible(true);
        this.t(96.0);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void V() {
    }
}
