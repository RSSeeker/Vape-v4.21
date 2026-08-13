package gg.vape.tutorial.selector;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.tutorial.page.ModulesTutorialPage;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import java.util.ArrayList;
import java.util.Arrays;

public class ModulesTutorialModuleBindInputSelector
extends TutorialTargetSelector<ModuleComponent> {
    private static final String targetModuleName = "AutoClicker";
    private final ModulesTutorialPage tutorialPage;
    private ModuleComponent selectedModule;

    private boolean matchesModule(ModuleComponent moduleComponent) {
        if (moduleComponent.getModule().getName().equals(targetModuleName)) {
            this.selectedModule = moduleComponent;
            return true;
        }
        return false;
    }

    @Override
    public boolean matches(ModuleComponent moduleComponent) {
        return this.matchesModule(moduleComponent);
    }


    public ModulesTutorialModuleBindInputSelector(ModulesTutorialPage modulesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = modulesTutorialPage;
        this.selectedModule = null;
    }

    @Override
    public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
        if (this.getTargetType().isInstance(guiComponent)) {
            ArrayList<GuiComponent> arrayList = super.findTargets(guiComponent);
            if (arrayList != null && this.selectedModule != null && this.selectedModule.equals(guiComponent)) {
                return new ArrayList<GuiComponent>(Arrays.asList(this.selectedModule.getBindInput()));
            }
            return arrayList;
        }
        return null;
    }
}
