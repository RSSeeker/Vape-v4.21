package gg.vape.ui.click.frame.impl.main;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiSection;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ClickGuiSectionTabComponent
extends InteractiveComponent {
    private boolean selected;
    private final SimpleTextLabelComponent label;
    private final ClickGuiSection section;

    public double getLabelHeight() {
        return this.label.getTextWidth() + 4.0;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ClickGuiSection getSection() {
        return this.section;
    }

    public ClickGuiSectionTabComponent(ClickGuiSection clickGuiSection) {
        this.section = clickGuiSection;
        this.label = new SimpleTextLabelComponent(clickGuiSection.A(), 0.75);
        this.label.setBold(true);
        this.label.setTextColor(ClickGuiSectionTabComponent.J.h);
        this.label.setExtraHeight(0);
        this.label.setOffsetX(0.0f);
        this.setPropagateMouseEvents(true);
        this.setShowDisabledOverlay(false);
        this.addChildren(this.label);
    }

    @Override
    public void H() {
        Color color;
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.A();
        double d4 = this.L();
        if (this.selected) {
            GuiRenderPrimitives.C(d, d2 + d4 + 0.5, d3, 1.0, ClientSettings.INSTANCE.getAccentColor());
            color = ClickGuiSectionTabComponent.J.A;
        } else {
            color = this.w$src$Z$e457mb() ? ClickGuiSectionTabComponent.J.Z : ClickGuiSectionTabComponent.J.h;
        }
        this.label.setTextColor(color);
        double d5 = this.label.getTextHeight();
        this.label.K(d + 1.5);
        this.label.S(d2 + d4 / 2.0 - d5 / 2.0 + 1.0);
        this.label.o(this.A());
    }
}
