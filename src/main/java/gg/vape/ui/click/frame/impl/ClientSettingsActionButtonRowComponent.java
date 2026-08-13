package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;

public class ClientSettingsActionButtonRowComponent
extends GuiComponent {
    private AnimatedCenteredTextLabelComponent b;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return 15.0;
    }

    @Override
    public void I() {
    }

    @Override
    public void u() {
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void F() {
    }

    public ClientSettingsActionButtonRowComponent(String string, GuiClickListener guiClickListener) {
        this.b = new AnimatedCenteredTextLabelComponent(string, ClientSettingsActionButtonRowComponent.J.l);
        this.b.addClickListener(guiClickListener);
        this.addChildren(this.b);
    }

    @Override
    public void H() {
        this.b.K(this.G$src$D$1b2f02a() + 5.0);
        this.b.S(this.n() + 1.0);
        this.b.o(this.A() - 10.0);
        this.b.Y(this.L() - 2.0);
    }
}
