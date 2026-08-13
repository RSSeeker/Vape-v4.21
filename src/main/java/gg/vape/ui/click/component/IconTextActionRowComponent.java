package gg.vape.ui.click.component;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.IconTextActionRowForwardClickMouseListener;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class IconTextActionRowComponent
extends InteractiveComponent {
    private final IconGlyphComponent icon = new IconGlyphComponent("create profile from", 6.0f, 6.0f, Color.WHITE);
    private final TruncatedTextComponent label;

    public void setText(String text) {
        this.label.setText(text);
        this.label.setAdditionalTooltipText(text);
    }

    public void setFontScale(double fontScale) {
        this.label.setFontScale(fontScale);
    }

    @Override
    public void u() {
        this.label.u();
    }

    @Override
    public void H() {
        if (this.w$src$Z$e457mb()) {
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), IconTextActionRowComponent.J.z);
        }
        double iconOffsetX = 12.0;
        this.icon.K(this.G$src$D$1b2f02a() + iconOffsetX);
        this.icon.S(this.n() + this.L() / 2.0 - this.icon.L() / 2.0);
        this.icon.H();
        this.label.K(this.icon.G$src$D$1b2f02a() + 10.0);
        this.label.S(this.n() + 0.5);
        this.label.o(this.A() - this.icon.A() - 4.0);
        this.label.Y(this.L());
        this.label.setMaxWidth(this.A() - this.icon.A() - 4.0);
        this.label.H();
        this.icon.o(6.0);
        this.icon.Y(6.0);
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void I() {
    }

    @Override
    public InteractiveComponent addClickListener(GuiClickListener clickListener) {
        this.label.addMouseListener(new IconTextActionRowForwardClickMouseListener(this, clickListener));
        return super.addClickListener(clickListener);
    }


    public IconTextActionRowComponent(String text) {
        this.label = new TruncatedTextComponent(text, "...", text, 50.0, 0.8, Color.WHITE, false, false);
        this.label.setAdditionalTooltipText(text);
        this.Y(18.0);
    }

    @Override
    public double C() {
        return 0.0;
    }

    public String getText() {
        return this.label.getText();
    }

    @Override
    public void F() {
        this.label.F();
    }

    public double getFontScale() {
        return this.label.getFontScale();
    }
}

