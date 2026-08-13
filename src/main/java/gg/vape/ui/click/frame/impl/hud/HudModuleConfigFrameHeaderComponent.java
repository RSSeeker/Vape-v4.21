package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class HudModuleConfigFrameHeaderComponent
extends FrameHeaderComponent {
    private final IconButtonComponent favoriteButton = new IconButtonComponent("newstar");
    private final HudModuleConfigFrame configFrame;
    private final IconButtonComponent backButton = new IconButtonComponent("moduleback");

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }


    public IconButtonComponent getBackButton() {
        return this.backButton;
    }

    public HudModuleConfigFrameHeaderComponent(HudModuleConfigFrame hudModuleConfigFrame) {
        super(hudModuleConfigFrame);
        this.configFrame = hudModuleConfigFrame;
        this.favoriteButton.addClickListener(new HudModuleConfigFrameToggleSelectedModuleClickHandler(hudModuleConfigFrame));
        this.backButton.addClickListener(new HudModuleConfigFrameHeaderCloseClickHandler(this, hudModuleConfigFrame));
        this.addChildren(this.favoriteButton, this.backButton);
    }

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = HudModuleConfigFrameHeaderComponent.J.A;
        double d = smoothFontRenderer.d(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().getName());
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        smoothFontRenderer.d(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().getName(), this.G$src$D$1b2f02a() + 10.0 + 8.0, d2, color);
        this.backButton.K(this.G$src$D$1b2f02a() + 5.0 - 2.0);
        this.backButton.S(this.n());
        this.backButton.Y(this.L());
        HudModule selectedModule = this.configFrame.getSelectedModule();
        this.favoriteButton.setVisible(selectedModule != null);
        if (selectedModule != null) {
            this.favoriteButton.K(this.G$src$D$1b2f02a() + this.A() - 7.5 - 8.0);
            this.favoriteButton.S(this.n());
            this.favoriteButton.Y(this.L());
            this.favoriteButton.setOverrideColor(selectedModule.isFavorite() ? HudModuleConfigFrameHeaderComponent.J.I : null);
        }
    }
}

