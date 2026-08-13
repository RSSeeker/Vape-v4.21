package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderPrimaryButtonClickHandler;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderSecondaryButtonClickHandler;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class SettingsFrameHeaderComponent
extends FrameHeaderComponent {
    private String i;
    private GuiClickListener G;
    private GuiClickListener I;
    private IconButtonComponent K = new IconButtonComponent("moduleback");
    private SquareIconButtonComponent R = new SquareIconButtonComponent("newclose", 1.5);
    private float o;

    static GuiClickListener D(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        return settingsFrameHeaderComponent.I;
    }

    @Override
    public void u() {
    }

    public SettingsFrameHeaderComponent(Frame frame, String string) {
        this(frame, string, 1.0f);
    }

    static GuiClickListener O(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        return settingsFrameHeaderComponent.G;
    }

    public SettingsFrameHeaderComponent(Frame frame, String string, float f) {
        super(frame);
        this.i = string;
        this.o = f;
        this.K.addClickListener(new SettingsFrameHeaderSecondaryButtonClickHandler(this));
        this.addChildren(this.K);
        this.R.addClickListener(new SettingsFrameHeaderPrimaryButtonClickHandler(this, frame));
        this.addChildren(this.R);
    }

    public SettingsFrameHeaderComponent n(GuiClickListener guiClickListener) {
        this.G = guiClickListener;
        return this;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = SettingsFrameHeaderComponent.J.A;
        double d = smoothFontRenderer.d(this.i);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0 + 1.0;
        smoothFontRenderer.d(this.i, this.G$src$D$1b2f02a() + 10.0 + 8.0, d2, color);
        this.K.K(this.G$src$D$1b2f02a() + 5.0 - 2.0);
        this.K.S(this.n() + 1.0);
        this.K.Y(this.L());
        this.K.setImageDivisor(3.5);
        this.R.K(this.G$src$D$1b2f02a() + this.A() - 13.0);
        this.R.S(this.n() + 1.0);
        this.R.Y(this.L());
        this.R.setIconScale(1.2f);
        this.R.setImageDivisor(3.5);
    }

    public SettingsFrameHeaderComponent f(GuiClickListener guiClickListener) {
        this.I = guiClickListener;
        return this;
    }

    @Override
    public void F() {
    }
}
