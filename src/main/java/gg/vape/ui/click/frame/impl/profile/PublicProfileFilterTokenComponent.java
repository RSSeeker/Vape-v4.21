package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class PublicProfileFilterTokenComponent
extends GuiComponent {
    private final WrappingTextLabelComponent label;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void F() {
    }

    public PublicProfileFilterTokenComponent(String string) {
        this.setPropagateMouseEvents(true);
        this.label = new WrappingTextLabelComponent(string, 0.7);
        this.label.setTextColor(PublicProfileFilterTokenComponent.J.Z);
        this.addChildren(this.label);
    }

    @Override
    public double x() {
        double d = this.label.getTextWidth();
        this.getClass();
        return d + 5.0 + 4.0;
    }

    public String getText() {
        return this.label.getText();
    }

    public void S(String string) {
        this.label.setText(string);
    }

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 12.0;
    }

    @Override
    public void u() {
    }

    @Override
    public void H() {
        this.label.K(this.G$src$D$1b2f02a());
        this.label.S(this.n() + this.L() / 2.0 - this.label.L() / 2.0);
        this.label.o(this.A());
        this.label.Y(this.L());
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), PublicProfileFilterTokenComponent.J.z);
    }
}
