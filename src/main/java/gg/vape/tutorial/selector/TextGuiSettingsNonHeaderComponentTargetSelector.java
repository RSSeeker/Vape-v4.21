package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.TextGuiTutorialPage;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;

public class TextGuiSettingsNonHeaderComponentTargetSelector
extends TutorialTargetSelector<GuiComponent> {
    private final TextGuiTutorialPage tutorialPage;

    public TextGuiSettingsNonHeaderComponentTargetSelector(TextGuiTutorialPage textGuiTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = textGuiTutorialPage;
    }

    @Override
    public boolean matches(GuiComponent guiComponent) {
        boolean bl = !(guiComponent instanceof SettingsFrameHeaderComponent);
        return bl;
    }

}

