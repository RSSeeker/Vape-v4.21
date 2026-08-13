package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileReviewDisplayType;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.ConfirmationDialogComponent;
import gg.vape.ui.click.component.FilledSpacerComponent;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.MirroredSpacerComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SkeletonPlaceholderComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileDateFormatUtil;
import gg.vape.ui.click.frame.impl.profile.PublicProfileReviewReplyComposerComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileReviewResponsePanel;
import gg.vape.ui.click.frame.impl.profile.PublicProfileUserAvatarComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public class PublicProfileReviewComponent
extends GuiComponent {
    private PublicProfileReviewReplyComposerComponent replyComposer;
    private SimpleTextLabelComponent metadataSeparator;
    @Nullable
    private Runnable displayedCallback;
    @Nullable
    private final PublicProfileReview review;
    private final PublicProfile publicProfile;
    private PanelComponent contentPanel;
    private final PublicProfileReviewDisplayType displayType;
    @Nullable
    private Runnable layoutChangedCallback;
    @Nullable
    private Runnable deleteCallback;

    public PublicProfileReviewDisplayType getDisplayType() {
        return this.displayType;
    }

    private void deleteReview(PublicProfileReview publicProfileReview) {
        publicProfileReview.delete(this.publicProfile, this.deleteCallback);
    }

    public PublicProfileReviewComponent setLayoutChangedCallback(@Nullable Runnable runnable) {
        this.layoutChangedCallback = runnable;
        return this;
    }

    @Override
    public double x() {
        return 0.0;
    }

    public PublicProfileReviewComponent setDisplayedCallback(@Nullable Runnable runnable) {
        this.displayedCallback = runnable;
        return this;
    }

    @Override
    public double C() {
        if (this.review == null) {
            return 30.0;
        }
        if (this.review.getMessage().isEmpty()) {
            return 15.0;
        }
        return this.contentPanel.L() + (this.replyComposer != null ? this.replyComposer.L() : 0.0) + 2.0;
    }

    private void openResponsePanel() {
        Vape.debugLog("parentFrame = " + this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa());
        ClientSettings.getFrame(PublicProfilesFrame.class).y(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), new PublicProfileReviewResponsePanel(this.publicProfile, this.review));
    }

    private void confirmDeleteReview(PublicProfileReview publicProfileReview) {
        ConfirmationDialogComponent.showStandard(this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), "Are you sure you want to delete your review?", "Delete", "newtrash", () -> this.deleteReview(publicProfileReview));
    }

    private void createReplyComposer(Consumer consumer, AtomicReference atomicReference) {
        PanelComponent panelComponent = new PanelComponent(this.A(), 20.0);
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        panelComponent.setShowDisabledOverlay(false);
        this.getClass();
        panelComponent.h(new SpacerComponent(5.0f * 2.0f, 0.0), new Object[0]);
        this.replyComposer = new PublicProfileReviewReplyComposerComponent(this.publicProfile, this.review, () -> PublicProfileReviewComponent.acceptPanel(consumer, panelComponent), panelComponent.A() - 10.0, panelComponent.L());
        panelComponent.h(this.replyComposer, new Object[0]);
        atomicReference.set(panelComponent);
        this.addChildren(panelComponent);
        Runnable runnable = this.layoutChangedCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    public PublicProfileReview getReview() {
        return this.review;
    }

    public PublicProfileReviewComponent setDeleteCallback(@Nullable Runnable runnable) {
        this.deleteCallback = runnable;
        return this;
    }


    public PublicProfileReviewComponent(PublicProfile publicProfile, @Nullable PublicProfileReview publicProfileReview, double d, PublicProfileReviewDisplayType publicProfileReviewDisplayType) {
        GuiComponent guiComponent;
        this.publicProfile = publicProfile;
        this.review = publicProfileReview;
        this.displayType = publicProfileReviewDisplayType;
        this.o(d);
        if (this.review == null) {
            this.contentPanel = new PanelComponent(d, this.C());
            this.contentPanel.setShowDisabledOverlay(false);
            this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
            this.contentPanel.h(new SkeletonPlaceholderComponent(d / 2.0, 10.0), new Object[0]);
            this.contentPanel.h(new SpacerComponent(0.0, 2.0), new Object[0]);
            this.contentPanel.h(new SkeletonPlaceholderComponent(d, 12.0), new Object[0]);
            this.addChildren(this.contentPanel);
            return;
        }
        this.contentPanel = new PanelComponent(d, 10.0);
        this.contentPanel.setShowDisabledOverlay(false);
        this.contentPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.addChildren(this.contentPanel);
        PanelComponent panelComponent = new PanelComponent(this.contentPanel.A(), 10.0);
        panelComponent.setShowDisabledOverlay(false);
        this.contentPanel.h(panelComponent, new Object[0]);
        panelComponent.h(new SpacerComponent(2.0, 0.0), new Object[0]);
        PanelComponent panelComponent2 = new PanelComponent(panelComponent.A() / 2.0, panelComponent.L());
        panelComponent2.setShowDisabledOverlay(false);
        panelComponent.h(panelComponent2, new Object[0]);
        PublicProfileUserAvatarComponent publicProfileUserAvatarComponent = new PublicProfileUserAvatarComponent(this.review.getCommenter(), 8.0, 8.0);
        PaddedComponent paddedComponent = new PaddedComponent(0.5, 0.0, 0.0, 0.0, publicProfileUserAvatarComponent);
        panelComponent2.h(paddedComponent, new Object[0]);
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(this.review.getCommenter().getUsername(), 0.7);
        double d2 = simpleTextLabelComponent.getTextWidth();
        this.getClass();
        simpleTextLabelComponent.o(d2 + 5.0);
        simpleTextLabelComponent.setBold(true);
        simpleTextLabelComponent.Y(10.0);
        simpleTextLabelComponent.setTextColor(PublicProfileReviewComponent.J.A);
        panelComponent2.h(simpleTextLabelComponent, new Object[0]);
        this.metadataSeparator = new SimpleTextLabelComponent("   ", 0.7);
        this.metadataSeparator.setBold(true);
        this.metadataSeparator.Y(10.0);
        this.metadataSeparator.setTextColor(PublicProfileReviewComponent.J.Z);
        panelComponent2.h(this.metadataSeparator, new Object[0]);
        Date date = this.review.getUpdatedDate() != null ? this.review.getUpdatedDate() : this.review.getCreatedDate();
        SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent(PublicProfileDateFormatUtil.i(date), 0.7);
        simpleTextLabelComponent2.w(PublicProfileDateFormatUtil.T(date));
        double d3 = simpleTextLabelComponent2.getTextWidth();
        this.getClass();
        simpleTextLabelComponent2.o(d3 + (double)(5.0f * 2.0f));
        simpleTextLabelComponent2.setBold(true);
        simpleTextLabelComponent2.Y(10.0);
        simpleTextLabelComponent2.setTextColor(PublicProfileReviewComponent.J.h);
        panelComponent2.h(simpleTextLabelComponent2, new Object[0]);
        MutableColor mutableColor = this.review.isLiked() ? new MutableColor(PublicProfileReviewComponent.J.B).F(0.8f).withAlpha(120) : new MutableColor(PublicProfileReviewComponent.J.d).withAlpha(150);
        GlyphIconComponent glyphIconComponent = new GlyphIconComponent(this.review.isLiked() ? "like active@2x" : "dislike active@2x", 6.0, 5.0, 20.0, 20.0, mutableColor, mutableColor, null);
        glyphIconComponent.setOffsetY(this.review.isLiked() ? 2.0 : 3.0);
        panelComponent2.h(glyphIconComponent, new Object[0]);
        PanelComponent panelComponent3 = new PanelComponent(this.displayType == PublicProfileReviewDisplayType.SELF ? 20.0 : (this.displayType == PublicProfileReviewDisplayType.REPLY ? 20.0 : 5.0), panelComponent.L());
        panelComponent3.setShowDisabledOverlay(false);
        panelComponent3.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        panelComponent.h(panelComponent3, "alignright");
        if (this.displayType == PublicProfileReviewDisplayType.SELF) {
            guiComponent = new TextLabel("delete", 0.75, false, 20.0, panelComponent3.L());
            ((TextLabel)guiComponent).setUseAlternateFont(true);
            ((InteractiveComponent)guiComponent).setClickListener(() -> this.confirmDeleteReview(publicProfileReview));
            panelComponent3.h(guiComponent, new Object[0]);
        } else if (this.displayType == PublicProfileReviewDisplayType.REPLY) {
            guiComponent = new TextLabel("reply", 0.75, false, 15.0, panelComponent3.L());
            panelComponent3.setVisible(false);
            ((TextLabel)guiComponent).setTextColor(null);
            ((TextLabel)guiComponent).setUseAlternateFont(true);
            AtomicReference atomicReference = new AtomicReference();
            ((InteractiveComponent)guiComponent).setSingleFutureClickListener(() -> this.toggleReplyComposer(atomicReference));
            panelComponent3.h(guiComponent, new Object[0]);
        }
        if (this.displayType == PublicProfileReviewDisplayType.REPLY || this.displayType == PublicProfileReviewDisplayType.OTHER) {
            guiComponent = new IconButtonComponent("flag comment hover@2x", 0.5, PublicProfileReviewComponent.J.Z, PublicProfileReviewComponent.J.f, 5.0, 5.0);
            panelComponent3.setVisible(false);
            ((InteractiveComponent)guiComponent).addClickListener(this::openResponsePanel);
            panelComponent3.h(guiComponent, "widthwrap");
            this.addActivationListener(panelComponent3::setVisible);
        }
        guiComponent = new PanelComponent(this.contentPanel.A(), 20.0);
        ((FrameComponent)guiComponent).l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        guiComponent.setShowDisabledOverlay(false);
        this.contentPanel.h(guiComponent, "wrap");
        double d4 = 15.0;
        ((FrameComponent)guiComponent).h(new SpacerComponent(15.0, 0.0), new Object[0]);
        PanelComponent panelComponent4 = new PanelComponent(this.contentPanel.A() - 15.0, 10.0);
        panelComponent4.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        panelComponent4.setShowDisabledOverlay(false);
        ((FrameComponent)guiComponent).h(panelComponent4, new Object[0]);
        WrappedTextComponent wrappedTextComponent = new WrappedTextComponent(this.review.getMessage(), 0.8, PublicProfileReviewComponent.J.h, true);
        wrappedTextComponent.o(panelComponent4.A());
        wrappedTextComponent.setWrapWidth(panelComponent4.A());
        wrappedTextComponent.Y(7 * wrappedTextComponent.getWrappedLines().size());
        if (publicProfileReviewDisplayType == PublicProfileReviewDisplayType.REPLY && publicProfileReview.isUnread()) {
            wrappedTextComponent.setBold(true);
            wrappedTextComponent.setTextColor(PublicProfileReviewComponent.J.A);
        }
        panelComponent4.h(new SpacerComponent(5.0, 1.0), new Object[0]);
        panelComponent4.h(wrappedTextComponent, new Object[0]);
        panelComponent4.h(new SpacerComponent(5.0, 2.0), new Object[0]);
        if (this.review.getResponse() != null) {
            PanelComponent panelComponent5 = new PanelComponent(this.contentPanel.A() - 15.0, 15.0);
            panelComponent5.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
            panelComponent5.setShowDisabledOverlay(false);
            panelComponent4.h(panelComponent5, "wrap");
            panelComponent5.h(new MirroredSpacerComponent(panelComponent5, 1.0, new FilledSpacerComponent(1.0, panelComponent5.L(), PublicProfileReviewComponent.J.h)), new Object[0]);
            PanelComponent panelComponent6 = new PanelComponent(panelComponent5.A() - 1.0, 50.0);
            panelComponent6.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
            panelComponent6.setShowDisabledOverlay(false);
            panelComponent5.h(panelComponent6, new Object[0]);
            PanelComponent panelComponent7 = new PanelComponent(panelComponent6.A(), 6.0);
            panelComponent7.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
            panelComponent7.setShowDisabledOverlay(false);
            panelComponent6.h(panelComponent7, new Object[0]);
            SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("Response from owner", 0.8, PublicProfileReviewComponent.J.A, true);
            simpleTextLabelComponent3.o(simpleTextLabelComponent3.getTextWidth());
            simpleTextLabelComponent3.Y(8.0);
            panelComponent7.h(simpleTextLabelComponent3, new Object[0]);
            panelComponent7.h(new SpacerComponent(4.0, 0.0), new Object[0]);
            Date date2 = this.review.getResponse().getUpdatedDate() != null ? this.review.getResponse().getUpdatedDate() : this.review.getResponse().getCreatedDate();
            SimpleTextLabelComponent simpleTextLabelComponent4 = new SimpleTextLabelComponent(PublicProfileDateFormatUtil.i(date2), 0.8, PublicProfileReviewComponent.J.h, true);
            simpleTextLabelComponent4.w(PublicProfileDateFormatUtil.T(date2));
            simpleTextLabelComponent4.o(simpleTextLabelComponent4.getTextWidth());
            simpleTextLabelComponent4.Y(8.0);
            panelComponent7.h(simpleTextLabelComponent4, new Object[0]);
            panelComponent6.h(new SpacerComponent(0.0, 2.0), new Object[0]);
            PanelComponent panelComponent8 = new PanelComponent(panelComponent6.A(), 8.0);
            panelComponent8.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
            panelComponent8.setShowDisabledOverlay(false);
            panelComponent6.h(panelComponent8, new Object[0]);
            panelComponent8.h(new SpacerComponent(5.0, 0.0), new Object[0]);
            WrappedTextComponent wrappedTextComponent2 = new WrappedTextComponent(this.review.getResponse().getResponse(), 0.8);
            wrappedTextComponent2.o(panelComponent6.A() - 8.0);
            wrappedTextComponent2.setWrapWidth(panelComponent6.A() - 8.0);
            wrappedTextComponent2.Y(7 * wrappedTextComponent2.getWrappedLines().size());
            panelComponent8.h(wrappedTextComponent2, new Object[0]);
            panelComponent8.setExplicitHeight(-1.0);
            panelComponent8.Y(wrappedTextComponent2.L());
            panelComponent6.setExplicitHeight(-1.0);
            panelComponent6.Y(panelComponent6.f().stream().mapToDouble(GuiComponent::L).sum());
            panelComponent5.setExplicitHeight(-1.0);
            panelComponent5.Y(panelComponent6.L());
            panelComponent4.h(new SpacerComponent(0.0, 2.0), new Object[0]);
        }
        panelComponent4.setExplicitHeight(-1.0);
        panelComponent4.Y(panelComponent4.f().stream().mapToDouble(GuiComponent::L).sum());
        guiComponent.setExplicitHeight(-1.0);
        guiComponent.Y(((FrameComponent)guiComponent).f().stream().mapToDouble(GuiComponent::L).sum());
        double d5 = this.contentPanel.f().stream().mapToDouble(GuiComponent::L).sum();
        this.contentPanel.setExplicitHeight(-1.0);
        this.contentPanel.Y(d5);
    }

    @Override
    public void H() {
        this.contentPanel.K(this.G$src$D$1b2f02a());
        this.contentPanel.S(this.n());
        this.contentPanel.o(this.A());
        if (this.review != null) {
            PublicProfileReviewReplyComposerComponent publicProfileReviewReplyComposerComponent = this.replyComposer;
            if (publicProfileReviewReplyComposerComponent != null) {
                publicProfileReviewReplyComposerComponent.getParentFrameComponent().K(this.G$src$D$1b2f02a());
                publicProfileReviewReplyComposerComponent.getParentFrameComponent().S(this.n() + this.contentPanel.L());
                publicProfileReviewReplyComposerComponent.getParentFrameComponent().P$src$V$i0cha4();
            }
            GuiRenderPrimitives.V(this.metadataSeparator.G$src$D$1b2f02a() + 4.0, this.metadataSeparator.n() + 4.0, 1.0, 1.0, PublicProfileReviewComponent.J.h);
        }
        this.contentPanel.l$src$V$1mibm4x();
    }

    private static void acceptComponent(Consumer consumer, GuiComponent guiComponent) {
        consumer.accept(guiComponent);
    }

    private CompletableFuture toggleReplyComposer(AtomicReference atomicReference) {
        GuiComponent guiComponent = (GuiComponent)atomicReference.get();
        Consumer<GuiComponent> consumer = component -> this.removeReplyComposer(atomicReference, component);
        if (guiComponent != null) {
            return CompletableFuture.runAsync(() -> PublicProfileReviewComponent.acceptComponent(consumer, guiComponent), ClientSettings.UI_EXECUTOR);
        }
        return CompletableFuture.runAsync(() -> this.createReplyComposer(consumer, atomicReference), ClientSettings.UI_EXECUTOR);
    }

    public PublicProfile getPublicProfile() {
        return this.publicProfile;
    }

    @Override
    public void F() {
        Runnable runnable = this.displayedCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    private void removeReplyComposer(AtomicReference atomicReference, GuiComponent guiComponent) {
        this.removeChild(guiComponent);
        this.replyComposer = null;
        Runnable runnable = this.layoutChangedCallback;
        if (runnable != null) {
            runnable.run();
        }
        atomicReference.set(null);
    }

    private static void acceptPanel(Consumer consumer, PanelComponent panelComponent) {
        consumer.accept(panelComponent);
    }
}

