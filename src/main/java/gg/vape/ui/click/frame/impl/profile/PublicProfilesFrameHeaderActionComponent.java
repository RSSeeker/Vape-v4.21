package gg.vape.ui.click.frame.impl.profile;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class PublicProfilesFrameHeaderActionComponent
extends FrameHeaderComponent {
    @Nullable
    private String iconResource;
    private static final String CLOSE_ICON_RESOURCE = "newclose";
    @Nullable
    private GuiClickListener clickListener;
    private float iconScale;
    private String title;
    private SquareIconButtonComponent closeButton = new SquareIconButtonComponent(CLOSE_ICON_RESOURCE, 1.5);

    public PublicProfilesFrameHeaderActionComponent(Frame frame, String string, String string2) {
        this(frame, string, string2, 1.0);
    }

    public SquareIconButtonComponent O$src$Lgg_vape_ui_click_component_SquareIconButtonComp$z3cp96() {
        return this.closeButton;
    }

    public PublicProfilesFrameHeaderActionComponent Q(@Nullable GuiClickListener guiClickListener) {
        this.clickListener = guiClickListener;
        return this;
    }


    public String K$src$Ljava_lang_String_$bvh3j6() {
        return this.title;
    }

    public void j(String string) {
        this.title = string;
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = PublicProfilesFrameHeaderActionComponent.J.A;
        double d = smoothFontRenderer.d(this.title);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        if (this.iconResource != null) {
            double d3 = this.n() + this.L() / 2.0 - (double)(8.0f * this.iconScale / 2.0f);
            smoothFontRenderer.d(this.title, this.G$src$D$1b2f02a() + 10.0 + 8.0, d2, color);
            ImageRenderer.drawImage(color, (float)this.G$src$D$1b2f02a() + 5.0f, (float)d3, this.iconResource, 8.0f * this.iconScale, 8.0f * this.iconScale, false);
        } else {
            smoothFontRenderer.d(this.title, this.G$src$D$1b2f02a() + 5.0, d2, color);
        }
        this.closeButton.K(this.G$src$D$1b2f02a() + this.A() - 7.5 - 8.0);
        this.closeButton.S(this.n());
        this.closeButton.Y(this.L());
    }

    public PublicProfilesFrameHeaderActionComponent(Frame frame, @Nullable String string, String string2, double d) {
        super(frame);
        this.iconScale = (float)d;
        this.iconResource = string;
        this.title = string2;
        this.closeButton.addClickListener(() -> {
            if (this.clickListener != null) {
                this.clickListener.onPrimaryClick();
            }
            ClientSettings.setFrameVisibility(frame.getClass(), false);
        });
        this.addChildren(this.closeButton);
    }
}
