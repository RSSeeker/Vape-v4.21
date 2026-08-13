package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileReviewResponse;
import gg.vape.event.impl.PublicProfileReviewEvent;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.ActionButtonGroupComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.input.SmallTextInputComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileUserAvatarComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PublicProfileReviewReplyComposerComponent
extends GuiComponent {
    private final ActionButtonGroupComponent actionButtons;
    private final IconButtonComponent submitOrCloseButton;
    private final PanelComponent contentPanel;
    private final SmallTextInputComponent responseInput;
    private final Runnable closeCallback;
    private final PublicProfileUserAvatarComponent avatar;

    public PublicProfileReviewReplyComposerComponent(PublicProfile publicProfile, PublicProfileReview publicProfileReview, Runnable runnable, double d, double d2) {
        this.closeCallback = runnable;
        this.contentPanel = new PanelComponent(d, d2);
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.contentPanel.setShowDisabledOverlay(false);
        this.avatar = new PublicProfileUserAvatarComponent(Vape.INSTANCE.getAccountInfo().getUserId(), 10.0, 10.0);
        this.avatar.setInset(2.0f);
        this.submitOrCloseButton = new IconButtonComponent("newclose", 0.8);
        this.submitOrCloseButton.setNormalColor(PublicProfileReviewReplyComposerComponent.J.f);
        this.submitOrCloseButton.setHoverColor(Color.white);
        this.responseInput = new SmallTextInputComponent("Leave a response!");
        this.responseInput.setText(publicProfileReview.getResponse() != null ? publicProfileReview.getResponse().getResponse() : "");
        this.responseInput.o(d - 20.0);
        this.responseInput.addKeyTypedListener(this::handleInputChanged);
        this.submitOrCloseButton.setSingleFutureClickListener(() -> this.submitOrClose(publicProfileReview));
        this.actionButtons = new ActionButtonGroupComponent(this.submitOrCloseButton);
        this.actionButtons.setShowDisabledOverlay(false);
        this.actionButtons.setPadding(0.0);
        this.actionButtons.o(5.0);
        this.actionButtons.Y(15.0);
        this.contentPanel.addChildren(new PaddedComponent(0.0, 2.0, this.avatar), this.responseInput, this.actionButtons);
        this.addChildren(this.contentPanel);
    }

    @Override
    public double C() {
        return this.contentPanel.L();
    }

    @Override
    public void H() {
        this.contentPanel.K(this.G$src$D$1b2f02a());
        this.contentPanel.S(this.n());
        this.contentPanel.o(this.A());
        this.contentPanel.Y(this.L());
        this.responseInput.o(this.A() - this.actionButtons.A() - this.avatar.A());
        this.contentPanel.l$src$V$1mibm4x();
        GuiRenderPrimitives.u(this.G$src$D$1b2f02a() + 18.0, this.n() + this.L() - 3.0, this.G$src$D$1b2f02a() + this.A() - 4.0, this.n() + this.L() - 3.0, 1.0f, PublicProfileReviewReplyComposerComponent.J.l);
    }

    private void handleResponseSaved(PublicProfileReview publicProfileReview, ApiResponse apiResponse, Throwable throwable) {
        if (throwable != null) {
            PublicProfileManager.showWarning("Failed to leave response.");
            Vape.logThrowable(throwable);
            return;
        }
        if (!apiResponse.isSuccessful()) {
            PublicProfileManager.showWarning("Failed to leave response: " + apiResponse.getError());
            return;
        }
        assert apiResponse.getData() != null;
        if (publicProfileReview.getResponse() != null) {
            PublicProfileManager.showInfo("Response updated!");
        } else {
            PublicProfileManager.showInfo("Response posted!");
        }
        publicProfileReview.setResponse((PublicProfileReviewResponse)apiResponse.getData());
        new PublicProfileReviewEvent(publicProfileReview).fire();
        this.closeCallback.run();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    private void handleInputChanged(char character, int keyCode) {
        if (this.responseInput.getText().isEmpty()) {
            this.submitOrCloseButton.setIconResource("newclose");
        } else {
            this.submitOrCloseButton.setIconResource("submit@2x");
        }
    }

    @Override
    public void F() {
    }


    @Override
    public double x() {
        return this.contentPanel.A();
    }

    @Override
    public void u() {
    }

    private static ApiResponse ignoreSaveFailure(Throwable throwable) {
        return null;
    }

    private CompletableFuture submitOrClose(PublicProfileReview publicProfileReview) {
        if (this.submitOrCloseButton.getIconResource().equalsIgnoreCase("newclose")) {
            return CompletableFuture.runAsync(this.closeCallback, ClientSettings.UI_EXECUTOR);
        }
        String responseText = this.responseInput.getText().trim();
        return ApiServices.getInstance().getPublicProfileApi().respondToReview(publicProfileReview, responseText).whenCompleteAsync((response, error) -> this.handleResponseSaved(publicProfileReview, response, error), (Executor)ClientSettings.UI_EXECUTOR).exceptionally(PublicProfileReviewReplyComposerComponent::ignoreSaveFailure);
    }

    @Override
    public void I() {
    }
}
