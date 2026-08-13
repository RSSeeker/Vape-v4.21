package gg.vape.tutorial;

import gg.vape.tutorial.TutorialPage;
import gg.vape.ui.click.component.GuiClickListener;

class TutorialActionNextClickHandler
implements GuiClickListener {
    final TutorialPage o;

    @Override
    public void onPrimaryClick() {
        this.o.advanceToNextAction();
    }

    TutorialActionNextClickHandler(TutorialPage bF) {
        this.o = bF;
    }
}
