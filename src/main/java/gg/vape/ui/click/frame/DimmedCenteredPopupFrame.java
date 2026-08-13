package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.CenteredPopupFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class DimmedCenteredPopupFrame
extends CenteredPopupFrame {
    public DimmedCenteredPopupFrame(GuiComponent guiComponent, GuiComponent guiComponent2) {
        super(guiComponent, guiComponent2);
    }


    @Override
    public void c() {
        GuiRenderPrimitives.d(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().G$src$D$1b2f02a(), this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n(), this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().A(), this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().L() + (double)(this.g$src$Z$iczr0v() ? 2 : 0), new Color(0, 0, 0, 100));
        super.c();
    }
}

