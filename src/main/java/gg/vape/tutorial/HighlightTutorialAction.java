package gg.vape.tutorial;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.tutorial.QueuedTutorialMessage;
import gg.vape.tutorial.TutorialAction;
import gg.vape.tutorial.TutorialActionComponent;
import gg.vape.tutorial.TutorialFrame;
import gg.vape.tutorial.TutorialTooltipPlacement;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public abstract class HighlightTutorialAction
extends TutorialAction {
    private double horizontalOffset;
    private TutorialTooltipPlacement tooltipPlacement = TutorialTooltipPlacement.TOP;
    private boolean includeHiddenComponents;
    private int queuedMessageCount = 1;
    private boolean includeInactiveTargets;
    private double verticalOffset;
    private final HashMap<GuiComponent, GuiMouseListener> mouseListeners;
    private final Queue<QueuedTutorialMessage> queuedMessages = new ArrayDeque<QueuedTutorialMessage>();
    private final List<GuiComponent> highlightedComponents;


    public TutorialTooltipPlacement getTooltipPlacement() {
        return this.tooltipPlacement;
    }

    public void positionTooltip(Frame frame, double d, double d2, double d3, double d4, TutorialTooltipPlacement tutorialTooltipPlacement) {
        double d5;
        double d6;
        double d7;
        double d8;
        double d9;
        if (tutorialTooltipPlacement == TutorialTooltipPlacement.TOP || tutorialTooltipPlacement == TutorialTooltipPlacement.BOTTOM) {
            d9 = 8.0;
            d8 = 18.0;
            d7 = 0.0;
            d6 = 0.0;
            d5 = 0.0;
            double d10 = d + d3 / 2.0;
            if (tutorialTooltipPlacement == TutorialTooltipPlacement.TOP) {
                d7 = d2 - frame.L() - 2.0;
                d6 = -d9;
                d5 = frame.L();
            } else {
                d7 = d2 + d4 + 2.0;
                d6 = d9;
                d5 = 0.0;
            }
            frame.K(d10 - frame.A() / 2.0);
            frame.S(d7 + d6);
            GuiRenderPrimitives.U(d10 - d8 / 2.0, (d7 += d5) + d6, d10, d7, d10 + d8 / 2.0, d7 + d6, new Color(26, 25, 26));
        }
        if (tutorialTooltipPlacement == TutorialTooltipPlacement.LEFT || tutorialTooltipPlacement == TutorialTooltipPlacement.RIGHT) {
            d9 = 10.0;
            d8 = 8.0;
            d7 = 0.0;
            d6 = d2 + d4 / 2.0;
            d5 = 0.0;
            if (tutorialTooltipPlacement == TutorialTooltipPlacement.LEFT) {
                d7 = -frame.A() - d8 - 3.0;
                d5 = d - d8 - 4.0;
                GuiRenderPrimitives.U(d5 + d8, d6, d5, d6 - d9 / 2.0, d5, d6 + d9 / 2.0, new Color(26, 25, 26));
            } else {
                d7 = d3 + d8 + 3.0;
                d5 = d + d7;
                GuiRenderPrimitives.U(d5 - d8, d6, d5, d6 - d9 / 2.0, d5, d6 + d9 / 2.0, new Color(26, 25, 26));
            }
            frame.K(d + d7);
            frame.S(d6 - frame.L() / 2.0);
        }
    }

    @Override
    public void render() {
        TutorialFrame tutorialFrame = ClientSettings.getFrame(TutorialFrame.class);
        if (!this.isTargetReady()) {
            return;
        }
        RectData rectData = this.getHighlightBounds();
        if (this.horizontalOffset != 0.0) {
            rectData.A(this.horizontalOffset);
        }
        if (this.verticalOffset != 0.0) {
            rectData.U(this.verticalOffset);
        }
        GuiRenderPrimitives.t(rectData.o() - 2.0, rectData.W() - 2.0, rectData.e() + 3.0, rectData.R() + 4.0, ClientSettings.INSTANCE.getAccentColor().brighter().brighter());
        this.positionTooltip(tutorialFrame, rectData.o(), rectData.W(), rectData.e(), rectData.R(), this.getTooltipPlacement());
    }

    public boolean includesHiddenComponents() {
        return this.includeHiddenComponents;
    }

    @Override
    public void cleanup() {
        Vape.debugLog("completed stage");
        for (GuiComponent guiComponent : this.highlightedComponents) {
            Vape.debugLog("removed " + guiComponent + " " + this.mouseListeners.get(guiComponent));
            guiComponent.removeMouseListener(this.mouseListeners.get(guiComponent));
        }
        this.highlightedComponents.clear();
        this.mouseListeners.clear();
    }

    @Override
    public boolean canAdvance() {
        if (this.queuedMessages.isEmpty()) {
            return true;
        }
        QueuedTutorialMessage queuedTutorialMessage = this.queuedMessages.poll();
        this.getComponent().setTitleText(queuedTutorialMessage.title);
        this.getComponent().setDescriptionText(queuedTutorialMessage.message);
        this.getComponent().getActionButton().setLabelText("Ok (" + (this.queuedMessageCount - this.queuedMessages.size()) + "/" + this.queuedMessageCount + ")");
        return false;
    }

    public void registerMouseListener(GuiComponent guiComponent, GuiMouseListener guiMouseListener) {
        Vape.debugLog("adding listener " + guiComponent + " " + guiMouseListener);
        guiComponent.addMouseListener(guiMouseListener);
        this.mouseListeners.put(guiComponent, guiMouseListener);
    }

    public boolean includesInactiveTargets() {
        return this.includeInactiveTargets;
    }

    public HighlightTutorialAction setVerticalOffset(double verticalOffset) {
        this.verticalOffset = verticalOffset;
        return this;
    }

    @Override
    public boolean isTargetReady() {
        return false;
    }

    public HighlightTutorialAction setIncludeInactiveTargets(boolean includeInactiveTargets) {
        this.includeInactiveTargets = includeInactiveTargets;
        return this;
    }

    public HighlightTutorialAction setTooltipPlacement(TutorialTooltipPlacement tooltipPlacement) {
        this.tooltipPlacement = tooltipPlacement;
        return this;
    }

    public abstract RectData getHighlightBounds();

    public HighlightTutorialAction setHorizontalOffset(double horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
        return this;
    }

    public HighlightTutorialAction queueMessage(String title, String message) {
        this.queuedMessages.add(new QueuedTutorialMessage(title, message));
        this.getComponent().getActionButton().setLabelText("Ok (1/" + ++this.queuedMessageCount + ")");
        return this;
    }

    public HighlightTutorialAction(TutorialActionComponent tutorialActionComponent, boolean bl) {
        super(tutorialActionComponent);
        this.mouseListeners = new HashMap();
        this.highlightedComponents = new ArrayList<GuiComponent>();
        this.includeHiddenComponents = bl;
    }

    public List<GuiComponent> getHighlightedComponents() {
        return this.highlightedComponents;
    }
}

