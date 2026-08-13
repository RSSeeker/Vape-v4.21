package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventScoreboardScores;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class ScoreboardScoresEventMappingTask
extends JavassistMappingTask {
    private static final String SCORES_RETURN_EXPRESSION = "$event.getScores();";

    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().scoreboard.listPlayerScoresMethod, EventScoreboardScores.class);
        eventInjectionSpec.setReturnExpression(SCORES_RETURN_EXPRESSION);
        this.registerEventInjection(eventInjectionSpec);
    }

    public ScoreboardScoresEventMappingTask() {
        super(MappedClasses.F6);
    }
}
