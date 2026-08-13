package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialFinishedPanel;
import gg.vape.tutorial.TutorialState;
import gg.vape.ui.click.component.GuiClickListener;

public class TutorialFinishedAcknowledgeClickHandler
implements GuiClickListener {
    final TutorialFinishedPanel Z;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().setState(TutorialState.FINISHED);
    }

    public TutorialFinishedAcknowledgeClickHandler(TutorialFinishedPanel tutorialFinishedPanel) {
        this.Z = tutorialFinishedPanel;
    }
}
