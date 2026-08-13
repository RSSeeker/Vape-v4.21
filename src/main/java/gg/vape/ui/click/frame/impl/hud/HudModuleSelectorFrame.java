package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleGroupTabClickHandler;
import gg.vape.ui.click.frame.impl.hud.HudModuleListPanel;
import gg.vape.ui.click.frame.impl.hud.HudModuleSearchBox;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorHeaderComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.ArrayList;

public class HudModuleSelectorFrame
extends Frame {
    private final PanelComponent categoryBar;
    private static HudModuleListPanel moduleListPanel;
    private final ArrayList<TextLabel> categoryLabels;
    private HudModuleGroup selectedGroup;
    private String searchQuery = "";
    private boolean initialized;
    private final HudModuleSearchBox searchBox = new HudModuleSearchBox(this);
    public static boolean overviewVisible;

    @Override
    public void v() {
    }

    public void queueForDisplay() {
        ClientSettings.queueFrameOpen(this);
        ClientSettings.queueFrameOpen(moduleListPanel);
    }

    public HudModuleSearchBox getSearchBox() {
        return this.searchBox;
    }

    @Override
    public boolean d$src$Z$1lx9d06() {
        return false;
    }

    @Override
    public String getName() {
        return "LegitMenuFrame";
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public double A() {
        return 350.0;
    }

    private void updateSelectedGroupIndicator() {
        for (TextLabel textLabel : this.categoryLabels) {
            if (this.selectedGroup.getName().equalsIgnoreCase(textLabel.getText())) {
                textLabel.setTextColor(Color.WHITE);
                this.e(textLabel);
                continue;
            }
            textLabel.setTextColor(null);
        }
    }

    public HudModuleGroup getSelectedGroup() {
        return this.selectedGroup;
    }

    public void selectGroup(HudModuleGroup group) {
        this.selectedGroup = group;
    }

    public HudModuleListPanel getModuleListPanel() {
        return moduleListPanel;
    }

    public String getSearchQuery() {
        return this.searchQuery;
    }

    @Override
    public void Y() {
    }

    private void e(TextLabel textLabel) {
        double d = textLabel.n() + 7.0;
        for (double d2 = textLabel.G$src$D$1b2f02a(); d2 < textLabel.G$src$D$1b2f02a() + (textLabel.getTextWidth() + 1.0); d2 += 2.0) {
            GuiRenderPrimitives.a(d2, d, 1.0, 1.0f, HudModuleSelectorFrame.J.A);
        }
    }

    @Override
    public void U() {
        this.queueForDisplay();
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (ClientSettings.getFrame(HudModuleConfigFrame.class).V$src$Z$1xhop3l()) {
            ClientSettings.getFrame(HudModuleConfigFrame.class).g(guiMouseEvent);
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }

    @Override
    public double L() {
        return (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() + 20.0) * 3.5 + 52.0;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        moduleListPanel.refreshModules();
    }

    public HudModuleSelectorFrame() {
        this.selectedGroup = HudModuleGroup.ALL;
        this.categoryLabels = new ArrayList();
        this.categoryBar = new PanelComponent(this.A(), 18.0);
        moduleListPanel = new HudModuleListPanel(this);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().t(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().I(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().U(false);
        this.setDisabledOverlayColor(HudModuleSelectorFrame.J.i);
        this.Y(new HudModuleSelectorHeaderComponent(this));
        this.setVisible(true);
        this.L(false, true);
        this.g(true);
        this.Y(false);
        this.addChildren(new GuiComponent[0]);
        this.categoryBar.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        SpacerComponent spacerComponent = new SpacerComponent(12.5, 0.0);
        this.categoryBar.h(spacerComponent, new Object[0]);
        PanelComponent panelComponent = new PanelComponent((this.categoryBar.A() - spacerComponent.A()) / 2.0, this.categoryBar.L());
        panelComponent.h(new SpacerComponent(0.0, 8.0), "wrap");
        this.categoryBar.h(panelComponent, new Object[0]);
        panelComponent.setShowDisabledOverlay(false);
        for (HudModuleGroup hudModuleGroup : HudModuleGroup.getGroups()) {
            TextLabel textLabel = new TextLabel(hudModuleGroup.getName(), 0.75);
            textLabel.addClickListener(new HudModuleGroupTabClickHandler(this, hudModuleGroup));
            textLabel.o(textLabel.getTextWidth());
            textLabel.Y(7.0);
            textLabel.setVisible(true);
            this.categoryLabels.add(textLabel);
            panelComponent.h(textLabel, new Object[0]);
            panelComponent.h(new SpacerComponent(17.5, 0.0), new Object[0]);
        }
        this.categoryBar.setShowDisabledOverlay(false);
        this.h(this.categoryBar, new Object[0]);
        this.categoryBar.h(this.searchBox, "alignright");
    }


    @Override
    public void c() {
        super.c();
        if (!this.initialized) {
            moduleListPanel.refreshModules();
            this.initialized = true;
            this.U();
        }
        this.categoryBar.setExplicitHeight(20.0);
        moduleListPanel.o(this.A());
        moduleListPanel.Y(moduleListPanel.d$src$D$ibccpu());
        moduleListPanel.M(this.G$src$D$1b2f02a(), this.n() + this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() + this.searchBox.L());
        moduleListPanel.c();
        this.updateSelectedGroupIndicator();
        this.l$src$V$1mibm4x();
    }

    @Override
    public double x() {
        return this.A();
    }

    @Override
    public void t(boolean bl, boolean bl2) {
        super.t(bl, bl2);
        moduleListPanel.t(bl, bl2);
        if (bl) {
            this.queueForDisplay();
        }
    }

    @Override
    public void J() {
        if (ClientSettings.getFrame(HudModuleConfigFrame.class).V$src$Z$1xhop3l()) {
            return;
        }
        super.J();
    }

}

