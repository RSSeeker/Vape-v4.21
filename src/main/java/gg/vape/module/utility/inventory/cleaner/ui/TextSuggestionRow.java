package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class TextSuggestionRow
extends GuiComponent {
    private final WrappingTextLabelComponent label;

    public TextSuggestionRow(String text) {
        this.setPropagateMouseEvents(true);
        this.label = new WrappingTextLabelComponent(text, 0.7);
        this.label.setTextColor(TextSuggestionRow.J.Z);
        this.addChildren(this.label);
    }

    @Override
    public void I() {
    }

    public void setText(String text) {
        this.label.setText(text);
    }

    @Override
    public void H() {
        this.label.K(this.G$src$D$1b2f02a());
        this.label.S(this.n() + this.L() / 2.0 - this.label.L() / 2.0);
        this.label.o(this.A());
        this.label.Y(this.L());
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), TextSuggestionRow.J.z);
    }

    @Override
    public void F() {
    }

    @Override
    public double x() {
        double textWidth = this.label.getTextWidth();
        this.getClass();
        return textWidth + 5.0 + 4.0;
    }

    public String getText() {
        return this.label.getText();
    }

    @Override
    public double C() {
        return 12.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void u() {
    }
}
