package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.render.hud.ScoreboardHudModule;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScoreObjective;

public class EventScoreboardObjectiveRender
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }


    public EventScoreboardObjectiveRender(Object objectiveOrMatrixStackHandle, Object objectiveOrRenderContextHandle) {
        ScoreboardHudModule scoreboardHudModule = Vape.INSTANCE.getModManager().getMod(ScoreboardHudModule.class);
        if (ForgeVersion.MC_1_16_5.d()) {
            scoreboardHudModule.updateObjective(new ScoreObjective(objectiveOrRenderContextHandle));
        } else {
            scoreboardHudModule.updateObjective(new ScoreObjective(objectiveOrMatrixStackHandle));
        }
    }

    public EventScoreboardObjectiveRender(Object objectiveHandle, int x, int y, Object scoreboardHandle) {
        ScoreboardHudModule scoreboardHudModule = Vape.INSTANCE.getModManager().getMod(ScoreboardHudModule.class);
        scoreboardHudModule.updateObjective(new ScoreObjective(objectiveHandle));
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        ScoreboardHudModule scoreboardHudModule = Vape.INSTANCE.getModManager().getMod(ScoreboardHudModule.class);
        this.setCancelled(scoreboardHudModule.boolean_r());
        return super.fire();
    }
}

