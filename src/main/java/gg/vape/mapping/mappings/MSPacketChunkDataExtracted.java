package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSPacketChunkDataExtracted
extends Mapping {
    private final MappingField dataSizeField;
    private final MappingField dataField;

    public MSPacketChunkDataExtracted() {
        super(MappedClasses.uU);
        this.dataField = this.J("data", true, byte[].class);
        this.dataSizeField = this.J("dataSize", true, Integer.TYPE);
    }

    public byte[] getData(Object extractedData) {
        return this.dataField.getByteArray(extractedData);
    }

    public int getDataSize(Object extractedData) {
        return this.dataSizeField.getInt(extractedData);
    }
}

