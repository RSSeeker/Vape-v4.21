package gg.vape.ui.click.frame.impl.target;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBase;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class TargetInfoStatStripComponent
extends GuiComponent {
    protected HudModuleFrameBase frame;

    public TargetInfoStatStripComponent(int n, int n2) {
        this.o(n);
        this.Y(n2);
        this.setDisabledOverlayColor(TargetInfoStatStripComponent.J.r);
    }

    @Override
    public void H() {
        Color color = this.frame != null ? this.frame.applyDefaultEditorAlpha(this.getDisabledOverlayColor()) : this.getDisabledOverlayColor();
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a() + 1.0, this.n(), this.A() - 2.0, this.L(), color, 1.0f);
    }

    public void setFrame(HudModuleFrameBase frame) {
        this.frame = frame;
    }
}

