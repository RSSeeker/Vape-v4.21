package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroInputComponent;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;

class FrameMacrosAddMacroClickHandler
implements GuiClickListener {
    final FrameMacrosAddMacroInputComponent P;
    final FrameMacros D;


    @Override
    public void onPrimaryClick() {
        if (FrameMacrosAddMacroInputComponent.p$src$Z$xrofzd(this.P)) {
            this.P.submit();
            if (FrameMacrosAddMacroInputComponent.j(this.P) != null) {
                Macro macro = Macro.create(FrameMacrosAddMacroInputComponent.j(this.P));
                if (Vape.INSTANCE.getMacrosManager().getMacro(FrameMacrosAddMacroInputComponent.j(this.P)) != null) {
                    this.P.setText("");
                    ClientSettings.activeComponent = null;
                    return;
                }
                ClientSettings.activeComponent = null;
                FrameMacrosAddMacroInputComponent.w(this.P, new FrameMacrosEditor(this.D, macro));
                FrameMacrosAddMacroInputComponent.p(this.P).N$src$V$13y6z98();
                this.P.addChildren(FrameMacrosAddMacroInputComponent.p(this.P));
            }
            this.P.setText("");
        }
    }

    FrameMacrosAddMacroClickHandler(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent, FrameMacros frameMacros) {
        this.P = frameMacrosAddMacroInputComponent;
        this.D = frameMacros;
    }
}

