package gg.vape.ui.click.frame.impl.main;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.Animation;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ClickGuiModuleCardComponent
extends GuiComponent {
    private static final long UNSAFE_BADGE_COLOR_RGB = -4353476493014852858L;
    private static final Color CARD_SHADOW;
    private static final Color ENABLED_BACKGROUND_END;
    private static final Color FAVORITE_HIGHLIGHT;
    private final SquareIconButtonComponent removeButton;
    private final ClickGuiModuleCardDetailComponent detailComponent;
    private final ColorAnimation hoverAnimation;
    private final IconGlyphComponent settingsIcon;
    private final ColorAnimation settingsHoverAnimation;
    private long bindWarningTimestamp;
    private final ColorAnimation enabledBackgroundAnimation;
    private final RectData settingsHitbox;
    private double dragOffsetY;
    @Nullable
    private Runnable settingsAction;
    private boolean reordering;
    private final DoubleAnimation enabledToggleAnimation;
    private final ColorAnimation enabledFillAnimation;
    private final DoubleAnimation selectionOffsetAnimation;
    private boolean favoriteHighlighted;
    private final ColorAnimation selectionAnimation;
    private final Mod module;
    private int pendingOrderIndex;
    private final TruncatedTextComponent nameLabel;
    private boolean dimmed;
    private final BindableInputComponent bindInput;
    private boolean unsafeBadgeEnabled;
    private boolean favoriteControlVisible;
    private final IconGlyphComponent favoriteIcon;
    private double draggedY;
    private final RectData favoriteHitbox;
    private final IconGlyphComponent reorderIcon;
    private boolean selected;
    private Runnable reorderAction;
    private boolean dragging;
    private final DoubleAnimation dimAnimation;
    private final ColorAnimation favoriteAnimation;

    private void updateDragOrder() {
        int n;
        if (!MouseInput.isButtonDown(MouseButton.LEFT_CLICK.ordinal())) {
            List<ClickGuiModuleCardComponent> list = this.collectSiblingCards();
            this.finishDragging(list);
            if (this.pendingOrderIndex != -1) {
                Vape.INSTANCE.getModuleProfileMetadataCodec().getSelectedModules().remove(this.module);
                Vape.INSTANCE.getModuleProfileMetadataCodec().getSelectedModules().add(this.pendingOrderIndex, this.module);
            }
            this.pendingOrderIndex = -1;
            if (this.reorderAction != null) {
                this.reorderAction.run();
            }
            return;
        }
        List<ClickGuiModuleCardComponent> list = this.collectSiblingCards();
        double d = this.L();
        double d2 = this.draggedY;
        double d3 = this.draggedY + d;
        int n2 = n = list.indexOf(this);
        for (int i = 0; i < list.size(); ++i) {
            ClickGuiModuleCardComponent clickGuiModuleCardComponent = list.get(i);
            if (clickGuiModuleCardComponent == this) continue;
            double d4 = clickGuiModuleCardComponent.getPaddedY();
            double d5 = d4 + clickGuiModuleCardComponent.L() / 2.0;
            if (i < n && d2 <= d5) {
                n2 = i;
                break;
            }
            if (i <= n || !(d3 >= d5)) continue;
            n2 = i;
        }
        this.pendingOrderIndex = n2;
        if (n2 != n) {
                this.previewReorder(n2, list);
        } else {
            for (ClickGuiModuleCardComponent clickGuiModuleCardComponent : list) {
                if (clickGuiModuleCardComponent == this) continue;
                clickGuiModuleCardComponent.draggedY = Double.NaN;
            }
        }
    }

    static {
        ENABLED_BACKGROUND_END = new Color(46, 45, 47);
        CARD_SHADOW = new Color(0, 0, 0, 152);
        FAVORITE_HIGHLIGHT = new Color(236, 129, 44, 30);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean bl) {
        this.selected = bl;
    }

    private void setAnimationState(Animation<?> animation, boolean bl) {
        if (bl) {
            animation.C();
        } else {
            animation.O();
        }
    }

    public boolean isUnsafeBadgeEnabled() {
        return this.unsafeBadgeEnabled;
    }

    public ClickGuiModuleCardComponent(Mod mod, double d) {
        this.getClass();
        this.hoverAnimation = new ColorAnimation(0.15, ClickGuiModuleCardComponent.J.t, ClickGuiModuleCardComponent.J.E);
        this.getClass();
        this.settingsHoverAnimation = new ColorAnimation(0.15, ClickGuiModuleCardComponent.J.t, ClickGuiModuleCardComponent.J.E);
        this.getClass();
        this.enabledFillAnimation = new ColorAnimation(0.15 * 1.5, ClickGuiModuleCardComponent.J.m, ClickGuiModuleCardComponent.J.H);
        this.getClass();
        this.enabledBackgroundAnimation = new ColorAnimation(0.15, ClickGuiModuleCardComponent.J.H, ENABLED_BACKGROUND_END);
        this.getClass();
        this.selectionAnimation = new ColorAnimation(0.15 * 1.5, ClickGuiModuleCardComponent.J.m, ClickGuiModuleCardComponent.J.H);
        this.getClass();
        this.selectionOffsetAnimation = new DoubleAnimation(0.15, 0.0, 2.0);
        this.getClass();
        this.enabledToggleAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
        this.getClass();
        this.dimAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
        this.getClass();
        this.favoriteAnimation = new ColorAnimation(0.15, ClickGuiModuleCardComponent.J.t, FAVORITE_HIGHLIGHT);
        this.settingsHitbox = new RectData(0.0, 0.0, 0.0, 0.0);
        this.favoriteHitbox = new RectData(0.0, 0.0, 0.0, 0.0);
        this.draggedY = Double.NaN;
        this.pendingOrderIndex = -1;
        this.module = mod;
        this.Y(d);
        this.setPropagateMouseEvents(true);
        this.nameLabel = new TruncatedTextComponent(mod.getName(), 50.0, 0.75);
        this.nameLabel.setHorizontalInset(0.0);
        String string = mod.getToolTip();
        this.w(string);
        this.detailComponent = new ClickGuiModuleCardDetailComponent();
        this.detailComponent.Y(this.module.S());
        this.detailComponent.O$src$V$wbyi9r(0.7);
        this.detailComponent.setUseExplicitWidth(true);
        this.settingsIcon = new IconGlyphComponent("settingdots", 6.0f, 6.0f);
        this.settingsIcon.setColor(ClickGuiModuleCardComponent.J.W);
        this.bindInput = new BindableInputComponent(this.module.getBind(), ClickGuiModuleCardComponent.J.A);
        this.bindInput.setVisible(false);
        this.bindInput.Y(10.0);
        this.reorderIcon = new IconGlyphComponent("newrearrange", 9.0f, 9.0f, ClickGuiModuleCardComponent.J.W);
        this.reorderIcon.setVisible(false);
        this.removeButton = new SquareIconButtonComponent("newclose", 1.0);
        this.removeButton.setVisible(false);
        this.removeButton.o(10.0);
        this.removeButton.Y(10.0);
        this.addChildren(this.reorderIcon, this.removeButton, this.nameLabel, this.settingsIcon);
        this.favoriteIcon = new IconGlyphComponent("newstar", 6.0f, 6.0f, ClickGuiModuleCardComponent.J.I);
        this.addChildren(this.favoriteIcon);
        this.addChildren(this.detailComponent);
        this.addChildren(this.bindInput);
        this.initializeAnimations();
    }

    public void setUnsafeBadgeEnabled(boolean bl) {
        this.unsafeBadgeEnabled = bl;
    }

    private void updateDraggedPosition() {
        MousePosition mousePosition = RenderUtils.h();
        double d = (double)mousePosition.H - this.dragOffsetY;
        GuiComponent guiComponent = this.getCardsContainer();
        if (guiComponent instanceof FrameComponent) {
            FrameComponent frameComponent = (FrameComponent)guiComponent;
            double d2 = frameComponent.n();
            double d3 = d2 + frameComponent.d$src$D$ibccpu();
            if (frameComponent.k$src$Z$if6xeb()) {
                if ((double)mousePosition.H < d2 + this.L()) {
                    frameComponent.b(frameComponent.J$src$D$hx1pag() + 3.0);
                } else if ((double)mousePosition.H > d3 - this.L()) {
                    frameComponent.b(frameComponent.J$src$D$hx1pag() - 3.0);
                }
                d = Math.max(d2, Math.min(d, d3 - this.L()));
            } else {
                double d4 = d2 + frameComponent.L() - this.L();
                d = Math.max(d2, Math.min(d, d4));
            }
        }
        this.draggedY = d;
    }

    private void finishDragging(List<ClickGuiModuleCardComponent> list) {
        this.dragging = false;
        this.draggedY = Double.NaN;
        this.setIgnoreFrameClipping(false);
        PaddedComponent paddedComponent = this.getPaddedWrapper();
        if (paddedComponent != null) {
            paddedComponent.setIgnoreFrameClipping(false);
        }
        if (ClientSettings.activeComponent == this) {
            ClientSettings.activeComponent = null;
        }
        for (ClickGuiModuleCardComponent clickGuiModuleCardComponent : list) {
            clickGuiModuleCardComponent.draggedY = Double.NaN;
        }
    }

    public boolean isDimmed() {
        return this.dimmed;
    }

    public void setReordering(boolean bl) {
        this.reordering = bl;
    }

    private void previewReorder(int n, List<ClickGuiModuleCardComponent> list) {
        ArrayList<ClickGuiModuleCardComponent> arrayList = new ArrayList<ClickGuiModuleCardComponent>(list);
        arrayList.remove(this);
        arrayList.add(n, this);
        for (int i = 0; i < arrayList.size(); ++i) {
            ClickGuiModuleCardComponent clickGuiModuleCardComponent = (ClickGuiModuleCardComponent)arrayList.get(i);
            if (clickGuiModuleCardComponent == this) continue;
            clickGuiModuleCardComponent.draggedY = list.get(i).getPaddedY();
        }
    }

    public void setFavoriteControlVisible(boolean bl) {
        this.favoriteControlVisible = bl;
    }

    private float getDimmedAlpha() {
        if (!this.dimmed || this.selected) {
            return 1.0f;
        }
        double d = Math.min(1.0, Math.max(0.0, this.dimAnimation.getInterpolatedValue()));
        return (float)(1.0 - 0.8 * d);
    }

    @Nullable
    private GuiComponent getCardsContainer() {
        FrameComponent frameComponent = this.getParentFrameComponent();
        if (frameComponent == null) {
            return null;
        }
        FrameComponent frameComponent2 = frameComponent.getParentFrameComponent();
        if (frameComponent2 == null) {
            return null;
        }
        return frameComponent2.getParentFrameComponent();
    }

    @Override
    public void H() {
        Color color;
        int n;
        double d;
        double d2;
        double d3;
        double d4;
        SmoothFontRenderer smoothFontRenderer;
        Color color2;
        boolean bl;
        Color color3;
        if (this.dragging) {
            this.updateDraggedPosition();
            this.updateDragOrder();
        }
        if (!Double.isNaN(this.draggedY)) {
            this.S(this.draggedY);
        }
        boolean bl2 = System.currentTimeMillis() - this.bindWarningTimestamp < 2000L;
        boolean bl3 = this.module.isRequiresBind() && !this.module.getBind().hasValidBinding();
        double d5 = this.G$src$D$1b2f02a();
        double d6 = this.n();
        double d7 = this.A();
        double d8 = this.L();
        double d9 = d6 + d8 / 2.0;
        double d10 = this.selectionOffsetAnimation.getInterpolatedValue();
        Color color4 = this.enabledFillAnimation.getInterpolatedColor();
        GuiRenderPrimitives.B(d5 += d10, d6, d7, d8, this.r(color4), 3.0f);
        Color color5 = this.hoverAnimation.getInterpolatedColor();
        if (color5.getAlpha() > 0) {
            GuiRenderPrimitives.B(d5, d6, d7, d8, this.r(color5), 3.0f);
        }
        Color color6 = ClientSettings.INSTANCE.getAccentColor();
        Color color7 = color3 = this.module.isEnabled() ? color6 : ClickGuiModuleCardComponent.J.y;
        if (bl2 && bl3) {
            color3 = ClickGuiModuleCardComponent.J.I;
        } else if (this.favoriteHighlighted) {
            color3 = ClickGuiModuleCardComponent.J.I;
        }
        RenderUtils.m(d5, d6 - 1.0, 2.0, d8 + 0.5);
        GuiRenderPrimitives.p(d5, d6, 10.0, d8 + 0.5, this.r(color3), false, 3.0f, 1.0f, 2.0f, this.r(CARD_SHADOW), 9);
        RenderUtils.T();
        MousePosition mousePosition = RenderUtils.h();
        double d11 = this.settingsIcon.A();
        double d12 = this.settingsIcon.L();
        double d13 = d5 + d7 - 4.0 - d11;
        double d14 = d9 - d12 / 2.0;
        this.settingsIcon.setVisible(!this.reordering);
        if (this.reordering) {
            bl = false;
        } else {
            this.settingsHitbox.M(d13 - 6.0);
            this.settingsHitbox.O(d14 - 8.0);
            this.settingsHitbox.A(d11 + 10.0);
            this.settingsHitbox.U(d12 + 16.0);
            bl = this.settingsHitbox.Z(mousePosition);
        }
        boolean bl4 = this.w$src$Z$e457mb() && !bl;
        this.hoverAnimation.u(bl4);
        this.settingsHoverAnimation.u(bl);
        this.enabledFillAnimation.u(this.module.isEnabled());
        this.enabledBackgroundAnimation.u(this.module.isEnabled());
        this.selectionAnimation.u(this.selected);
        this.selectionOffsetAnimation.u(this.selected);
        this.enabledToggleAnimation.u(this.module.isEnabled());
        this.dimAnimation.u(this.dimmed && !this.selected);
        this.favoriteAnimation.u(this.favoriteHighlighted);
        this.settingsIcon.K(d13);
        this.settingsIcon.S(d14);
        if (!this.reordering && (color2 = this.settingsHoverAnimation.getInterpolatedColor()).getAlpha() > 0) {
            GuiRenderPrimitives.p(this.settingsHitbox.o(), this.settingsHitbox.W(), this.settingsHitbox.e(), this.settingsHitbox.R(), this.r(color2), false, 2.0f, 1.0f, 0.0f, ClickGuiModuleCardComponent.J.u, 6);
        }
        color2 = ClickGuiModuleCardComponent.J.W;
        this.settingsIcon.setColor(this.r(bl ? ClickGuiModuleCardComponent.J.f : color2));
        double d15 = d13 - 12.5 - 10.0;
        double d16 = d9 - 3.5;
        if (!this.reordering) {
            Color color8 = this.module.isEnabled() ? color6 : ClickGuiModuleCardComponent.J.K;
            GuiRenderPrimitives.j(d15, d16, 12.5, 7.0, this.r(color8));
            double d17 = 5.5;
            double d18 = d15 + 1.5 + 5.5 * this.enabledToggleAnimation.getInterpolatedValue();
            if (this.module.isRequiresBind() && this.module.getBind().hasValidBinding()) {
                d18 += 3.125;
            }
            double d19 = d16 + 1.5;
            Color color9 = ClickGuiModuleCardComponent.J.i;
            GuiRenderPrimitives.V((float)d18, (float)d19, 4.0, (float)(0.8 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor()), color9);
        }
        double d20 = d15 - this.bindInput.A() - 8.0;
        double d21 = d9 - 5.0;
        boolean bl5 = this.module.getBind() != null && this.module.getBind().hasValidBinding();
        boolean bl6 = this.bindInput.getCaptureTask().isCapturing();
        boolean bl7 = !this.reordering && (bl5 || bl6 || bl4);
        this.bindInput.K(d20);
        this.bindInput.S(d21);
        this.bindInput.o(this.bindInput.getBindLabel().getRenderedWidth());
        this.bindInput.Y(10.0);
        this.bindInput.setVisible(bl7);
        this.bindInput.setHighlighted(bl2);
        this.bindInput.setActiveAlpha(15);
        this.bindInput.setAccentColor(ClickGuiModuleCardComponent.J.Z);
        this.bindInput.setAlphaMultiplier(this.getDimmedAlpha());
        double d22 = d20 + (double)(this.bindInput.V$src$Z$1xhop3l() ? 0 : 20) - 6.0;
        double d23 = d5 + 84.0;
        double d24 = Math.max(0.0, d22 - d23);
        boolean bl8 = this.detailComponent.V$src$Z$1xhop3l();
        Color color10 = this.module.isEnabled() ? ClickGuiModuleCardComponent.J.A : ClickGuiModuleCardComponent.J.C;
        this.detailComponent.n(bl2 ? ClickGuiModuleCardComponent.J.I : this.r(color10));
        this.detailComponent.K(this.getDimmedAlpha());
        this.detailComponent.S(d6);
        this.detailComponent.Y(d8);
        boolean bl9 = bl2 && this.module.isRequiresBind();
        boolean bl10 = this.module.L() && !this.module.isEnabled();
        boolean bl11 = this.module.getCategory() == Category.OTHER && this.unsafeBadgeEnabled;
        boolean bl12 = !this.module.isEnabled() && (this.module.t$src$Z$14g275z() || this.module.Q());
        double d25 = 0.0;
        if (bl10 && !bl9) {
            smoothFontRenderer = this.getAlternateFontRenderer(0.65);
            String string = "New!";
            d4 = smoothFontRenderer.N(string) + 4.0;
            d3 = smoothFontRenderer.d(string) + 2.0;
            d2 = d23;
            d = d9 - d3 / 2.0;
            GuiRenderPrimitives.d(d2, d, d4, d3, this.r(ClientSettings.INSTANCE.getAccentColor()));
            smoothFontRenderer.d(string, d2 + 2.0, d + 1.0, this.r(ColorUtil.getContrastingGray(ClientSettings.INSTANCE.getAccentColor(), 35, 255)));
            d25 = d4 + 3.0;
        } else if (bl11 && !bl9) {
            smoothFontRenderer = this.getAlternateFontRenderer(0.65);
            String string = "UNSAFE";
            d4 = smoothFontRenderer.N(string) + 4.0;
            d3 = smoothFontRenderer.d(string) + 2.0;
            d2 = d23;
            d = d9 - d3 / 2.0;
            n = (int)UNSAFE_BADGE_COLOR_RGB;
            color = new Color(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
            GuiRenderPrimitives.d(d2, d, d4, d3, this.r(color));
            smoothFontRenderer.d(string, d2 + 2.0, d + 1.0, this.r(ColorUtil.getContrastingGray(color, 35, 255)));
            d25 = d4 + 3.0;
        } else if (bl12 && !bl9) {
            smoothFontRenderer = this.getAlternateFontRenderer(0.65);
            String string = this.module.Q() ? "INDEV" : "BETA";
            d4 = smoothFontRenderer.N(string) + 4.0;
            d3 = smoothFontRenderer.d(string) + 2.0;
            d2 = d23;
            d = d9 - d3 / 2.0;
            GuiRenderPrimitives.d(d2, d, d4, d3, this.r(ClientSettings.INSTANCE.getAccentColor()));
            smoothFontRenderer.d(string, d2 + 2.0, d + 1.0, this.r(ColorUtil.getContrastingGray(ClientSettings.INSTANCE.getAccentColor(), 35, 255)));
            d25 = d4 + 3.0;
        }
        double d26 = d23 + d25;
        d4 = Math.max(0.0, d24 - d25);
        this.detailComponent.K(d26);
        this.detailComponent.o(d4);
        this.detailComponent.J(d4);
        d3 = d5 + 10.0;
        d2 = this.reorderIcon.A();
        this.reorderIcon.setVisible(this.reordering);
        this.removeButton.setVisible(this.reordering);
        if (this.reordering) {
            d = d2 - 1.0;
            double d27 = d3 + d2 / 2.0 - d / 2.0;
            double d28 = d9 - d / 2.0;
            GuiRenderPrimitives.V((float)d27, (float)d28, (float)d, 1.0, this.r(ClickGuiModuleCardComponent.J.Q));
            this.reorderIcon.setColor(this.r(ClickGuiModuleCardComponent.J.W));
            this.reorderIcon.K(d3);
            this.reorderIcon.S(d9 - d2 / 2.0);
            d3 += d + 6.0;
            double d29 = 6.0;
            this.removeButton.K(d5 + d7 - 6.0 - this.removeButton.A());
            this.removeButton.S(d9 - this.removeButton.L() / 2.0);
        }
        d = this.favoriteIcon.A();
        n = this.favoriteControlVisible || this.module.isFavorite() && this.favoriteControlVisible ? 1 : 0;
        this.favoriteIcon.setVisible(n != 0);
        if (n != 0) {
            color = this.module.isFavorite() ? ClickGuiModuleCardComponent.J.I : ClickGuiModuleCardComponent.J.K;
            this.favoriteIcon.setColor(this.r(color));
            this.favoriteIcon.K(d3);
            this.favoriteIcon.S(d9 - d / 2.0);
            this.favoriteHitbox.M(d3 - 2.0);
            this.favoriteHitbox.O(d9 - d / 2.0 - 4.0);
            this.favoriteHitbox.A(d + 4.0);
            this.favoriteHitbox.U(d + 8.0);
            d3 += d + 6.0;
        }
        double d30 = bl8 ? Math.max(d3, d23 - 6.0) : d22;
        double d31 = Math.max(0.0, d30 - d3);
        Color color11 = bl2 && bl3 ? ClickGuiModuleCardComponent.J.I : (this.module.isEnabled() ? Color.WHITE : ClickGuiModuleCardComponent.J.A);
        this.nameLabel.setTextColor(this.r(color11));
        this.nameLabel.K(d3);
        this.nameLabel.S(d6);
        this.nameLabel.o(d31);
        this.nameLabel.setMaxWidth(this.nameLabel.A());
        this.nameLabel.Y(d8);
    }

    private List<ClickGuiModuleCardComponent> collectSiblingCards() {
        GuiComponent guiComponent = this.getCardsContainer();
        if (guiComponent == null) {
            return new ArrayList<ClickGuiModuleCardComponent>();
        }
        ArrayList<ClickGuiModuleCardComponent> arrayList = new ArrayList<ClickGuiModuleCardComponent>();
        for (GuiComponent guiComponent2 : guiComponent.f()) {
            ClickGuiModuleCardComponent clickGuiModuleCardComponent;
            if (!(guiComponent2 instanceof PaddedComponent) || (clickGuiModuleCardComponent = ((PaddedComponent)guiComponent2).t(ClickGuiModuleCardComponent.class)) == null) continue;
            arrayList.add(clickGuiModuleCardComponent);
        }
        arrayList.sort(ClickGuiModuleCardComponent::lambda$collectButtons$0);
        return arrayList;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (guiMouseEvent.getAction() == MouseButton.LEFT_CLICK) {
            if (this.reordering && this.reorderIcon.V$src$Z$1xhop3l()) {
                if (this.removeButton.V$src$Z$1xhop3l() && this.removeButton.i(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                    this.module.K(false);
                    if (this.reorderAction != null) {
                        this.reorderAction.run();
                    }
                    return;
                }
                MousePosition mousePosition = RenderUtils.h();
                this.dragOffsetY = (double)mousePosition.H - this.n();
                this.dragging = true;
                this.draggedY = Double.NaN;
                this.setIgnoreFrameClipping(true);
                PaddedComponent paddedComponent = this.getPaddedWrapper();
                if (paddedComponent != null) {
                    paddedComponent.setIgnoreFrameClipping(true);
                }
                ClientSettings.activeComponent = this;
                return;
            }
            if (this.favoriteIcon.V$src$Z$1xhop3l() && this.favoriteControlVisible && this.favoriteHitbox.J(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                this.module.K(!this.module.isFavorite());
                return;
            }
            if (this.settingsHitbox.J(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                if (this.settingsAction != null) {
                    this.settingsAction.run();
                }
                return;
            }
            if (this.bindInput.V$src$Z$1xhop3l() && this.bindInput.i(guiMouseEvent.getX(), guiMouseEvent.getY())) {
                return;
            }
            if (this.module.isRequiresBind()) {
                this.bindWarningTimestamp = System.currentTimeMillis();
                return;
            }
            this.module.setEnabled(!this.module.isEnabled(), true);
        } else if (guiMouseEvent.getAction() == MouseButton.RIGHT_CLICK && this.settingsAction != null) {
            this.settingsAction.run();
        }
    }

    private void initializeAnimations() {
        this.setAnimationState(this.enabledFillAnimation, this.module.isEnabled());
        this.setAnimationState(this.enabledBackgroundAnimation, this.module.isEnabled());
        this.setAnimationState(this.enabledToggleAnimation, this.module.isEnabled());
        this.setAnimationState(this.selectionAnimation, this.selected);
        this.setAnimationState(this.selectionOffsetAnimation, this.selected);
        this.setAnimationState(this.dimAnimation, this.dimmed && !this.selected);
        this.setAnimationState(this.favoriteAnimation, this.favoriteHighlighted);
    }

    public ClickGuiModuleCardComponent(Mod mod) {
        this(mod, 22.0);
    }

    public Mod getModule() {
        return this.module;
    }

    private String getBindLabel() {
        String string = this.module.getBind().getBindText();
        if (string != null && !string.isEmpty()) {
            return string;
        }
        return this.module.isRequiresBind() ? "Set bind" : "";
    }

    private void updateDetailText() {
        if (System.currentTimeMillis() - this.bindWarningTimestamp < 2000L) {
            if (this.module.isRequiresBind() && this.module.getBind().hasValidBinding()) {
                this.detailComponent.Y(Collections.singletonList(ClickGuiModuleCardRenderState.j("Use via keybind while in game")));
            } else {
                this.detailComponent.Y(Collections.singletonList(ClickGuiModuleCardRenderState.j("Must be bound to use")));
            }
            return;
        }
        this.detailComponent.Y(this.module.S());
    }


    public boolean isFavoriteHighlighted() {
        return this.favoriteHighlighted;
    }

    private Color r(Color color) {
        if (color == null) {
            return null;
        }
        if (this.dimmed && !this.selected) {
            int n = Math.max(0, Math.round((float)color.getAlpha() * this.getDimmedAlpha()));
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
        }
        return color;
    }

    @Override
    public void u() {
        this.updateDetailText();
    }

    public boolean isReordering() {
        return this.reordering;
    }

    private static int lambda$collectButtons$0(ClickGuiModuleCardComponent clickGuiModuleCardComponent, ClickGuiModuleCardComponent clickGuiModuleCardComponent2) {
        PaddedComponent paddedComponent = clickGuiModuleCardComponent.getPaddedWrapper();
        PaddedComponent paddedComponent2 = clickGuiModuleCardComponent2.getPaddedWrapper();
        double d = paddedComponent != null ? paddedComponent.n() : 0.0;
        double d2 = paddedComponent2 != null ? paddedComponent2.n() : 0.0;
        return Double.compare(d, d2);
    }

    public void setDimmed(boolean bl) {
        this.dimmed = bl;
    }

    public void setSettingsAction(@Nullable Runnable runnable) {
        this.settingsAction = runnable;
    }

    public void setFavoriteHighlighted(boolean bl) {
        this.favoriteHighlighted = bl;
    }

    private double getPaddedY() {
        PaddedComponent paddedComponent = this.getPaddedWrapper();
        return paddedComponent != null ? paddedComponent.n() : this.n();
    }

    public boolean isFavoriteControlVisible() {
        return this.favoriteControlVisible;
    }

    @Nullable
    private PaddedComponent getPaddedWrapper() {
        FrameComponent frameComponent = this.getParentFrameComponent();
        if (frameComponent == null) {
            return null;
        }
        FrameComponent frameComponent2 = frameComponent.getParentFrameComponent();
        return frameComponent2 instanceof PaddedComponent ? (PaddedComponent)frameComponent2 : null;
    }

    public void setReorderAction(Runnable runnable) {
        this.reorderAction = runnable;
    }
}

