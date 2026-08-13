package gg.vape.tutorial;

import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class TutorialTargetSelector<T extends GuiComponent> {
    private static boolean obfuscationState;
    private final Class<T> targetType;

    public static void setObfuscationState(boolean state) {
        obfuscationState = state;
    }

    public Class<T> getTargetType() {
        return this.targetType;
    }

    static {
        if (!TutorialTargetSelector.isObfuscationStateDisabled()) {
            TutorialTargetSelector.setObfuscationState(true);
        }
    }

    public abstract boolean matches(T component);

    public static boolean isObfuscationStateDisabled() {
        boolean state = TutorialTargetSelector.getObfuscationState();
        return !state;
    }

    public static boolean getObfuscationState() {
        return obfuscationState;
    }


    public TutorialTargetSelector(Class<T> clazz) {
        this.targetType = clazz;
    }

    public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
        if (this.getTargetType().isInstance(guiComponent) && this.matches(this.getTargetType().cast(guiComponent))) {
            return new ArrayList<GuiComponent>(Arrays.asList(guiComponent));
        }
        return null;
    }
}
