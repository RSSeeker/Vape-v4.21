package gg.vape.ui.click.component;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.IconShape;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class ShapeIconComponent
extends GuiComponent {
    @Nullable
    private IconGlyphComponent icon;
    private double horizontalPadding;
    private TruncatedTextComponent label;
    private Supplier<Integer> countSupplier;
    private Integer fixedCount;
    private Color backgroundColor;
    private static final String MAX_COUNT_TEXT;
    private static int legacyState;
    private String text;
    private IconShape shape;
    private float cornerRadius;
    private double minimumWidth;
    private Color foregroundColor;
    private double fontScale;
    private double iconHeight;

    public double getIconHeight() {
        return this.iconHeight;
    }

    public Color getForegroundColor() {
        return this.foregroundColor;
    }

    static {
        ShapeIconComponent.setLegacyState(13);
        MAX_COUNT_TEXT = "9+";
    }

    public ShapeIconComponent setShape(IconShape shape) {
        this.shape = shape;
        return this;
    }

    @Override
    public double x() {
        return this.minimumWidth;
    }

    public IconShape getShape() {
        return this.shape;
    }

    public ShapeIconComponent withText(String text) {
        this.text = text == null ? "" : text;
        this.fixedCount = null;
        this.countSupplier = null;
        this.label.setText(this.text.toUpperCase());
        return this;
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public static void setLegacyState(int legacyState) {
        ShapeIconComponent.legacyState = legacyState;
    }

    public Supplier<Integer> getCountSupplier() {
        return this.countSupplier;
    }

    public static int getLegacyState() {
        return legacyState;
    }

    public ShapeIconComponent setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public double getHorizontalPadding() {
        return this.horizontalPadding;
    }

    public Integer getFixedCount() {
        return this.fixedCount;
    }

    public ShapeIconComponent setIconHeight(double iconHeight) {
        this.iconHeight = iconHeight;
        return this;
    }

    public void clearContent() {
        this.fixedCount = null;
        this.countSupplier = null;
        this.text = null;
        this.label.setText("");
    }

    public ShapeIconComponent(IconShape shape, String text, double height, double minimumWidth, double horizontalPadding, float cornerRadius, Color backgroundColor, Color foregroundColor, double fontScale) {
        this.shape = shape;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.fontScale = fontScale;
        this.setShowDisabledOverlay(false);
        this.Y(height);
        this.iconHeight = height;
        this.minimumWidth = minimumWidth;
        this.horizontalPadding = horizontalPadding;
        this.cornerRadius = cornerRadius;
        this.label = new TruncatedTextComponent(text == null ? "" : text, "", 0.0, fontScale, foregroundColor, true);
        this.label.setShadowEnabled(false);
        this.label.setMaxWidth(32767.0);
        this.addChildren(this.label);
        this.icon = null;
    }

    public ShapeIconComponent setCountSupplier(Supplier<Integer> countSupplier) {
        this.countSupplier = countSupplier;
        this.fixedCount = null;
        this.text = null;
        return this;
    }

    public ShapeIconComponent setFixedCount(Integer fixedCount) {
        this.fixedCount = fixedCount;
        this.countSupplier = null;
        this.text = null;
        return this;
    }

    @Nullable
    private String resolveDisplayText() {
        Integer count = null;
        if (this.countSupplier != null) {
            count = this.countSupplier.get();
        } else if (this.fixedCount != null) {
            count = this.fixedCount;
        }
        if (count != null) {
            if (count <= 0) {
                return "";
            }
            if (count > 9) {
                return MAX_COUNT_TEXT;
            }
            return String.valueOf(count);
        }
        if (this.text != null) {
            return this.text;
        }
        return null;
    }

    public ShapeIconComponent setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public double getRequiredWidth() {
        if (this.shape == IconShape.CIRCLE) {
            return this.iconHeight;
        }
        String displayText = this.resolveDisplayText();
        double textWidth = displayText == null || displayText.isEmpty() ? Math.ceil(this.getAlternateFontRenderer(this.fontScale).N(this.label.getText())) : Math.ceil(this.getAlternateFontRenderer(this.fontScale).N(displayText));
        return Math.max(this.minimumWidth, textWidth + this.horizontalPadding * 2.0);
    }


    @Nullable
    public IconGlyphComponent getIcon() {
        return this.icon;
    }

    public ShapeIconComponent setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        return this;
    }

    public ShapeIconComponent setHorizontalPadding(double horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
        return this;
    }

    public TruncatedTextComponent getLabel() {
        return this.label;
    }

    public static int getLegacyCompatibilityValue() {
        int legacyState = ShapeIconComponent.getLegacyState();
        return 0;
    }

    public ShapeIconComponent setMinimumWidth(double minimumWidth) {
        this.minimumWidth = minimumWidth;
        return this;
    }

    public void setIcon(@Nullable IconGlyphComponent icon) {
        if (this.icon != null) {
            this.icon.setVisible(false);
        }
        this.icon = icon;
        if (this.icon != null) {
            this.addChildren(this.icon);
        }
    }

    public void setText(String text) {
        this.text = text == null ? "" : text;
        this.fixedCount = null;
        this.countSupplier = null;
        this.label.setText(this.text == null ? "" : this.text.toUpperCase());
    }

    public ShapeIconComponent setFontScale(double fontScale) {
        this.fontScale = fontScale;
        return this;
    }

    @Override
    public void H() {
        double x = this.G$src$D$1b2f02a();
        double y = this.n();
        double width = this.A();
        double height = this.L();
        String displayText = this.resolveDisplayText();
        if (displayText != null) {
            this.label.setText(displayText.toUpperCase());
        }
        double iconSize = Math.min(width, height);
        double iconX = x + (width - iconSize) / 2.0;
        double iconY = y + (height - iconSize) / 2.0;
        if (this.shape == IconShape.CIRCLE) {
            GuiRenderPrimitives.B(x, y, width, height, this.backgroundColor, (float)(this.iconHeight / 2.0));
            if (this.icon != null) {
                this.icon.K(iconX);
                this.icon.S(iconY);
                this.icon.o(iconSize);
                this.icon.Y(iconSize);
                this.icon.c();
            } else {
                this.label.K(x);
                this.label.S(y);
                this.label.o(width);
                this.label.Y(height);
                this.label.setMaxWidth(width);
                this.label.setTextColor(this.foregroundColor != null ? this.foregroundColor : Color.WHITE);
                this.label.setCentered(true);
            }
        } else {
            GuiRenderPrimitives.B(x, y, width, height, this.backgroundColor, this.cornerRadius);
            if (this.icon != null) {
                this.icon.K(iconX);
                this.icon.S(iconY);
                this.icon.o(iconSize);
                this.icon.Y(iconSize);
                this.icon.c();
            } else {
                this.label.K(x);
                this.label.S(y);
                this.label.o(width);
                this.label.Y(height);
                this.label.setMaxWidth(Math.max(0.0, width - this.horizontalPadding * 2.0));
                if (this.foregroundColor != null) {
                    this.label.setTextColor(this.foregroundColor);
                }
                this.label.setCentered(true);
            }
        }
    }

    public ShapeIconComponent(IconShape shape, IconGlyphComponent icon, double size, Color backgroundColor) {
        this.shape = shape;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = null;
        this.fontScale = 0.0;
        this.setShowDisabledOverlay(false);
        this.Y(size);
        this.iconHeight = size;
        this.minimumWidth = size;
        this.horizontalPadding = 0.0;
        this.cornerRadius = (float)(size / 2.0);
        this.label = new TruncatedTextComponent("", "", 0.0, 0.5, Color.WHITE, true);
        this.label.setShadowEnabled(false);
        this.label.setMaxWidth(32767.0);
        this.icon = icon;
        if (this.icon != null) {
            this.addChildren(this.icon);
        }
    }

    public double getFontScale() {
        return this.fontScale;
    }

    @Override
    public Color getDisabledOverlayColor() {
        return this.backgroundColor;
    }

    public String getText() {
        return this.text;
    }
}

