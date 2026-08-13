package gg.vape.tutorial;

import gg.vape.tutorial.TutorialNextPromptSkipAllClickHandler;
import gg.vape.tutorial.TutorialNextPromptStartNextClickHandler;
import gg.vape.tutorial.TutorialOverlayPanelBase;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.gui.AnimatedUnderlinedTextLabel;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class TutorialNextPromptPanel
extends TutorialOverlayPanelBase {
    @Override
    public double x() {
        return 120.0;
    }

    @Override
    public double C() {
        return 68.0;
    }

    @Override
    public void H() {
        this.setDisabledOverlayColor(new Color(26, 26, 26, 255));
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a() - 1.0, this.n() - 1.0, this.A() + 2.0, this.L() + 2.0 + 2.0, new Color(38, 38, 38, 255), 1.0f, 2.0f, 1.0f);
        super.H();
    }

    public TutorialNextPromptPanel(String string, String string2) {
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().t(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().I(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().U(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().u(false);
        this.h(new SimpleTextLabelComponent("Completed:\n" + string + " tutorial", 1.0, TutorialNextPromptPanel.J.Z, true), "offsetY 10, offsetX 10");
        AnimatedUnderlinedTextLabel animatedUnderlinedTextLabel = new AnimatedUnderlinedTextLabel("Next: " + string2, 0.9, TutorialNextPromptPanel.J.Z, TutorialNextPromptPanel.J.h);
        this.h(animatedUnderlinedTextLabel, "spanwidth, offsetX 15, offsetY 45");
        animatedUnderlinedTextLabel.setClickListener(new TutorialNextPromptStartNextClickHandler(this));
        SquareIconButtonComponent squareIconButtonComponent = new SquareIconButtonComponent("newclose", 1.3, TutorialNextPromptPanel.J.l, TutorialNextPromptPanel.J.l.brighter(), 7.0, 7.0);
        squareIconButtonComponent.addClickListener(new TutorialNextPromptSkipAllClickHandler(this));
        this.h(squareIconButtonComponent, "offsetX 107, offsetY 5");
    }

    @Override
    public void c() {
        super.c();
    }
}
