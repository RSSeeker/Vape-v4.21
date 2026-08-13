package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileReviewResponse;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.InsetFilledSpacerComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.input.SmallTextInputComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Nullable;

public class PublicProfileReviewResponsePanel
extends PanelComponent {
    private final PublicProfile publicProfile;

    private ApiResponse handleReportFailure(Throwable throwable) {
        this.closePopup();
        return null;
    }

    private CompletableFuture submitReport(PublicProfileReview publicProfileReview, SmallTextInputComponent reasonInput, PublicProfileReviewResponse publicProfileReviewResponse) {
        if (publicProfileReview != null) {
            return ApiServices.getInstance().getPublicProfileApi().reportReview(publicProfileReview.getCommentId(), reasonInput.getText()).whenCompleteAsync(this::handleReportComplete, (Executor)ClientSettings.UI_EXECUTOR).exceptionally(this::handleReportFailure);
        }
        if (publicProfileReviewResponse != null) {
            return ApiServices.getInstance().getPublicProfileApi().reportReviewResponse(publicProfileReviewResponse.getId(), reasonInput.getText()).whenCompleteAsync(this::handleReportComplete, (Executor)ClientSettings.UI_EXECUTOR).exceptionally(this::handleReportFailure);
        }
        return null;
    }

    private void closePopup() {
        Frame frame = this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
        if (!(frame instanceof PopupFrame)) {
            return;
        }
        PopupFrame popupFrame = (PopupFrame)frame;
        ClientSettings.removePopup(popupFrame);
    }

    private void handleReportComplete(ApiResponse apiResponse, Throwable throwable) {
        this.closePopup();
        if (throwable != null) {
            Vape.logThrowable(throwable);
            PublicProfileManager.showWarning("Failed to create report.");
            return;
        }
        if (!apiResponse.isSuccessful()) {
            Vape.debugLog("Failed to create report: " + apiResponse.getError());
            PublicProfileManager.showWarning("Failed to create report: " + apiResponse.getError());
            return;
        }
        PublicProfileManager.showInfo("Successfully created report.");
    }

    public PublicProfileReviewResponsePanel(PublicProfile publicProfile, PublicProfileReviewResponse publicProfileReviewResponse) {
        super(200.0, 100.0);
        this.publicProfile = publicProfile;
        this.setupReportForm(null, publicProfileReviewResponse);
    }

    private void scheduleClose() {
        ClientSettings.UI_EXECUTOR.execute(this::closePopup);
    }

    public PublicProfileReviewResponsePanel(PublicProfile publicProfile, PublicProfileReview publicProfileReview) {
        super(200.0, 100.0);
        this.publicProfile = publicProfile;
        this.setupReportForm(publicProfileReview, null);
    }

    private void setupReportForm(@Nullable PublicProfileReview publicProfileReview, @Nullable PublicProfileReviewResponse publicProfileReviewResponse) {
        if (publicProfileReview == null && publicProfileReviewResponse == null) {
            return;
        }
        this.setShowDisabledOverlay(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        String userName = publicProfileReview != null ? publicProfileReview.getCommenter().getUsername() : this.publicProfile.getOwner() != null ? this.publicProfile.getOwner().getUsername() : "Anonymous";
        String reportType = publicProfileReview != null ? "review" : "response";
        WrappingTextLabelComponent wrappingTextLabelComponent10 = new WrappingTextLabelComponent("Report " + userName + "'s " + reportType, 1.0);
        wrappingTextLabelComponent10.o(this.A());
        wrappingTextLabelComponent10.setTextColor(PublicProfileReviewResponsePanel.J.A);
        wrappingTextLabelComponent10.setBold(true);
        this.h(wrappingTextLabelComponent10, new Object[0]);
        this.h(new SpacerComponent(0.0, 20.0), new Object[0]);
        SmallTextInputComponent smallTextInputComponent = new SmallTextInputComponent("+  Type reason...");
        smallTextInputComponent.setRightInset(0.0f);
        smallTextInputComponent.setHorizontalInset(0.0);
        smallTextInputComponent.setLeftInset(0.0f);
        double d = this.A() / 2.0 - smallTextInputComponent.A() / 2.0;
        this.h(new SpacerComponent(d, 12.0), "widthwrap");
        this.h(smallTextInputComponent, new Object[0]);
        this.h(new SpacerComponent(d, 12.0), "widthwrap");
        this.h(new InsetFilledSpacerComponent(smallTextInputComponent.A(), 0.0, 0.5, 0.0, PublicProfileReviewResponsePanel.J.y), "widthwrap");
        PanelComponent panelComponent = new PanelComponent(this.A(), 12.0);
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.h(new SpacerComponent(0.0, 6.0), new Object[0]);
        this.h(panelComponent, new Object[0]);
        panelComponent.h(new SpacerComponent(100.0, 12.0), new Object[0]);
        TextLabel textLabel = new TextLabel("CANCEL", 0.7);
        textLabel.setTextColor(PublicProfileReviewResponsePanel.J.Z);
        textLabel.setUseAlternateFont(true);
        textLabel.o(30.0);
        textLabel.Y(12.0);
        textLabel.addClickListener(this::scheduleClose);
        panelComponent.h(textLabel, new Object[0]);
        TextButton textButton = new TextButton("REPORT", PublicProfileReviewResponsePanel.J.B, PublicProfileReviewResponsePanel.J.O);
        textButton.setFontScale(0.7);
        textButton.setDeriveTextColorFromBackground(false);
        textButton.setNormalTextColor(Color.WHITE);
        textButton.setUseAlternateFont(true);
        textButton.o(30.0);
        textButton.Y(12.0);
        textButton.setSingleFutureClickListener(() -> this.submitReport(publicProfileReview, smallTextInputComponent, publicProfileReviewResponse));
        panelComponent.h(textButton, new Object[0]);
    }
}
