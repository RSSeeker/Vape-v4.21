package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.tutorial.TutorialAction;
import gg.vape.tutorial.TutorialActionFinishClickHandler;
import gg.vape.tutorial.TutorialActionNextClickHandler;
import gg.vape.tutorial.TutorialFrame;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;

public class TutorialPage {
    private final List<TutorialAction> actions = new ArrayList<TutorialAction>();
    private TutorialAction currentAction;
    private final String title;
    private TutorialFrame tutorialFrame;
    private static final String debugPrefix;
    private static GuiComponent[] obfuscationState;

    public TutorialAction getCurrentAction() {
        return this.currentAction;
    }

    public void addAction(TutorialAction tutorialAction) {
        this.actions.add(tutorialAction);
        tutorialAction.getComponent().getActionButton().setClickListener(new TutorialActionNextClickHandler(this));
        tutorialAction.getComponent().getSkipLabel().setClickListener(new TutorialActionFinishClickHandler(this));
        tutorialAction.setPage(this);
    }

    public TutorialPage(String title) {
        this.title = title;
    }

    public void resetAndStart() {
        this.currentAction = null;
        this.advanceToNextAction();
    }

    public String getTitle() {
        return this.title;
    }

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }


    static {
        TutorialPage.setObfuscationState(new GuiComponent[2]);
        debugPrefix = "3 ";
    }

    public void activateAction(TutorialAction tutorialAction) {
        if (this.currentAction != null) {
            this.currentAction.cleanup();
        }
        this.currentAction = tutorialAction;
        this.currentAction.start();
        if (this.tutorialFrame != null) {
            this.tutorialFrame.t$src$V$zbu1jn();
            this.tutorialFrame.h(tutorialAction.getComponent(), new Object[0]);
            this.tutorialFrame.l$src$V$1mibm4x();
        }
    }

    public static void setObfuscationState(GuiComponent[] guiComponentArray) {
        obfuscationState = guiComponentArray;
    }

    public void advanceToNextAction() {
        Vape.debugLog("1");
        if (this.currentAction != null) {
            if (!this.currentAction.canAdvance()) {
                return;
            }
            boolean bl = false;
            boolean bl2 = false;
            Vape.debugLog("2");
            for (TutorialAction tutorialAction : this.actions) {
                if (bl) {
                    this.activateAction(tutorialAction);
                    bl2 = true;
                    break;
                }
                if (!tutorialAction.equals(this.currentAction)) continue;
                bl = true;
            }
            Vape.debugLog(debugPrefix + bl + " " + bl2);
            if (!bl2) {
                Vape.INSTANCE.getTutorialManager().completeCurrentPage();
            }
        } else {
            this.activateAction(this.actions.get(0));
        }
    }

    public void setFrame(TutorialFrame tutorialFrame) {
        this.tutorialFrame = tutorialFrame;
    }
}

