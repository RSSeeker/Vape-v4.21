package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileSummary;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfileListingDetailsPanel;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import java.awt.Point;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class PublicProfileListingResultOpenClickHandler
implements GuiMouseListener {
    final PublicProfileSummary profileSummary;
    final AtomicBoolean clickPending;
    final PublicProfilesFrame profilesFrame;
    static final boolean ASSERTIONS_DISABLED = !PublicProfilesFrame.class.desiredAssertionStatus();


    private static ApiResponse lambda$onClick$1(Throwable throwable) {
        Vape.logThrowable(throwable);
        return null;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!this.clickPending.get()) {
            return;
        }
        this.clickPending.set(false);
        PublicProfileListingDetailsPanel detailsPanel = this.profilesFrame.l((PublicProfile)null);
        detailsPanel.T(ApiServices.getInstance().getPublicProfileApi().viewProfile(this.profileSummary.getProfileId()).whenCompleteAsync((response, error) -> this.handleProfileLoad(this.clickPending, detailsPanel, this.profileSummary, response, error), (Executor)ClientSettings.UI_EXECUTOR).exceptionally(PublicProfileListingResultOpenClickHandler::lambda$onClick$1));
    }

    private void handleProfileLoad(AtomicBoolean clickState, PublicProfileListingDetailsPanel detailsPanel, PublicProfileSummary summary, ApiResponse apiResponse, Throwable throwable) {
        clickState.set(true);
        if (detailsPanel.R$src$Ljava_util_concurrent_CompletableFuture_$1ccqvok().isCancelled()) {
            return;
        }
        detailsPanel.T((CompletableFuture<?>)null);
        if (throwable != null) {
            Vape.logThrowable(throwable);
            PublicProfilesFrame.k(this.profilesFrame, detailsPanel.E());
            return;
        }
        if (!apiResponse.isSuccessful()) {
            Vape.debugLog("Failed to get public response details of " + summary.getProfileId() + ": " + apiResponse.getError());
            PublicProfileManager.showWarning("Failed to view profile: " + apiResponse.getError());
            PublicProfilesFrame.k(this.profilesFrame, detailsPanel.E());
            return;
        }
        if (!ASSERTIONS_DISABLED && apiResponse.getData() == null) {
            throw new AssertionError();
        }
        Vape.INSTANCE.getPublicProfileManager().addProfileTags((PublicProfile)apiResponse.getData());
        this.profilesFrame.l((PublicProfile)apiResponse.getData());
    }

    PublicProfileListingResultOpenClickHandler(PublicProfilesFrame publicProfilesFrame, AtomicBoolean atomicBoolean, PublicProfileSummary publicProfileSummary) {
        this.profilesFrame = publicProfilesFrame;
        this.clickPending = atomicBoolean;
        this.profileSummary = publicProfileSummary;
    }
}
