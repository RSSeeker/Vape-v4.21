package gg.vape.tutorial;

import gg.vape.tutorial.TutorialWelcomePanel;
import gg.vape.ui.click.component.gui.TextButton;
import java.awt.Color;

public class TutorialWelcomeStartButton
extends TextButton {
    final TutorialWelcomePanel UU;

    @Override
    public void o(double d) {
        super.o(d);
    }

    public TutorialWelcomeStartButton(TutorialWelcomePanel tutorialWelcomePanel, String string, Color color, Color color2) {
        super(string, color, color2);
        this.UU = tutorialWelcomePanel;
    }
}
