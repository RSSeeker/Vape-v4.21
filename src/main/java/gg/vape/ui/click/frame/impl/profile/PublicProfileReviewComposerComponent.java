package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.FilledSpacerComponent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;
import gg.vape.ui.click.component.input.SmallTextInputComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileUserAvatarComponent;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PublicProfileReviewComposerComponent
extends GuiComponent {
    private final boolean editingExistingReview;
    private final PublicProfile publicProfile;
    private final SmallTextInputComponent feedbackInput;
    static final boolean ASSERTIONS_DISABLED = !PublicProfileReviewComposerComponent.class.desiredAssertionStatus();
    private final Runnable reviewUpdatedCallback;
    private FlowLayoutComponent actionLayout = new FlowLayoutComponent(100.0);
    private final Runnable closeCallback;
    private final GlyphIconComponent submitButton;
    private final PublicProfileUserAvatarComponent avatar;
    private final PanelComponent container;

    @Override
    public double C() {
        return this.container.L();
    }

    private static ApiResponse lambda$submit$5(Throwable throwable) {
        return null;
    }

    private void lambda$submit$4(boolean bl, boolean bl2, ApiResponse apiResponse, Throwable throwable) {
        if (throwable != null) {
            PublicProfileManager.showWarning("Failed to leave review.");
            Vape.logThrowable(throwable);
            return;
        }
        if (!apiResponse.isSuccessful()) {
            PublicProfileManager.showWarning("Failed to leave review: " + apiResponse.getError());
            return;
        }
        if (!ASSERTIONS_DISABLED && apiResponse.getData() == null) {
            throw new AssertionError();
        }
        if (this.editingExistingReview) {
            PublicProfileManager.showInfo("Review updated!");
        } else {
            PublicProfileManager.showInfo("Review posted!");
        }
        if (this.publicProfile.getViewerReview() == null) {
            if (bl) {
                this.publicProfile.setLikes(this.publicProfile.getLikes() + 1L);
            } else {
                this.publicProfile.setDislikes(this.publicProfile.getDislikes() + 1L);
            }
        }
        this.publicProfile.setViewerReview((PublicProfileReview)apiResponse.getData());
        this.reviewUpdatedCallback.run();
        if (bl2) {
            this.closeCallback.run();
        }
    }

    private CompletableFuture lambda$new$3(Runnable runnable, boolean bl) {
        runnable.run();
        String string = this.feedbackInput.getText().trim();
        if (string.isEmpty() && !bl) {
            PublicProfileManager.showWarning("You must provide feedback when leaving a negative review!");
            return null;
        }
        return this.Y(bl, string, true);
    }

    private void lambda$new$1(Color color, char c, int n) {
        if (this.feedbackInput.getText().isEmpty()) {
            this.submitButton.setNormalColor(PublicProfileReviewComposerComponent.J.W);
            this.submitButton.setInteractionDisabled(true);
        } else {
            this.submitButton.setNormalColor(color);
            this.submitButton.setInteractionDisabled(false);
        }
    }

    private void lambda$new$2(boolean bl) {
        if (bl) {
            this.feedbackInput.setPlaceholderText("Share additional feedback? (Optional)");
            this.feedbackInput.setPlaceholderColor(PublicProfileReviewComposerComponent.J.B);
        } else {
            this.feedbackInput.setPlaceholderText("Please provide feedback with your rating...");
            this.feedbackInput.setPlaceholderColor(PublicProfileReviewComposerComponent.J.I);
        }
    }

    public SmallTextInputComponent k() {
        return this.feedbackInput;
    }


    private void lambda$new$0() {
        CompletableFuture.runAsync(this.closeCallback, ClientSettings.UI_EXECUTOR);
    }

    public PublicProfileReviewComposerComponent(PublicProfile publicProfile, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        this.publicProfile = publicProfile;
        this.editingExistingReview = bl2;
        this.closeCallback = runnable;
        this.reviewUpdatedCallback = runnable2;
        this.container = new PanelComponent(20.0, 20.0);
        this.container.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.container.setShowDisabledOverlay(false);
        this.avatar = new PublicProfileUserAvatarComponent(Vape.INSTANCE.getAccountInfo().getUserId(), 15.0, 15.0);
        this.avatar.setInset(2.0f);
        Color color = bl ? PublicProfileReviewComposerComponent.J.B : PublicProfileReviewComposerComponent.J.d;
        Color color2 = bl ? PublicProfileReviewComposerComponent.J.O : PublicProfileReviewComposerComponent.J.c;
        this.submitButton = new GlyphIconComponent("submit@2x", 6.0, 6.0, 6.0, 12.0, color, color2, null);
        this.submitButton.setNormalColor(PublicProfileReviewComposerComponent.J.W);
        this.submitButton.setInteractionDisabled(true);
        this.submitButton.setCenterVertically(true);
        AnimatedCenteredTextLabelComponent animatedCenteredTextLabelComponent = new AnimatedCenteredTextLabelComponent(bl && !bl2 ? "No thanks" : "Cancel", PublicProfileReviewComposerComponent.J.l);
        animatedCenteredTextLabelComponent.o(35.0);
        animatedCenteredTextLabelComponent.Y(12.0);
        animatedCenteredTextLabelComponent.setFontScale(0.75);
        animatedCenteredTextLabelComponent.setClickListener(this::lambda$new$0);
        this.feedbackInput = new SmallTextInputComponent("");
        PublicProfileReview publicProfileReview = publicProfile.getViewerReview();
        if (publicProfileReview != null) {
            this.feedbackInput.setText(publicProfileReview.getMessage());
            this.feedbackInput.addKeyTypedListener((arg_0, arg_1) -> this.lambda$new$1(color, arg_0, arg_1));
            Runnable runnable3 = () -> this.lambda$new$2(bl);
            runnable3.run();
            this.submitButton.setSingleFutureClickListener(() -> this.lambda$new$3(runnable3, bl));
            this.actionLayout.h(this.submitButton, new Object[0]);
            this.actionLayout.h(new FilledSpacerComponent(12.0, 12.0, 1.0, 6.0, PublicProfileReviewComposerComponent.J.l), new Object[0]);
            this.actionLayout.h(animatedCenteredTextLabelComponent, new Object[0]);
            this.actionLayout.setShowDisabledOverlay(false);
            this.container.addChildren(this.avatar, this.feedbackInput, new PaddedComponent(2.0, 0.0, 0.0, 0.0, this.actionLayout));
            this.addChildren(this.container);
            if (bl && !bl2) {
                this.Y(true, publicProfileReview.getMessage(), false);
            }
            return;
        }
        this.feedbackInput.addKeyTypedListener((arg_0, arg_1) -> this.lambda$new$1(color, arg_0, arg_1));
        Runnable runnable4 = () -> this.lambda$new$2(bl);
        runnable4.run();
        this.submitButton.setSingleFutureClickListener(() -> this.lambda$new$3(runnable4, bl));
        this.actionLayout.h(this.submitButton, new Object[0]);
        this.actionLayout.h(new FilledSpacerComponent(12.0, 12.0, 1.0, 6.0, PublicProfileReviewComposerComponent.J.l), new Object[0]);
        this.actionLayout.h(animatedCenteredTextLabelComponent, new Object[0]);
        this.actionLayout.setShowDisabledOverlay(false);
        this.container.addChildren(this.avatar, this.feedbackInput, new PaddedComponent(2.0, 0.0, 0.0, 0.0, this.actionLayout));
        this.addChildren(this.container);
        if (bl && !bl2) {
            this.Y(true, "", false);
        }
    }

    @Override
    public void H() {
        this.container.K(this.G$src$D$1b2f02a());
        this.container.S(this.n());
        this.container.o(this.A());
        this.container.Y(this.L());
        this.feedbackInput.o(this.A() - (this.actionLayout.A() + 8.0) - this.avatar.A());
        this.container.l$src$V$1mibm4x();
    }

    private CompletableFuture<ApiResponse<PublicProfileReview>> Y(boolean bl, String string, boolean bl2) {
        return ApiServices.getInstance().getPublicProfileApi().createReview(this.publicProfile, bl, string).whenCompleteAsync((arg_0, arg_1) -> this.lambda$submit$4(bl, bl2, arg_0, arg_1), (Executor)ClientSettings.UI_EXECUTOR).exceptionally(PublicProfileReviewComposerComponent::lambda$submit$5);
    }

    public PublicProfileUserAvatarComponent g$src$Lgg_vape_ui_click_frame_impl_profile_PublicProfi$1wft5vq() {
        return this.avatar;
    }

    @Override
    public double x() {
        return this.container.A();
    }
}
