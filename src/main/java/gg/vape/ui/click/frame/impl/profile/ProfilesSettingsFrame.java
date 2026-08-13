package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.Profile;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.click.frame.SettingsSubpageFrame;
import gg.vape.ui.click.frame.impl.profile.ProfileCreatePanelComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileListEntryComponent;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrameState;
import gg.vape.ui.click.frame.impl.profile.ProfilesShowActiveRowsClickHandler;
import gg.vape.ui.click.frame.impl.profile.ProfilesShowAllRowsClickHandler;
import gg.vape.ui.font.SmoothFontRenderer;

public class ProfilesSettingsFrame
extends SettingsSubpageFrame {
    private final TextLabel doneButton;
    private boolean showingAllProfiles;
    private PopupFrame activePopup;
    private final PanelComponent profileList;
    private ProfileListEntryComponent forwardedEntry;
    private final ProfileCreatePanelComponent createPanel;
    private final FlowLayoutComponent contentLayout;
    private BooleanToggleComponent autoEnableModulesToggle;
    private final IconButtonComponent editHiddenProfilesButton = new IconButtonComponent("newedit", 0.7);

    public void closePopupAndDiscardDraft() {
        if (this.activePopup != null) {
            this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().restoreDefaultNavigation();
            ClientSettings.removePopup(this.activePopup);
            this.activePopup = null;
        }
        if (this.createPanel.getPendingProfile() != null) {
            Vape.INSTANCE.getProfilesManager().setActiveProfile(this.createPanel.getPendingProfile());
            this.createPanel.setPendingProfile(null);
        }
    }

    public void setForwardedEntry(ProfileListEntryComponent entry) {
        this.forwardedEntry = entry;
    }

    public void removeProfile(Profile profile) {
        ProfileListEntryComponent matchingEntry = null;
        for (GuiComponent component : this.profileList.f()) {
            if (component instanceof ProfileListEntryComponent) {
                ProfileListEntryComponent entry = (ProfileListEntryComponent)component;
                if (entry.getProfile().equals(profile)) {
                    matchingEntry = entry;
                }
            }
        }
        if (matchingEntry == null) {
            return;
        }
        this.profileList.removeChild(matchingEntry);
        this.profileList.l$src$V$1mibm4x();
        this.contentLayout.l$src$V$1mibm4x();
    }

    @Override
    public void w() {
        super.w();
        if (this.activePopup != null) {
            ClientSettings.removePopup(this.activePopup);
            this.activePopup = null;
        }
    }

    public FlowLayoutComponent getContentLayout() {
        return this.contentLayout;
    }

    public IconButtonComponent getEditHiddenProfilesButton() {
        return this.editHiddenProfilesButton;
    }

    public PopupFrame getActivePopup() {
        return this.activePopup;
    }

    public ProfileListEntryComponent getForwardedEntry() {
        return this.forwardedEntry;
    }

    public void addProfile(Profile profile) {
        this.profileList.addChildren(new ProfileListEntryComponent(this, profile));
        this.profileList.l$src$V$1mibm4x();
        this.contentLayout.l$src$V$1mibm4x();
    }

    @Override
    public void J() {
        if (this.forwardedEntry != null) {
            if (this.forwardedEntry.w$src$Z$e457mb()) {
                this.forwardedEntry.J();
            }
            return;
        }
        super.J();
    }


    @Override
    public void c() {
        int hiddenProfileCount = 0;
        for (Profile profile : Vape.INSTANCE.getProfilesManager().getProfiles()) {
            if (!profile.isVisible()) {
                ++hiddenProfileCount;
            }
        }
        if (!this.showingAllProfiles) {
            if (hiddenProfileCount > 0) {
                this.editHiddenProfilesButton.setIconResource("newhide");
            } else {
                this.editHiddenProfilesButton.setIconResource("newedit");
            }
            this.editHiddenProfilesButton.setVisible(this.w$src$Z$e457mb());
        }
        for (int i = 0; i < 5; ++i) {
            this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().updateLayout();
        }
        this.profileList.o(110.0);
        super.c();
        if (!this.showingAllProfiles && !this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().isShowingBackNavigation() && hiddenProfileCount > 0 && this.w$src$Z$e457mb()) {
            SmoothFontRenderer font = this.getFontRenderer(0.9);
            String countText = Integer.toString(hiddenProfileCount);
            double textHeight = font.d(countText);
            double textWidth = font.N(countText);
            font.d(countText, this.G$src$D$1b2f02a() + this.A() - 40.0 - textWidth, this.n() + this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().L() / 2.0 - textHeight / 2.0, ProfilesSettingsFrame.J.Z);
        }
    }

    public void setShowingAllProfiles(boolean showingAllProfiles) {
        this.showingAllProfiles = showingAllProfiles;
    }

    public void showAllProfileRows() {
        this.showingAllProfiles = true;
        for (GuiComponent component : this.f()) {
            if (component instanceof ProfileListEntryComponent) {
                component.setVisible(true);
            }
        }
    }

    public void showActiveProfileRows() {
        this.showingAllProfiles = false;
        for (GuiComponent component : this.f()) {
            if (component instanceof ProfileListEntryComponent
                && !((ProfileListEntryComponent)component).getProfile().isVisible()) {
                component.setVisible(false);
            }
        }
    }

    public void setActivePopup(PopupFrame popup) {
        this.activePopup = popup;
    }

    public static void refreshProfileList() {
        ProfilesSettingsFrame frame = ClientSettings.getFrame(ProfilesSettingsFrame.class);
        frame.profileList.removeMarkedChildren();
        for (Profile profile : Vape.INSTANCE.getProfilesManager().getProfiles()) {
            frame.addProfile(profile);
        }
        frame.contentLayout.l$src$V$1mibm4x();
    }

    @Override
    public void u() {
        super.u();
        this.profileList.o(110.0);
        if (this.activePopup != null) {
            this.activePopup.l$src$V$1mibm4x();
        }
    }

    public BooleanToggleComponent getAutoEnableModulesToggle() {
        return this.autoEnableModulesToggle;
    }

    public ProfilesSettingsFrame() {
        super("newprofiles", "Profiles");
        this.doneButton = new TextLabel("Done", 0.8);
        this.setVisible(false);
        this.o(103.0);
        this.N(false);
        this.D(true);
        this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().setDefaultIconScale(0.5f);
        for (GuiComponent guiComponent : ProfilesSettingsFrameState.F(false)) {
            BooleanToggleComponent booleanToggleComponent;
            this.n(guiComponent);
            if (!(guiComponent instanceof BooleanToggleComponent) || !(booleanToggleComponent = (BooleanToggleComponent)guiComponent).getBoundValue().equals(Vape.INSTANCE.getPublicProfileSettings().autoLoadModuleStates)) continue;
            this.autoEnableModulesToggle = booleanToggleComponent;
        }
        this.contentLayout = new FlowLayoutComponent(this.A());
        this.contentLayout.h(new SpacerComponent(1.0, 5.0), new Object[0]);
        this.createPanel = new ProfileCreatePanelComponent(this);
        this.contentLayout.addChildren(this.createPanel);
        this.profileList = new PanelComponent(100.0, 125.0);
        this.contentLayout.addChildren(this.profileList);
        this.contentLayout.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.profileList.t(125.0);
        this.profileList.o(110.0);
        this.profileList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.addChildren(this.contentLayout);
        this.doneButton.setVisible(false);
        this.doneButton.setExplicitWidth(20.0);
        this.doneButton.setExplicitHeight(14.0);
        this.editHiddenProfilesButton.w("Edit hidden profiles");
        this.editHiddenProfilesButton.addClickListener(new ProfilesShowAllRowsClickHandler(this));
        this.doneButton.addClickListener(new ProfilesShowActiveRowsClickHandler(this));
        this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().addAction(this.editHiddenProfilesButton);
        this.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().addAction(this.doneButton);
    }

    public ProfileCreatePanelComponent getCreatePanel() {
        return this.createPanel;
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (this.forwardedEntry != null) {
            this.forwardedEntry.dispatchMouseEvent(guiMouseEvent);
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }

    @Override
    public void K$src$V$qg5iru() {
        super.K$src$V$qg5iru();
        this.closePopupAndDiscardDraft();
    }

    public PanelComponent getProfileList() {
        return this.profileList;
    }

    @Override
    public String getName() {
        return "Profiles";
    }

    public TextLabel getDoneButton() {
        return this.doneButton;
    }

    public boolean isShowingAllProfiles() {
        return this.showingAllProfiles;
    }
}
