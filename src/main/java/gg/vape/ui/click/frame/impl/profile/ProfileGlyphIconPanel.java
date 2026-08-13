package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.Profile;
import gg.vape.config.PublicProfile;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.frame.FrameStackManager;
import gg.vape.ui.click.frame.impl.main.ClickGuiFrameManager;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Nullable;

public class ProfileGlyphIconPanel
extends GlyphIconComponent {
    private Profile profile;

    private void openAsOverlay(long publicProfileId, ClickGuiFrameManager frameManager) {
        PublicProfilesFrame profilesFrame = ClientSettings.getFrame(PublicProfilesFrame.class);
        frameManager.setSidecarFrame(profilesFrame);
        ApiServices.getInstance().getPublicProfileApi().viewProfile(publicProfileId)
            .whenCompleteAsync((response, error) -> handleProfileResponse(profilesFrame, response, error), (Executor)ClientSettings.UI_EXECUTOR)
            .exceptionally(ProfileGlyphIconPanel::ignoreLoadFailure);
    }


    public ProfileGlyphIconPanel(@Nullable Profile profile) {
        this(profile, 6.0, 8.0);
    }

    public Profile getProfile() {
        return this.profile;
    }

    private void handleClick() {
        if (this.profile == null || this.profile.getRemoteMetadata() == null) {
            return;
        }
        long publicProfileId = this.profile.getRemoteMetadata().getPublicProfileId();
        FrameStackManager activeStack = ClientSettings.INSTANCE.getActiveStack();
        if (activeStack instanceof ClickGuiFrameManager) {
            this.openAsOverlay(publicProfileId, (ClickGuiFrameManager)activeStack);
        } else {
            PublicProfilesFrame.s(publicProfileId);
        }
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private static void handleProfileResponse(PublicProfilesFrame profilesFrame, ApiResponse response, Throwable error) {
        if (error != null) {
            Vape.logThrowable(error);
            return;
        }
        if (!response.isSuccessful()) {
            Vape.debugLog("Failed to load public profile data: " + response.getError());
            return;
        }
        if (response.getData() == null) {
            throw new AssertionError();
        }
        profilesFrame.l((PublicProfile)response.getData());
    }

    public void refreshVisibility() {
        if (this.profile != null && this.profile.getRemoteMetadata() != null) {
            this.setVisible(true);
            String iconKey = this.profile.getRemoteMetadata().hasNewerPublishedVersion()
                ? "external link outdated hover@2x"
                : "external link hover@2x";
            this.setIconResource(iconKey);
        } else {
            this.setVisible(false);
        }
    }

    public ProfileGlyphIconPanel(@Nullable Profile profile, double iconWidth, double iconHeight) {
        super("external link hover@2x", iconWidth, iconWidth, iconHeight, iconHeight, null, null, null);
        this.profile = profile;
        this.setNormalColor(Color.WHITE);
        this.w("View public profile");
        this.refreshVisibility();
        this.setupClickListener();
    }

    private void setupClickListener() {
        this.clearClickListeners();
        this.addClickListener(this::handleClick);
    }

    private static ApiResponse ignoreLoadFailure(Throwable error) {
        return null;
    }
}
