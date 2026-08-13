package gg.vape.ui.click.component;

import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class AnimatedTextButtonComponent
extends InteractiveComponent {
    private final DoubleAnimation arrowAnimation;
    private final double fontScale;
    private static final String EXPAND_ARROW_RESOURCE = "expandarrow";
    private final float arrowSize;
    private final Color idleArrowColor;
    private boolean useAlternateFont;
    private boolean pressed;
    private final String text;
    private boolean expanded;

    private void syncArrowAnimationDirection() {
        if (this.expanded) {
            this.arrowAnimation.C();
        } else if (this.arrowAnimation.I$src$Z$c48gtw()) {
            this.arrowAnimation.O();
        }
    }

    public DoubleAnimation getArrowAnimation() {
        return this.arrowAnimation;
    }

    @Override
    public void F() {
        this.pressed = true;
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public double C() {
        return 18.0;
    }

    public void setUseAlternateFont(boolean useAlternateFont) {
        this.useAlternateFont = useAlternateFont;
    }

    @Override
    public void I() {
    }

    public AnimatedTextButtonComponent(String text, double fontScale) {
        this(text, fontScale, false);
    }

    public AnimatedTextButtonComponent(String text) {
        this(text, 0.9);
    }

    public boolean isUseAlternateFont() {
        return this.useAlternateFont;
    }

    public void setExpanded(boolean expanded) {
        boolean changed = this.expanded != expanded;
        this.expanded = expanded;
        if (changed) {
            if (this.arrowAnimation.l()) {
                this.syncArrowAnimationDirection();
            } else {
                this.arrowAnimation.J();
            }
        }
    }

    public AnimatedTextButtonComponent(String text, double fontScale, boolean expanded) {
        this.getClass();
        this.arrowAnimation = new DoubleAnimation(0.15, 0.0, 3.0);
        this.arrowSize = 4.0f;
        this.idleArrowColor = AnimatedTextButtonComponent.J.W;
        this.text = text;
        this.fontScale = fontScale;
        this.expanded = expanded;
        this.addClickListener(this::toggleExpanded);
        this.syncArrowAnimationDirection();
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    private void toggleExpanded() {
        this.setExpanded(!this.expanded);
    }


    @Override
    public void u() {
        if (this.pressed && !this.w$src$Z$e457mb()) {
            this.pressed = false;
        }
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.expanded && this.useAlternateFont ? this.getAlternateFontRenderer(this.fontScale) : this.getFontRenderer(this.fontScale);
        double textHeight = fontRenderer.d(this.text);
        double textY = this.n() + this.L() / 2.0 - textHeight / 2.0;
        double arrowY = this.n() + this.L() / 2.0 - (double)(this.arrowSize / 2.0f);
        Color backgroundColor = AnimatedTextButtonComponent.J.m;
        Color textColor = AnimatedTextButtonComponent.J.Z;
        if (this.pressed) {
            backgroundColor = AnimatedTextButtonComponent.J.a;
            textColor = AnimatedTextButtonComponent.J.A;
        }
        if (this.expanded) {
            backgroundColor = AnimatedTextButtonComponent.J.a;
            textColor = AnimatedTextButtonComponent.J.A;
            if (this.pressed) {
                textColor = ColorUtil.offsetRgb(textColor, 30.0);
            }
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A() - 2.0, this.L() - 2.5, backgroundColor);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n() + 1.5, this.A(), this.L() - 3.0, backgroundColor);
        Color arrowColor = this.expanded || this.pressed ? textColor : this.idleArrowColor;
        float rightEdge = (float)this.G$src$D$1b2f02a() + (float)this.A();
        this.getClass();
        ImageRenderer.drawImage(arrowColor, rightEdge - 5.0f - 5.0f + this.arrowAnimation.getInterpolatedValue().floatValue(), (float)arrowY, EXPAND_ARROW_RESOURCE, this.arrowSize, this.arrowSize, false);
        double leftEdge = this.G$src$D$1b2f02a();
        this.getClass();
        fontRenderer.d(this.text, leftEdge + (double)(5.0f * 2.0f), textY, textColor);
    }
}
