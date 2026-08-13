package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiKeyTypedListener;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroInputComponent;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;

class FrameMacrosAddMacroKeyTypedListener
implements GuiKeyTypedListener {
    final FrameMacros C;
    final FrameMacrosAddMacroInputComponent d;


    FrameMacrosAddMacroKeyTypedListener(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent, FrameMacros frameMacros) {
        this.d = frameMacrosAddMacroInputComponent;
        this.C = frameMacros;
    }

    @Override
    public void onKeyTyped(char c, int n) {
        if (FrameMacrosAddMacroInputComponent.p$src$Z$xrofzd(this.d)) {
            this.d.submit();
            if (FrameMacrosAddMacroInputComponent.j(this.d) != null) {
                Macro macro = Macro.create(FrameMacrosAddMacroInputComponent.j(this.d));
                if (Vape.INSTANCE.getMacrosManager().getMacro(FrameMacrosAddMacroInputComponent.j(this.d)) != null) {
                    this.d.setText("");
                    ClientSettings.activeComponent = null;
                    return;
                }
                ClientSettings.activeComponent = null;
                FrameMacrosAddMacroInputComponent.w(this.d, new FrameMacrosEditor(this.C, macro));
                FrameMacrosAddMacroInputComponent.p(this.d).N$src$V$13y6z98();
                this.d.addChildren(FrameMacrosAddMacroInputComponent.p(this.d));
            }
            this.d.setText("");
        }
    }
}

