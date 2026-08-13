package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.wrapper.impl.ForgeVersion;

public class MResourceKey
extends Mapping {
    private final MappingField locationField;

    public MResourceKey() {
        super(MappedClasses.qB);
        this.locationField = ((MappingFieldBuilder)this.fieldBuilder("location", MappedClasses.zC).setNameForVersion(ForgeVersion.MC_1_21_11.n(), "identifier")).buildField();
    }

    public static Object getLocation(MResourceKey mapping, Object resourceKey) {
        return mapping.readLocation(resourceKey);
    }

    private Object readLocation(Object resourceKey) {
        return this.locationField.getObject(resourceKey);
    }
}

