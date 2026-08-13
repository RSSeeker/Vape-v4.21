package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MAtomicReferenceArrayBridge
extends Mapping {
    private static final String CHUNKS_FIELD_NAME = "chunks";
    private final MappingField chunksField;

    public MAtomicReferenceArrayBridge() {
        super(MappedClasses.zd);
        this.chunksField = this.J(CHUNKS_FIELD_NAME, true, AtomicReferenceArray.class);
    }

    public Object getChunks(Object storage) {
        return this.chunksField.getObject(storage);
    }
}

