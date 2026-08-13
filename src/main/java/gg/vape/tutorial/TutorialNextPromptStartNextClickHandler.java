package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialNextPromptPanel;
import gg.vape.ui.click.component.GuiClickListener;

public class TutorialNextPromptStartNextClickHandler
implements GuiClickListener {
    final TutorialNextPromptPanel V;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().startNextPage();
    }

    public TutorialNextPromptStartNextClickHandler(TutorialNextPromptPanel tutorialNextPromptPanel) {
        this.V = tutorialNextPromptPanel;
    }
}
