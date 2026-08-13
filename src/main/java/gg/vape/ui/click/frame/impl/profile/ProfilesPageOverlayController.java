package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.Profile;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.vape.ui.click.frame.impl.profile.ProfileGlyphIconPanel;
import gg.vape.unmap.Bendable;
import org.jetbrains.annotations.Nullable;

public class ProfilesPageOverlayController
extends ClickGuiSidecarPanelBase {
    @Nullable
    private Runnable savedCloseAction;
    private final BindableInputComponent bindInput;
    private final ProfileGlyphIconPanel glyphPanel;

    public ProfileGlyphIconPanel getGlyphPanel() {
        return this.glyphPanel;
    }

    public void setBindInputEnabled(boolean enabled) {
        this.bindInput.setHighlighted(enabled);
    }

    @Override
    public void setLeadingIconKey(@Nullable String string) {
    }

    public void setGlyphVisible(boolean visible) {
        this.glyphPanel.setVisible(visible);
    }

    @Override
    public void setBackAction(@Nullable Runnable runnable) {
        Runnable runnable2 = runnable != null ? runnable : this.savedCloseAction;
        super.setLeadingAction(runnable2);
        this.getLeadingIcon().setIconResource("moduleback");
        this.getLeadingIcon().setVisible(true);
        this.getLeadingIcon().clearClickListeners();
        this.getLeadingIcon().clearMouseListeners();
        this.getCloseButton().setVisible(false);
    }

    public void setBindInputVisible(boolean visible) {
        this.bindInput.setVisible(visible);
    }

    @Override
    public void setCloseAction(@Nullable Runnable runnable) {
        this.savedCloseAction = runnable;
        super.setCloseAction(runnable);
    }

    public void setBinding(@Nullable Bendable bendable) {
        if (bendable != null) {
            this.bindInput.setBendable(bendable);
        }
    }

    public ProfilesPageOverlayController(@Nullable Runnable closeAction, @Nullable Profile profile) {
        this.savedCloseAction = closeAction;
        this.getCloseButton().setVisible(false);
        this.getLeadingIcon().setIconResource("moduleback");
        this.getLeadingIcon().setVisible(true);
        this.setLeadingAction(closeAction);
        this.glyphPanel = new ProfileGlyphIconPanel(profile);
        this.glyphPanel.o(6.0);
        this.glyphPanel.Y(6.0);
        this.bindInput = new BindableInputComponent(profile, ProfilesPageOverlayController.J.A);
        this.bindInput.setActiveOverride(false);
        this.bindInput.setVisible(true);
        this.bindInput.o(10.0);
        this.bindInput.Y(10.0);
        this.addChildren(this.glyphPanel, this.bindInput);
        this.addTrailingComponent(this.bindInput);
        this.addTrailingComponent(this.glyphPanel);
        this.setTrailingComponentSpacing(4.0);
        super.setCloseAction(closeAction);
    }


    public BindableInputComponent getBindInput() {
        return this.bindInput;
    }
}

