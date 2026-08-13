package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTeam;
import gg.vape.wrapper.Wrapper;

public class Team
extends Wrapper {
    public boolean isSameTeam(Team team) {
        return MTeam.isSameTeam(Team.vapeInstance.getMappingsMapperCompat().qW, this.I, team.getObject());
    }

    public Team(Object wrappedObject) {
        super(wrappedObject);
    }
}
