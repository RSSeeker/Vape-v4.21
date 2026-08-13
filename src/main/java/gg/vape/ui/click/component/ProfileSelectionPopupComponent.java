package gg.vape.ui.click.component;

import gg.vape.config.Profile;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.CenteredGlyphComponent;
import gg.vape.ui.click.component.ConfirmationDialogComponent;
import gg.vape.ui.click.component.IconTextActionRowComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.PopupSelectorComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import java.awt.Color;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public class ProfileSelectionPopupComponent
extends PopupSelectorComponent {
    private final SimpleTextLabelComponent derivedFromLabel;
    private final TruncatedTextComponent selectedProfileLabel;
    private final CenteredGlyphComponent dropdownArrow;
    private static final double PADDING = 10.0;
    private final CenteredGlyphComponent missingProfileWarning = new CenteredGlyphComponent("warning@2x", 5.0f, 5.0f, Color.WHITE);
    @Nullable
    private Profile selectedProfile;
    @Nullable
    private Consumer<Profile> selectionCallback;

    @Nullable
    public Profile getSelectedProfile() {
        return this.selectedProfile;
    }

    @Nullable
    public Consumer<Profile> getSelectionCallback() {
        return this.selectionCallback;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        super.g(mouseEvent);
    }

    public ProfileSelectionPopupComponent(String label, @Nullable Profile selectedProfile, Profile ... profiles) {
        super(new PanelComponent(108.0, 135.0));
        this.dropdownArrow = new CenteredGlyphComponent("arrow down active@2x", 2.0f, 2.0f, Color.GRAY);
        this.selectedProfile = selectedProfile;
        this.popupContent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.popupContent.setShowDisabledOverlay(true);
        this.popupContent.I(true);
        this.popupContent.setDisabledOverlayColor(ProfileSelectionPopupComponent.J.B);
        this.popupContent.h(new SpacerComponent(0.0, 10.0), new Object[0]);
        WrappingTextLabelComponent wrappingTextLabelComponent = new WrappingTextLabelComponent(label, 1.0, Color.WHITE);
        wrappingTextLabelComponent.setBold(true);
        wrappingTextLabelComponent.Y(12.0);
        wrappingTextLabelComponent.o(this.popupContent.A());
        this.popupContent.h(wrappingTextLabelComponent, new Object[0]);
        this.popupContent.h(new SpacerComponent(0.0, 4.0), new Object[0]);
        PanelComponent panelComponent = new PanelComponent(this.popupContent.A(), 104.0);
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.setDisabledOverlayColor(this.popupContent.getDisabledOverlayColor());
        panelComponent.I(true);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        panelComponent.t(panelComponent.L());
        this.popupContent.h(panelComponent, "widthwrap");
        for (Profile profile : profiles) {
            IconTextActionRowComponent iconTextActionRowComponent = new IconTextActionRowComponent(profile.getName());
            iconTextActionRowComponent.o(panelComponent.A());
            iconTextActionRowComponent.Y(12.0);
            iconTextActionRowComponent.setClickListener(() -> this.requestProfileSelectionConfirmation(profile));
            panelComponent.h(iconTextActionRowComponent, "wrap");
            panelComponent.h(new SpacerComponent(0.0, 2.0), "wrap");
        }
        this.derivedFromLabel = new SimpleTextLabelComponent("DERIVED FROM", 0.65f, ProfileSelectionPopupComponent.J.h);
        this.selectedProfileLabel = new TruncatedTextComponent("", "...", 58.0, 0.65f, ProfileSelectionPopupComponent.J.A, false);
        this.selectedProfileLabel.o(this.selectedProfileLabel.getMaxWidth());
        this.missingProfileWarning.w("The original profile that this profile was derived from no longer exists.");
        this.addChildren(this.derivedFromLabel, this.selectedProfileLabel, this.missingProfileWarning, this.dropdownArrow);
        this.setPropagateMouseEvents(true);
    }


    public void setSelectionCallback(@Nullable Consumer<Profile> selectionCallback) {
        this.selectionCallback = selectionCallback;
    }

    private void selectProfile(Profile profile) {
        if (this.popupFrame != null) {
            ClientSettings.removePopup(this.popupFrame);
            this.popupFrame = null;
        }
        this.selectedProfile = profile;
        Consumer<Profile> consumer = this.selectionCallback;
        if (consumer != null) {
            consumer.accept(profile);
        }
    }

    private void requestProfileSelectionConfirmation(Profile profile) {
        ConfirmationDialogComponent.show(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), "Are you sure you want to change the derived profile?\n \nProfile settings will be swapped to other profile", "Confirm", "newtrash", () -> this.selectProfile(profile), 100.0, null, null);
    }

    @Override
    public void H() {
        this.derivedFromLabel.K(this.G$src$D$1b2f02a());
        this.derivedFromLabel.S(this.n());
        this.missingProfileWarning.setVisible(this.selectedProfile == null);
        this.missingProfileWarning.K(this.derivedFromLabel.G$src$D$1b2f02a() + this.derivedFromLabel.getTextWidth() + 5.0);
        this.missingProfileWarning.S(this.n() - 1.0);
        this.selectedProfileLabel.K(this.derivedFromLabel.G$src$D$1b2f02a() + this.derivedFromLabel.getTextWidth() + 8.0);
        this.selectedProfileLabel.S(this.n() + 2.0);
        this.selectedProfileLabel.setText(this.selectedProfile != null ? this.selectedProfile.getName() : "");
        this.dropdownArrow.K(this.missingProfileWarning.V$src$Z$1xhop3l() ? this.missingProfileWarning.G$src$D$1b2f02a() + this.missingProfileWarning.A() + 4.0 : this.selectedProfileLabel.G$src$D$1b2f02a() + this.selectedProfileLabel.getRenderedWidth() + 2.0);
        this.dropdownArrow.S(this.n() + 2.0);
    }
}
