package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSharedMonsterAttributesBridge
extends Mapping {
    private final MappingField normalField;
    private final MappingField seeThroughField;

    public MSharedMonsterAttributesBridge() {
        super(MappedClasses.Y8);
        this.normalField = this.registerStaticField("NORMAL", true, MappedClasses.Y8);
        this.seeThroughField = this.registerStaticField("SEE_THROUGH", true, MappedClasses.Y8);
    }

    public Object getNormal() {
        return this.normalField.getObject(null);
    }

    public Object getSeeThrough() {
        return this.seeThroughField.getObject(null);
    }
}
