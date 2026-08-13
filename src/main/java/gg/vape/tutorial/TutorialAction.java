package gg.vape.tutorial;

import gg.vape.tutorial.TutorialActionComponent;
import gg.vape.tutorial.TutorialPage;

public abstract class TutorialAction {
    private static String[] obfuscationState;
    private TutorialPage page;
    private final TutorialActionComponent component;

    public TutorialActionComponent getComponent() {
        return this.component;
    }

    public boolean canAdvance() {
        return true;
    }

    public void setPage(TutorialPage tutorialPage) {
        this.page = tutorialPage;
    }

    public abstract boolean isTargetReady();

    public void cleanup() {
    }

    public TutorialAction(TutorialActionComponent tutorialActionComponent) {
        this.component = tutorialActionComponent;
    }

    public void start() {
    }

    public void render() {
    }

    public TutorialPage getPage() {
        return this.page;
    }

    public static void setObfuscationState(String[] stringArray) {
        obfuscationState = stringArray;
    }

    public static String[] getObfuscationState() {
        return obfuscationState;
    }

    static {
        if (TutorialAction.getObfuscationState() == null) {
            TutorialAction.setObfuscationState(new String[2]);
        }
    }
}
