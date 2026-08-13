package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewHeaderComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewListFrame;

public class HudModuleOverviewFrame
extends Frame {
    private static final String FRAME_NAME = "LegitMinModuleFrame";
    private final HudModuleOverviewListFrame moduleList = new HudModuleOverviewListFrame();
    private boolean initialized;

    @Override
    public void c() {
        super.c();
        if (!this.initialized) {
            this.moduleList.U();
            this.initialized = true;
        }
        this.moduleList.o(this.A());
        this.moduleList.Y(this.L());
        this.moduleList.M(this.G$src$D$1b2f02a(), this.n() + this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L());
    }

    @Override
    public void t(boolean bl, boolean bl2) {
        super.t(bl, bl2);
        this.moduleList.setVisible(bl);
    }

    @Override
    public double L() {
        return 50.0;
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public String getName() {
        return FRAME_NAME;
    }

    public HudModuleOverviewFrame() {
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().t(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().I(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().U(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().u(false);
        this.setDisabledOverlayColor(HudModuleOverviewFrame.J.i);
        this.Y(new HudModuleOverviewHeaderComponent(this));
        this.setVisible(false);
        this.L(false, false);
        this.g(true);
    }


    @Override
    public void v() {
    }

    public HudModuleOverviewListFrame getModuleList() {
        return this.moduleList;
    }

    @Override
    public double x() {
        return this.A();
    }

    @Override
    public void U() {
        super.U();
        this.moduleList.U();
    }

    @Override
    public double A() {
        return 137.5;
    }

    @Override
    public void Y() {
    }
}

