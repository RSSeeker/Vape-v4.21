package gg.vape.ui.click.frame.impl.profile;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.api.PagedResult;
import gg.vape.config.Profile;
import gg.vape.config.ProfileSnapshot;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileReviewDisplayType;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.sync.RemoteProfileData;
import gg.vape.ui.click.component.ActionButtonGroupComponent;
import gg.vape.ui.click.component.CenteredGlyphComponent;
import gg.vape.ui.click.component.CollapsiblePanelComponent;
import gg.vape.ui.click.component.ConfirmationDialogComponent;
import gg.vape.ui.click.component.DualTextLabelRowComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PagedResultListComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SkeletonPlaceholderComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TwoLineTextDisplayComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileDateFormatUtil;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileReviewComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileReviewComposerComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileSnapshotPanelBase;
import gg.vape.ui.click.frame.impl.profile.PublicProfileUserAvatarComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import gg.vape.ui.click.layout.WrappingFlowLayout;
import gg.vape.utils.ClipboardUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

public class PublicProfileListingDetailsPanel
extends PublicProfileSnapshotPanelBase {
    private final IconButtonComponent likeButton = new IconButtonComponent("like active@2x", 0.8);
    private final IconButtonComponent dislikeButton = new IconButtonComponent("dislike active@2x", 0.8);
    private PagedResultListComponent reviewsList;

    private void deleteOwnReview() {
        this.publicProfile.getViewerReview().delete(this.publicProfile, this::rebuildReviews);
    }

    @Override
    protected void e() {
        super.e();
        if (this.snapshot == null || this.publicProfile == null) {
            this.showLoadingState();
            return;
        }
        this.setupFooter();
    }

    private void changeOwnReview(boolean positive) {
        this.publicProfile.getViewerReview().delete(this.publicProfile, () -> this.refreshAfterReview(positive));
    }

    private static ApiResponse ignoreDownloadFailure(Throwable throwable) {
        return null;
    }

    private void showDetailsPanel() {
        PanelComponent panelComponent;
        assert this.publicProfile != null;
        this.b$src$V$s019hq();
        double d = this.gg.A();
        this.getClass();
        double d2 = d - 5.0;
        PanelComponent panelComponent2 = new PanelComponent(d2, 12.0);
        panelComponent2.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        panelComponent2.setShowDisabledOverlay(false);
        this.gg.h(panelComponent2, new Object[0]);
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Details", 0.9);
        simpleTextLabelComponent.setOffsetX(0.0f);
        simpleTextLabelComponent.setBold(true);
        simpleTextLabelComponent.setTextColor(PublicProfileListingDetailsPanel.J.A);
        panelComponent2.h(simpleTextLabelComponent, new Object[0]);
        simpleTextLabelComponent.o(panelComponent2.A());
        FlowLayoutComponent flowLayoutComponent = new FlowLayoutComponent(d2);
        flowLayoutComponent.setShowDisabledOverlay(false);
        CollapsiblePanelComponent collapsiblePanelComponent = new CollapsiblePanelComponent(this.publicProfile.getDescription(), d2);
        SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent("Created: " + PublicProfileDateFormatUtil.H(this.publicProfile.getCreationDate()), 0.8);
        simpleTextLabelComponent2.Y(8.0);
        simpleTextLabelComponent2.o(d2);
        simpleTextLabelComponent2.setOffsetX(0.0f);
        collapsiblePanelComponent.getContentLayout().h(new SpacerComponent(0.0, 6.0), new Object[0]);
        collapsiblePanelComponent.getContentLayout().h(simpleTextLabelComponent2, new Object[0]);
        PanelComponent panelComponent3 = new PanelComponent(d2, 10.0);
        panelComponent3.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        panelComponent3.setShowDisabledOverlay(false);
        SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("Share code: " + this.publicProfile.getUppercaseShareCode(), 0.8);
        simpleTextLabelComponent3.Y(8.0);
        simpleTextLabelComponent3.setOffsetX(0.0f);
        panelComponent3.h(simpleTextLabelComponent3, new Object[0]);
        GlyphIconComponent glyphIconComponent = new GlyphIconComponent("newcopy", 4.0, 4.0, 8.0, 8.0, PublicProfileListingDetailsPanel.J.W, PublicProfileListingDetailsPanel.J.f, PublicProfileListingDetailsPanel.J.l);
        glyphIconComponent.setOutlineAlpha(0.75f);
        glyphIconComponent.setOffsetX(2.0);
        glyphIconComponent.setOffsetY(2.0);
        panelComponent3.h(new SpacerComponent(4.0, 0.0), new Object[0]);
        panelComponent3.h(glyphIconComponent, new Object[0]);
        glyphIconComponent.setClickListener(this::copyShareCode);
        collapsiblePanelComponent.getContentLayout().h(panelComponent3, new Object[0]);
        flowLayoutComponent.h(collapsiblePanelComponent, new Object[0]);
        flowLayoutComponent.h(new SpacerComponent(0.0, 8.0), new Object[0]);
        this.gg.h(flowLayoutComponent, new Object[0]);
        if (!this.publicProfile.getTags().isEmpty()) {
            panelComponent = new PanelComponent(d2, 12.0);
            panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
            panelComponent.setShowDisabledOverlay(false);
            this.gg.h(panelComponent, new Object[0]);
            for (String stringArray2 : this.publicProfile.getTags()) {
                panelComponent.h(new PublicProfileFilterTokenComponent(stringArray2), new Object[0]);
                panelComponent.h(new SpacerComponent(2.0, 0.0), new Object[0]);
            }
        }
        this.gg.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        panelComponent = new PanelComponent(d2, 40.0);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        panelComponent.setShowDisabledOverlay(false);
        this.gg.h(panelComponent, new Object[0]);
        String[] stringArray3 = new String[]{"Positive reviews", "Last updated", "Downloads"};
        String[] stringArray = new String[]{String.valueOf(this.publicProfile.getLikes()), PublicProfileDateFormatUtil.i(this.publicProfile.getLatestDate()), String.valueOf(this.publicProfile.getDownloads())};
        panelComponent.h(new SpacerComponent(0.0, 5.0), "wrap");
        for (int function = 0; function < stringArray3.length; ++function) {
            String string = stringArray3[function];
            String string2 = stringArray[function];
            TwoLineTextDisplayComponent twoLineTextDisplayComponent = new TwoLineTextDisplayComponent(string, string2);
            twoLineTextDisplayComponent.o(panelComponent.A() / (double)stringArray3.length - 2.0);
            twoLineTextDisplayComponent.Y(29.0);
            panelComponent.h(new PaddedComponent(1.0, twoLineTextDisplayComponent), "widthwrap");
        }
        panelComponent.h(new SpacerComponent(0.0, 5.0), "wrap");
        this.reviewsList = new PagedResultListComponent(d2, 50.0, 2);
        this.reviewsList.setLoadThreshold(6);
        this.reviewsList.N(new WrappingFlowLayout(this.reviewsList));
        this.reviewsList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.reviewsList.setShowDisabledOverlay(false);
        this.reviewsList.N(false);
        this.reviewsList.setScrollContainer(this.gg);
        Function<PublicProfileReview, PublicProfileReviewComponent> reviewFactory = this::createReviewComponent;
        this.reviewsList.setPlaceholderSupplier(() -> PublicProfileListingDetailsPanel.createEmptyReviewComponent(reviewFactory));
        this.reviewsList.setPageLoader(() -> this.loadReviewsPage(reviewFactory));
        this.reviewsList.setPageMetadata(this.publicProfile.getReviews());
        this.gg.h(this.reviewsList, new Object[0]);
        this.rebuildReviews();
    }

    private void handleDownloadComplete(ApiResponse apiResponse, Throwable throwable) {
        if (throwable != null) {
            Vape.logThrowable(throwable);
            PublicProfileManager.showWarning("Failed to download profile.");
            return;
        }
        if (!apiResponse.isSuccessful()) {
            Vape.debugLog("Failed to download public profile: " + apiResponse.getError());
            PublicProfileManager.showWarning("Failed to view public profile: " + apiResponse.getError());
            return;
        }
        RemoteProfileData remoteProfileData = (RemoteProfileData)apiResponse.getData();
        assert remoteProfileData != null;
        JsonObject jsonObject = this.sanitizeRemoteProfileData(remoteProfileData);
        Profile profile = new Profile(remoteProfileData.getName(), remoteProfileData.getVapeVersion());
        profile.loadJson(jsonObject);
        profile.setPublishedData(jsonObject);
        Vape.INSTANCE.getProfilesManager().addProfile(profile);
        this.setupFooter();
        PublicProfileManager.showInfo("Successfully downloaded " + remoteProfileData.getName());
    }

    private PublicProfileReviewComponent createReviewComponent(PublicProfileReview review) {
        return new PublicProfileReviewComponent(this.publicProfile, review, this.reviewsList.A(), PublicProfileReviewDisplayType.OTHER);
    }

    private static ApiResponse ignoreUpdateFailure(Throwable throwable) {
        return null;
    }

    private CompletableFuture updateDownloadedProfile(Profile profile) {
        return ApiServices.getInstance().getPublicProfileApi().downloadProfileUpdate(this.publicProfile.getProfileId()).whenCompleteAsync((response, error) -> this.handleProfileUpdate(profile, response, error), (Executor)ClientSettings.UI_EXECUTOR).exceptionally(PublicProfileListingDetailsPanel::ignoreUpdateFailure);
    }

    private void showLoadingState() {
        this.showLoadingPlaceholders();
        this.gg.setVisible(false);
        this.gb.setVisible(false);
        double d = this.getRightPanel().A();
        this.getClass();
        PanelComponent panelComponent = new PanelComponent(d - (double)(5.0f * 2.0f), this.getRightPanel().L());
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.getRightPanel().h(panelComponent, new Object[0]);
        panelComponent.h(new SkeletonPlaceholderComponent(30.0, 10.0), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 2.0), new Object[0]);
        double d2 = panelComponent.A();
        this.getClass();
        panelComponent.h(new SkeletonPlaceholderComponent(d2 - 5.0, 20.0), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 2.0), new Object[0]);
        panelComponent.h(new SkeletonPlaceholderComponent(panelComponent.A() / 2.0, 10.0), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        double d3 = panelComponent.A();
        this.getClass();
        panelComponent.h(new SkeletonPlaceholderComponent(d3 - 5.0, 30.0), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        panelComponent.h(new SkeletonPlaceholderComponent(50.0, 10.0), "widthwrap");
        panelComponent.h(new SpacerComponent(panelComponent.A() - 100.0, 2.0), "widthwrap");
        panelComponent.h(new SkeletonPlaceholderComponent(45.0, 10.0), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 5.0), new Object[0]);
        double d4 = panelComponent.A();
        this.getClass();
        panelComponent.h(new SkeletonPlaceholderComponent(d4 - 5.0, 68.0), new Object[0]);
    }

    private void startPositiveReview() {
        this.chooseReview(true);
    }

    private void setupFooter() {
        Object object;
        this.s$src$V$1l7a8uk();
        this.gb.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.gb.h(new SpacerComponent(0.0, 8.0), "wrap");
        this.gb.h(new SpacerComponent(5.0, 0.0), new Object[0]);
        if (OnlineConnectionManager.INSTANCE.isCurrentUser(this.publicProfile.getOwner())) {
            this.gb.h(new SpacerComponent(45.0, 0.0), new Object[0]);
        } else {
            this.likeButton.setHoverColor(PublicProfileListingDetailsPanel.J.O);
            this.likeButton.setClickListener(this::startPositiveReview);
            this.dislikeButton.setHoverColor(PublicProfileListingDetailsPanel.J.c);
            this.dislikeButton.setClickListener(this::startNegativeReview);
            this.updateVoteButtonColors();
            object = new ActionButtonGroupComponent(this.likeButton, this.dislikeButton);
            ((GuiComponent)object).o(45.0);
            ((GuiComponent)object).Y(15.0);
            this.gb.h((GuiComponent)object, new Object[0]);
        }
        object = Vape.INSTANCE.getProfilesManager().getProfileByPublicProfileId(this.publicProfile.getProfileId());
        if (object == null) {
            this.gb.h(new SpacerComponent(15.0, 0.0), new Object[0]);
            TextButton textButton = new TextButton("Download", 0.8, PublicProfileListingDetailsPanel.J.B, PublicProfileListingDetailsPanel.J.O);
            textButton.setDeriveTextColorFromBackground(false);
            textButton.setNormalTextColor(Color.WHITE);
            textButton.o(144.0);
            textButton.Y(15.0);
            textButton.setSingleFutureClickListener(this::downloadProfile);
            this.gb.h(textButton, new Object[0]);
        } else {
            Profile downloadedProfile = (Profile)object;
            assert ((Profile)object).getRemoteMetadata() != null;
            if (this.publicProfile.getVersion() == ((Profile)object).getRemoteMetadata().getVersion()) {
                this.gb.h(new SpacerComponent(68.0, 0.0), new Object[0]);
                GlyphIconComponent glyphIconComponent = new GlyphIconComponent("info", 8.0, 8.0, 8.0, 8.0, PublicProfileListingDetailsPanel.J.W, PublicProfileListingDetailsPanel.J.W, null);
                this.gb.h(new PaddedComponent(4.0, 0.0, 0.0, 0.0, glyphIconComponent), new Object[0]);
                SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Downloaded and up to date", 0.8, PublicProfileListingDetailsPanel.J.Z, true);
                simpleTextLabelComponent.setOffsetX(3.0f);
                simpleTextLabelComponent.o(70.0);
                simpleTextLabelComponent.Y(17.0);
                this.gb.h(simpleTextLabelComponent, new Object[0]);
            } else {
                this.gb.h(new SpacerComponent(12.0, 0.0), new Object[0]);
                CenteredGlyphComponent centeredGlyphComponent = new CenteredGlyphComponent("info", 8.0f, 8.0f);
                centeredGlyphComponent.o(10.0);
                centeredGlyphComponent.Y(12.0);
                this.gb.h(centeredGlyphComponent, new Object[0]);
                SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("Previously downloaded", 0.8, PublicProfileListingDetailsPanel.J.Z, true);
                simpleTextLabelComponent.o(75.0);
                simpleTextLabelComponent.Y(17.0);
                this.gb.h(simpleTextLabelComponent, new Object[0]);
                TextButton textButton = new TextButton("Update", 0.8, PublicProfileListingDetailsPanel.J.B, PublicProfileListingDetailsPanel.J.O);
                textButton.o(60.0);
                textButton.Y(15.0);
                textButton.setDeriveTextColorFromBackground(false);
                textButton.setNormalTextColor(Color.WHITE);
                textButton.setSingleFutureClickListener(() -> this.updateDownloadedProfile(downloadedProfile));
                this.gb.h(textButton, new Object[0]);
            }
        }
    }

    private void updateVoteButtonColors() {
        if (this.publicProfile.getViewerReview() != null) {
            if (this.publicProfile.getViewerReview().isLiked()) {
                this.likeButton.setNormalColor(PublicProfileListingDetailsPanel.J.B);
                this.dislikeButton.setNormalColor(PublicProfileListingDetailsPanel.J.W);
            } else {
                this.dislikeButton.setNormalColor(PublicProfileListingDetailsPanel.J.d);
                this.likeButton.setNormalColor(PublicProfileListingDetailsPanel.J.W);
            }
        } else {
            this.likeButton.setNormalColor(PublicProfileListingDetailsPanel.J.W);
            this.dislikeButton.setNormalColor(PublicProfileListingDetailsPanel.J.W);
        }
    }

    private CompletableFuture loadReviewsPage(Function function) {
        return ApiServices.getInstance().getPublicProfileApi().getDelayedReviewPage(this.publicProfile, this.reviewsList.getNextPageIndex()).thenApplyAsync(response -> this.mapReviewsResponse(function, response), (Executor)ClientSettings.UI_EXECUTOR);
    }

    private void handleProfileUpdate(Profile profile, ApiResponse apiResponse, Throwable throwable) {
        if (throwable != null) {
            Vape.logThrowable(throwable);
            return;
        }
        if (!apiResponse.isSuccessful()) {
            Vape.debugLog("Failed to down profile update: " + apiResponse.getError());
            PublicProfileManager.showWarning("Failed to download profile update: " + apiResponse.getError());
            return;
        }
        RemoteProfileData remoteProfileData = (RemoteProfileData)apiResponse.getData();
        assert remoteProfileData != null;
        JsonObject jsonObject = this.sanitizeRemoteProfileData(remoteProfileData);
        profile.loadJson(jsonObject);
        this.setupFooter();
        PublicProfileManager.showInfo("Successfully updated " + remoteProfileData.getName());
    }

    private void copyShareCode() {
        ClipboardUtil.setText(this.publicProfile.getUppercaseShareCode());
        PublicProfileManager.showInfo("Copied share code to clipboard");
    }

    private List mapReviewsResponse(Function function, ApiResponse apiResponse) {
        if (!apiResponse.isSuccessful()) {
            return null;
        }
        assert apiResponse.getData() != null;
        PagedResult<PublicProfileReview> reviews = (PagedResult<PublicProfileReview>)apiResponse.getData();
        this.reviewsList.setPageMetadata(reviews);
        ArrayList arrayList = new ArrayList();
        for (PublicProfileReview publicProfileReview : reviews.getContent()) {
            arrayList.add(function.apply(publicProfileReview));
        }
        return arrayList;
    }

    private void startNegativeReview() {
        this.chooseReview(false);
    }

    private static GuiComponent createEmptyReviewComponent(Function function) {
        return (PublicProfileReviewComponent)function.apply(null);
    }

    private CompletableFuture downloadProfile() {
        return ApiServices.getInstance().getPublicProfileApi().downloadProfile(this.publicProfile.getProfileId()).whenCompleteAsync(this::handleDownloadComplete, (Executor)ClientSettings.UI_EXECUTOR).exceptionally(PublicProfileListingDetailsPanel::ignoreDownloadFailure);
    }

    private void refreshAfterReview(boolean positive) {
        this.rebuildReviews();
        this.chooseReview(positive);
    }


    public PublicProfileListingDetailsPanel(PublicProfilesFrame publicProfilesFrame, @Nullable PublicProfile publicProfile, @Nullable ProfileSnapshot profileSnapshot) {
        super(publicProfilesFrame, publicProfile, profileSnapshot);
        this.setDetailsCallback(this::showDetailsPanel);
        this.e();
    }

    @Override
    protected void customizeHeader(PanelComponent panelComponent) {
        if (this.publicProfile == null) {
            return;
        }
        panelComponent.h(new SpacerComponent(0.0, 2.0), new Object[0]);
        PanelComponent panelComponent2 = new PanelComponent(this.getLeftPanel().A(), 10.0);
        panelComponent2.setShowDisabledOverlay(false);
        panelComponent2.h(new SpacerComponent(5.0, 0.0), new Object[0]);
        PublicProfileUserAvatarComponent publicProfileUserAvatarComponent = new PublicProfileUserAvatarComponent(this.publicProfile.getOwner(), 10.0, 10.0);
        panelComponent2.h(publicProfileUserAvatarComponent, new Object[0]);
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("By " + (this.publicProfile.getOwner() != null ? this.publicProfile.getOwner().getUsername() : "Anonymous"));
        simpleTextLabelComponent.setBold(true);
        simpleTextLabelComponent.Y(10.0);
        panelComponent2.h(simpleTextLabelComponent, new Object[0]);
        panelComponent.h(panelComponent2, new Object[0]);
    }

    private void chooseReview(boolean positive) {
        if (OnlineConnectionManager.INSTANCE.isCurrentUser(this.publicProfile.getOwner())) {
            return;
        }
        if (this.publicProfile.getViewerReview() != null) {
            if (this.publicProfile.getViewerReview().isLiked() == positive) {
                ConfirmationDialogComponent.showStandard(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), "Are you sure you want to delete your review?", "Delete", "newtrash", this::deleteOwnReview);
            } else {
                ConfirmationDialogComponent.showStandard(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), "Are you sure you want to change your review?", "Confirm", "reset_circle", () -> this.changeOwnReview(positive));
            }
            return;
        }
        this.s$src$V$1l7a8uk();
        this.gb.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.gb.h(new SpacerComponent(0.0, 8.0), "wrap");
        this.gb.h(new SpacerComponent(5.0, 0.0), new Object[0]);
        PublicProfileReviewComposerComponent publicProfileReviewComposerComponent = new PublicProfileReviewComposerComponent(this.publicProfile, positive, this.publicProfile.getViewerReview() != null, this::setupFooter, this::rebuildReviews);
        publicProfileReviewComposerComponent.o(this.gb.A() - 5.0);
        publicProfileReviewComposerComponent.Y(this.gb.L() - 8.0);
        this.gb.h(publicProfileReviewComposerComponent, new Object[0]);
    }

    private JsonObject sanitizeRemoteProfileData(RemoteProfileData remoteProfileData) {
        JsonObject jsonObject = remoteProfileData.toJson();
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("data");
        if (jsonObject2 == null) {
            return jsonObject;
        }
        JsonObject jsonObject3 = jsonObject2.getAsJsonObject("enabled");
        if (jsonObject3 == null) {
            return jsonObject;
        }
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            if (mod.getCategory() != Category.OTHER) continue;
            jsonObject3.remove(mod.getName());
        }
        return jsonObject;
    }

    private void rebuildReviews() {
        this.reviewsList.removeMarkedChildren();
        PanelComponent panelComponent = new PanelComponent(this.reviewsList.A(), 8.0);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        panelComponent.setShowDisabledOverlay(false);
        this.reviewsList.h(panelComponent, new Object[0]);
        panelComponent.h(new DualTextLabelRowComponent("Reviews", String.valueOf(this.publicProfile.getReviewCount()), 8.0, 0.8), new Object[0]);
        PanelComponent panelComponent2 = new PanelComponent(65.0, panelComponent.L());
        panelComponent2.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        panelComponent2.setShowDisabledOverlay(false);
        panelComponent.h(panelComponent2, "alignright");
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(this.publicProfile.getApprovalPercentage() + "% positive reviews", 0.8, PublicProfileListingDetailsPanel.J.B);
        panelComponent2.h(simpleTextLabelComponent, new Object[0]);
        this.reviewsList.h(new SpacerComponent(0.0, 5.0), "wrap");
        PublicProfileReview publicProfileReview = this.publicProfile.getViewerReview();
        if (publicProfileReview != null) {
            PublicProfileReviewComponent publicProfileReviewComponent = new PublicProfileReviewComponent(this.publicProfile, publicProfileReview, this.reviewsList.A(), PublicProfileReviewDisplayType.SELF).setDeleteCallback(this::rebuildReviews);
            this.reviewsList.h(publicProfileReviewComponent, new Object[0]);
            this.reviewsList.h(new SpacerComponent(0.0, 2.0), new Object[0]);
        }
        for (PublicProfileReview publicProfileReview2 : this.publicProfile.getReviews().getContent()) {
            this.reviewsList.h(new PublicProfileReviewComponent(this.publicProfile, publicProfileReview2, this.reviewsList.A(), PublicProfileReviewDisplayType.OTHER), new Object[0]);
            this.reviewsList.h(new SpacerComponent(0.0, 2.0), new Object[0]);
        }
        this.reviewsList.setExplicitHeight(this.reviewsList.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().y());
        this.reviewsList.t(this.reviewsList.getExplicitHeight());
        this.updateVoteButtonColors();
    }
}
