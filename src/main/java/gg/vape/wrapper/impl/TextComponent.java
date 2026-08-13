package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MTextComponent;

public class TextComponent
extends ITextComponent {
    public static TextComponent p(Object object) {
        return new TextComponent(MTextComponent.n(TextComponent.vapeInstance.getMappingsMapperCompat().RA, object));
    }

    public TextComponent(Object object) {
        super(object);
    }

    public String U() {
        return (String)MTextComponent.s(TextComponent.vapeInstance.getMappingsMapperCompat().RA, this.I);
    }

    public Style w() {
        return new Style(MTextComponent.B(TextComponent.vapeInstance.getMappingsMapperCompat().RA, this.I));
    }

    public TextComponent(ScorePlayerTeam team, String playerName) {
        super(new TextComponent(Vape.INSTANCE.getMappingsMapperCompat().scorePlayerTeam.formatPlayerNameComponent(team.getObject(), ScorePlayerTeamTextComponent.B(playerName).getObject())).getObject());
    }
}
