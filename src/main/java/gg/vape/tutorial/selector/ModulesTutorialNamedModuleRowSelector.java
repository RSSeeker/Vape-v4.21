package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ModulesTutorialPage;
import gg.vape.ui.click.component.module.ModuleComponent;

public class ModulesTutorialNamedModuleRowSelector
extends TutorialTargetSelector<ModuleComponent> {
    private final ModulesTutorialPage tutorialPage;
    private static final String targetModuleName = "AutoClicker";

    private boolean matchesModule(ModuleComponent moduleComponent) {
        return moduleComponent.getModule().getName().equals(targetModuleName);
    }

    @Override
    public boolean matches(ModuleComponent moduleComponent) {
        return this.matchesModule(moduleComponent);
    }

    public ModulesTutorialNamedModuleRowSelector(ModulesTutorialPage modulesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = modulesTutorialPage;
    }
}
