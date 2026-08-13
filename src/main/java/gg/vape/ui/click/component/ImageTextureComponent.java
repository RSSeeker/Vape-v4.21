package gg.vape.ui.click.component;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ImageTextureComponent
extends GuiComponent {
    private float inset;
    @Nullable
    private Color tint;
    private GlImageTexture texture;
    private boolean showBorder;

    public GlImageTexture getTexture() {
        return this.texture;
    }

    public void setTint(@Nullable Color tint) {
        this.tint = tint;
    }

    public void setTexture(GlImageTexture texture) {
        this.texture = texture;
    }

    @Override
    public void H() {
        this.texture.bind();
        if (this.showBorder) {
            double borderExpansion = 4.0;
            GuiRenderPrimitives.V((double)((float)this.G$src$D$1b2f02a() + this.inset) - borderExpansion / 2.0, (double)((float)this.n() + this.inset) - borderExpansion / 2.0, (double)((float)this.L() - this.inset) + borderExpansion, 1.0, ImageTextureComponent.J.l);
        }
        GuiRenderPrimitives.u((float)this.G$src$D$1b2f02a() + this.inset, (float)this.n() + this.inset, (float)this.L() - this.inset, 1.0f, Color.WHITE, this.texture);
    }

    @Override
    public double C() {
        return 8.0;
    }

    @Override
    public double x() {
        return 8.0;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    @Nullable
    public Color getTint() {
        return this.tint;
    }

    public float getInset() {
        return this.inset;
    }

    public void setInset(float inset) {
        this.inset = inset;
    }
}
