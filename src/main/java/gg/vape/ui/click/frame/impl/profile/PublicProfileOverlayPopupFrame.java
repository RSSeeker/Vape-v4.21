package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOverlayCloseClickHandler;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOverlayOutsideClickCloseHandler;
import java.awt.Color;

public class PublicProfileOverlayPopupFrame
extends PopupFrame {
    private final GuiComponent overlayContent;
    private int offsetY = 0;
    private int offsetX = 0;
    private final PanelComponent overlayContainer = (PanelComponent)this.D$src$Lgg_vape_ui_click_component_GuiComponent_$srx612();
    private boolean closeOnOutsideClick = false;

    public boolean X$src$Z$n2tvta() {
        return this.closeOnOutsideClick;
    }

    public PublicProfileOverlayPopupFrame(GuiComponent guiComponent, GuiComponent guiComponent2) {
        super(guiComponent, new PanelComponent(0.0, 0.0));
        this.overlayContent = guiComponent2;
        this.overlayContainer.h(guiComponent2, new Object[0]);
        this.overlayContainer.setDisabledOverlayColor(new Color(0, 0, 0, 130));
        this.overlayContainer.setShowDisabledOverlay(true);
        this.overlayContainer.C$src$V$nadrmg();
        this.overlayContainer.setExplicitWidth(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().A());
        this.overlayContainer.setExplicitHeight(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().L() + 2.0);
        this.overlayContainer.addMouseListener(new PublicProfileOverlayCloseClickHandler(this));
        this.addGlobalMouseListener(new PublicProfileOverlayOutsideClickCloseHandler(this));
    }

    public void T(int n) {
        this.offsetX = n;
    }

    public void p(boolean bl) {
        this.closeOnOutsideClick = bl;
    }

    static boolean isOutsideClickCloseEnabled(PublicProfileOverlayPopupFrame popupFrame) {
        return popupFrame.closeOnOutsideClick;
    }

    public void Q(int n) {
        this.offsetY = n;
    }

    @Override
    public void c() {
        this.K(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().G$src$D$1b2f02a());
        this.S(this.X$src$Lgg_vape_ui_click_frame_Frame_$1aw5qf9().n());
        this.overlayContainer.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().z(this.overlayContent, "offsetx " + this.offsetX + ", offsety " + this.offsetY);
        this.l$src$V$1mibm4x();
        super.c();
    }
}
