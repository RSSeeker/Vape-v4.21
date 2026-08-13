package gg.vape.ui.click.component;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class DualTextLabelRowComponent
extends PanelComponent {
    private final SimpleTextLabelComponent primaryLabel;
    private final WrappingTextLabelComponent secondaryLabel;

    @Override
    public void c() {
        GuiRenderPrimitives.d(this.secondaryLabel.G$src$D$1b2f02a() - 2.0, this.secondaryLabel.n() + 1.0, this.secondaryLabel.A() + 4.0, this.secondaryLabel.getFontScale() * 10.0, DualTextLabelRowComponent.J.z);
        super.c();
    }

    public DualTextLabelRowComponent(String primaryText, String secondaryText, double height, double fontScale) {
        super(40.0, height);
        this.setShowDisabledOverlay(false);
        this.setDisabledOverlayColor(Color.RED);
        this.primaryLabel = new SimpleTextLabelComponent(primaryText, fontScale, DualTextLabelRowComponent.J.A);
        this.primaryLabel.o(this.primaryLabel.getTextWidth() + 8.0);
        this.primaryLabel.setBold(true);
        this.primaryLabel.setOffsetX(0.0f);
        this.secondaryLabel = new WrappingTextLabelComponent(secondaryText, fontScale * 0.9, DualTextLabelRowComponent.J.Z);
        this.secondaryLabel.o(this.secondaryLabel.getTextWidth() + 4.0);
        this.setExplicitWidth(this.primaryLabel.A() + this.secondaryLabel.A() + 2.0);
        this.addChildren(this.primaryLabel, this.secondaryLabel);
    }

    @Override
    public double x() {
        return this.secondaryLabel.A();
    }

    @Override
    public void H() {
    }

    @Override
    public double C() {
        return 6.0;
    }

    public void setFontScale(double fontScale) {
        this.primaryLabel.setFontScale(fontScale);
        this.secondaryLabel.setFontScale(fontScale);
    }
}
