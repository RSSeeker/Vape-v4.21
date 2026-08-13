package gg.vape.ui.click.frame.impl.quickactions;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionRowClickListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;

public class QuickActionRowComponent
extends BooleanToggleComponent {
    private String iconName;
    private Class targetFrameClass;
    private int iconSize;


    @Override
    public double C() {
        return 20.0;
    }

    @Override
    protected void renderLabel() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        double textHeight = fontRenderer.d(this.label);
        double textY = this.n() + this.L() / 2.0 - textHeight / 2.0;
        GuiRenderPrimitives.F(this.iconName, this.G$src$D$1b2f02a() + 5.0 + 4.0, this.n() + this.L() / 2.0, (double)this.iconSize, this.iconSize, this.labelColor);
        fontRenderer.d(this.label, this.G$src$D$1b2f02a() + (double)this.iconSize + 10.0, textY, this.labelColor);
    }

    public QuickActionRowComponent(String label, String iconName, double fontScale, int iconSize) {
        super(label, fontScale);
        this.iconName = iconName;
        this.iconSize = iconSize;
        this.setBrightenOnHover(true);
    }

    public void setTargetFrameClass(Class targetFrameClass) {
        this.targetFrameClass = targetFrameClass;
        this.addMouseListener(new QuickActionRowClickListener(this, targetFrameClass));
    }

    @Override
    protected void synchronizeValueState() {
        if (this.targetFrameClass != null && ((GuiComponent)ClientSettings.getFrame(this.targetFrameClass)).V$src$Z$1xhop3l() != this.isOn() && !this.isAnimating()) {
            this.toggle();
        }
        super.synchronizeValueState();
    }
}

