package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MItemBlock
extends Mapping {
    private static final String STORAGE_FIELD_NAME = "storage";
    private final MappingField storageField;

    public Object getStorage(Object itemBlock) {
        return this.storageField.getObject(itemBlock);
    }

    public MItemBlock() {
        super(MappedClasses.I);
        this.storageField = this.J(STORAGE_FIELD_NAME, true, MappedClasses.zd);
    }
}

