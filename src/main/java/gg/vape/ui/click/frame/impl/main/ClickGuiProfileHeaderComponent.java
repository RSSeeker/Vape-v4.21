package gg.vape.ui.click.frame.impl.main;

import gg.vape.config.Profile;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileGlyphIconPanel;
import java.awt.Color;

public class ClickGuiProfileHeaderComponent
extends GuiComponent {
    private static final double COMPONENT_GAP = 4.0;
    private static final String ELLIPSIS = "...";
    private final ProfileGlyphIconPanel glyphPanel;
    private final TruncatedTextComponent title;
    private final BindableInputComponent bindInput;

    public ClickGuiProfileHeaderComponent(Profile profile, double d) {
        this.o(d);
        this.Y(22.0);
        this.setShowDisabledOverlay(false);
        String string = profile != null ? profile.getName() : "";
        this.title = new TruncatedTextComponent(string, ELLIPSIS, d, 0.875, Color.WHITE, true);
        this.bindInput = new BindableInputComponent(profile, ClickGuiProfileHeaderComponent.J.Z);
        this.bindInput.setActiveOverride(false);
        this.bindInput.Y(10.0);
        this.glyphPanel = new ProfileGlyphIconPanel(profile);
        this.glyphPanel.o(12.0);
        this.glyphPanel.Y(12.0);
        this.addChildren(this.title, this.bindInput, this.glyphPanel);
    }


    @Override
    public void H() {
        double d;
        super.onDisable();
        double d2 = this.G$src$D$1b2f02a();
        double d3 = this.n();
        double d4 = this.A();
        double d5 = d2 + d4;
        double d6 = this.bindInput.A();
        double d7 = d5 - d6;
        double d8 = d3 + (this.L() - this.bindInput.L()) / 2.0;
        this.bindInput.K(d7);
        this.bindInput.S(d8);
        d5 = d7;
        if (this.glyphPanel.V$src$Z$1xhop3l()) {
            d = d5 - this.glyphPanel.A();
            double d9 = d3 + (this.L() - this.glyphPanel.L()) / 2.0;
            this.glyphPanel.K(d);
            this.glyphPanel.S(d9 + 3.0);
            d5 = d - COMPONENT_GAP;
        }
        d = Math.max(0.0, d5 - d2 - COMPONENT_GAP);
        this.title.K(d2);
        this.title.S(d3);
        this.title.o(d);
        this.title.Y(this.L());
        this.title.setMaxWidth(d);
        super.H();
    }

    public void updateProfile(Profile profile) {
        this.title.setText(profile != null ? profile.getName() : "");
        this.bindInput.setBendable(profile);
        this.glyphPanel.setProfile(profile);
        this.glyphPanel.refreshVisibility();
    }

    public ProfileGlyphIconPanel getGlyphPanel() {
        return this.glyphPanel;
    }

    public BindableInputComponent getBindInput() {
        return this.bindInput;
    }
}

