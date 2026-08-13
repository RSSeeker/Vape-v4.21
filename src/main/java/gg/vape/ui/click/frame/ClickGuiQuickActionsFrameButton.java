package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ClickGuiQuickActionsFrameButton
extends IconButtonComponent {
    final ClickGuiQuickActionsComponent sC;

    @Override
    public void H() {
        if (this.w$src$Z$e457mb()) {
            GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + 1.5, this.n() + 4.0, 11.0, 1.0, new Color(255, 255, 255, 15));
        }
        super.H();
    }


    public ClickGuiQuickActionsFrameButton(ClickGuiQuickActionsComponent clickGuiQuickActionsComponent, String string, double d) {
        super(string, d);
        this.sC = clickGuiQuickActionsComponent;
    }
}
