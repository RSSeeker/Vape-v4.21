package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MScoreObjective;
import gg.vape.wrapper.Wrapper;

public class ScoreObjective
extends Wrapper {
    public TextComponent getFormattedDisplayName() {
        return new TextComponent(MScoreObjective.getFormattedDisplayName(ScoreObjective.vapeInstance.getMappingsMapperCompat().scoreObjective, this.I));
    }

    public ITextComponent getDisplayNameComponent() {
        ITextComponent displayName = new ITextComponent(MScoreObjective.getDisplayNameComponent(ScoreObjective.vapeInstance.getMappingsMapperCompat().scoreObjective, this.I));
        return displayName;
    }

    public Scoreboard getScoreboard() {
        return new Scoreboard(MScoreObjective.getScoreboard(ScoreObjective.vapeInstance.getMappingsMapperCompat().scoreObjective, this.I));
    }

    public String getDisplayNameText() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getDisplayNameComponent().getFormattedText();
        }
        return MScoreObjective.getDisplayNameText(ScoreObjective.vapeInstance.getMappingsMapperCompat().scoreObjective, this.I);
    }

    public ScoreObjective(Object object) {
        super(object);
    }

}

