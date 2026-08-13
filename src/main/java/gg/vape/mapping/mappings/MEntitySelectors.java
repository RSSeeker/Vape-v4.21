package gg.vape.mapping.mappings;

import com.google.common.base.Predicate;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntitySelectors
extends Mapping {
    private final MappingMethod collisionPredicateMethod;

    public Object getCollisionPredicate(Object entityHandle) {
        return this.collisionPredicateMethod.invokeObject(null, entityHandle);
    }

    public MEntitySelectors() {
        super(MappedClasses.qW);
        this.collisionPredicateMethod = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this
                .methodBuilder("getTeamCollisionPredicate", Predicate.class, MappedClasses.zc)
                .setTypeForVersion(ForgeVersion.MC_1_16_5.n(), java.util.function.Predicate.class))
                .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "pushableBy"))
                .setStaticMember(true))
                .buildMethod();
    }
}

