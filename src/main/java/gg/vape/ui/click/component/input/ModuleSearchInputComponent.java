package gg.vape.ui.click.component.input;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;
import gg.vape.ui.click.frame.impl.ModuleSearchFrameHeader;

public class ModuleSearchInputComponent
extends TextInputComponentBase {
    private ClientSettingsSearchFrameHeader clientSettingsHeader;
    private ModuleSearchFrameHeader moduleSearchHeader;
    private int previousQueryLength = 0;

    @Override
    public double x() {
        return 0.0;
    }

    public ModuleSearchInputComponent(ModuleSearchFrameHeader moduleSearchFrameHeader) {
        super("");
        this.moduleSearchHeader = moduleSearchFrameHeader;
        this.actionButton.setVisible(false);
    }

    @Override
    public void c() {
        if (!(this.clientSettingsHeader == null || ClientSettings.activeComponent != null && ClientSettings.activeComponent.equals(this))) {
            ClientSettings.activeComponent = this;
        }
        this.renderText();
    }

    @Override
    public float getLeftInset() {
        return 0.0f;
    }


    @Override
    public float getRightInset() {
        return 0.0f;
    }

    @Override
    public void F() {
    }

    @Override
    public void u() {
        int n;
        if (this.V$src$Z$1xhop3l() && this.moduleSearchHeader != null && (n = this.getText().length()) != this.previousQueryLength) {
            this.moduleSearchHeader.l$src$V$11ec2hr();
            this.moduleSearchHeader.K$src$V$10w6uwu();
            this.previousQueryLength = n;
        }
    }

    public ModuleSearchInputComponent(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        super("");
        this.clientSettingsHeader = clientSettingsSearchFrameHeader;
        this.actionButton.setVisible(false);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.moduleSearchHeader != null) {
            super.g(guiMouseEvent);
            return;
        }
        boolean bl = this.getBounds().J(guiMouseEvent.getX(), guiMouseEvent.getY());
        if (bl) {
            if (guiMouseEvent.getAction().equals((Object)MouseButton.RIGHT_CLICK)) {
                this.setText("");
                ((ClientSettingsSearchFrame)this.clientSettingsHeader.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0()).rebuildContent();
            }
            if (ClientSettings.activeComponent != null) {
                // empty if block
            }
            return;
        }
        if (ClientSettings.activeComponent != null) {
            ClientSettings.activeComponent = null;
            this.clientSettingsHeader.a(false);
            this.setVisible(false);
        }
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void H() {
    }

    @Override
    public void submit() {
    }
}
