package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SelectableTextRowToggleClickListener;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public abstract class ClickGuiSidecarPanelBase
extends GuiComponent {
    @Nullable
    private String leadingIconKey;
    @Nullable
    private Runnable backAction;
    private double trailingComponentSpacing;
    @Nullable
    private Runnable closeAction;
    private final SquareIconButtonComponent closeButton;
    @Nullable
    private Runnable leadingAction;
    private final List<GuiComponent> trailingComponents;
    private final TruncatedTextComponent titleComponent;
    private final SpacerComponent leadingClickArea;
    private final GlyphIconComponent leadingIcon;
    private boolean dividerVisible;

    public void setTitleColor(Color color) {
        this.titleComponent.setTextColor(color);
    }

    public void setDividerVisible(boolean visible) {
        this.dividerVisible = visible;
    }

    protected void setLeadingAction(@Nullable Runnable action) {
        this.leadingAction = action;
        this.leadingClickArea.setVisible(action != null);
    }

    public void addTrailingComponent(GuiComponent guiComponent) {
        if (guiComponent == null) {
            return;
        }
        this.trailingComponents.add(guiComponent);
        this.addChildren(guiComponent);
        double closeButtonLeft = this.G$src$D$1b2f02a() + this.A() - 4.0 - this.closeButton.A();
        double trailingRightEdge = closeButtonLeft - 4.0;
        double componentWidth = guiComponent.A() > 0.0 ? guiComponent.A() : guiComponent.x();
        double componentHeight = guiComponent.L() > 0.0 ? guiComponent.L() : guiComponent.C();
        guiComponent.o(componentWidth);
        guiComponent.Y(componentHeight);
        guiComponent.K(trailingRightEdge - componentWidth);
        guiComponent.S(this.n() + this.L() / 2.0 - componentHeight / 2.0);
    }

    public TruncatedTextComponent getTitleComponent() {
        return this.titleComponent;
    }

    public void setTrailingComponentSpacing(double spacing) {
        this.trailingComponentSpacing = spacing;
    }

    protected SpacerComponent getLeadingClickArea() {
        return this.leadingClickArea;
    }

    public void setTitle(String title) {
        this.titleComponent.setText(title);
    }

    @Override
    public void g(GuiMouseEvent event) {
    }

    public void setLeadingIconColor(Color color) {
        this.leadingIcon.setNormalColor(color);
    }

    protected void layoutTitle(TruncatedTextComponent title, double x, double y, double width, double height) {
        title.K(x);
        title.S(y);
        title.o(width);
        title.Y(height);
        title.setMaxWidth(width);
    }

    @Override
    public double C() {
        return 20.0;
    }

    public GlyphIconComponent getLeadingIcon() {
        return this.leadingIcon;
    }

    public ClickGuiSidecarPanelBase() {
        this.leadingIcon = new GlyphIconComponent("online", 5.0, 5.0, 5.0, 5.0, ClickGuiSidecarPanelBase.J.W, ClickGuiSidecarPanelBase.J.f, null);
        this.titleComponent = new TruncatedTextComponent("", 50.0, 0.75);
        this.closeButton = new SquareIconButtonComponent("newclose", 1.2, new Color(0, 0, 0, 0), ClickGuiSidecarPanelBase.J.z, 8.0, 8.0);
        this.trailingComponents = new ArrayList<GuiComponent>();
        this.dividerVisible = true;
        this.trailingComponentSpacing = 2.0;
        this.setShowDisabledOverlay(false);
        this.titleComponent.setHorizontalInset(0.0);
        this.titleComponent.setTextColor(Color.WHITE);
        this.titleComponent.setBold(true);
        this.closeButton.setNormalColor(ClickGuiSidecarPanelBase.J.h);
        this.closeButton.setHoverColor(ClickGuiSidecarPanelBase.J.A);
        this.closeButton.addClickListener(this::handleCloseClick);
        this.leadingClickArea = new SpacerComponent(0.0, 0.0);
        this.leadingClickArea.setVisible(false);
        this.leadingClickArea.addMouseListener(new SelectableTextRowToggleClickListener(this));
        this.addChildren(this.leadingIcon, this.titleComponent, this.closeButton, this.leadingClickArea);
        this.leadingIcon.setNormalColor(ClickGuiSidecarPanelBase.J.W);
        this.leadingIcon.setHoverColor(ClickGuiSidecarPanelBase.J.f);
        this.Y(20.0);
    }


    public void setCloseAction(@Nullable Runnable action) {
        this.closeAction = action;
    }

    @Override
    public double x() {
        return 110.0;
    }

    public void setLeadingIconKey(@Nullable String iconKey) {
        this.leadingIconKey = iconKey;
        if (this.backAction != null) {
            return;
        }
        boolean hasIcon = iconKey != null && !iconKey.isEmpty();
        this.leadingIcon.setVisible(hasIcon);
        if (hasIcon) {
            this.leadingIcon.setIconResource(iconKey);
            this.leadingIcon.setNormalColor(ClickGuiSidecarPanelBase.J.W);
        }
    }

    private void handleCloseClick() {
        if (this.closeAction != null) {
            this.closeAction.run();
        }
    }

    public void setBackAction(@Nullable Runnable action) {
        this.backAction = action;
        boolean hasBackAction = action != null;
        this.leadingIcon.clearClickListeners();
        this.leadingIcon.clearMouseListeners();
        if (hasBackAction) {
            this.leadingIcon.setIconResource("moduleback");
            this.leadingIcon.setVisible(true);
            this.leadingIcon.setHoverColor(ClickGuiSidecarPanelBase.J.f);
            this.leadingAction = action;
            this.leadingClickArea.setVisible(true);
        } else {
            this.leadingAction = null;
            this.leadingClickArea.setVisible(false);
            if (this.leadingIconKey != null && !this.leadingIconKey.isEmpty()) {
                this.leadingIcon.setIconResource(this.leadingIconKey);
                this.leadingIcon.setVisible(true);
            } else {
                this.leadingIcon.setVisible(false);
            }
        }
    }

    public SquareIconButtonComponent getCloseButton() {
        return this.closeButton;
    }

    @Nullable
    public Runnable getLeadingAction() {
        return this.leadingAction;
    }

    @Override
    public void H() {
        double x = this.G$src$D$1b2f02a();
        double y = this.n();
        double width = this.A();
        double height = this.L();
        if (this.dividerVisible) {
            GuiRenderPrimitives.C(x, y + height - 0.5, width, 0.5, ClickGuiSidecarPanelBase.J.k);
        }
        double closeButtonWidth = this.closeButton.V$src$Z$1xhop3l() ? this.closeButton.A() : 0.0;
        double closeButtonX = x + width - 4.0 - closeButtonWidth;
        this.closeButton.K(closeButtonX);
        this.closeButton.S(y + height / 2.0 - this.closeButton.L() / 2.0);
        double trailingRightEdge = closeButtonWidth == 0.0 ? x + width - 4.0 : closeButtonX - 4.0;
        boolean firstTrailingComponent = true;
        for (GuiComponent component : new ArrayList<GuiComponent>(this.trailingComponents)) {
            if (component == null || !component.V$src$Z$1xhop3l()) {
                continue;
            }
            double componentWidth = component.A() > 0.0 ? component.A() : component.x();
            double componentHeight = component.L() > 0.0 ? component.L() : component.C();
            double componentX = firstTrailingComponent
                ? trailingRightEdge - componentWidth
                : trailingRightEdge - componentWidth - this.trailingComponentSpacing;
            component.o(componentWidth);
            component.Y(componentHeight);
            component.K(componentX);
            component.S(y + height / 2.0 - componentHeight / 2.0);
            trailingRightEdge = componentX;
            firstTrailingComponent = false;
        }
        double titleX = x + 6.0;
        if (this.leadingIcon.V$src$Z$1xhop3l()) {
            this.leadingIcon.S(y + height / 2.0 - 2.5);
            this.leadingIcon.K(titleX);
            titleX += this.leadingIcon.A() + 6.0;
        }
        double titleWidth = Math.max(0.0, trailingRightEdge - titleX - 12.0);
        this.layoutTitle(this.titleComponent, titleX, y, titleWidth, height);
        if (this.leadingClickArea.V$src$Z$1xhop3l()) {
            double clickAreaWidth = Math.max(0.0, this.titleComponent.G$src$D$1b2f02a() - x);
            this.leadingClickArea.K(x);
            this.leadingClickArea.S(y);
            this.leadingClickArea.o(clickAreaWidth);
            this.leadingClickArea.Y(height);
        }
        boolean leadingAreaHovered = this.leadingClickArea.V$src$Z$1xhop3l() && this.leadingClickArea.t();
        this.leadingIcon.setNormalColor(leadingAreaHovered ? ClickGuiSidecarPanelBase.J.f : ClickGuiSidecarPanelBase.J.W);
    }

    public void clearTrailingComponents() {
        for (GuiComponent guiComponent : new ArrayList<GuiComponent>(this.trailingComponents)) {
            if (this.f().contains(guiComponent)) {
                try {
                    this.removeChild(guiComponent);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.trailingComponents.remove(guiComponent);
        }
    }
}

