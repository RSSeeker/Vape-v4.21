package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.PopupFrame;

public class CenteredPopupFrame
extends PopupFrame {
    private double ue = 0.0;

    public void n(double d) {
        this.ue = d;
    }

    @Override
    public void c() {
        double d = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().G$src$D$1b2f02a() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().A() / 2.0;
        double d2 = this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().n() + this.V$src$Lgg_vape_ui_click_component_GuiComponent_$jpbobc().L() / 2.0 + this.ue / 2.0;
        double d3 = d - this.A() / 2.0;
        double d4 = d2 - this.L() / 2.0;
        this.K(d3);
        this.S(d4);
        this.l$src$V$1mibm4x();
        super.c();
    }

    public CenteredPopupFrame(GuiComponent guiComponent, GuiComponent guiComponent2) {
        super(guiComponent, guiComponent2);
    }
}

