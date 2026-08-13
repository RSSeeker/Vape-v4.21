package gg.vape.ui.click.frame.impl.target;

import gg.vape.Vape;
import gg.vape.ui.click.frame.impl.target.TargetInfoStatStripComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class TargetInfoPositiveStatStripComponent
extends TargetInfoStatStripComponent {
    private int comparisonValue;

    @Override
    public void H() {
        String string;
        super.H();
        Color color = this.comparisonValue > 0 ? TargetInfoPositiveStatStripComponent.J.B : (this.comparisonValue < 0 ? TargetInfoPositiveStatStripComponent.J.d : TargetInfoPositiveStatStripComponent.J.A);
        String string2 = string = this.comparisonValue >= 0 ? "+" + String.valueOf(this.comparisonValue) : String.valueOf(this.comparisonValue);
        if (this.comparisonValue >= 9) {
            string = "9+";
        }
        Color color2 = this.frame != null ? this.frame.applyDefaultEditorAlpha(color) : color;
        ImageRenderer.drawImage(color2, (float)this.G$src$D$1b2f02a() + 5.0f, (float)this.n() + 2.0f, "sword_header", 6.0f, 6.0f, false);
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().K(0.9f, true);
        smoothFontRenderer.d(string, (double)((float)this.G$src$D$1b2f02a()) + this.A() - smoothFontRenderer.N(string) - 5.0, (float)this.n() + 1.0f, color2);
    }

    public void decrement() {
        --this.comparisonValue;
    }

    public void setComparisonValue(int comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    public TargetInfoPositiveStatStripComponent() {
        super(24, 10);
    }

    public void increment() {
        ++this.comparisonValue;
    }

}

