package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.IconShape;
import gg.vape.ui.click.component.ShapeIconComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;
import java.util.function.Supplier;

public class ProfileListEntryBadgeComponent
extends InteractiveComponent {
    private static final Color ICON_COLOR = new Color(173, 173, 173);
    private static final Color LABEL_COLOR = new Color(209, 209, 209);
    private final String label;
    private final ColorAnimation iconColorAnimation;
    private Supplier<Integer> countSupplier;
    private final ShapeIconComponent countBadge;
    private final IconGlyphComponent icon = new IconGlyphComponent("newpublicprofiles", 6.0f, 6.0f, ICON_COLOR);
    private final ColorAnimation labelColorAnimation;

    @Override
    public void I() {
    }

    private void updatePreferredWidth() {
        SmoothFontRenderer font = this.getAlternateFontRenderer(0.625);
        double width = 9.0 + font.N(this.label.toUpperCase());
        if (this.countSupplier.get() > 0) {
            width += 11.0;
        }
        this.o(width);
    }

    @Override
    public void u() {
        this.updatePreferredWidth();
    }

    public void setBadgeCount(int count) {
        this.countSupplier = () -> count;
        this.countBadge.setCountSupplier(this.countSupplier);
        this.updatePreferredWidth();
    }

    public void setBadgeCountSupplier(Supplier<Integer> supplier) {
        this.countSupplier = supplier != null ? supplier : () -> 0;
        this.countBadge.setCountSupplier(this.countSupplier);
        this.updatePreferredWidth();
    }


    public int getBadgeCount() {
        return this.countSupplier.get();
    }

    public ProfileListEntryBadgeComponent() {
        this.label = "VIEW PUBLIC PROFILES";
        this.countBadge = new ShapeIconComponent(IconShape.CIRCLE, null, 7.0, 7.0, 0.0, 3.5f, ProfileListEntryBadgeComponent.J.d, Color.WHITE, 0.5);
        this.countSupplier = () -> 0;
        this.countBadge.setCountSupplier(this.countSupplier);
        this.iconColorAnimation = new ColorAnimation(0.15, ICON_COLOR, LABEL_COLOR);
        this.labelColorAnimation = new ColorAnimation(0.15, LABEL_COLOR, LABEL_COLOR.brighter());
        this.Y(12.0);
        this.updatePreferredWidth();
        this.setShowDisabledOverlay(false);
        this.addChildren(this.countBadge);
    }

    @Override
    public void H() {
        double x = this.G$src$D$1b2f02a();
        double centerY = this.n() + this.L() / 2.0;
        boolean hovered = this.w$src$Z$e457mb();
        this.iconColorAnimation.u(hovered);
        this.labelColorAnimation.u(hovered);
        this.icon.K(x);
        this.icon.S(centerY - 3.0);
        this.icon.setColor(this.iconColorAnimation.getInterpolatedColor());
        this.icon.c();
        SmoothFontRenderer font = this.getAlternateFontRenderer(0.625);
        String renderedLabel = this.label.toUpperCase();
        double labelX = x + 9.0;
        font.d(renderedLabel, labelX, centerY - font.d(renderedLabel) / 2.0, this.labelColorAnimation.getInterpolatedColor());
        if (this.countSupplier.get() > 0) {
            double badgeX = labelX + font.N(renderedLabel) + 4.0;
            this.countBadge.K(badgeX);
            this.countBadge.S(centerY - 3.5);
            this.countBadge.o(7.0);
            this.countBadge.Y(7.0);
            this.countBadge.setVisible(true);
            this.countBadge.c();
        } else {
            this.countBadge.setVisible(false);
        }
    }
}

