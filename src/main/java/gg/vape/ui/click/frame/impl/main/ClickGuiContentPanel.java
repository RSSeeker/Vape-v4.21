package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.layout.ClickGuiContentLayout;

public class ClickGuiContentPanel
extends PanelComponent {
    private static final String DEFAULT_LAYOUT = "wrap, widthwrap";
    private final ClickGuiContentLayout contentLayout = new ClickGuiContentLayout(this);

    @Override
    public double x() {
        return 0.0;
    }

    public ClickGuiContentLayout getContentLayout() {
        return this.contentLayout;
    }

    public void setResponsiveWidthEnabled(boolean enabled) {
        this.contentLayout.C(enabled);
    }

    @Override
    public void Y(double d) {
        if (d != this.L()) {
            super.setExplicitHeight(d);
            this.H(true);
        }
    }

    public void setLayoutMode(String layoutMode) {
        this.contentLayout.M(layoutMode);
    }

    @Override
    public void removeMarkedChildren() {
        super.removeMarkedChildren();
        this.contentLayout.E();
    }

    public boolean isResponsiveWidthEnabled() {
        return this.contentLayout.K();
    }

    @Override
    public void Y() {
    }


    public ClickGuiContentPanel(double d) {
        this(d, 0.0);
    }

    public ClickGuiContentPanel(double d, double d2) {
        super(d, d2);
        this.contentLayout.t(false);
        this.contentLayout.M(false);
        this.contentLayout.U(false);
        this.contentLayout.I(false);
        this.contentLayout.u(false);
        this.contentLayout.M(DEFAULT_LAYOUT);
        this.N(this.contentLayout);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void o(double d) {
        if (d != this.A()) {
            super.setExplicitWidth(d);
            this.H(true);
        }
    }

    @Override
    public void V() {
    }

    @Override
    public void v() {
    }
}

