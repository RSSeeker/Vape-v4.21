package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.SingleComponentHighlightTutorialAction;
import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class SingleComponentHighlightAdvanceClickListener
implements GuiMouseListener {
    final SingleComponentHighlightTutorialAction H;

    @Override
    public void I(Point point) {
    }


    public SingleComponentHighlightAdvanceClickListener(SingleComponentHighlightTutorialAction singleComponentHighlightTutorialAction) {
        this.H = singleComponentHighlightTutorialAction;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (mouseClickButton != MouseClickButton.LEFT_CLICK) {
            return;
        }
        TutorialPage tutorialPage = this.H.getPage();
        if (tutorialPage.getCurrentAction() != null && tutorialPage.getCurrentAction().equals(this.H)) {
            Vape.INSTANCE.getTutorialManager().getCurrentPage().advanceToNextAction();
        }
    }
}

