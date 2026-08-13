package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MScorePlayerTeam;

public class ScorePlayerTeam
extends Team {
    public static TextComponent formatNameForTeamText(Team team, ITextComponent playerName) {
        if (ForgeVersion.MC_1_16_5.v()) {
            throw new UnsupportedOperationException("This method is only for versions 1.16.5 and above");
        }
        return new TextComponent(MScorePlayerTeam.formatNameForTeam(ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam, team.getObject(), playerName.getObject()));
    }

    public static TextComponentBaseBridge formatNameForTeamMutable(Team team, ITextComponent playerName) {
        if (ForgeVersion.MC_1_20_6.v()) {
            throw new UnsupportedOperationException("This method is only for versions 1.16.5 and above");
        }
        return new TextComponentBaseBridge(MScorePlayerTeam.formatNameForTeam(ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam, team.getObject(), playerName.getObject()));
    }

    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
    }

    public String getPrefix() {
        if (ForgeVersion.MC_1_20_6.d()) {
            ITextComponent playerPrefix = new ITextComponent(ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam.getPlayerPrefix(this.getObject()));
            if (playerPrefix.isNull()) {
                return "";
            }
            return playerPrefix.getFormattedText();
        }
        return MScorePlayerTeam.getPrefix(ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam, this.getObject());
    }

    public ScorePlayerTeam(Object object) {
        super(object);
    }

    public TextFormatting getColor() {
        Object color = ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam.getColor(this.getObject());
        if (color == null) {
            return null;
        }
        return new TextFormatting(color);
    }

    public static String formatPlayerName(Team team, String playerName) {
        if (ForgeVersion.MC_1_16_5.d()) {
            TextComponent textComponent = new TextComponent(ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam.formatPlayerNameComponent(team.getObject(), ScorePlayerTeamTextComponent.B(playerName).getObject()));
            return textComponent.U();
        }
        return MScorePlayerTeam.formatPlayerName(ScorePlayerTeam.vapeInstance.getMappings().scorePlayerTeam, team.getObject(), playerName);
    }
}
