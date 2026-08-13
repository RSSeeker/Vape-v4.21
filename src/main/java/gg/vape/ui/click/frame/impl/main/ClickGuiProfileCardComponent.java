package gg.vape.ui.click.frame.impl.main;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.IconShape;
import gg.vape.ui.click.component.ShapeIconComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileCardActionState;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.StringUtils;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ClickGuiProfileCardComponent
extends InteractiveComponent {
    private static final Color ACTIVE_MARKER_COLOR;
    private double truncatedNameWidth;
    private final ColorAnimation settingsHoverAnimation;
    private final DoubleAnimation dimAnimation;
    private boolean selected;
    private final ShapeIconComponent activeBadge;
    private String activeBadgeText;
    private boolean active;
    private String displayName;
    private final IconGlyphComponent settingsIcon;
    private String truncatedName;
    private boolean settingsHovered;
    @Nullable
    private Runnable settingsAction;
    private final RectData settingsHitbox = new RectData(0.0, 0.0, 0.0, 0.0);
    private final BindableInputComponent bindInput;
    private Profile profile;
    private final ColorAnimation hoverAnimation;
    private boolean badgeVisible;
    private boolean dimmed;

    public void setActiveBadgeText(String string) {
        this.activeBadgeText = string == null ? "" : StringUtils.l(string).trim();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (guiMouseEvent.getAction() == MouseButton.RIGHT_CLICK && !this.getClickCooldown().isCoolingDown() && this.settingsAction != null) {
            this.settingsAction.run();
            this.getClickCooldown().setActive(true);
            return;
        }
        if (guiMouseEvent.getAction() == MouseButton.LEFT_CLICK && !this.getClickCooldown().isCoolingDown()) {
            if (this.settingsHovered && this.settingsAction != null) {
                this.settingsAction.run();
                this.getClickCooldown().setActive(true);
                return;
            }
            if (this.bindInput.V$src$Z$1xhop3l() && this.bindInput.i(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                return;
            }
            if (this.profile != null) {
                Vape.INSTANCE.getProfilesManager().switchProfile(this.profile);
                this.getClickCooldown().setActive(true);
            }
        }
        super.g(guiMouseEvent);
    }

    private Color applyDimmedState(Color color) {
        return ProfileCardActionState.t(color, this.dimAnimation, this.dimmed);
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean bl) {
        this.active = bl;
    }


    public String getDisplayName() {
        return this.displayName;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isDimmed() {
        return this.dimmed;
    }

    private void updateTruncatedName(SmoothFontRenderer smoothFontRenderer, double d) {
        String string;
        String string2 = string = this.displayName == null ? "" : StringUtils.l(this.displayName).trim();
        if (string.isEmpty() || d <= 0.0) {
            this.truncatedName = "";
            this.truncatedNameWidth = 0.0;
            return;
        }
        double d2 = smoothFontRenderer.N(string);
        if (d2 <= d) {
            this.truncatedName = string;
            this.truncatedNameWidth = d2;
            return;
        }
        String string3 = "...";
        double d3 = smoothFontRenderer.N(string3);
        if (d3 > d) {
            this.truncatedName = "";
            this.truncatedNameWidth = 0.0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string.charAt(i));
            double d4 = smoothFontRenderer.N(stringBuilder.toString()) + d3;
            if (!(d4 > d)) continue;
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            break;
        }
        this.truncatedName = stringBuilder.append(string3).toString();
        this.truncatedNameWidth = smoothFontRenderer.N(this.truncatedName);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setDimmed(boolean bl) {
        this.dimmed = bl;
    }

    public void setSelected(boolean bl) {
        this.selected = bl;
    }

    public ClickGuiProfileCardComponent(Profile profile) {
        this.activeBadge = new ShapeIconComponent(IconShape.ROUNDED_RECT, "ACTIVE", 10.0, 12.0, 3.0, 2.0f, Color.BLACK, Color.WHITE, 0.65);
        this.getClass();
        this.hoverAnimation = new ColorAnimation(0.15, ClickGuiProfileCardComponent.J.t, ClickGuiProfileCardComponent.J.z);
        this.getClass();
        this.settingsHoverAnimation = new ColorAnimation(0.15, ClickGuiProfileCardComponent.J.t, ClickGuiProfileCardComponent.J.E);
        this.getClass();
        this.dimAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
        this.truncatedName = "";
        this.badgeVisible = true;
        this.activeBadgeText = "ACTIVE";
        this.profile = profile;
        this.displayName = profile.getName();
        this.setPropagateMouseEvents(true);
        this.settingsIcon = new IconGlyphComponent("settingdots", 6.0f, 6.0f, ClickGuiProfileCardComponent.J.W);
        this.settingsIcon.setSnapToPixels(true);
        this.bindInput = new BindableInputComponent(profile, ClickGuiProfileCardComponent.J.A);
        this.bindInput.setVisible(false);
        this.bindInput.Y(10.0);
        this.Y(22.0);
        this.setShowDisabledOverlay(false);
        this.addChildren(this.settingsIcon, this.activeBadge);
        this.addChildren(this.bindInput);
    }

    @Override
    public void H() {
        double d;
        boolean bl;
        Color color;
        double d2 = this.G$src$D$1b2f02a();
        double d3 = this.n();
        double d4 = this.A();
        double d5 = this.L();
        double d6 = d3 + d5 / 2.0;
        MousePosition mousePosition = RenderUtils.h();
        this.dimAnimation.u(this.dimmed && !this.selected);
        Color color2 = this.active ? this.applyDimmedState(J.z()) : null;
        Color color3 = ClickGuiProfileCardComponent.J.m;
        if (this.selected) {
            color3 = ClickGuiProfileCardComponent.J.H;
        } else if (this.active) {
            color3 = ClickGuiProfileCardComponent.J.R;
        }
        GuiRenderPrimitives.B(d2, d3, d4, d5, this.applyDimmedState(color3), 3.0f);
        if (this.active) {
            if (color2 == null) {
                throw new AssertionError();
            }
            color = ColorUtil.withAlpha(color2, 51);
            GuiRenderPrimitives.P(d2, d3, d4, d5 - 0.5, this.applyDimmedState(color), 3.0f, 0.75f, 1.0f);
        }
        if ((color = this.hoverAnimation.getInterpolatedColor()).getAlpha() > 0) {
            GuiRenderPrimitives.B(d2, d3, d4, d5, this.applyDimmedState(color), 3.0f);
        }
        double d7 = d2 + 8.0;
        double d8 = d6 - 3.0 - 0.5;
        GuiRenderPrimitives.m((float)d7, (float)d8, 6.0f, 1.0f, 0.5f, this.applyDimmedState(this.active ? color2 : ACTIVE_MARKER_COLOR));
        if (this.active) {
            this.activeBadge.setBackgroundColor(ColorUtil.withAlpha(color2, 30));
            Color color4 = J.z();
            double d9 = Math.min(1.0, Math.max(0.0, this.dimAnimation.getInterpolatedValue()));
            Color color5 = color4;
            if (this.dimmed || d9 > 0.0) {
                float f = (float)(1.0 - 0.4 * d9);
                int n = Math.max(0, (int)((float)color4.getRed() * f));
                int n2 = Math.max(0, (int)((float)color4.getGreen() * f));
                int n3 = Math.max(0, (int)((float)color4.getBlue() * f));
                color5 = new Color(n, n2, n3, color4.getAlpha());
            }
            this.activeBadge.setForegroundColor(color5);
            double d10 = 2.4;
            double d11 = d7 + (6.0 - d10) / 2.0;
            double d12 = d6 - d10 / 2.0 - 0.5;
            GuiRenderPrimitives.V((float)d11, (float)d12, (float)d10, 0.5, this.applyDimmedState(color2));
        }
        double d13 = d2 + d4 - 6.0 - 6.0;
        double d14 = d6 - 3.0;
        this.settingsHitbox.M(d13 - 6.0);
        this.settingsHitbox.O(d14 - 8.0);
        this.settingsHitbox.A(16.0);
        this.settingsHitbox.U(22.0);
        this.settingsHovered = this.settingsHitbox.Z(mousePosition);
        boolean bl2 = this.w$src$Z$e457mb() && !this.settingsHovered;
        this.hoverAnimation.u(bl2 && !this.selected);
        this.settingsHoverAnimation.u(this.settingsHovered);
        Color color6 = this.settingsHoverAnimation.getInterpolatedColor();
        if (color6.getAlpha() > 0) {
            GuiRenderPrimitives.p(this.settingsHitbox.o(), this.settingsHitbox.W(), this.settingsHitbox.e(), this.settingsHitbox.R(), this.applyDimmedState(color6), false, 2.0f, 1.0f, 0.0f, ClickGuiProfileCardComponent.J.u, 6);
        }
        Color color7 = ClickGuiProfileCardComponent.J.W;
        if (this.active || this.selected) {
            color7 = Color.WHITE;
        } else if (this.settingsHovered) {
            color7 = ClickGuiProfileCardComponent.J.f;
        }
        this.settingsIcon.setColor(this.applyDimmedState(color7));
        this.settingsIcon.K(d13);
        this.settingsIcon.S(d14);
        this.settingsIcon.c();
        double d16 = 0.0;
        boolean bl3 = this.profile.hasValidBinding();
        boolean bl4 = this.bindInput.getCaptureTask().isCapturing();
        boolean bl5 = bl = bl3 || bl4 || this.w$src$Z$e457mb();
        if (bl) {
            d16 = d13 - 2.0 - this.bindInput.A() - 8.0;
            d = d6 - 5.0;
            this.bindInput.K(d16);
            this.bindInput.S(d);
            this.bindInput.Y(10.0);
            this.bindInput.setVisible(true);
            if (this.dimmed) {
                this.bindInput.setActiveOverride(false);
            } else {
                this.bindInput.setActiveOverride(null);
            }
        } else {
            this.bindInput.setVisible(false);
        }
        d = d7 + 6.0 + 6.0;
        double d17 = (this.bindInput.V$src$Z$1xhop3l() ? d16 : d13) - 8.0;
        double d18 = Math.max(0.0, d17 - d);
        boolean bl6 = this.shouldShowActiveBadge();
        if (bl6) {
            double d19 = 0.0;
            this.activeBadge.setText(this.activeBadgeText);
            this.activeBadge.setVisible(true);
            d19 = this.activeBadge.getRequiredWidth();
            d18 = Math.max(0.0, d18 - d19 - 4.0);
            SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.75);
            this.updateTruncatedName(smoothFontRenderer, d18);
            double d20 = d6 - smoothFontRenderer.d("A") / 2.0;
            Color color8 = ClickGuiProfileCardComponent.J.A;
            if (this.active || this.selected) {
                color8 = Color.WHITE;
            }
            double d21 = Math.min(1.0, Math.max(0.0, this.dimAnimation.getInterpolatedValue()));
            if (this.dimmed || d21 > 0.0) {
                float f = (float)(1.0 - 0.6 * d21);
                int n = Math.max(0, (int)((float)color8.getRed() * f));
                int n4 = Math.max(0, (int)((float)color8.getGreen() * f));
                int n5 = Math.max(0, (int)((float)color8.getBlue() * f));
                color8 = new Color(n, n4, n5, color8.getAlpha());
            }
            smoothFontRenderer.d(this.truncatedName, d, d20 - 0.5, color8);
            double d22 = d + this.truncatedNameWidth + 4.0 + 1.0;
            double d23 = d6 - 5.0;
            this.activeBadge.K(d22);
            this.activeBadge.S(d23);
            this.activeBadge.o(20.0);
            this.activeBadge.Y(10.0);
            this.activeBadge.getLabel().setFontScale(0.5);
            this.activeBadge.c();
            return;
        }
        double d24 = 0.0;
        this.activeBadge.setVisible(false);
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.75);
        this.updateTruncatedName(smoothFontRenderer, d18);
        double d25 = d6 - smoothFontRenderer.d("A") / 2.0;
        Color color9 = ClickGuiProfileCardComponent.J.A;
        if (this.active || this.selected) {
            color9 = Color.WHITE;
        }
        double d26 = Math.min(1.0, Math.max(0.0, this.dimAnimation.getInterpolatedValue()));
        if (this.dimmed || d26 > 0.0) {
            float f = (float)(1.0 - 0.6 * d26);
            int n = Math.max(0, (int)((float)color9.getRed() * f));
            int n6 = Math.max(0, (int)((float)color9.getGreen() * f));
            int n7 = Math.max(0, (int)((float)color9.getBlue() * f));
            color9 = new Color(n, n6, n7, color9.getAlpha());
        }
        smoothFontRenderer.d(this.truncatedName, d, d25 - 0.5, color9);
    }

    public boolean shouldShowActiveBadge() {
        return this.badgeVisible && this.active && !this.activeBadgeText.isEmpty();
    }

    public void setSettingsAction(@Nullable Runnable runnable) {
        this.settingsAction = runnable;
    }

    public void setDisplayName(String string) {
        this.displayName = string;
    }

    static {
        ACTIVE_MARKER_COLOR = new Color(62, 61, 62);
    }

    public void setBadgeVisible(boolean bl) {
        this.badgeVisible = bl;
    }
}

