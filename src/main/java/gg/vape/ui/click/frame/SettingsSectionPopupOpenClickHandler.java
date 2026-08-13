package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.GuiComponentContract;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.frame.CenteredPopupFrame;
import gg.vape.ui.click.frame.SettingsSectionComponent;
import gg.vape.ui.click.frame.SettingsSubpageFrame;

public class SettingsSectionPopupOpenClickHandler
implements GuiClickListener {
    final SettingsSectionComponent a;
    final SettingsSubpageFrame P;
    private static final String c = "wrap";


    @Override
    public void onPrimaryClick() {
        double d = this.P.A();
        PanelComponent panelComponent = new PanelComponent(d, this.P.L() - this.P.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().L());
        SettingsSubpageFrame.j(this.P, ClientSettings.createPopup(this.P, panelComponent, CenteredPopupFrame.class));
        SettingsSubpageFrame.q(this.P).s(false);
        SettingsSubpageFrame.q(this.P).n(18.0);
        SettingsSubpageFrame.q(this.P).o(d);
        panelComponent.t(this.P.L() - this.P.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().L());
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(c);
        panelComponent.h(new ColorDividerComponent(GuiComponentContract.J.l, 0.5, d), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 0.5), new Object[0]);
        for (GuiComponent guiComponent : this.a.n$src$ALgg_vape_ui_click_component_GuiComponent_$ay9kg5()) {
            panelComponent.addChildren(guiComponent);
            guiComponent.setDisabledOverlayColor(GuiComponentContract.J.r);
            if (guiComponent.getBoundValue() != null && guiComponent.getBoundValue().getParent() != null) {
                guiComponent.setDisabledOverlayColor(GuiComponentContract.J.i);
            }
            this.O(guiComponent, d);
        }
        panelComponent.setDisabledOverlayColor(GuiComponentContract.J.r);
    }

    public SettingsSectionPopupOpenClickHandler(SettingsSubpageFrame settingsSubpageFrame, SettingsSectionComponent settingsSectionComponent) {
        this.P = settingsSubpageFrame;
        this.a = settingsSectionComponent;
    }

    private void O(GuiComponent guiComponent, double d) {
        guiComponent.setUseExplicitWidth(true);
        guiComponent.o(d);
        guiComponent.setExplicitWidth(d);
        for (GuiComponent guiComponent2 : guiComponent.f()) {
            this.O(guiComponent2, d);
        }
    }
}

