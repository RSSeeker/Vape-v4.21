package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;
import gg.vape.ui.click.frame.impl.main.ClickGuiContentPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiPanelBase;

public class ClickGuiPageBase
extends ClickGuiPanelBase {
    private final ClickGuiContentPanel sidebarHeader;
    private final SimpleTextLabelComponent titleLabel;
    private final ClickGuiContentPanel sidebarContainer;
    private final double sidebarWidth;
    private final ClickGuiContentPanel sidebarContent;
    private final ClickGuiContentPanel mainContainer;
    private final ClickGuiContentPanel mainContent;
    private final double mainWidth;

    public void U(String string) {
        this.titleLabel.setText(string);
    }

    public ClickGuiContentPanel getMainContainer() {
        return this.mainContainer;
    }

    protected FlowLayoutComponent createFlowLayout(double width, double height) {
        FlowLayoutComponent flowLayoutComponent = new FlowLayoutComponent(width, height);
        flowLayoutComponent.setUseExplicitWidth(true);
        flowLayoutComponent.setUseExplicitHeight(true);
        flowLayoutComponent.o(width);
        flowLayoutComponent.t(height);
        flowLayoutComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        flowLayoutComponent.I(true);
        return flowLayoutComponent;
    }

    public ClickGuiContentPanel getSidebarContainer() {
        return this.sidebarContainer;
    }

    public ClickGuiContentPanel getSidebarContent() {
        return this.sidebarContent;
    }

    public ClickGuiContentPanel getSidebarHeader() {
        return this.sidebarHeader;
    }

    public ClickGuiContentPanel getMainContent() {
        return this.mainContent;
    }

    private SimpleTextLabelComponent createTitleLabel(String text) {
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(text);
        simpleTextLabelComponent.setExtraHeight(0);
        simpleTextLabelComponent.setOffsetX(0.0f);
        simpleTextLabelComponent.setBold(true);
        simpleTextLabelComponent.setFontScale(1.0);
        simpleTextLabelComponent.setTextColor(ClickGuiPageBase.J.A);
        return simpleTextLabelComponent;
    }

    private ClickGuiContentPanel createContentPanel(double width, double height) {
        ClickGuiContentPanel clickGuiContentPanel = new ClickGuiContentPanel(width, height);
        clickGuiContentPanel.setUseExplicitWidth(true);
        clickGuiContentPanel.setUseExplicitHeight(true);
        clickGuiContentPanel.o(width);
        clickGuiContentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        clickGuiContentPanel.setShowDisabledOverlay(false);
        clickGuiContentPanel.t(height);
        return clickGuiContentPanel;
    }

    private ClickGuiContentPanel createHeaderPanel(double width) {
        ClickGuiContentPanel clickGuiContentPanel = new ClickGuiContentPanel(width, 16.0);
        clickGuiContentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        clickGuiContentPanel.setResponsiveWidthEnabled(true);
        clickGuiContentPanel.setUseExplicitWidth(true);
        clickGuiContentPanel.setUseExplicitHeight(true);
        clickGuiContentPanel.o(width);
        clickGuiContentPanel.setShowDisabledOverlay(false);
        return clickGuiContentPanel;
    }

    public ClickGuiPageBase(double d, double d2, double d3, double d4, String string) {
        super(d, d2);
        this.setShowDisabledOverlay(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.sidebarWidth = d3;
        this.sidebarHeader = this.createHeaderPanel(d3);
        this.titleLabel = this.createTitleLabel(string);
        this.titleLabel.Y(this.sidebarHeader.L());
        this.sidebarHeader.h(new PaddedComponent(0.0, 0.0, 2.0, 0.0, this.titleLabel), new Object[0]);
        this.sidebarContainer = this.createContentPanel(d3, d2 - 10.0);
        this.sidebarContainer.h(this.sidebarHeader, "wrap");
        this.sidebarContainer.h(new SpacerComponent(0.0, 4.0), "wrap");
        this.sidebarContent = this.createContentPanel(d3, this.sidebarContainer.L() - this.sidebarContainer.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y() - 2.0);
        this.sidebarContent.t(this.sidebarContent.L());
        this.sidebarContent.F(FrameScrollbarPlacement.OUTSIDE);
        this.sidebarContent.I(true);
        this.sidebarContainer.h(new PaddedComponent(1.0, 0.0, 0.0, 0.0, this.sidebarContent), "wrap");
        this.addChildren(new PaddedComponent(7.0, 5.0, 10.0, 0.0, this.sidebarContainer));
        this.mainWidth = this.A() - this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().C() - 20.0 + d4;
        this.mainContainer = this.createContentPanel(this.mainWidth, d2 - 18.0);
        this.mainContainer.setResponsiveWidthEnabled(true);
        this.mainContent = this.createContentPanel(this.mainContainer.A(), this.mainContainer.L() - 0.1);
        this.mainContent.setResponsiveWidthEnabled(true);
        this.mainContent.t(this.mainContent.L());
        this.mainContent.F(FrameScrollbarPlacement.OUTSIDE);
        this.mainContent.I(true);
        this.mainContainer.h(this.mainContent, "wrap");
        this.addChildren(new PaddedComponent(8.0, 5.0, 10.0 - d4, 10.0, this.mainContainer));
    }

    @Override
    public void K(double d, double d2, double d3) {
        super.K(d, d2, d3);
        this.sidebarHeader.o(this.sidebarWidth);
        this.sidebarContent.o(this.sidebarWidth);
        double d4 = Math.max(0.0, this.mainWidth - d3);
        this.mainContainer.o(d4);
        this.mainContent.o(d4);
    }
}
