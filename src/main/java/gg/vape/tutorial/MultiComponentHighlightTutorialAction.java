package gg.vape.tutorial;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.tutorial.ClassTutorialTargetSelector;
import gg.vape.tutorial.HighlightTutorialAction;
import gg.vape.tutorial.MultiComponentHighlightAdvanceClickListener;
import gg.vape.tutorial.TutorialActionComponent;
import gg.vape.tutorial.TutorialTargetSelector;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;

public class MultiComponentHighlightTutorialAction
extends HighlightTutorialAction {
    private static final String g = "start ";
    private final TutorialTargetSelector targetSelector;
    private final GuiComponent searchRoot;

    @Override
    public boolean isTargetReady() {
        return this.searchRoot.V$src$Z$1xhop3l();
    }

    @Override
    public RectData getHighlightBounds() {
        if (this.getHighlightedComponents().size() < 1) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        GuiComponent guiComponent = null;
        GuiComponent guiComponent2 = null;
        for (int i = 0; i < this.getHighlightedComponents().size(); ++i) {
            GuiComponent guiComponent3 = this.getHighlightedComponents().get(i);
            if (!guiComponent3.V$src$Z$1xhop3l() && !this.includesInactiveTargets()) continue;
            guiComponent3.c();
            if (guiComponent == null) {
                guiComponent = guiComponent3;
            }
            guiComponent2 = guiComponent3;
        }
        if (guiComponent == null) {
            return new RectData(0.0, 0.0, 0.0, 0.0);
        }
        return new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent2.n() + guiComponent2.L() - guiComponent.n());
    }

    @Override
    public void start() {
        Vape.debugLog(g + this);
        for (GuiComponent object : this.searchRoot.f()) {
            ArrayList<GuiComponent> arrayList = this.targetSelector.findTargets(object);
            if (arrayList == null) continue;
            this.getHighlightedComponents().addAll(arrayList);
        }
        if (this.includesHiddenComponents()) {
            this.getComponent().getActionButton().setVisible(false);
            MultiComponentHighlightAdvanceClickListener multiComponentHighlightAdvanceClickListener = new MultiComponentHighlightAdvanceClickListener(this);
            for (GuiComponent guiComponent : this.getHighlightedComponents()) {
                this.registerMouseListener(guiComponent, multiComponentHighlightAdvanceClickListener);
            }
        }
    }


    public MultiComponentHighlightTutorialAction(GuiComponent guiComponent, Class clazz, String string, String string2, boolean bl) {
        this(guiComponent, new ClassTutorialTargetSelector(clazz, clazz), string, string2, bl);
    }

    public MultiComponentHighlightTutorialAction(GuiComponent guiComponent, TutorialTargetSelector tutorialTargetSelector, String string, String string2, boolean bl) {
        super(new TutorialActionComponent(string, string2), bl);
        this.targetSelector = tutorialTargetSelector;
        this.searchRoot = guiComponent;
    }
}
