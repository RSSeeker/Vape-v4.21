package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MStringTextComponent;

public class ScorePlayerTeamTextComponent
extends MutableTextComponent {
    public ScorePlayerTeamTextComponent(Object object) {
        super(object);
    }


    public static ScorePlayerTeamTextComponent P(String string) {
        if (ForgeVersion.MC_1_20_6.v()) {
            Vape.notifyNativeStackTrace();
        }
        return new ScorePlayerTeamTextComponent(MStringTextComponent.s(ScorePlayerTeamTextComponent.vapeInstance.getMappings().Dh, string));
    }

    public String Y() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.v()) {
            Vape.notifyNativeStackTrace();
        }
        return ScorePlayerTeamTextComponent.vapeInstance.getMappings().Dh.W(this.getObject());
    }

    public static ScorePlayerTeamTextComponent B(String string) {
        return new ScorePlayerTeamTextComponent(ScorePlayerTeamTextComponent.vapeInstance.getMappings().Dh.i(string));
    }
}

