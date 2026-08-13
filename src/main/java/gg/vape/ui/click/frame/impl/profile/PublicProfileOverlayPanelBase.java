package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.InsetFilledSpacerComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SplitPanelComponent;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOverlayCloseButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;

public class PublicProfileOverlayPanelBase
extends SplitPanelComponent {
    @Nullable
    private PublicProfileOverlayCloseButton closeButton;
    protected final PublicProfilesFrame profilesFrame;
    @Nullable
    private PopupFrame popupFrame;
    private static int staticState;
    @Nullable
    private CompletableFuture<?> pendingRequest;
    protected PanelComponent headerPanel;
    protected PanelComponent contentPanel;
    protected boolean showHeader = true;
    /** Compatibility aliases retained for subclasses compiled against the recovered hierarchy. */
    @Deprecated protected PanelComponent gb;
    @Deprecated protected PanelComponent gg;
    @Deprecated protected boolean gZ = true;

    public void d$src$V$15t6q4y() {
        CompletableFuture<?> completableFuture = this.pendingRequest;
        if (completableFuture != null && !completableFuture.isCancelled() && !completableFuture.isCompletedExceptionally()) {
            completableFuture.cancel(true);
        }
    }

    @Nullable
    public PopupFrame E() {
        return this.popupFrame;
    }

    public void S(@Nullable PopupFrame popupFrame) {
        this.popupFrame = popupFrame;
    }

    public void T(@Nullable CompletableFuture<?> completableFuture) {
        this.pendingRequest = completableFuture;
    }

    public void n$src$V$s6msm2() {
        this.getLeftPanel().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.getLeftPanel().setShowDisabledOverlay(false);
        this.getLeftPanel().removeMarkedChildren();
    }

    public static void r(int n) {
        staticState = n;
    }

    public void s$src$V$1l7a8uk() {
        this.headerPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.headerPanel.setShowDisabledOverlay(false);
        this.headerPanel.removeMarkedChildren();
    }

    @Nullable
    public PublicProfileOverlayCloseButton s$src$Lgg_vape_ui_click_frame_impl_profile_PublicProfi$urrnyv() {
        return this.closeButton;
    }

    public static int S$src$I$rrsca4() {
        return staticState;
    }

    public void b$src$V$s019hq() {
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.contentPanel.removeMarkedChildren();
    }

    public PublicProfileOverlayPanelBase(PublicProfilesFrame publicProfilesFrame) {
        super(-1.0, -1.0, new PanelComponent(-1.0, -1.0), new PanelComponent(-1.0, -1.0));
        this.profilesFrame = publicProfilesFrame;
        this.e();
    }

    static {
        PublicProfileOverlayPanelBase.r(67);
    }

    public static int g$src$I$s2s85c() {
        int n = PublicProfileOverlayPanelBase.S$src$I$rrsca4();
        return 0;
    }

    public void K(@Nullable PublicProfileOverlayCloseButton publicProfileOverlayCloseButton) {
        if (this.closeButton != null) {
            this.closeButton.setExpanded(false);
        }
        this.closeButton = publicProfileOverlayCloseButton;
    }

    protected void e() {
        this.setShowDisabledOverlay(false);
        this.getLeftPanel().t$src$V$zbu1jn();
        this.getLeftPanel().setShowDisabledOverlay(false);
        this.getRightPanel().t$src$V$zbu1jn();
        this.getRightPanel().setShowDisabledOverlay(false);
        this.getClass();
        double d = 5.0f * 4.0f;
        this.o(this.profilesFrame.A() - d);
        double d2 = this.profilesFrame.L() - this.profilesFrame.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() - 2.0 - d;
        this.getClass();
        this.Y(d2 - 5.0);
        PanelComponent panelComponent = this.getRightPanel();
        this.getRightPanel().N(false);
        this.getRightPanel().l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.getRightPanel().t(this.getRightPanel().L());
        this.h(panelComponent, new Object[0]);
        this.headerPanel = new PanelComponent(panelComponent.A(), 30.0);
        this.gb = this.headerPanel;
        this.headerPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.headerPanel.setShowDisabledOverlay(false);
        this.contentPanel = this.showHeader ? new PanelComponent(panelComponent.A() - 1.0, panelComponent.L() - this.headerPanel.L()) : new PanelComponent(panelComponent.A() - 1.0, panelComponent.L());
        this.gg = this.contentPanel;
        this.contentPanel.t(this.contentPanel.L() + 2.0);
        this.contentPanel.setShowDisabledOverlay(false);
        this.contentPanel.setDisabledOverlayColor(Color.MAGENTA);
        panelComponent.h(this.contentPanel, new Object[0]);
        if (this.showHeader) {
            panelComponent.h(this.headerPanel, new Object[0]);
            InsetFilledSpacerComponent insetFilledSpacerComponent = new InsetFilledSpacerComponent(this.headerPanel.A(), 1.0, 1.0, 0.0, PublicProfileOverlayPanelBase.J.h);
            insetFilledSpacerComponent.setRemovable(false);
        }
    }

    @Nullable
    public CompletableFuture<?> R$src$Ljava_util_concurrent_CompletableFuture_$1ccqvok() {
        return this.pendingRequest;
    }

}

