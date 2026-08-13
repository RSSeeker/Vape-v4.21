package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ModulesTutorialPage;
import gg.vape.ui.click.frame.ModuleCategoryNavigationButtonComponent;

public class ModulesTutorialCategoryButtonSelector
extends TutorialTargetSelector<ModuleCategoryNavigationButtonComponent> {
    private static final String targetCategoryName = "Combat";
    private final ModulesTutorialPage tutorialPage;

    private boolean matchesCategory(ModuleCategoryNavigationButtonComponent moduleCategoryNavigationButtonComponent) {
        return moduleCategoryNavigationButtonComponent.N$src$Ljava_lang_String_$wy122q().equals(targetCategoryName);
    }

    @Override
    public boolean matches(ModuleCategoryNavigationButtonComponent moduleCategoryNavigationButtonComponent) {
        return this.matchesCategory(moduleCategoryNavigationButtonComponent);
    }

    public ModulesTutorialCategoryButtonSelector(ModulesTutorialPage modulesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = modulesTutorialPage;
    }
}
