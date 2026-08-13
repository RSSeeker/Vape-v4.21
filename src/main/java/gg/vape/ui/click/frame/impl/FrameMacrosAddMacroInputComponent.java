package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroClickHandler;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroKeyTypedListener;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;

public class FrameMacrosAddMacroInputComponent
extends TextInputComponentBase {
    private FrameMacrosEditor Xa;
    private String XX;
    private boolean X1 = false;

    static boolean p$src$Z$xrofzd(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent) {
        return frameMacrosAddMacroInputComponent.X1;
    }

    public FrameMacrosAddMacroInputComponent(FrameMacros frameMacros) {
        super("");
        this.actionButton.setIconResource("newnext");
        this.addKeyTypedListener(new FrameMacrosAddMacroKeyTypedListener(this, frameMacros));
        this.actionButton.addClickListener(new FrameMacrosAddMacroClickHandler(this, frameMacros));
    }

    @Override
    public void submit() {
        if (!this.X1) {
            if (!this.hasNonBlankText()) {
                this.setText("");
                return;
            }
            this.XX = this.getText();
        }
        this.X1 = !this.X1;
        this.actionButtonColor = this.X1 ? FrameMacrosAddMacroInputComponent.J.K : null;
    }

    static String j(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent) {
        return frameMacrosAddMacroInputComponent.XX;
    }

    static FrameMacrosEditor p(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent) {
        return frameMacrosAddMacroInputComponent.Xa;
    }


    @Override
    public double C() {
        return this.Xa != null ? this.Xa.L() : 20.0;
    }

    static FrameMacrosEditor w(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent, FrameMacrosEditor frameMacrosEditor) {
        frameMacrosAddMacroInputComponent.Xa = frameMacrosEditor;
        return frameMacrosAddMacroInputComponent.Xa;
    }

    @Override
    public void H() {
        if (this.Xa != null) {
            if (!this.Xa.V$src$Z$1xhop3l()) {
                this.Xa = null;
                this.getParentFrameComponent().l$src$V$1mibm4x();
            } else {
                this.actionButton.setVisible(false);
                this.Xa.K(this.G$src$D$1b2f02a());
                this.Xa.S(this.n());
                return;
            }
        }
        this.actionButton.setVisible(true);
        if (this.X1) {
            this.setText("");
            this.setBackgroundColor(FrameMacrosAddMacroInputComponent.J.m);
            this.setPlaceholderText("Press a key to bind");
            this.actionButton.setIconResource("newbind");
            this.setInputEnabled(false);
        } else {
            this.setBackgroundColor(FrameMacrosAddMacroInputComponent.J.r);
            this.setPlaceholderText("Type item name");
            this.actionButton.setIconResource("newnext");
            this.setInputEnabled(true);
        }
        super.H();
    }

    @Override
    public double x() {
        return 110.0;
    }
}
