package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class TwoLineTextDisplayComponent
extends GuiComponent {
    private final WrappingTextLabelComponent secondaryLabel;
    private final WrappingTextLabelComponent primaryLabel;

    public TwoLineTextDisplayComponent(String secondaryText, String primaryText) {
        this.setDisabledOverlayColor(TwoLineTextDisplayComponent.J.E);
        this.primaryLabel = new WrappingTextLabelComponent(primaryText, 0.9);
        this.primaryLabel.setBold(true);
        this.primaryLabel.setTextColor(TwoLineTextDisplayComponent.J.A);
        this.primaryLabel.Y(4.0);
        this.primaryLabel.setUseExplicitHeight(true);
        this.addChildren(this.primaryLabel);
        this.secondaryLabel = new WrappingTextLabelComponent(secondaryText, 0.65);
        this.secondaryLabel.setBold(true);
        this.secondaryLabel.setTextColor(TwoLineTextDisplayComponent.J.C);
        this.secondaryLabel.Y(4.0);
        this.secondaryLabel.setUseExplicitHeight(true);
        this.addChildren(this.secondaryLabel);
    }

    public void setSecondaryFontScale(double fontScale) {
        this.secondaryLabel.setFontScale(fontScale);
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.getDisabledOverlayColor());
        double halfHeight = this.L() / 2.0;
        this.secondaryLabel.S(this.n() + halfHeight + this.secondaryLabel.getTextHeight() / 2.0);
        this.primaryLabel.S(this.n() + halfHeight - 7.0);
        for (GuiComponent child : this.f()) {
            child.K(this.G$src$D$1b2f02a());
            child.o(this.A());
            child.H();
        }
        double secondaryWidth = this.secondaryLabel.A();
        this.getClass();
        this.secondaryLabel.setWrapWidthOverride(secondaryWidth - 5.0);
        double primaryWidth = this.primaryLabel.A();
        this.getClass();
        this.primaryLabel.setWrapWidthOverride(primaryWidth - 5.0);
    }

    public void setPrimaryFontScale(double fontScale) {
        this.primaryLabel.setFontScale(fontScale);
    }

    @Override
    public void F() {
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }
}
