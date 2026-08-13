package gg.vape.ui.click.frame.impl.target;

import gg.vape.Vape;
import gg.vape.ui.click.frame.impl.target.TargetInfoStatStripComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class TargetInfoResettingCombatStatStripComponent
extends TargetInfoStatStripComponent {
    private static final String ICON = "combo_display";
    private int combo;

    public TargetInfoResettingCombatStatStripComponent() {
        super(24, 10);
    }

    public void decrementCombo() {
        this.combo = this.combo <= 0 ? --this.combo : 0;
    }


    @Override
    public void H() {
        super.H();
        Color color = this.combo > 0 ? TargetInfoResettingCombatStatStripComponent.J.B : (this.combo < 0 ? TargetInfoResettingCombatStatStripComponent.J.d : TargetInfoResettingCombatStatStripComponent.J.A);
        String string = String.valueOf(Math.abs(this.combo));
        Color color2 = this.frame != null ? this.frame.applyDefaultEditorAlpha(color) : color;
        ImageRenderer.drawImage(color2, (float)this.G$src$D$1b2f02a() + 4.0f, (float)this.n() + 2.0f, ICON, 6.0f, 6.0f, false);
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().K(0.9f, true);
        smoothFontRenderer.d(string, (double)((float)this.G$src$D$1b2f02a()) + this.A() - smoothFontRenderer.N(string) - 5.0, (float)this.n() + 1.0f, color2);
    }

    public void incrementCombo() {
        this.combo = this.combo >= 0 ? ++this.combo : 0;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }
}

