package gg.vape.tutorial;

import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.ui.click.component.GuiComponent;

class ClassTutorialTargetSelector
extends TutorialTargetSelector<GuiComponent> {
    private final Class selectedClass;

    @Override
    public boolean matches(GuiComponent guiComponent) {
        return this.selectedClass.isInstance(guiComponent);
    }

    ClassTutorialTargetSelector(Class clazz, Class clazz2) {
        super(clazz);
        this.selectedClass = clazz2;
    }
}
