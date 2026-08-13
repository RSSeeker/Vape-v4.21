package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ProfileListEntryBackgroundComponent
extends GuiComponent {
    private static final String b = "private_profile@2x";

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void I() {
    }

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    @Override
    public void H() {
        GuiRenderPrimitives.F(b, this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + this.L() / 2.0, 10.0, 10.0, Color.WHITE);
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }
}

