package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;

public abstract class ClickGuiPanelBase
extends PanelComponent {
    private static GuiComponent[] UY;

    public void t(double d, double d2) {
        this.K(d, d2, 0.0);
    }

    public static GuiComponent[] a_up_arr_C() {
        return UY;
    }

    protected ClickGuiPanelBase(double d, double d2) {
        super(d, d2);
        this.setHorizontalInset(0.0);
        this.setShowDisabledOverlay(false);
    }

    public void K(double d, double d2, double d3) {
        this.o(d);
        this.Y(d2);
    }

    public void void_K() {
    }

    public void void_Z() {
    }

    public static void M(GuiComponent[] guiComponentArray) {
        UY = guiComponentArray;
    }

    static {
        if (ClickGuiPanelBase.a_up_arr_C() != null) {
            ClickGuiPanelBase.M(new GuiComponent[5]);
        }
    }

    public /* synthetic */ void K() {
        this.void_K();
    }

    public /* synthetic */ void Z$src$V$15w0jcm() {
        this.void_Z();
    }
}

