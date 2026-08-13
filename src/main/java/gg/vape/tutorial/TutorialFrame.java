package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;

public class TutorialFrame
extends Frame {
    private static final String eb;
    private static GuiComponent[] zb;

    public static GuiComponent[] l$src$ALgg_vape_ui_click_component_GuiComponent_$xp34x4() {
        return zb;
    }

    @Override
    public void Y() {
        this.l$src$V$1mibm4x();
        Vape.INSTANCE.getTutorialManager().refreshCurrentAction();
        ClientSettings.queueFrameOpen(this);
    }

    public static void n(GuiComponent[] guiComponentArray) {
        zb = guiComponentArray;
    }

    @Override
    public void c() {
        super.c();
    }

    static {
        TutorialFrame.n(new GuiComponent[4]);
        eb = "tutorial";
    }

    @Override
    public void v() {
    }

    @Override
    public String getName() {
        return eb;
    }
}
