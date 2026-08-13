package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.HudTutorialPage;
import gg.vape.ui.click.frame.impl.hud.HudModuleListEntry;

public class HudTutorialKeystrokesHudModuleRowSelector
extends TutorialTargetSelector<HudModuleListEntry> {
    private final HudTutorialPage tutorialPage;
    private static final String targetModuleName = "Keystrokes";

    public HudTutorialKeystrokesHudModuleRowSelector(HudTutorialPage hudTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = hudTutorialPage;
    }

    private boolean matchesTargetName(HudModuleListEntry hudModuleListEntry) {
        return hudModuleListEntry.getModule().getName().equals(targetModuleName);
    }

    @Override
    public boolean matches(HudModuleListEntry hudModuleListEntry) {
        return this.matchesTargetName(hudModuleListEntry);
    }
}
