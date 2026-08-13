package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.PublicProfileSummary;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SkeletonPlaceholderComponent;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GuiRenderPrimitives;
import org.jetbrains.annotations.Nullable;

public class PublicProfileListingResultCardComponent
extends GuiComponent {
    private SkeletonPlaceholderComponent titlePlaceholder;
    private final ColorAnimation borderAnimation;
    private SimpleTextLabelComponent likesLabel;
    private SkeletonPlaceholderComponent metadataPlaceholder;
    private IconGlyphComponent likeIcon;
    @Nullable
    private PublicProfileSummary profileSummary;
    private SkeletonPlaceholderComponent authorPlaceholder;
    private final ColorAnimation fillAnimation;
    private WrappedTextComponent profileTitle;
    private SimpleTextLabelComponent authorLabel;
    private boolean ownedByCurrentUser;

    @Override
    public void H() {
        this.fillAnimation.u(this.w$src$Z$e457mb());
        this.borderAnimation.u(this.w$src$Z$e457mb());
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), PublicProfileListingResultCardComponent.J.m, 2.0f);
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.fillAnimation.getInterpolatedColor(), 2.0f);
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.borderAnimation.getInterpolatedColor(), 2.0f, 1.0f, 1.0f);
        if (this.profileSummary != null) {
            this.profileTitle.K(this.G$src$D$1b2f02a() + 8.0);
            this.profileTitle.S(this.n() + 8.0);
            this.authorLabel.K(this.G$src$D$1b2f02a() + 8.0);
            this.authorLabel.S(this.profileTitle.n() - 1.0 + this.profileTitle.C() + 4.0);
            double d = 10.0;
            double d2 = this.G$src$D$1b2f02a() + 8.0;
            double d3 = this.n() + this.L() - d - 8.0;
            GuiRenderPrimitives.B(d2, d3, this.likeIcon.A() + this.likesLabel.A() + 1.0, d, PublicProfileListingResultCardComponent.J.m.brighter(), (float)(d / 2.0) - 0.5f);
            if (this.ownedByCurrentUser) {
                double d4 = this.G$src$D$1b2f02a() + 40.0;
                GuiRenderPrimitives.B(d4, d3, 30.0, d, new MutableColor(PublicProfileListingResultCardComponent.J.q).withAlpha(80), (float)(d / 2.0) - 0.5f);
                this.getAlternateFontRenderer(0.7).W(this.profileSummary.getUppercaseShareCode(), (int)d4 + 15, (int)d3 + 3, PublicProfileListingResultCardComponent.J.q);
            }
            this.likeIcon.K(d2 + 5.0);
            this.likeIcon.S(d3 + 2.0);
            this.likeIcon.setColor(PublicProfileListingResultCardComponent.J.W);
            this.likesLabel.K(this.likeIcon.G$src$D$1b2f02a() + this.likeIcon.A() - 1.5);
            this.likesLabel.S(this.likeIcon.n() - 1.5);
            this.likesLabel.o(12.0 + this.likesLabel.getTextWidth());
        } else {
            double d = this.G$src$D$1b2f02a();
            this.getClass();
            this.authorPlaceholder.K(d + 5.0);
            double d5 = this.n();
            this.getClass();
            this.authorPlaceholder.S(d5 + 5.0);
            double d6 = this.G$src$D$1b2f02a();
            this.getClass();
            this.titlePlaceholder.K(d6 + 5.0);
            this.titlePlaceholder.S(this.authorPlaceholder.n() + this.authorPlaceholder.L() + 2.0);
            double d7 = this.G$src$D$1b2f02a();
            this.getClass();
            this.metadataPlaceholder.K(d7 + 5.0 * 1.5);
            this.metadataPlaceholder.S(this.n() + this.L() - this.metadataPlaceholder.L() - 8.0);
        }
    }

    @Nullable
    public PublicProfileSummary r$src$Lgg_vape_config_PublicProfileSummary_$1fdzurr() {
        return this.profileSummary;
    }


    public PublicProfileListingResultCardComponent(@Nullable PublicProfileSummary publicProfileSummary) {
        this.getClass();
        this.borderAnimation = new ColorAnimation(0.15, PublicProfileListingResultCardComponent.J.m, PublicProfileListingResultCardComponent.J.l);
        this.getClass();
        this.fillAnimation = new ColorAnimation(0.15, PublicProfileListingResultCardComponent.J.t, PublicProfileListingResultCardComponent.J.E);
        this.o(78.0);
        this.Y(72.0);
        if (publicProfileSummary != null) {
            this.profileSummary = publicProfileSummary;
            this.profileTitle = new WrappedTextComponent(publicProfileSummary.getName(), 0.85);
            this.profileTitle.setBold(true);
            this.profileTitle.setUseExplicitWidth(true);
            this.profileTitle.o(50.0);
            this.profileTitle.setWrapWidth(50.0);
            this.profileTitle.Y(10.0);
            this.profileTitle.setTextColor(PublicProfileListingResultCardComponent.J.A);
            this.profileTitle.setBold(true);
            this.authorLabel = new SimpleTextLabelComponent(publicProfileSummary.getOwner() != null ? publicProfileSummary.getOwner().getUsername() : "Anonymous", 0.75);
            this.authorLabel.setOffsetX(0.0f);
            this.authorLabel.setOffsetY(0.0f);
            this.likesLabel = new SimpleTextLabelComponent(Long.toString(this.profileSummary.getLikes()), 0.7);
            this.likesLabel.setBold(true);
            this.likeIcon = new IconGlyphComponent("like active@2x", 6.0f, 5.0f);
            this.setPropagateMouseEvents(true);
            if (publicProfileSummary.getUppercaseShareCode() != null && publicProfileSummary.getUppercaseShareCode().equalsIgnoreCase(ClientSettings.getFrame(PublicProfilesFrame.class).o$src$Ljava_lang_String_$ububnq())) {
                this.ownedByCurrentUser = true;
            }
            this.addChildren(this.profileTitle, this.authorLabel, this.likeIcon, this.likesLabel);
        } else {
            GuiComponent[] guiComponentArray = new GuiComponent[1];
            this.authorPlaceholder = new SkeletonPlaceholderComponent(60.0, 10.0);
            guiComponentArray[0] = this.authorPlaceholder;
            this.addChildren(guiComponentArray);
            GuiComponent[] guiComponentArray2 = new GuiComponent[1];
            this.titlePlaceholder = new SkeletonPlaceholderComponent(30.0, 10.0);
            guiComponentArray2[0] = this.titlePlaceholder;
            this.addChildren(guiComponentArray2);
            GuiComponent[] guiComponentArray3 = new GuiComponent[1];
            this.metadataPlaceholder = new SkeletonPlaceholderComponent(25.0, 10.0);
            guiComponentArray3[0] = this.metadataPlaceholder;
            this.addChildren(guiComponentArray3);
        }
    }
}

