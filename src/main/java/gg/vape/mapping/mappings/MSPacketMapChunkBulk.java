package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MSPacketMapChunkBulk
extends Mapping {
    private static String mappingControlFlowToken;

    public MSPacketMapChunkBulk() {
        super(MappedClasses.uv);
    }

    public static String getMappingControlFlowToken() {
        return mappingControlFlowToken;
    }

    public static void setMappingControlFlowToken(String token) {
        mappingControlFlowToken = token;
    }

    static {
        if (MSPacketMapChunkBulk.getMappingControlFlowToken() == null) {
            MSPacketMapChunkBulk.setMappingControlFlowToken("ih9zKb");
        }
    }
}
