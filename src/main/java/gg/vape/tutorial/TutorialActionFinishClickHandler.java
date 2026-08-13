package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.component.GuiClickListener;

class TutorialActionFinishClickHandler
implements GuiClickListener {
    final TutorialPage w;

    TutorialActionFinishClickHandler(TutorialPage tutorialPage) {
        this.w = tutorialPage;
    }

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getTutorialManager().completeCurrentPage();
    }
}
