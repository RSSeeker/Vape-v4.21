package gg.vape.ui.click.frame;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.frame.CenteredPopupFrame;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.FrameToolbarComponent;
import gg.vape.ui.click.frame.FrameToolbarEntry;
import gg.vape.ui.click.frame.OutlinedFrameBase;
import gg.vape.ui.click.frame.SettingsSectionComponent;
import gg.vape.ui.click.frame.SettingsSectionPopupOpenClickHandler;
import gg.vape.ui.click.frame.SettingsSubpageFrameCollapseClickHandler;
import gg.vape.ui.click.frame.SettingsSubpageFrameRefreshClickHandler;
import gg.vape.ui.click.layout.ComponentLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsSubpageFrame
extends OutlinedFrameBase
implements CollapsibleFrame {
    private final FrameToolbarComponent fP;
    private final IconButtonComponent fE;
    private boolean fH = false;
    private final String fW;
    private PanelComponent fh;
    private CenteredPopupFrame f8;
    private final IconButtonComponent fy;
    private final List<GuiComponent> fv = new ArrayList<GuiComponent>();
    private CenteredPopupFrame fu;

    private void W() {
        if (this.fu != null) {
            ClientSettings.removePopup(this.fu);
            this.fu = null;
        }
    }

    public void n(GuiComponent ... guiComponentArray) {
        this.fv.addAll(Arrays.asList(guiComponentArray));
        for (GuiComponent guiComponent : this.fv) {
            guiComponent.setDisabledOverlayColor(SettingsSubpageFrame.J.t);
            if (guiComponent.getBoundValue() == null || guiComponent.getBoundValue().getParent() == null) continue;
            guiComponent.setDisabledOverlayColor(SettingsSubpageFrame.J.r);
        }
    }

    public SettingsSubpageFrame(String string, String string2) {
        this(string, string2, true, true);
    }

    @Override
    public void w() {
        this.K$src$V$qg5iru();
        this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().restoreDefaultNavigation();
        this.fH = !this.fH;
        for (GuiComponent guiComponent : this.f()) {
            if (!guiComponent.isRemovable()) continue;
            guiComponent.setVisible(!this.fH);
        }
        if (this.fH) {
            this.h$src$V$q8u99h();
        }
        this.fy.setIconResource(this.fH ? "downexpand" : "upcollapse");
        this.H(true);
    }

    public SettingsSubpageFrame(String string, String string2, boolean bl, boolean bl2) {
        this.fW = string2;
        ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
        componentLayout.t(false);
        componentLayout.U(false);
        componentLayout.M(false);
        componentLayout.I(false);
        componentLayout.M("wrap");
        this.fy = new IconButtonComponent(this.fH ? "downexpand" : "upcollapse", 0.25);
        this.fy.addClickListener(new SettingsSubpageFrameRefreshClickHandler(this));
        this.fE = new IconButtonComponent("newsettings", 0.9);
        this.fE.addClickListener(new SettingsSubpageFrameCollapseClickHandler(this));
        this.fP = new FrameToolbarComponent(this, string, string2);
        this.Y(this.fP);
        if (bl) {
            this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().addEntry(new FrameToolbarEntry(this.fy, false));
        }
        if (bl2) {
            this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().addAction(this.fE);
            this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().getCloseButton().addClickListener(this::p);
        }
        this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().getNavigationButton().addClickListener(this::K$src$V$qg5iru);
    }

    public FrameToolbarComponent i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6() {
        return this.fP;
    }

    public IconButtonComponent q$src$Lgg_vape_ui_click_component_IconButtonComponent_$1bvowkh() {
        return this.fE;
    }

    public void K$src$V$qg5iru() {
        if (this.f8 != null) {
            ClientSettings.removePopup(this.f8);
            this.f8 = null;
            ArrayList arrayList = new ArrayList();
            this.fv.forEach(arg_0 -> SettingsSubpageFrame.lambda$onSettingsBack$0(arrayList, arg_0));
            if (arrayList.size() == 1 && arrayList.get(0) instanceof SettingsSectionComponent) {
                this.fP.restoreDefaultNavigation();
                this.W();
            }
        } else if (this.fu != null) {
            this.p();
        }
    }

    private void D(GuiComponent guiComponent, double d) {
        guiComponent.setUseExplicitWidth(true);
        guiComponent.o(d);
        guiComponent.setExplicitWidth(d);
        for (GuiComponent guiComponent2 : guiComponent.f()) {
            this.D(guiComponent2, d);
        }
    }

    private static void lambda$onSettingsBack$0(ArrayList arrayList, GuiComponent guiComponent) {
        if (guiComponent.V$src$Z$1xhop3l()) {
            arrayList.add(guiComponent);
        }
    }

    @Override
    public boolean q() {
        return this.fH;
    }

    public CenteredPopupFrame H$src$Lgg_vape_ui_click_frame_CenteredPopupFrame_$1qmombx() {
        return this.fu;
    }

    public void h$src$V$q8u99h() {
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public SettingsSectionComponent s(String string, GuiComponent ... guiComponentArray) {
        SettingsSectionComponent settingsSectionComponent = new SettingsSectionComponent(string, 0.9, guiComponentArray);
        settingsSectionComponent.addClickListener(new SettingsSectionPopupOpenClickHandler(this, settingsSectionComponent));
        return settingsSectionComponent;
    }

    public static CenteredPopupFrame q(SettingsSubpageFrame settingsSubpageFrame) {
        return settingsSubpageFrame.f8;
    }

    public List<GuiComponent> h() {
        return this.fv;
    }

    @Override
    public void l$src$V$1mibm4x() {
        super.H(true);
        this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().updateLayout();
    }

    @Override
    public void u() {
        super.u();
        if (this.fh != null && this.fu != null) {
            this.fh.l$src$V$1mibm4x();
            this.fu.l$src$V$1mibm4x();
        }
    }

    @Override
    public String getName() {
        return this.fW;
    }

    public static CenteredPopupFrame j(SettingsSubpageFrame settingsSubpageFrame, CenteredPopupFrame centeredPopupFrame) {
        settingsSubpageFrame.f8 = centeredPopupFrame;
        return settingsSubpageFrame.f8;
    }

    public void p() {
        if (this.fu == null) {
            this.fP.showSettingsNavigation(true);
            try {
                double d = this.A();
                this.fh = new PanelComponent(d, this.L() - this.fP.L());
                this.fu = ClientSettings.createPopup(this, this.fh, CenteredPopupFrame.class);
                this.fu.n(18.0);
                this.fu.o(d);
                this.fh.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
                this.fu.l$src$V$1mibm4x();
                this.fh.h(new ColorDividerComponent(SettingsSubpageFrame.J.l, 0.5, d), new Object[0]);
                this.fh.h(new SpacerComponent(0.0, 0.5), new Object[0]);
                ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
                for (GuiComponent guiComponent : this.fv) {
                    this.fh.addChildren(guiComponent);
                    guiComponent.setDisabledOverlayColor(SettingsSubpageFrame.J.r);
                    if (guiComponent.getBoundValue() != null && guiComponent.getBoundValue().getParent() != null) {
                        guiComponent.setDisabledOverlayColor(SettingsSubpageFrame.J.r);
                    }
                    this.D(guiComponent, d);
                    if (!guiComponent.V$src$Z$1xhop3l()) continue;
                    arrayList.add(guiComponent);
                }
                this.fu.l$src$V$1mibm4x();
                if (arrayList.size() == 1 && arrayList.get(0) instanceof SettingsSectionComponent) {
                    SettingsSectionComponent settingsSectionComponent = (SettingsSectionComponent)arrayList.get(0);
                    settingsSectionComponent.dispatchPrimaryClick();
                }
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        } else {
            this.fP.restoreDefaultNavigation();
            this.W();
            if (this.f8 != null) {
                ClientSettings.removePopup(this.f8);
                this.f8 = null;
            }
        }
    }

    public void J(String string, boolean bl) {
        for (GuiComponent guiComponent : this.X(new ArrayList<GuiComponent>(Arrays.asList(this)), 0)) {
            if (!(guiComponent instanceof SettingsSectionComponent) || !((SettingsSectionComponent)guiComponent).A$src$Ljava_lang_String_$9tmd4u().equals(string)) continue;
            guiComponent.setVisible(bl);
        }
    }
}
