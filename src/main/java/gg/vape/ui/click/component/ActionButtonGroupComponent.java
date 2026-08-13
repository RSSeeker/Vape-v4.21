package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.FilledSpacerComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ActionButtonGroupComponent
extends GuiComponent {
    private final List<InteractiveComponent> buttons;
    private final PanelComponent buttonPanel = new PanelComponent(this.A(), this.L());
    @Nullable
    private Color borderColor;
    private final float borderRadius = 1.5f;
    private int renderedButtonCount = -1;
    private double padding = 1.0;
    private final float borderAlpha = 1.0f;


    public double getPadding() {
        return this.padding;
    }

    public void setPadding(double padding) {
        this.padding = padding;
    }

    @Override
    public void H() {
        if (this.isShowDisabledOverlay()) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.borderColor != null ? this.borderColor : ActionButtonGroupComponent.J.l, this.borderRadius, this.borderAlpha, 1.0f);
        }
        this.buttonPanel.K(this.G$src$D$1b2f02a());
        this.buttonPanel.S(this.n());
        this.buttonPanel.o(this.A());
        this.buttonPanel.Y(this.L());
        List<InteractiveComponent> visibleButtons = this.getVisibleButtons();
        if (this.renderedButtonCount != visibleButtons.size()) {
            this.buttonPanel.removeMarkedChildren();
            this.buttonPanel.k(true);
            double buttonWidth = (this.buttonPanel.A() - this.padding * 2.0) / (double)visibleButtons.size();
            double buttonHeight = (this.buttonPanel.L() - this.padding * 2.0) / 2.0;
            this.buttonPanel.h(new SpacerComponent(0.0, this.buttonPanel.L() / 2.0 - buttonHeight / 2.0), "wrap");
            for (int buttonIndex = 0; buttonIndex < visibleButtons.size(); ++buttonIndex) {
                InteractiveComponent button = visibleButtons.get(buttonIndex);
                button.setUseExplicitWidth(true);
                button.setUseExplicitHeight(true);
                button.setExplicitWidth(buttonWidth);
                button.setExplicitHeight(buttonHeight);
                this.buttonPanel.h(button, new Object[0]);
                if (buttonIndex == visibleButtons.size() - 1) continue;
                this.buttonPanel.addChildren(new FilledSpacerComponent(1.0, buttonHeight, ActionButtonGroupComponent.J.l));
            }
            this.renderedButtonCount = visibleButtons.size();
        }
    }

    public List<InteractiveComponent> getVisibleButtons() {
        ArrayList<InteractiveComponent> visibleButtons = new ArrayList<InteractiveComponent>();
        for (InteractiveComponent button : this.buttons) {
            if (!button.V$src$Z$1xhop3l()) continue;
            visibleButtons.add(button);
        }
        return visibleButtons;
    }

    public ActionButtonGroupComponent(InteractiveComponent ... buttons) {
        this(Arrays.asList(buttons));
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    @Override
    public double x() {
        return 0.0;
    }

    public ActionButtonGroupComponent(List<InteractiveComponent> buttons) {
        this.buttonPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.buttonPanel.setShowDisabledOverlay(false);
        this.buttons = buttons;
        this.addChildren(this.buttonPanel);
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    public List<InteractiveComponent> getButtons() {
        return this.buttons;
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void F() {
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void u() {
    }

    public float getBorderRadius() {
        return this.borderRadius;
    }

    @Override
    public void I() {
    }

    public float getBorderAlpha() {
        return this.borderAlpha;
    }
}

