package gg.vape.wrapper.impl;

import com.google.common.collect.Lists;
import gg.vape.mapping.mappings.MScoreboard;
import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.Collection;

public class Scoreboard
extends Wrapper {
    public Collection<Score> getPlayerScores(ScoreObjective objective) {
        Collection scoreHandles = MScoreboard.getPlayerScores(Scoreboard.vapeInstance.getMappings().scoreboard, this.I, objective.getObject());
        ArrayList scores = Lists.newArrayList();
        for (Object scoreHandle : scoreHandles) {
            scores.add(new Score(scoreHandle));
        }
        return scores;
    }

    public Scoreboard(Object object) {
        super(object);
    }

    public ScorePlayerTeam getPlayersTeam(String playerName) {
        return new ScorePlayerTeam(MScoreboard.getPlayersTeam(Scoreboard.vapeInstance.getMappings().scoreboard, this.I, playerName));
    }
}
