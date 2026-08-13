package gg.vape.ui.click.component;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.IconShape;
import gg.vape.ui.click.component.ShapeIconComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class FriendModuleInteractiveComponent
extends InteractiveComponent {
    private Color normalContentColor;
    private final ColorAnimation selectionBackgroundAnimation;
    private static final double BADGE_TEXT_SCALE = 0.65;
    private Color normalPrimaryColor;
    private Color badgeColor;
    private final double trailingIconHeight;
    private final SimpleTextLabelComponent labelComponent;
    private static final double BADGE_SIZE = 8.0;
    private static final float CORNER_RADIUS = 3.0f;
    @Nullable
    private final Supplier<Integer> countSupplier;
    private static final double TRAILING_ICON_HEIGHT = 3.0;
    private Color hoverPrimaryColor;
    private static final double LABEL_SCALE = 0.75;
    private static final String SELECTED_SUPPLIER_NAME = "selectedSupplier";
    private Color hoverContentColor;
    private final ColorAnimation hoverBackgroundAnimation;
    private static final double COMPONENT_HEIGHT = 18.0;
    private final IconGlyphComponent trailingIcon;
    private static final double ICON_SIZE = 6.0;
    private static final double HORIZONTAL_PADDING = 6.0;
    private Color selectedPrimaryColor;
    private final Supplier<Boolean> selectedSupplier;
    private static final double ITEM_GAP = 6.0;
    private Color selectedContentColor;
    private final ShapeIconComponent countBadge;
    private final IconGlyphComponent leadingIcon;
    private Color selectedBadgeColor;

    public IconGlyphComponent getTrailingIcon() {
        return this.trailingIcon;
    }

    public SimpleTextLabelComponent getLabelComponent() {
        return this.labelComponent;
    }

    public FriendModuleInteractiveComponent(String label, @Nullable String leadingIconResource, Supplier<Boolean> selectedSupplier, @Nullable Supplier<Integer> countSupplier, @Nullable String trailingIconResource) {
        this.normalPrimaryColor = this.normalContentColor = new Color(115, 113, 115);
        this.hoverPrimaryColor = this.hoverContentColor = new Color(209, 209, 209);
        this.selectedPrimaryColor = this.selectedContentColor = Color.WHITE;
        this.badgeColor = Color.WHITE;
        this.selectedBadgeColor = Color.WHITE;
        this.getClass();
        this.hoverBackgroundAnimation = new ColorAnimation(0.15, FriendModuleInteractiveComponent.J.t, FriendModuleInteractiveComponent.J.E);
        this.getClass();
        this.selectionBackgroundAnimation = new ColorAnimation(0.15, new Color(34, 33, 34, 0), new Color(34, 33, 34));
        this.selectedSupplier = Objects.requireNonNull(selectedSupplier, SELECTED_SUPPLIER_NAME);
        this.countSupplier = countSupplier;
        this.Y(COMPONENT_HEIGHT);
        this.setShowDisabledOverlay(false);
        this.setPropagateMouseEvents(true);
        this.leadingIcon = new IconGlyphComponent(leadingIconResource != null ? leadingIconResource : "", (float)ICON_SIZE, (float)ICON_SIZE, this.normalContentColor);
        if (leadingIconResource != null) {
            this.leadingIcon.setSnapToPixels(true);
            this.addChildren(this.leadingIcon);
        } else {
            this.leadingIcon.setVisible(false);
        }
        this.labelComponent = new SimpleTextLabelComponent(label, LABEL_SCALE, this.normalContentColor);
        this.labelComponent.setOffsetX(0.0f);
        this.labelComponent.setOffsetY(0.0f);
        this.labelComponent.setExtraHeight(0);
        this.addChildren(this.labelComponent);
        this.countBadge = new ShapeIconComponent(IconShape.CIRCLE, "0", BADGE_SIZE, 0.0, 0.0, CORNER_RADIUS, FriendModuleInteractiveComponent.J.d, Color.WHITE, BADGE_TEXT_SCALE);
        if (countSupplier != null) {
            this.countBadge.setCountSupplier(countSupplier);
            this.countBadge.setVisible(false);
            this.addChildren(this.countBadge);
        } else {
            this.countBadge.setVisible(false);
        }
        this.trailingIcon = new IconGlyphComponent(trailingIconResource, 1.5f, (float)TRAILING_ICON_HEIGHT, this.normalContentColor);
        if (trailingIconResource != null) {
            this.trailingIcon.setSnapToPixels(true);
            this.addChildren(this.trailingIcon);
            this.trailingIconHeight = TRAILING_ICON_HEIGHT;
        } else {
            this.trailingIcon.setVisible(false);
            this.trailingIconHeight = 0.0;
        }
    }

    public void setSelectedBadgeColor(Color selectedBadgeColor) {
        this.selectedBadgeColor = selectedBadgeColor;
    }

    public void setHoverContentColor(Color hoverContentColor) {
        this.hoverContentColor = hoverContentColor;
    }

    public void setLabel(String label) {
        this.labelComponent.setText(label);
    }

    public Color getNormalPrimaryColor() {
        return this.normalPrimaryColor;
    }

    @Override
    public void H() {
        boolean selected = Boolean.TRUE.equals(this.selectedSupplier.get());
        boolean hovered = this.w$src$Z$e457mb();
        this.hoverBackgroundAnimation.u(hovered && !selected);
        this.selectionBackgroundAnimation.u(selected);
        double componentX = this.G$src$D$1b2f02a();
        double componentY = this.n();
        double componentWidth = this.A();
        double componentHeight = this.L();
        if (selected) {
            GuiRenderPrimitives.B(componentX, componentY, componentWidth, componentHeight, this.selectionBackgroundAnimation.getInterpolatedColor(), CORNER_RADIUS);
        } else if (hovered) {
            GuiRenderPrimitives.B(componentX, componentY, componentWidth, componentHeight, this.hoverBackgroundAnimation.getInterpolatedColor(), CORNER_RADIUS);
        }
        Color primaryColor = this.normalPrimaryColor;
        Color contentColor = this.normalContentColor;
        if (selected) {
            primaryColor = this.selectedPrimaryColor;
            contentColor = this.selectedContentColor;
        } else if (hovered) {
            primaryColor = this.hoverPrimaryColor;
            contentColor = this.hoverContentColor;
        }
        double contentLeftX = componentX + HORIZONTAL_PADDING;
        if (this.leadingIcon.V$src$Z$1xhop3l()) {
            this.leadingIcon.setColor(primaryColor);
            this.leadingIcon.K(contentLeftX);
            this.leadingIcon.S(componentY + (componentHeight - ICON_SIZE) / 2.0);
            contentLeftX += ICON_SIZE + ITEM_GAP;
        }
        double contentRightX = componentX + componentWidth - HORIZONTAL_PADDING;
        if (this.trailingIcon.V$src$Z$1xhop3l()) {
            double trailingIconX = contentRightX - this.trailingIconHeight;
            this.trailingIcon.setColor(contentColor);
            this.trailingIcon.K(trailingIconX);
            this.trailingIcon.S(componentY + (componentHeight - this.trailingIconHeight) / 2.0);
            contentRightX = trailingIconX - ITEM_GAP;
        }
        if (this.countSupplier != null) {
            Integer suppliedCount = this.countSupplier.get();
            int count = Math.max(0, suppliedCount != null ? suppliedCount : 0);
            boolean showCountBadge = this.shouldShowCountBadge() && count > 0;
            this.countBadge.setVisible(showCountBadge);
            if (showCountBadge) {
                Color resolvedBadgeColor = selected ? this.selectedBadgeColor : this.badgeColor;
                double badgeWidth = this.countBadge.getShape() == IconShape.CIRCLE ? BADGE_SIZE : 12.0;
                double badgeX = contentRightX - badgeWidth;
                double badgeY = componentY + (componentHeight - BADGE_SIZE) / 2.0;
                contentRightX = badgeX - ITEM_GAP;
                this.countBadge.K(badgeX);
                this.countBadge.S(badgeY);
                this.countBadge.o(badgeWidth);
                this.countBadge.Y(BADGE_SIZE);
                this.countBadge.setForegroundColor(resolvedBadgeColor);
                this.countBadge.c();
            }
        }
        double labelWidth = Math.max(0.0, contentRightX - contentLeftX);
        this.labelComponent.setTextColor(contentColor);
        this.labelComponent.o(labelWidth);
        this.labelComponent.Y(componentHeight);
        this.labelComponent.K(contentLeftX);
        this.labelComponent.S(componentY);
    }

    public void setNormalPrimaryColor(Color normalPrimaryColor) {
        this.normalPrimaryColor = normalPrimaryColor;
    }


    public void setNormalContentColor(Color normalContentColor) {
        this.normalContentColor = normalContentColor;
    }

    public Supplier<Boolean> getSelectedSupplier() {
        return this.selectedSupplier;
    }

    public ColorAnimation getSelectionBackgroundAnimation() {
        return this.selectionBackgroundAnimation;
    }

    @Nullable
    public Supplier<Integer> getCountSupplier() {
        return this.countSupplier;
    }

    public void setSelectedContentColor(Color selectedContentColor) {
        this.selectedContentColor = selectedContentColor;
    }

    public Color getBadgeColor() {
        return this.badgeColor;
    }

    private boolean shouldShowCountBadge() {
        if (ClientSettings.INSTANCE == null || ClientSettings.INSTANCE.showEnabledCount == null) {
            return true;
        }
        return ClientSettings.INSTANCE.showEnabledCount.getEffectiveValue();
    }

    public double getTrailingIconHeight() {
        return this.trailingIconHeight;
    }

    public Color getNormalContentColor() {
        return this.normalContentColor;
    }

    public void setSelectedPrimaryColor(Color selectedPrimaryColor) {
        this.selectedPrimaryColor = selectedPrimaryColor;
    }

    public Color getSelectedBadgeColor() {
        return this.selectedBadgeColor;
    }

    public void setBadgeColor(Color badgeColor) {
        this.badgeColor = badgeColor;
    }

    public Color getHoverContentColor() {
        return this.hoverContentColor;
    }

    public void setHoverPrimaryColor(Color hoverPrimaryColor) {
        this.hoverPrimaryColor = hoverPrimaryColor;
    }

    public Color getSelectedPrimaryColor() {
        return this.selectedPrimaryColor;
    }

    public ColorAnimation getHoverBackgroundAnimation() {
        return this.hoverBackgroundAnimation;
    }

    public Color getSelectedContentColor() {
        return this.selectedContentColor;
    }

    public ShapeIconComponent getCountBadge() {
        return this.countBadge;
    }

    public FriendModuleInteractiveComponent(String label, @Nullable String leadingIconResource, Supplier<Boolean> selectedSupplier) {
        this(label, leadingIconResource, selectedSupplier, null, null);
    }

    public IconGlyphComponent getLeadingIcon() {
        return this.leadingIcon;
    }

    public Color getHoverPrimaryColor() {
        return this.hoverPrimaryColor;
    }
}

