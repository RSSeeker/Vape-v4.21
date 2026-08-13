package gg.vape.ui.click.component;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SpacerComponent;

public class MirroredSpacerComponent
extends SpacerComponent {
    private final GuiComponent heightSource;
    private final SpacerComponent renderedSpacer;

    @Override
    public void H() {
        super.H();
        this.renderedSpacer.K(this.G$src$D$1b2f02a());
        this.renderedSpacer.S(this.n());
        this.renderedSpacer.setExplicitHeight(-1.0);
        this.renderedSpacer.Y(this.L());
        this.renderedSpacer.c();
    }

    @Override
    public double C() {
        return this.heightSource.L();
    }

    public MirroredSpacerComponent(GuiComponent heightSource, double width, SpacerComponent renderedSpacer) {
        super(width, 0.0);
        this.heightSource = heightSource;
        this.renderedSpacer = renderedSpacer;
    }

    @Override
    public double L() {
        return this.heightSource.L();
    }

    public MirroredSpacerComponent(GuiComponent heightSource, SpacerComponent renderedSpacer) {
        this(heightSource, 0.0, renderedSpacer);
    }
}
