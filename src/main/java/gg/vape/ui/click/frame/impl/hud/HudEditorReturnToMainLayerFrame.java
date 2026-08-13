package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.hud.HudEditorReturnToMainLayerHeaderComponent;
import gg.vape.wrapper.impl.Minecraft;

public class HudEditorReturnToMainLayerFrame
extends Frame {
    private boolean pressed;
    private final HudEditorReturnToMainLayerHeaderComponent header;

    public HudEditorReturnToMainLayerHeaderComponent getHeader() {
        return this.header;
    }


    @Override
    public void Y() {
        this.setDisabledOverlayColor(HudEditorReturnToMainLayerFrame.J.i);
        ClientSettings.INSTANCE.getActiveStack().v(this);
        this.centerAtTop();
    }

    @Override
    public String getName() {
        return "LegitToggleFrame";
    }

    public void centerAtTop() {
        this.M((double)Minecraft.J() / 4.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() - this.A() / 2.0, 7.0);
    }

    @Override
    public void F() {
        this.pressed = true;
    }

    @Override
    public void u() {
        if (this.pressed && !this.w$src$Z$e457mb()) {
            this.pressed = false;
        }
    }

    public HudEditorReturnToMainLayerFrame() {
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.I2 = false;
        this.setVisible(true);
        this.header = new HudEditorReturnToMainLayerHeaderComponent(this);
        this.Y(this.header);
    }

    @Override
    public boolean V$src$Z$1xhop3l() {
        return true;
    }

    @Override
    public void v() {
    }
}

